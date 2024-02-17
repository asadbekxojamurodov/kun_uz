package com.example.dto.article;

import com.example.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ArticleFilterDTO {
    private String id;
    private String title;
    private Integer regionId;
    private Integer categoryId;
    private Integer moderatorId;
    private Integer publisherId;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
    private LocalDate publishedDateFrom;
    private LocalDate publishedDateTo;
    private ArticleStatus status;
}
