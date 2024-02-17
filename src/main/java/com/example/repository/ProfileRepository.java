package com.example.repository;

import com.example.entity.ProfileEntity;
import com.example.enums.ProfileStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>,
        PagingAndSortingRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findByEmailAndPassword(String email, String password);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible = false where id =:id")
    int deleteProfile(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("Update ProfileEntity  set status =?2 where id = ?1")
    void updateStatus(Integer id, ProfileStatus active);


    @Query("from ProfileEntity where id = ?1 and visible = true and status = 'ACTIVE'")
    Optional<ProfileEntity> getActiveProfile(Integer profileId);

    Optional<ProfileEntity> findByPhone(String phone);
}
