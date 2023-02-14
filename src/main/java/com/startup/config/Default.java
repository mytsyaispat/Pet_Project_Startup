package com.startup.config;

import com.startup.logic.entity.Roles;
import com.startup.auth.entity.Role;
import com.startup.auth.entity.User;
import com.startup.auth.service.RoleService;
import com.startup.auth.service.UserService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Component
public class Default {

    private final UserService userService;
    private final RoleService roleService;

    public Default(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createDefault() {
        createDefaultRolesIfNotCreated();
        createAdminIfNotCreated();
    }

    @Transactional
    private void createDefaultRolesIfNotCreated() {
        if (roleService.getRoleByName("ADMIN").isEmpty()) {
            roleService.createRole(new Role(Roles.ADMIN.name()));
            roleService.createRole(new Role(Roles.USER.name()));
        }
    }
    @Transactional
    private void createAdminIfNotCreated() {
        if (userService.findUserByUsername("admin").isEmpty()) {
            Optional<Role> roleOptional = roleService.getRoleByName(Roles.ADMIN.name());
            while (roleOptional.isEmpty()) {
                createDefaultRolesIfNotCreated();
            }
            userService.createUser(new User("admin", "admin", Set.of(roleOptional.get())));
        }
    }

}
