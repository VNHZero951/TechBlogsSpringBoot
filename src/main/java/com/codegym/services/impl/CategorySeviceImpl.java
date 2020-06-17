package com.codegym.services.impl;

import com.codegym.model.Category;
import com.codegym.repository.CategoryRepository;
import com.codegym.services.BaseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategorySeviceImpl implements BaseServices<Category> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void save(Category object) {
            categoryRepository.save(object);
    }

    @Override
    public Category findById(long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public void remove(long id) {
        categoryRepository.deleteById(id);
    }
}
