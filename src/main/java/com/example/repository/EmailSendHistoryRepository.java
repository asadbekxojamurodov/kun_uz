package com.example.repository;

import com.example.entity.message.EmailSendHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface EmailSendHistoryRepository extends CrudRepository<EmailSendHistoryEntity,Integer> {

    @Query("SELECT count (s) from EmailSendHistoryEntity s where s.email =?1 and s.createdDate between ?2 and ?3")
    Long countSendEmail(String email, LocalDateTime from, LocalDateTime to);

}
