package com.startup.auth.service;

import com.startup.auth.entity.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface RoleService {

    ResponseEntity<?> createRole(Role role);

    Optional<Role> findRole(String role);
    Optional<Role> findById(Long id);

    ResponseEntity<?> getRoles();

    ResponseEntity<?> getRole(Long id);

}
