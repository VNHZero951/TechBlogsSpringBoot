package com.codegym.services;

import org.springframework.data.domain.Page;

import java.util.List;

public interface BaseServices<T> {
    List<T> findAll();
    void save(T object);
    T findById(long id);
    void remove(long id);
}
