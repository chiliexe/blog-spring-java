package com.chiliexe.springBlog.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@Component
public class ImageUpload {
    
    public String saveAndReturnPath(MultipartFile file)
    {
        Path rootPath = Paths.get(System.getProperty("user.dir"), "uploads");
        Path filePath = checkIfFileExists(rootPath.resolve(file.getOriginalFilename()));


        try {
            if(!Files.exists(rootPath))
                Files.createDirectories(rootPath);
            
            byte[] bytes = file.getBytes();
            Files.write(filePath, bytes);

            return filePath.getFileName().toString();



        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private Path checkIfFileExists(Path fullPath)
    {
        String filepath = fullPath.getParent().toString();
        String filename = fullPath.getFileName().toString();
        StringBuilder sb = new StringBuilder();
    
        sb.append(filename);

        while( Files.exists(Paths.get(filepath, sb.toString())))
        {
            sb.insert(0, "x");
        }

        Path fullFilePath = Paths.get(filepath, sb.toString());

        return fullFilePath;
    }
}
