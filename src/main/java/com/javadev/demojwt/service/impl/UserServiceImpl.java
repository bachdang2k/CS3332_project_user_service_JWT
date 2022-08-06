package com.javadev.demojwt.service.impl;

import com.javadev.demojwt.model.User;
import com.javadev.demojwt.repository.IUsersRepository;
import com.javadev.demojwt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUsersRepository usersRepository;

    @Override
    public Optional<User> findByUserName(String name) {
        return usersRepository.findByUserName(name);
    }

    @Override
    public Boolean existsByUserName(String userName) {
        return usersRepository.existsByUserName(userName);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    @Override
    public User Save(User user) {
        return usersRepository.save(user);
    }
}
