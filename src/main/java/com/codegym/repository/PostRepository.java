package com.codegym.repository;

import com.codegym.model.Category;
import com.codegym.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    @Override
    @Modifying
    @Query("update Category p set p.isDelete=1 where p.id=:id")
    void deleteById(@Param("id") Long id);


    @Override
    @Modifying
    @Query("select p from Post p where p.isDelete=0")
    List<Post> findAll();

//    List<Post> findAllByDate(Pageable pageable);
}
