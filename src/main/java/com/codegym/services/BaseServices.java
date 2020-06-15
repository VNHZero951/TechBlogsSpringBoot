package com.codegym.services;

public interface BaseServices<T> {
    Iterable<T> findAll();
    void save(T object);
    T findById(long id);
    void remove(long id);
}
