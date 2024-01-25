package com.example.service;

import com.example.dto.PaginationResultDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.ProfileCustomRepository;
import com.example.repository.ProfileRepository;
import com.example.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    ProfileCustomRepository profileCustomRepository;


    public ProfileDTO create(ProfileDTO dto) {

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setRole(ProfileRole.USER);
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        profileRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean update(Integer id, ProfileDTO dto) {
        ProfileEntity entity = get(id);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUpdatedDate(LocalDateTime.now());
        profileRepository.save(entity);
        return true;
    }

    public PageImpl<ProfileDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable paging = PageRequest.of(page - 1, size, sort);

        Page<ProfileEntity> profilePage = profileRepository.findAll(paging);
        List<ProfileEntity> entityList = profilePage.getContent();
        long totalElement = profilePage.getTotalElements();

        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : entityList) {
            if (entity.isVisible()) {
                dtoList.add(toDTO(entity));
            }
        }
        return new PageImpl<>(dtoList, paging, totalElement);
    }

    public Boolean delete(Integer id) {
        return profileRepository.deleteProfile(id) != 0;
    }

    public List<ProfileDTO> getActive() {
        Iterable<ProfileEntity> entityList = profileRepository.findAll();
        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : entityList) {
            if (entity.getStatus().equals(ProfileStatus.ACTIVE)) {
                dtoList.add(toDTO(entity));
            }
        }
        return dtoList;
    }



    public List<ProfileDTO> getAll() {
        Iterable<ProfileEntity> entityList = profileRepository.findAll();
        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : entityList) {
                dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public PageImpl<ProfileDTO> filter(ProfileFilterDTO filter, Integer page, Integer size) {
        PaginationResultDTO<ProfileEntity> paginationResult = profileCustomRepository.filter(filter, page, size);
        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : paginationResult.getList()) {
            dtoList.add(toDTO(entity));
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        return new PageImpl<>(dtoList, pageable, paginationResult.getTotalSize());
    }

    private ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;

    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> new AppBadException("Profile not found"));
    }

}
