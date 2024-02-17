package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.entity.CategoryEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto) {
        if (dto.getNameUz().isEmpty() || dto.getNameEn().isEmpty() ||
                dto.getNameRu().isEmpty() || dto.getOrderNumber() == null) {
            throw new AppBadException("the entire field of the category is required");
        }
        if (dto.getNameUz().length() < 2 || dto.getNameEn().length() < 2 ||
            dto.getNameRu().length() < 2) {
            throw new AppBadException("Category is very short");
        }

        CategoryEntity entity = new CategoryEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        categoryRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    public Boolean update(Integer id, CategoryDTO dto) {
        if (dto.getNameUz().isEmpty() && dto.getNameEn().isEmpty() &&
                dto.getNameRu().isEmpty() && dto.getOrderNumber() == null) {
            throw new AppBadException("Category is required");
        }
        CategoryEntity entity = get(id);
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setOrderNumber(dto.getOrderNumber());
        categoryRepository.save(entity);
        return true;
    }

    public CategoryEntity get(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new AppBadException("Category not found"));
    }

    public Boolean delete(Integer id) {
        if (id ==null){
            throw new AppBadException("Id is required");
        }
        return categoryRepository.deleteCategory(id) != 0;
    }

    public List<CategoryDTO> getAll() {
        List<CategoryEntity> all = categoryRepository.getAll();
        List<CategoryDTO> dtoList = new LinkedList<>();
        for (CategoryEntity entity : all) {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(entity.getId());
            dto.setOrderNumber(entity.getOrderNumber());
            dto.setNameUz(entity.getNameUz());
            dto.setNameEn(entity.getNameEn());
            dto.setNameRu(entity.getNameRu());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setVisible(entity.isVisible());
            dtoList.add(dto);
        }
        return dtoList;
    }


    public List<CategoryDTO> getByLang(AppLanguage language) {
        List<CategoryDTO> dtoList = new LinkedList<>();
        Iterable<CategoryEntity> all = categoryRepository.findAll();

        for (CategoryEntity entity : all) {
            CategoryDTO dto = new CategoryDTO();
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
