package com.startup.auth.service;

import com.startup.auth.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {

    ResponseEntity<String> register(User user);

    User getUserByUsername(String username);

}
