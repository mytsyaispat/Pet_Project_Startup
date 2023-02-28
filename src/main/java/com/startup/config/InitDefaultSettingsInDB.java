package com.startup.config;

import com.startup.auth.controller.dto.UserDTO;
import com.startup.auth.entity.Role;
import com.startup.auth.service.RoleService;
import com.startup.auth.service.UserService;
import com.startup.logic.entity.Roles;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class InitDefaultSettingsInDB {

    private final UserService userService;
    private final RoleService roleService;

    public InitDefaultSettingsInDB(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createDefault() {
        createDefaultRolesIfNotCreated();
        createAdminIfNotCreated();
    }

    @Transactional
    void createDefaultRolesIfNotCreated() {
        if (roleService.getRoleByName("ADMIN").isEmpty()) {
            roleService.createRole(new Role(Roles.ADMIN.name()));
            roleService.createRole(new Role(Roles.USER.name()));
        }
    }

    @Transactional
    void createAdminIfNotCreated() {
        if (userService.findUserByUsername("admin").isEmpty()) {
            Optional<Role> roleOptional = roleService.getRoleByName(Roles.ADMIN.name());
            while (roleOptional.isEmpty()) {
                createDefaultRolesIfNotCreated();
            }
            userService.createUser(new UserDTO("admin", "admin"));
        }
    }

}
