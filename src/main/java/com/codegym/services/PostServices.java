package com.codegym.services;

import com.codegym.model.Post;

public interface PostServices extends BaseServices<Post> {
    Post findId();
}
