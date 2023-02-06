package com.startup.auth.service.impl;

import com.startup.logic.entity.Roles;
import com.startup.auth.entity.User;
import com.startup.auth.repository.UserRepository;
import com.startup.auth.service.RoleService;
import com.startup.auth.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserServiceImpl(@Lazy PasswordEncoder passwordEncoder, UserRepository userRepository, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public ResponseEntity<String> register(User user) {
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT, "This username is already in use!");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(roleService.findRole(Roles.USER.name()).get()));
        userRepository.save(user);
        return ResponseEntity.ok("User successfully registered!");
    }



    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException("This username is not found!");
        return optionalUser.get();
    }

    @Override
    public Optional<User> findAdmin() {
        return userRepository.findByUsername("admin");
    }
}
