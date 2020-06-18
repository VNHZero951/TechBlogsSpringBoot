package com.codegym.services.impl;

import com.codegym.model.Post_Tag;
import com.codegym.repository.Post_TagRepository;
import com.codegym.services.Post_TagServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class Post_TagServiceImpl implements Post_TagServices {
    @Autowired
    private Post_TagRepository post_tagRepository;

    @Override
    public List<Post_Tag> findAll() {
        return post_tagRepository.findAll();
    }

    @Override
    public void save(Post_Tag object) {
        post_tagRepository.save(object);
    }

    @Override
    public Post_Tag findById(long id) {
        return post_tagRepository.findById(id).get();
    }

    @Override
    public void remove(long id) {
        post_tagRepository.deleteById(id);
    }

    @Override
    public Post_Tag findByIdByPost_tag(Long idP, Long idT) {
        return post_tagRepository.findByIdByPost_tag(idP,idT);
    }
}
