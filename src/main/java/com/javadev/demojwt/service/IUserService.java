package com.javadev.demojwt.service;

import com.javadev.demojwt.model.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByUserName(String name); // check is Username exist in database
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);
    User Save(User user);
}
