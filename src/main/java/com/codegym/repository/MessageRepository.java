package com.codegym.repository;

import com.codegym.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long>{
    @Override
    @Modifying
    @Query("update Message m set m.isDelete=1 where m.id=:id")
    void deleteById(@Param("id") Long id);

    @Override
    @Query(value="SELECT * FROM messages where is_delete=0;",nativeQuery = true)
    List<Message> findAll();
}
