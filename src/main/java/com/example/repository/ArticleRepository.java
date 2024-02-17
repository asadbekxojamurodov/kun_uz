package com.example.repository;

import com.example.entity.article.ArticleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String>,
        PagingAndSortingRepository<ArticleEntity, String> {

    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible = false where id = :id")
    int deleteArticle(@Param("id") String id);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set status = 'PUBLISHED' where id = ?1 and visible = true")
    int updateStatus(String id);


    @Query("from ArticleEntity where id = :id and visible = true ")
    Optional<ArticleEntity> getById(@Param("id") String id);

    @Query("from ArticleEntity  order by viewCount desc limit 4")
    List<ArticleEntity> get4MostReadArticle();


    Page<ArticleEntity> findAllByRegionId(Pageable paging, Integer regionId);

    @Query("from ArticleEntity where categoryId = ?1 and visible = true order by createdDate desc limit ?2")
        //limitda xato beryabdi
    List<ArticleEntity> findLast5ArticleByCategoryId(Integer categoryId, int num);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set viewCount = viewCount + 1 where id = ?1 and visible = true")
    int increaseViewCount(String articleId);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set sharedCount = sharedCount + 1 where id = ?1 and visible = true")
    int increaseShareCount(String articleId);

    @Query("from ArticleEntity where id = ?1 and visible = true and status = 'PUBLISHED'")
    ArticleEntity checkVisibleAndPublish(String articleId);

    @Query("from ArticleEntity where id = ?1 and visible = true and status = 'PUBLISHED'")
    Optional<ArticleEntity> getByArticleId(String articleId);


}
