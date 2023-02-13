package com.startup.auth.service;

import com.startup.auth.entity.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.Set;

public interface RoleService {

    ResponseEntity<String> createRole(Role role);

    Optional<Role> getRoleByName(String name);

    Set<Role> getRoles();

    Optional<Role> getRoleById(Long id);

}
