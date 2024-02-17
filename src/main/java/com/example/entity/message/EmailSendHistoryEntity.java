package com.example.entity.message;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_send_history")
public class EmailSendHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "message", nullable = false)
    private String message;
    @Column(name = "email", nullable = false)
    private String email;
}
