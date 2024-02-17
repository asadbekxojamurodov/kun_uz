package com.example.service;

import com.example.dto.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;


    public RegionDTO create(RegionDTO dto) {
        RegionEntity entity = new RegionEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setOrderNumber(dto.getOrderNumber());
        regionRepository.save(entity);

        dto.setId(entity.getId());
        dto.setVisible(entity.isVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean update(Integer id, RegionDTO dto) {
        RegionEntity entity = get(id);

        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setOrderNumber(dto.getOrderNumber());

        regionRepository.save(entity);
        return true;
    }


    public Boolean delete(Integer id) {
        return regionRepository.deleteRegion( id) !=0;
    }


    private RegionDTO toDTO(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(entity.isVisible());
        return dto;
    }


    public List<RegionDTO> getVisible() {
        Iterable<RegionEntity> entityList = regionRepository.findAll();
        List<RegionDTO> dtoList = new LinkedList<>();
        for (RegionEntity entity : entityList) {
            if (entity.isVisible()) {
                dtoList.add(toDTO(entity));
            }
        }
        return dtoList;
    }

    public List<RegionDTO> getAll() {
        Iterable<RegionEntity> entityList = regionRepository.findAll();
        List<RegionDTO> dtoList = new LinkedList<>();
        for (RegionEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public List<RegionDTO> getByLang(AppLanguage language) {
        List<RegionDTO> dtoList = new LinkedList<>();
        Iterable<RegionEntity> all = regionRepository.findAll();

        for (RegionEntity entity : all) {
            RegionDTO dto = new RegionDTO();
            dto.setId(entity.getId());
            switch (language) {
                case UZ -> dto.setName(entity.getNameUz());
                case RU -> dto.setName(entity.getNameRu());
                default -> dto.setName(entity.getNameEn());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }


    public RegionEntity get(Integer id) {
       return regionRepository.findById(id).orElseThrow(() -> new AppBadException("Region not found"));
    }


}

