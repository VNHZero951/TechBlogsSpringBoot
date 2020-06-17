package com.codegym.services.impl;

import com.codegym.model.Post;
import com.codegym.repository.PostRepository;
import com.codegym.services.BaseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostContentServiceImpl implements BaseServices<Post> {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public void save(Post object) {
        postRepository.save(object);
    }

    @Override
    public Post findById(long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public void remove(long id) {
        postRepository.deleteById(id);
    }
}
