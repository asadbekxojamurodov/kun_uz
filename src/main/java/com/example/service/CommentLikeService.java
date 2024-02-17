package com.example.service;

import com.example.dto.comment.CommentLikeDTO;
import com.example.entity.comment.CommentEntity;
import com.example.entity.comment.CommentLikeEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.LikeStatus;
import com.example.repository.CommentLikeRepository;
import com.example.repository.CommentRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentLikeService {

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ProfileService profileService;

    private static final int increment = 1;
    private static final int decrement = -1;

    public CommentLikeDTO createLike(CommentLikeDTO dto) {
        return getCommentLikeDTO(dto, LikeStatus.DISLIKE, LikeStatus.LIKE);
    }

    public CommentLikeDTO createDislike(CommentLikeDTO dto) {
        return getCommentLikeDTO(dto, LikeStatus.LIKE, LikeStatus.DISLIKE);
    }

    public Boolean remove(Integer cId, Integer pId) {
        CommentEntity commentEntity = commentService.get(cId);

        Optional<CommentLikeEntity> optional = commentLikeRepository.findByCommentIdAndProfileId(cId, pId);

        if (optional.isPresent()) {
            CommentLikeEntity commentLikeEntity = optional.get();
            LikeStatus status = commentLikeEntity.getStatus();

            updateCount(commentEntity, status, decrement);
            commentLikeRepository.delete(commentLikeEntity);
            return true;
        }
        return false;
    }

    private void updateCount(CommentEntity entity, LikeStatus status, int delta) {
        if (status.equals(LikeStatus.LIKE)) {
            entity.setLikeCount(entity.getLikeCount() + delta);
        } else if (status.equals(LikeStatus.DISLIKE)) {
            entity.setDislikeCount(entity.getDislikeCount() + delta);
        }
        commentRepository.save(entity);
    }

    @NotNull
    private CommentLikeDTO getCommentLikeDTO(CommentLikeDTO dto, LikeStatus firstEmotion, LikeStatus secondEmotion) {

        CommentEntity commentEntity = commentService.get(dto.getCommentId());
        ProfileEntity profileEntity = profileService.get(dto.getProfileId());
        LikeStatus newStatus = dto.getStatus();

        Optional<CommentLikeEntity> optional = commentLikeRepository.findByCommentIdAndProfileId(dto.getCommentId(), dto.getProfileId());

        if (optional.isPresent()) {
            CommentLikeEntity commentLikeEntity = optional.get();
            LikeStatus oldStatus = commentLikeEntity.getStatus();

            if (oldStatus.equals(firstEmotion) && newStatus.equals(secondEmotion)) {
                updateCount(commentEntity, oldStatus, decrement);
                updateCount(commentEntity, newStatus, increment);
                commentLikeRepository.updateStatus(commentLikeEntity.getId(), newStatus);
            }
            dto.setId(commentLikeEntity.getId());
            dto.setCreatedDate(LocalDateTime.now());
        }

        CommentLikeEntity article = new CommentLikeEntity();
        if (optional.isEmpty()) {

            article.setCommentId(commentEntity.getId());
            article.setProfileId(profileEntity.getId());
            article.setCreatedDate(LocalDateTime.now());
            article.setStatus(newStatus);
            commentLikeRepository.save(article);
            updateCount(commentEntity, newStatus, increment);

            dto.setId(article.getId());
            dto.setCreatedDate(LocalDateTime.now());
        }
        return dto;
    }


}
