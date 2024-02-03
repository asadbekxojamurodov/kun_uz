package com.example.dto;


import com.example.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private String id;
    private List<String> articleType;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private Integer imageId;
    private RegionDTO regionId;
    private CategoryDTO categoryId;
    private Integer moderatorId;
    private Integer publisherId;
    private ArticleStatus status;
    private Boolean visible;
    private Integer viewCount;
    private LocalDateTime publishedDate;
    private LocalDateTime createdDate;
}
