package com.startup.auth.service.impl;

import com.startup.auth.entity.Role;
import com.startup.auth.repository.RoleRepository;
import com.startup.auth.service.RoleService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"roles", "role"}, allEntries = true)
    public Role createRole(Role role) {
        Optional<Role> roleOptional = roleRepository.findByName(role.getName());
        if (roleOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This role has already been created!");
        return roleRepository.save(role);
    }

    @Override
    @Cacheable(value = "role")
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    @Cacheable(value = "roles")
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Cacheable(value = "role")
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }
}
