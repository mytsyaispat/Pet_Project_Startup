package com.startup.auth.service;

import com.startup.auth.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;


public interface UserService extends UserDetailsService {

    ResponseEntity<String> createUser(User user);

    Optional<User> findUserByUsername(String username);

}
