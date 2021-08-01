package com.chiliexe.springBlog.repositories;

import java.util.Optional;

import com.chiliexe.springBlog.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
