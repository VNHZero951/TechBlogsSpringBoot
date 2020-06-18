package com.codegym.services;

import com.codegym.model.Tag;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagServices extends BaseServices<Tag> {
    List<Tag> findTagByContentId(@Param("idPostContent") Long idPostContent);
}
