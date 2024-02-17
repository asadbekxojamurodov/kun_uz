package com.example.repository;

import com.example.entity.CategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer>,
        PagingAndSortingRepository<CategoryEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update CategoryEntity set visible = false where id =:id")
    int deleteCategory(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("from CategoryEntity where visible = true order by orderNumber desc ")
    List<CategoryEntity> getAll();

}
