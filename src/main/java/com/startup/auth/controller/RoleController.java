package com.startup.auth.controller;

import com.startup.auth.entity.Role;
import com.startup.auth.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("admin/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<?> createRole(Role role) {
        roleService.createRole(role);
        return new ResponseEntity<>("Role successfully created!", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roleSet = roleService.getRoles();
        if (roleSet.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(roleSet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        Optional<Role> roleOptional = roleService.getRoleById(id);
        if (roleOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!");
        return ResponseEntity.ok(roleOptional.get());
    }
}
