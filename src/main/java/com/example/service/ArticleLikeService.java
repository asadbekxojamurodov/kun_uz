package com.example.service;

import com.example.dto.article.ArticleLikeDTO;
import com.example.entity.article.ArticleEntity;
import com.example.entity.article.ArticleLikeEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.LikeStatus;
import com.example.exp.AppBadException;
import com.example.repository.ArticleLikeRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ArticleLikeService {

    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ProfileService profileService;

    public ArticleLikeDTO createLike(ArticleLikeDTO dto) {
        return changeStatus(dto, LikeStatus.DISLIKE, LikeStatus.LIKE);
    }

    public ArticleLikeDTO createDislike(ArticleLikeDTO dto) {
        return changeStatus(dto, LikeStatus.LIKE, LikeStatus.DISLIKE);
    }

    public Boolean remove(Integer pId, String aId) {
        ArticleLikeEntity articleLikeEntity = get(pId, aId);
        articleLikeRepository.delete(articleLikeEntity);
        return true;
    }

    @NotNull
    private ArticleLikeDTO changeStatus(ArticleLikeDTO dto, LikeStatus oldEmotion, LikeStatus newEmotion) {

        ArticleEntity articleEntity = articleService.get(dto.getArticleId());
        ProfileEntity profileEntity = profileService.get(dto.getProfileId());
        LikeStatus newStatus = dto.getStatus();

        Optional<ArticleLikeEntity> optional = articleLikeRepository.findByArticleIdAndProfileId(dto.getArticleId(), dto.getProfileId());

        if (optional.isPresent()) {

            ArticleLikeEntity articleLikeEntity = optional.get();
            LikeStatus oldStatus = articleLikeEntity.getStatus();

            if (oldStatus.equals(oldEmotion) && newStatus.equals(newEmotion)) {
                articleLikeRepository.updateStatus(articleLikeEntity.getId(), newStatus);
            }
            dto.setId(articleLikeEntity.getId());
            dto.setCreatedDate(LocalDateTime.now());

        }
        if (optional.isEmpty()) {
            ArticleLikeEntity article = new ArticleLikeEntity();
            article.setArticleId(articleEntity.getId());
            article.setProfileId(profileEntity.getId());
            article.setCreatedDate(LocalDateTime.now());
            article.setStatus(newStatus);
            articleLikeRepository.save(article);

            dto.setId(article.getId());
            dto.setCreatedDate(LocalDateTime.now());
        }
        return dto;
    }


    @NotNull
    private ArticleLikeEntity get(Integer pId, String aId) {
        Optional<ArticleLikeEntity> optional = articleLikeRepository.findByArticleIdAndProfileId(aId, pId);
        if (optional.isEmpty()) {
            throw new AppBadException("Article like not found");
        }
        return optional.get();
    }

}