package com.javadev.demojwt.service.impl;

import com.javadev.demojwt.model.Role;
import com.javadev.demojwt.model.RoleName;
import com.javadev.demojwt.repository.IRoleRepository;
import com.javadev.demojwt.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}
