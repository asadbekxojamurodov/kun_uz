package com.example.repository;


import com.example.entity.comment.CommentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {


    Optional<CommentEntity> findByArticleId(String articleId);

    List<CommentEntity> getByArticleIdAndVisibleTrue(String articleId);

    @Transactional
    @Modifying
    @Query("update CommentEntity set visible = false where id = ?1")
    int deleteComment(Integer id);

    Page<CommentEntity> findAllBy(Pageable paging);

    List<CommentEntity> getById(Integer id);

}
