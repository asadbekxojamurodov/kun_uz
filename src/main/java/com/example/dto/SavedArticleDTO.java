package com.example.dto;

import com.example.dto.article.ArticleDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavedArticleDTO {

    private Integer id;

    @Min(value = 1, message = "RegionId should not be less than 1")
    private Integer profileId;

    @NotBlank(message = "articleId should not be empty")
    private String articleId;

    private ArticleDTO article;

    private AttachDTO photo;

    private LocalDateTime createdDate;
}
