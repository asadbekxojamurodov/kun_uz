package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginationResultDTO<T> {

    private Long totalSize;
    private List<T> list;
    public PaginationResultDTO(Long totalSize, List<T> list) {
        this.totalSize = totalSize;
        this.list = list;
    }
}
    