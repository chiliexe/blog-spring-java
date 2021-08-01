package com.chiliexe.springBlog.repositories;

import com.chiliexe.springBlog.models.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    
}
