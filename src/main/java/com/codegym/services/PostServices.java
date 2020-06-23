package com.codegym.services;

import com.codegym.model.Post;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostServices extends BaseServices<Post> {
    Post findId();

    List<Post> findCategory(@Param("idC") Long idC);
}
