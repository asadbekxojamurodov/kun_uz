package com.example.entity;

import com.example.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@Entity
public class SmsHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "message")
    private String message;

    @Column(name = "status")
    private ProfileStatus status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "used_date")
    private LocalDateTime usedDate;
}
