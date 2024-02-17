package com.example.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentDTO {
    private String content;
    private String articleId;
    private Integer profileId;
    private Integer replyId;
}
