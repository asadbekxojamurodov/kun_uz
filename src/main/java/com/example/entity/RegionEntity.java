package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "region")
public class RegionEntity extends BaseEntity {

    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

    @Column(name = "name_uz", nullable = false)
    private String nameUz;

    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @Column(name = "name_ru", nullable = false)
    private String nameRu;
}
