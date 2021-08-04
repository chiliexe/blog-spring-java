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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PostService {

    public static final Integer itemsPerPage = 10;

    @Autowired
    private PostRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageUpload imageUpload;

    public Post findBySlug(String slug) {
        Post post = repository.findBySlugAndPublishedTrue(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        return post;
    }

    public List<Post> findByUser(User user) {
        return repository.findByUserAndPublishedTrue(user, Sort.by("id").descending());
    }

    public List<Post> findByCategory(String category) {
        var categoryObj = categoryRepository.findByName(category)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        List<Post> posts = repository.findByCategoryAndPublishedTrue(categoryObj, Sort.by("id").descending())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        return posts;
    }

    public Post findByUserAndSlug(User user, String slug) {
        return repository.findByUserAndSlugAndPublishedTrue(user, slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public Page<Post> findAll(Integer page) {
        Integer pageNumber = (page == null) ? 1 : page;
        var pageRequest = PageRequest.of(pageNumber - 1, itemsPerPage, Sort.by("id").descending());
        return repository.findAllByPublishedTrue(pageRequest);
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

    public Post update(Post post, MultipartFile file, User user, String slug) {
        Post editPost = repository.findBySlugAndPublishedTrue(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        
        editPost.setUser(user);
        editPost.setPublished(true);
        editPost.setTitle(post.getTitle());
        editPost.setSummary(post.getSummary());
        editPost.setContent(post.getContent());
        editPost.setUpdatedAt(LocalDate.now());
        editPost.setCategory(post.getCategory());
        editPost.setSlug(new Slugify().slugify(post.getTitle()));

        if (!file.getOriginalFilename().isEmpty())
            editPost.setImage(imageUpload.saveAndReturnPath(file));

        return repository.save(editPost);
    }

}
