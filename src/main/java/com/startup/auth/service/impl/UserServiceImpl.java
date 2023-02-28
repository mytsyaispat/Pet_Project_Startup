package com.startup.auth.service.impl;

import com.startup.auth.controller.dto.UserDTO;
import com.startup.auth.entity.Role;
import com.startup.auth.entity.User;
import com.startup.auth.repository.UserRepository;
import com.startup.auth.service.RoleService;
import com.startup.auth.service.UserService;
import com.startup.logic.entity.Roles;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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
    public User createUser(UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(userDTO.password);
        if (optionalUser.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This username is already in use!");
        User user = new User()
                .setUsername(userDTO.username)
                .setPassword(passwordEncoder.encode(userDTO.password));
        Optional<Role> roleOptional = roleService.getRoleByName(Roles.USER.name());
        if (roleOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role USER in not fount id DB!");
        user.addRole(roleOptional.get());
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = findUserByUsername(username);
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException("This user is not found!");
        return optionalUser.get();
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
