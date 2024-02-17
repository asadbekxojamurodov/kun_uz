package com.example.dto.comment;

import com.example.dto.BaseDTO;
import com.example.entity.article.ArticleEntity;
import com.example.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO  extends BaseDTO {
 private String content;
 private String articleId;
 private Integer profileId;
 private ArticleEntity article;
 private ProfileEntity profile;
 private Integer replyId;
 private LocalDateTime updateDate;
}