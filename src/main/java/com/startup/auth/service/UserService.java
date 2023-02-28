package com.startup.auth.service;

import com.startup.auth.controller.dto.UserDTO;
import com.startup.auth.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;


public interface UserService extends UserDetailsService {

    User createUser(UserDTO userDTO);

    Optional<User> findUserByUsername(String username);

}
