package com.startup.auth.service;

import com.startup.auth.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;


public interface UserService extends UserDetailsService {

    ResponseEntity<String> register(User user);

    User getUserByUsername(String username);

    Optional<User> findAdmin();

}
