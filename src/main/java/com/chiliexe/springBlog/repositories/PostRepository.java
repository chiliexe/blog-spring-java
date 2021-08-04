package com.chiliexe.springBlog.repositories;

import java.util.List;
import java.util.Optional;

import com.chiliexe.springBlog.models.Category;
import com.chiliexe.springBlog.models.Post;
import com.chiliexe.springBlog.models.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>{

    Page<Post> findAllByPublishedTrue(Pageable pageable);
    Optional<Post> findBySlugAndPublishedTrue(String slug);
    List<Post> findByUserAndPublishedTrue(User user, Sort sort);
    Optional<Post> findByUserAndSlugAndPublishedTrue(User user, String slug);
    Optional<List<Post>> findByCategoryAndPublishedTrue(Category category, Sort sort);
}
