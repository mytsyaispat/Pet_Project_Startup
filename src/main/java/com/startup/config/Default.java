package com.startup.config;

import com.startup.Logic.entity.Roles;
import com.startup.auth.entity.Role;
import com.startup.auth.entity.User;
import com.startup.auth.service.RoleService;
import com.startup.auth.service.UserService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        if (roleService.findRole("ADMIN").isEmpty()) {
            roleService.createRole(new Role(Roles.ADMIN.name()));
            roleService.createRole(new Role(Roles.USER.name()));
        }
    }
    @Transactional
    private void createAdminIfNotCreated() {
        if (userService.findAdmin().isEmpty()) {
            userService.register(new User("admin", "admin", List.of(new Role("ADMIN"))));
        }
    }

}
