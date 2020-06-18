package com.codegym.services;

import com.codegym.model.Post_Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Post_TagServices  extends BaseServices<Post_Tag> {
    Post_Tag findByIdByPost_tag(@Param("idP") Long idP, @Param("idT") Long idT);
}
