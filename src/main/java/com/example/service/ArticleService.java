package com.example.service;

import com.example.dto.ArticleDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.CategoryEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ArticleStatus;
import com.example.exp.AppBadException;
import com.example.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RegionService regionService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProfileService profileService;

    public ArticleDTO create(ArticleDTO dto, Integer moderatorId) {
        ArticleEntity entity = new ArticleEntity();

        CategoryEntity category = categoryService.get(dto.getCategoryId().getId());
        RegionEntity region = regionService.get(dto.getRegionId().getId());
        ProfileEntity profile = profileService.get(moderatorId);


        if (region == null){
            throw new AppBadException("Category not found");
        }
        if (category == null){
            throw new AppBadException("Region not found");
        }
        if (profile == null){
            throw new AppBadException("Profile not found");
        }

        entity.setContent(dto.getContent());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setModerator(profile);
        entity.setCategory(category);
        entity.setRegion(region);
        entity.setVisible(true);
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        entity.setCreatedDate(LocalDateTime.now());
        articleRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


}
