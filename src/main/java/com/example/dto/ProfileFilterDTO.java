package com.example.dto;

import com.example.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class ProfileFilterDTO {
    private String name;
    private String surname;
    private ProfileRole role;
    private LocalDate fromDate;
    private LocalDate toDate;
}
