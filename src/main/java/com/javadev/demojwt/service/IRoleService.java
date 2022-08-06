package com.javadev.demojwt.service;

import com.javadev.demojwt.model.Role;
import com.javadev.demojwt.model.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName name);
}
