package com.codegym.services.impl;

import com.codegym.model.Tag;
import com.codegym.repository.TagRepository;
import com.codegym.services.BaseServices;
import com.codegym.services.TagServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagServices {

    @Autowired
    private TagRepository tagRepository;
    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public void save(Tag object) {
        tagRepository.save(object);
    }

    @Override
    public Tag findById(long id) {
        return tagRepository.findById(id).get();
    }

    @Override
    public void remove(long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<Tag> findTagByContentId(Long idPostContent) {
        return tagRepository.findTagByContentId(idPostContent);
    }
}
