package com.example.entity;

import com.example.entity.article.ArticleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "saved_article")
public class SavedArticleEntity {

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

    @Column(name = "photo_id")
    private String photoId;
    @ManyToOne
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private AttachEntity photo;

    @Column(name = "created_date")
    protected LocalDateTime createdDate = LocalDateTime.now();
}
