package com.example.service;

import com.example.dto.article.ArticleCreateDTO;
import com.example.entity.article.ArticleEntity;
import com.example.entity.article.ArticleTypesEntity;
import com.example.exp.AppBadException;
import com.example.repository.ArticleRepository;
import com.example.repository.ArticleTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypesService {

    @Autowired
    private ArticleTypesRepository articleTypesRepository;

    @Autowired
    private ArticleRepository articleRepository;


    public void create(String articleId, List<Integer> typesIdList) {
        for (Integer typeId : typesIdList) {
            create(articleId, typeId);
        }
    }

    public void create(String articleId, Integer typesId) {
        ArticleTypesEntity entity = new ArticleTypesEntity();
        entity.setArticleId(articleId);
        entity.setTypesId(typesId);
        articleTypesRepository.save(entity);
    }

    public void merge(String articleId, List<Integer> typesIdList) {
        // create
        // [] old
        // [1,2,3,4,5] new

        // update
        // [1,2,3,4,5] - old
        // [7,5] - new

        ArticleEntity entityList = get(articleId);
        ArticleCreateDTO dto = toDTO(entityList);
        for (int i = 0; i < dto.getArticleType().size(); i++) {
            for (Integer integer : typesIdList) {
                if (!dto.getArticleType().get(i).equals(integer)) {
                    dto.getArticleType().remove(i);
                }
            }
        }

    }

    public ArticleCreateDTO toDTO(ArticleEntity entity) {
        ArticleCreateDTO dto = new ArticleCreateDTO();
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setDescription(entity.getDescription());
        dto.setPhotoId(entity.getPhotoId());
        dto.setRegionId(entity.getRegionId());
        dto.setCategoryId(entity.getCategoryId());
        return dto;
    }

    public ArticleEntity get(String id) {
        Optional<ArticleEntity> optionalList = articleRepository.getById(id);
        if (optionalList.isEmpty()) {
            throw new AppBadException("ArticleTypes not found");
        }
        return optionalList.get();
    }


}
