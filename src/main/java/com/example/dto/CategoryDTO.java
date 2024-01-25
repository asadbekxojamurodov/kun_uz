package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO extends BaseDTO {
    private Integer order_number;
    private String name_uz;
    private String name_en;
    private String name_ru;
    private String name;
    private boolean visible;
}
