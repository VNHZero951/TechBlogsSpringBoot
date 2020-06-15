package com.codegym.repository;

import com.codegym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


public interface UserRepository  extends JpaRepository<User,Long> {
    User findByEmail(String email);
    User findByUsername(String username);
}
