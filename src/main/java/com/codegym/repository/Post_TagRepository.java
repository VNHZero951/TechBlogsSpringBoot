package com.codegym.repository;

import com.codegym.model.Post_Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Post_TagRepository extends JpaRepository<Post_Tag,Long> {
    @Query(value="SELECT post_tags.* FROM post_tags where post_tags.post_id= :idP and post_tags.tag_id= :idT", nativeQuery=true)
    Post_Tag findByIdByPost_tag(@Param("idP") Long idP, @Param("idT") Long idT);
}
