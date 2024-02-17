package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class BaseDTO {
    private Integer id;
    private LocalDateTime createdDate;
    private boolean visible;
}
