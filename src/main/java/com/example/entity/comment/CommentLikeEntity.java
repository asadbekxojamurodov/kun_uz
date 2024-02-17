package com.example.entity.comment;

import com.example.entity.ProfileEntity;
import com.example.entity.comment.CommentEntity;
import com.example.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment_like_dislike")
public class CommentLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "profile_id", nullable = false)
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false, insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "comment_id", nullable = false)
    private Integer commentId;
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false, insertable = false, updatable = false)
    private CommentEntity comment;

    @Column(name = "created_date")
    protected LocalDateTime createdDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private LikeStatus status;
}