package com.startup.auth.controller;

import com.startup.auth.entity.User;
import com.startup.auth.service.UserService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user/")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        return userService.register(user);
    }

}
