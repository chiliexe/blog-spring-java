package com.chiliexe.springBlog.repositories;

import java.util.List;
import java.util.Optional;

import com.chiliexe.springBlog.models.Category;
import com.chiliexe.springBlog.models.Post;
import com.chiliexe.springBlog.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>{
    Optional<Post> findBySlug(String slug);
    Optional<List<Post>> findByCategoryOrderByIdDesc(Category category);
    List<Post> findByUser(User user);
}
