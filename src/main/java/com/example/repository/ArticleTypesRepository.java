package com.example.repository;


import com.example.entity.article.ArticleTypesEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ArticleTypesRepository extends CrudRepository<ArticleTypesEntity, Integer> {

    List<ArticleTypesEntity> getByTypesIdOrderByCreatedDateDesc(Integer id);

    List<ArticleTypesEntity> getByArticleIdNotIn(List<String> ArticleId);
    List<ArticleTypesEntity> getByTypesIdAndArticleIdNotIn(Integer typesId, Collection<String> articleId);



    @Transactional
    @Modifying
    @Query("update ArticleTypesEntity set visible = false where articleId = :id")
    void deleteArticle(@Param("id") String id);



}

