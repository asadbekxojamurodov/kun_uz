package com.example.dto.article;

import com.example.dto.CategoryDTO;
import com.example.dto.RegionDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleFullInfoDTO {
    private String id;
    private String titleUz;
    private String titleRu;
    private String titleEn;
    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;
    private String contentUz;
    private String contentRu;
    private String contentEn;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private Integer viewCount;
    private RegionDTO region;
    private CategoryDTO category;
    private LocalDateTime publishedDate;
    private List<String> tagNames;
    private Integer likeCount;
}
//    id(uuid),title,description,content,shared_count,
//    region(key,name),category(key,name),published_date,view_count,like_count,
//        tagList(name)