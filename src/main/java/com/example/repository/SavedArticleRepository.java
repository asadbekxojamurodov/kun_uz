package com.example.repository;

import com.example.dto.article.ArticleDTO;
import com.example.entity.SavedArticleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SavedArticleRepository extends CrudRepository<SavedArticleEntity, Integer> {
    List<SavedArticleEntity> findByProfileId(Integer pid);

    @Transactional
    int deleteByArticleIdAndProfileId(String articleId,Integer profileId);

    Optional<SavedArticleEntity> findByArticleIdAndProfileId(String article, Integer profileId);
}
