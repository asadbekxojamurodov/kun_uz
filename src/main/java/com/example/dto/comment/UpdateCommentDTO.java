package com.example.dto.comment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateCommentDTO {
    private Integer id;
    private String content;
    private String articleId;
    private LocalDateTime updatedDate;
}
