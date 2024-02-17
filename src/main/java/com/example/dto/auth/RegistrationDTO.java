package com.example.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {

   @NotNull(message = "Name cannot be null")
   private String name;

   @NotNull(message = "Surname cannot be null")
   private String surname;

   @Email(message = "Email should be valid", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
   private String email;

   @NotBlank(message = "Password field must have a value")
   private String password;

   @NotBlank(message = "Phone field must have a value")
   private String phone;
}
