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
        if (dto.getName_uz().isEmpty() || dto.getName_en().isEmpty() ||
                dto.getName_ru().isEmpty() || dto.getOrder_number() == null) {
            throw new AppBadException("the entire field of the category is required");
        }
        if (dto.getName_uz().length() < 2 || dto.getName_en().length() < 2 ||
            dto.getName_ru().length() < 2) {
            throw new AppBadException("Category is very short");
        }

        CategoryEntity entity = new CategoryEntity();
        entity.setNameUz(dto.getName_uz());
        entity.setNameEn(dto.getName_en());
        entity.setNameRu(dto.getName_ru());
        entity.setOrderNumber(dto.getOrder_number());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        categoryRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    public Boolean update(Integer id, CategoryDTO dto) {
        if (dto.getName_uz().isEmpty() && dto.getName_en().isEmpty() &&
                dto.getName_ru().isEmpty() && dto.getOrder_number() == null) {
            throw new AppBadException("Category is required");
        }
        CategoryEntity entity = get(id);
        entity.setNameUz(dto.getName_uz());
        entity.setNameEn(dto.getName_en());
        entity.setNameRu(dto.getName_ru());
        entity.setOrderNumber(dto.getOrder_number());
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
            dto.setOrder_number(entity.getOrderNumber());
            dto.setName_uz(entity.getNameUz());
            dto.setName_en(entity.getNameEn());
            dto.setName_ru(entity.getNameRu());
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
