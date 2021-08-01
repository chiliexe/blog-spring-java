package com.chiliexe.springBlog.services;

import java.util.List;

import javax.validation.Valid;

import com.chiliexe.springBlog.models.Category;
import com.chiliexe.springBlog.repositories.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public void save(@Valid Category category) {

        repository.save(category);

    }

    public List<Category> findAll()
    {
        return repository.findAll();
    }

    public Category findById(Long id)
    {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }
}
