package com.chiliexe.springBlog.services;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import com.chiliexe.springBlog.models.Post;
import com.chiliexe.springBlog.models.User;
import com.chiliexe.springBlog.repositories.CategoryRepository;
import com.chiliexe.springBlog.repositories.PostRepository;
import com.chiliexe.springBlog.utils.ImageUpload;
import com.github.slugify.Slugify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PostService {
    
    @Autowired
    private PostRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageUpload imageUpload;


    public Post findBySlug(String slug)
    {
        Post post = repository.findBySlug(slug)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        return post;
    }
    public List<Post> findByUser(User user)
    {
        return repository.findByUser(user);
    }
    public List<Post> findByCategory(String category)
    {
        var categoryObj = categoryRepository.findByName(category)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        List<Post> posts = repository.findByCategoryOrderByIdDesc(categoryObj)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        return posts;
    }

    public List<Post> findAll() {
        return repository.findAll(Sort.by("id").descending());
    }



    public Post save(@Valid Post post, MultipartFile file, User user) {

        post.setUser(user);
        post.setPublished(true);
		post.setCreatedAt(LocalDate.now());
		post.setUpdatedAt(LocalDate.now());
		post.setSlug(new Slugify().slugify(post.getTitle()));
        post.setImage(imageUpload.saveAndReturnPath(file));
        

        return repository.save(post);

    }

    
}
