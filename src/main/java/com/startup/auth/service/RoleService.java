package com.startup.auth.service;

import com.startup.auth.entity.Role;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface RoleService {

    ResponseEntity<?> createRole(Role role);

    Optional<Role> findRole(String role);

}
