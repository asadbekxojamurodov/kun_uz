package com.example.dto.article;

import com.example.enums.LikeStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ArticleLikeDTO {

    private Integer id;

    @Size(min = 5, message = "articleId should not be empty")
    @NotBlank(message = "articleId should not be empty")
    private String articleId;

    @Min(value = 1, message = "RegionId should not be less than 1")
    private Integer profileId;

    private LocalDateTime createdDate;

    private LikeStatus status;
}
