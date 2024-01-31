package com.example.dto;

import com.example.enums.SmsStatus;

import java.time.LocalDateTime;

public class SmsHistoryDTO {
    private Integer id;
    private String phone;
    private String message;
    private SmsStatus status;
    private LocalDateTime created_date;
    private LocalDateTime used_date;  // (if necessary)

//    type(if necessary);

}
