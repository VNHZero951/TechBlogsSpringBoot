package com.codegym.repository;

import com.codegym.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Override
    @Modifying
    @Query("update Category p set p.isDelete=1 where p.id=:id")
    void deleteById(@Param("id") Long id);

    @Override
    @Modifying
    @Query("select c from Category c where c.isDelete=0")
    List<Category> findAll();
}
