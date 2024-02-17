package com.example.dto;

import com.example.enums.SmsStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsHistoryDTO {

    private Integer id;
    private SmsStatus status;
    private LocalDateTime created_date;

    @NotBlank(message = "Phone field must have a value")
    private String phone;

    @NotBlank(message = "message cannot be null")
    private String message;
}
