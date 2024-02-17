package com.example.entity.article;

import com.example.entity.*;
import com.example.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "article")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "shared_count", nullable = false)
    private Integer sharedCount = 0;

    @Column(name = "photo_id")
    private String photoId;
    @ManyToOne
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private AttachEntity photo;

    @Column(name = "region_id")
    private Integer regionId;
    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false, insertable = false, updatable = false)
    private RegionEntity region;

    @Column(name = "category_id")
    private Integer categoryId;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, insertable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "moderator_id")
    private Integer moderatorId;
    @ManyToOne
    @JoinColumn(name = "moderator_id", nullable = false, insertable = false, updatable = false)
    private ProfileEntity moderator;

    @Column(name = "publisher_id")
    private Integer publisherId;
    @ManyToOne
    @JoinColumn(name = "publisher_id", insertable = false, updatable = false)
    private ProfileEntity publisher;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArticleStatus status = ArticleStatus.NOT_PUBLISHED;

    @Column(name = "visible")
    private Boolean visible = true;

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<ArticleTypesEntity> articleTypesList;

    @Column(name = "like_count")
    private Long likeCount = 0l;

    @Column(name = "dislike_count")
    private Long dislikeCount = 0l;
}