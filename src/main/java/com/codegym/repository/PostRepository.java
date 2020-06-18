package com.codegym.repository;

import com.codegym.model.Category;
import com.codegym.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
//    @Override
//    @Modifying
//    @Query("update Post p set p.isDelete=1 where p.id=:id")
//    void deleteById(@Param("id") Long id);

    @Override
    @Modifying
    @Query("update Post p set p.isDelete=1 where p.id=:id")
    void deleteById(@Param("id") Long id);


    @Override
    @Modifying
    @Query("select p from Post p where p.isDelete=0")
    List<Post> findAll();

    @Query(value="SELECT * FROM posts order by posts.id desc limit 1;",nativeQuery=true)
    Post findId();


    @Override
    @Query(value="SELECT * FROM posts WHERE is_delete=0", nativeQuery = true)
    Page<Post> findAll(Pageable pageable);

//        ##########Category Controller###########
    @Query(value="SELECT * FROM posts INNER JOIN categories on posts.categories_id = categories.id where categories.is_delete = 0 AND categories_id=1 AND posts.is_delete = 0 ORDER BY date DESC", nativeQuery = true)
    Page<Post> findAllByCategoryAndIdBy1(PageRequest of);
    @Query(value="SELECT * FROM posts INNER JOIN categories on posts.categories_id = categories.id where categories.is_delete = 0 AND categories_id=2 AND posts.is_delete = 0 ORDER BY date DESC", nativeQuery = true)
    Page<Post> findAllByCategoryAndIdBy2(PageRequest of);
    @Query(value="SELECT * FROM posts INNER JOIN categories on posts.categories_id = categories.id where categories.is_delete = 0 AND categories_id=3 AND posts.is_delete = 0 ORDER BY date DESC", nativeQuery = true)
    Page<Post> findAllByCategoryAndIdBy3(PageRequest of);

//    @Query(value="SELECT * FROM posts WHERE is_delete=0", nativeQuery = true)
//    Post findAllByCategoryAndAndContent();
    //        ##########Category Controller###########

}
