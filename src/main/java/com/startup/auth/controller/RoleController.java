package com.startup.auth.controller;

import com.startup.auth.entity.Role;
import com.startup.auth.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
