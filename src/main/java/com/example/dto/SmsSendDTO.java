package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsSendDTO {
    private String phone;
    private String code;
}