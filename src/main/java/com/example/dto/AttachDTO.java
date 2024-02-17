package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {

    private String id;

    @NotBlank(message = "Path field must have a value")
    private String path;

    private Long size;

    private String extension;

    private String originalName;

    private String url;

    private LocalDateTime createdData;
}
