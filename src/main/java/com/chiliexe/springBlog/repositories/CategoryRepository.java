package com.chiliexe.springBlog.repositories;

import java.util.Optional;

import com.chiliexe.springBlog.models.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    Optional<Category> findByName(String name);
}
