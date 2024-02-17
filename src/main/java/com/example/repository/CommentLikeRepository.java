package com.example.repository;


import com.example.entity.comment.CommentLikeEntity;
import com.example.enums.LikeStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity,Integer> {


    Optional<CommentLikeEntity> findByCommentIdAndProfileId(Integer commentId, Integer profileId);


    @Transactional
    @Modifying
    @Query(" update CommentLikeEntity set status = ?2 where id = ?1")
    void updateStatus(Integer id, LikeStatus newStatus);

}
