package com.startup.auth.service.impl;

import com.startup.auth.entity.Role;
import com.startup.auth.repository.RoleRepository;
import com.startup.auth.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<?> createRole(Role role) {
        Optional<Role> roleOptional = roleRepository.findByName(role.getName());
        if (roleOptional.isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT, "This role has already been created!");
        roleRepository.save(role);
        return ResponseEntity.ok("Role successfully created!");
    }

    @Override
    public Optional<Role> findRole(String role) {
        return roleRepository.findByName(role);
    }
}
