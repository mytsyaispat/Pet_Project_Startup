package com.startup.auth.service.impl;

import com.startup.auth.entity.Role;
import com.startup.auth.repository.RoleRepository;
import com.startup.auth.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
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

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok((Set<Role>) roleRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getRole(Long id) {
        return ResponseEntity.ok(roleRepository.findById(id));
    }
}
