package com.startup.auth.controller;

import com.startup.auth.entity.Role;
import com.startup.auth.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("role")
    public ResponseEntity<?> createRole(Role role) {
        return roleService.createRole(role);
    }

    @GetMapping("roles")
    public ResponseEntity<?> getRoles() {
        return roleService.getRoles();
    }

    @GetMapping("role")
    public ResponseEntity<?> getRole(@RequestParam Long id) {
        return roleService.getRole(id);
    }
}
