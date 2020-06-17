package com.codegym.services.impl;

import com.codegym.model.User;
import com.codegym.repository.UserRepository;
import com.codegym.services.BaseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailImpl implements BaseServices<User> {

    @Autowired
    UserRepository userRepository;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User object) {
        userRepository.save(object);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void remove(long id) {
        userRepository.deleteById(id);
    }
}
