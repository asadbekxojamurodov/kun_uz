package com.example.dto.profile;

import com.example.dto.BaseDTO;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO extends BaseDTO {

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Surname cannot be null")
    private String surname;

    @Email(message = "Email should be valid", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;

    @NotNull(message = "phone cannot be null")
    private String phone;

    @NotBlank(message = "Name field must have a value")
//    @Size(min = 7, max = 30)
    private String password;

    private ProfileStatus status;
    private ProfileRole role;
    protected LocalDateTime updatedDate;
    private String jwt;
}
