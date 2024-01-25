package com.example.service;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.RegionDTO;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.RegionEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {

    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public ArticleTypeDTO create(ArticleTypeDTO dto) {
        ArticleTypeEntity entity = new ArticleTypeEntity();

        entity.setOrderNumber(dto.getOrder_number());
        entity.setNameUz(dto.getName_uz());
        entity.setNameRu(dto.getName_ru());
        entity.setNameEn(dto.getName_en());

        articleTypeRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    public Boolean update(Integer id, ArticleTypeDTO dto) {

        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Article type not found");
        }
        ArticleTypeEntity entity = optional.get();
        entity.setNameUz(dto.getName_uz());
        entity.setNameEn(dto.getName_en());
        entity.setNameRu(dto.getName_ru());
        entity.setOrderNumber(dto.getOrder_number());
        entity.setUpdatedDate(LocalDateTime.now());
        articleTypeRepository.save(entity);
        return true;
    }


    public Boolean delete(Integer id) {
        int effectedRows = articleTypeRepository.deleteArticle(id);
        return effectedRows != 0;
    }


    public PageImpl<ArticleTypeDTO> pagination(Integer page, Integer size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable paging = PageRequest.of(page - 1, size, sort);

        Page<ArticleTypeEntity> articleTypePage = articleTypeRepository.findAll(paging);
        List<ArticleTypeEntity> entityList = articleTypePage.getContent();
        long totalElement = articleTypePage.getTotalElements();

        List<ArticleTypeDTO> dtoList = new LinkedList<>();
        for (ArticleTypeEntity entity : entityList) {
            if (entity.isVisible()) {
                dtoList.add(toDTO(entity));
            }
        }
        return new PageImpl<>(dtoList, paging, totalElement);
    }

    public List<ArticleTypeDTO> getVisible() {
        Iterable<ArticleTypeEntity> entityList = articleTypeRepository.findAll();
        List<ArticleTypeDTO> dtoList = new LinkedList<>();
        for (ArticleTypeEntity entity : entityList) {
            if (entity.isVisible()) {
                dtoList.add(toDTO(entity));
            }
        }
        return dtoList;
    }

    public List<ArticleTypeDTO> getAll() {
        Iterable<ArticleTypeEntity> entityList = articleTypeRepository.findAll();
        List<ArticleTypeDTO> dtoList = new LinkedList<>();
        for (ArticleTypeEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    private ArticleTypeDTO toDTO(ArticleTypeEntity entity) {
        ArticleTypeDTO dto = new ArticleTypeDTO();
        dto.setId(entity.getId());
        dto.setName_uz(entity.getNameUz());
        dto.setName_ru(entity.getNameRu());
        dto.setName_en(entity.getNameEn());
        dto.setOrder_number(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(entity.isVisible());
        return dto;
    }


    public List<ArticleTypeDTO> getByLang(AppLanguage language) {
        List<ArticleTypeDTO> dtoList = new LinkedList<>();
        Iterable<ArticleTypeEntity> all = articleTypeRepository.findAll();

        for (ArticleTypeEntity entity : all) {
            ArticleTypeDTO dto = new ArticleTypeDTO();
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
