package com.example.repository;

import com.example.entity.message.SmsHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, Integer>,
        PagingAndSortingRepository<SmsHistoryEntity, Integer> {

    @Query("SELECT count (s) from SmsHistoryEntity s where s.phone =?1 and s.createdDate between ?2 and ?3")
    Long countSendSMS(String phone, LocalDateTime from, LocalDateTime to);

    Optional<SmsHistoryEntity> findByPhone(String phone);

}
