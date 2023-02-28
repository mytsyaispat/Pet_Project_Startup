package com.startup.auth.service;

import com.startup.auth.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role createRole(Role role);

    Optional<Role> getRoleByName(String name);

    List<Role> getRoles();

    Optional<Role> getRoleById(Long id);

}
