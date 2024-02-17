package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO extends BaseDTO {
    @NotNull(message = "orderNumber required")
    private Integer orderNumber;

    @Size(min = 2, max = 200, message = "nameUz must be  between 2 and 200 character")
    @NotBlank(message = "nameUz must be  between 2 and 200 character")
    private String nameUz;

    @Size(min = 2, max = 200, message = "nameEn must be  between 2 and 200 character")
    @NotBlank(message = "nameEn must be  between 2 and 200 character")
    private String nameEn;

    @Size(min = 2, max = 200, message = "nameRu must be  between 2 and 200 character")
    @NotBlank(message = "nameRu must be  between 2 and 200 character")
    private String nameRu;

    private String name;
}
