package com.chiliexe.springBlog.services;

import javax.validation.Valid;

import com.chiliexe.springBlog.models.Comment;
import com.chiliexe.springBlog.models.Post;
import com.chiliexe.springBlog.repositories.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentService {
    
    @Autowired
    private CommentRepository repository;

    public Comment save(@Valid Comment comment, Post post)
    {
        if(comment.getName().isEmpty())
            comment.setName("Anonymous");

        comment.setPost(post);
        comment.setPublished(true);
        return repository.save(comment);
    }
}
