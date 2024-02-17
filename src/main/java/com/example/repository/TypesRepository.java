package com.example.repository;


import com.example.entity.article.TypesEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface TypesRepository extends CrudRepository<TypesEntity, Integer>,
        PagingAndSortingRepository<TypesEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update TypesEntity set visible = false where id =:id")
    int deleteArticle(@Param("id") Integer id);


}
