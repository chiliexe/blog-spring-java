package com.chiliexe.springBlog.services;

import javax.validation.Valid;

import com.chiliexe.springBlog.models.Role;
import com.chiliexe.springBlog.models.User;
import com.chiliexe.springBlog.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    public User save(User user, String authority)
    {
        if(authority == null) authority = Role.USER;
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setAuthority(authority);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Email ou Senha Incorretos"));
    }

    public User update(@Valid User user) {
        var editUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        editUser.setName(user.getName());
        editUser.setLastname(user.getLastname());
        editUser.setEmail(user.getEmail());

        return userRepository.save(editUser);
    }
    
}
