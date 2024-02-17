package com.example.entity.article;

import com.example.entity.ProfileEntity;
import com.example.entity.article.ArticleEntity;
import com.example.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "article_like_dislike")
public class ArticleLikeEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "article_id", nullable = false)
    private String articleId;
    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false, insertable = false, updatable = false)
    private ArticleEntity article;

    @Column(name = "profile_id", nullable = false)
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false, insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "created_date")
    protected LocalDateTime createdDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private LikeStatus status;
}