package com.example.entity.message;

import com.example.enums.SmsStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "sms")
public class SmsHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "message", columnDefinition = "text")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SmsStatus status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "used_date")
    private LocalDateTime usedDate;
}
