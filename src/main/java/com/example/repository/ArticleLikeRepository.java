package com.example.repository;

import com.example.entity.article.ArticleLikeEntity;
import com.example.enums.LikeStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Integer> {

    Optional<ArticleLikeEntity> findByArticleIdAndProfileId(String articleId, Integer profileId);

    @Transactional
    @Modifying
    @Query("update ArticleLikeEntity set status = ?2 where id = ?1")
    void updateStatus(Integer id, LikeStatus newStatus);


}
