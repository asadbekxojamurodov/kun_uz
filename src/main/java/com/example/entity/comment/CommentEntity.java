package com.example.entity.comment;

import com.example.entity.BaseEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.article.ArticleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity extends BaseEntity {

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

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

    @Column(name = "reply_id")
    private Integer replyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id", insertable = false, updatable = false)
    private CommentEntity replyComment;

    @Column(name = "like_count")
    private Long likeCount = 0l;

    @Column(name = "dislike_count")
    private Long dislikeCount = 0l;
}
