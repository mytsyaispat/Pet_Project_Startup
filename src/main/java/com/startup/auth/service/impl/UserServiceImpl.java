package com.startup.auth.service.impl;

import com.startup.Logic.entity.Roles;
import com.startup.auth.entity.User;
import com.startup.auth.repository.UserRepository;
import com.startup.auth.service.UserService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<String> register(User user) {
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT, "This username is already in use!");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Roles.USER.name());
        userRepository.save(user);
        return ResponseEntity.ok("User successfully registered!");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        if (!userRepository.findAll().iterator().hasNext()) {
            userRepository.save(new User("admin", passwordEncoder.encode("admin"), Roles.ADMIN.name()));
        }
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }
}
