package com.javadev.demojwt.repository;

import com.javadev.demojwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String name); // check is Username exist in database
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);

    User save(User user);
}
