package com.startup.auth.controller;

import com.startup.auth.entity.Role;
import com.startup.auth.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@RestController
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("admin/role")
    public ResponseEntity<?> createRole(Role role) {
        return roleService.createRole(role);
    }

    @GetMapping("admin/roles")
    public ResponseEntity<Set<Role>> getRoles() {
        Set<Role> roleSet = roleService.getRoles();
        if (roleSet.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(roleSet);
    }

    @GetMapping("admin/role/{id}")
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        Optional<Role> roleOptional = roleService.getRoleById(id);
        if (roleOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!");
        return ResponseEntity.ok(roleOptional.get());
    }
}
