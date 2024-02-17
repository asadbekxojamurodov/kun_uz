package com.example.service;

import com.example.dto.article.TypesDTO;
import com.example.entity.article.TypesEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TypesService {

    @Autowired
    private TypesRepository typesRepository;

    public TypesDTO create(TypesDTO dto) {
        TypesEntity entity = new TypesEntity();

        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());

        typesRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    public Boolean update(Integer id, TypesDTO dto) {
        Optional<TypesEntity> optional = typesRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Article type not found");
        }
        TypesEntity entity = optional.get();
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setUpdatedDate(LocalDateTime.now());
        typesRepository.save(entity);
        return true;
    }


    public Boolean delete(Integer id) {
        int effectedRows = typesRepository.deleteArticle(id);
        return effectedRows != 0;
    }


    public PageImpl<TypesDTO> pagination(Integer page, Integer size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable paging = PageRequest.of(page - 1, size, sort);

        Page<TypesEntity> articleTypePage = typesRepository.findAll(paging);
        List<TypesEntity> entityList = articleTypePage.getContent();
        long totalElement = articleTypePage.getTotalElements();

        List<TypesDTO> dtoList = new LinkedList<>();
        for (TypesEntity entity : entityList) {
            if (entity.isVisible()) {
                dtoList.add(toDTO(entity));
            }
        }
        return new PageImpl<>(dtoList, paging, totalElement);
    }

    public List<TypesDTO> getVisible() {
        Iterable<TypesEntity> entityList = typesRepository.findAll();
        List<TypesDTO> dtoList = new LinkedList<>();
        for (TypesEntity entity : entityList) {
            if (entity.isVisible()) {
                dtoList.add(toDTO(entity));
            }
        }
        return dtoList;
    }

    public List<TypesDTO> getAll() {
        Iterable<TypesEntity> entityList = typesRepository.findAll();
        List<TypesDTO> dtoList = new LinkedList<>();
        for (TypesEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    private TypesDTO toDTO(TypesEntity entity) {
        TypesDTO dto = new TypesDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(entity.isVisible());
        return dto;
    }


    public List<TypesDTO> getByLang(AppLanguage language) {
        List<TypesDTO> dtoList = new LinkedList<>();
        Iterable<TypesEntity> all = typesRepository.findAll();

        for (TypesEntity entity : all) {
            TypesDTO dto = new TypesDTO();
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

}
