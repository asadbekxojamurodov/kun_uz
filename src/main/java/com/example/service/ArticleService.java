package com.example.service;

import com.example.dto.PaginationResultDTO;
import com.example.dto.article.ArticleCreateDTO;
import com.example.dto.article.ArticleDTO;
import com.example.dto.article.ArticleFilterDTO;
import com.example.entity.article.ArticleEntity;
import com.example.entity.article.ArticleTypesEntity;
import com.example.exp.AppBadException;
import com.example.repository.ArticleCustomRepository;
import com.example.repository.ArticleRepository;
import com.example.repository.ArticleTypesRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleTypesRepository articleTypesRepository;

    @Autowired
    private ArticleTypesService articleTypesService;

    @Autowired
    private ArticleCustomRepository articleCustomRepository;

    public ArticleCreateDTO create(ArticleCreateDTO dto, Integer profileId) {
        // check
        ArticleEntity article = new ArticleEntity();
        article.setTitle(dto.getTitle());
        article.setDescription(dto.getDescription());
        article.setContent(dto.getContent());

        article.setPhotoId(dto.getPhotoId());
        article.setRegionId(dto.getRegionId());
        article.setCategoryId(dto.getCategoryId());
        article.setModeratorId(profileId);


        // category ..
        articleRepository.save(article);

        articleTypesService.create(article.getId(), dto.getArticleType());
        // article.setModerator(null); //  profileId

        return dto;
    }

    public Boolean update(String articleId, ArticleCreateDTO dto, Integer profileId) {
        // check
        ArticleEntity article = get(articleId);
        article.setTitle(dto.getTitle());
        article.setDescription(dto.getDescription());
        article.setContent(dto.getContent());

        article.setRegionId(dto.getRegionId());
        article.setModeratorId(profileId);
        article.setPhotoId(dto.getPhotoId());
        // category ..
        articleRepository.save(article);

        articleTypesService.merge(article.getId(), dto.getArticleType());
        // article.setModerator(null); //  profileId
        return true;
    }


    public ArticleEntity get(String id) {
        return articleRepository.findById(id).orElseThrow(() -> new AppBadException("Article not found"));
    }

    public Boolean delete(String id) {
        get(id);
        return articleRepository.deleteArticle(id) != 0;
    }

    public Boolean updateStatus(String articleId, Integer profileId) {
        ArticleEntity entity = get(articleId);
        entity.setPublisherId(profileId);
        entity.setPublishedDate(LocalDateTime.now());
        return articleRepository.updateStatus(articleId) != 0;
    }

    public List<ArticleDTO> getLastArticle(Integer id, int num) {
        List<ArticleTypesEntity> entityList = articleTypesRepository.getByTypesIdOrderByCreatedDateDesc(id);
        List<ArticleDTO> dtoList = new LinkedList<>();

        for (ArticleTypesEntity entity : entityList) {
            ArticleEntity checkedEntity = articleRepository.checkVisibleAndPublish(entity.getArticleId());
            if (checkedEntity != null){
                ArticleEntity article = new ArticleEntity();
                article.setId(checkedEntity.getId());
                article.setTitle(checkedEntity.getTitle());
                article.setDescription(checkedEntity.getDescription());
                article.setPhotoId(checkedEntity.getPhotoId());
                article.setPublishedDate(checkedEntity.getPublishedDate());
                dtoList.add(toDTOShortInfo(article));
                num--;
                if (0 == num){
                    return dtoList;
                }
            }
        }
        return dtoList;
    }


    public List<ArticleDTO> getByTypeAndRegion(Integer typeId, Integer regionId, int num) {
        List<ArticleTypesEntity> entityList = articleTypesRepository.getByTypesIdOrderByCreatedDateDesc(typeId);
        List<ArticleDTO> dtoList = new LinkedList<>();

        for (ArticleTypesEntity entity : entityList) {
            ArticleEntity articleEntity = articleRepository.checkVisibleAndPublish(entity.getArticleId());
            if (articleEntity != null && articleEntity.getRegionId().equals(regionId)){
                ArticleEntity article = new ArticleEntity();
                article.setId(articleEntity.getId());
                article.setTitle(articleEntity.getTitle());
                article.setDescription(articleEntity.getDescription());
                article.setPhotoId(articleEntity.getPhotoId());
                article.setPublishedDate(articleEntity.getPublishedDate());
                dtoList.add(toDTOShortInfo(article));
                num--;
                if (0 == num){
                    return dtoList;
                }
            }
        }
        return dtoList;
    }


    public List<ArticleDTO> get8ArticleNotIncludedInGivenList(List<String> ArticleId) {
        List<ArticleTypesEntity> entityList = articleTypesRepository.getByArticleIdNotIn(ArticleId);
        return getArticleInfo(entityList);
    }

    public List<ArticleDTO> getArticleByTypeAndExceptGivenArticleId(Integer type, Collection<String> id) {
        List<ArticleTypesEntity> articleTypesList = articleTypesRepository.getByTypesIdAndArticleIdNotIn(type, id);
        return getArticleInfo(articleTypesList);
    }

    public ArticleDTO toDTOShortInfo(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPhotoId(entity.getPhotoId());
        dto.setPublishedDate(entity.getPublishedDate());
        return dto;
    }

    @NotNull
    private List<ArticleDTO> getArticleInfo(List<ArticleTypesEntity> entityList) {
        List<ArticleDTO> dtoList = new LinkedList<>();

        for (ArticleTypesEntity articleTypesList : entityList) {
            String uuid = (articleTypesList.getArticleId());
            ArticleEntity getEntity = get(uuid);
            if (getEntity.getVisible() && getEntity.getPublishedDate() != null) {
                ArticleEntity entity = new ArticleEntity();
                entity.setId(getEntity.getId());
                entity.setTitle(getEntity.getTitle());
                entity.setDescription(getEntity.getDescription());
                entity.setPhotoId(getEntity.getPhotoId());
                entity.setPublishedDate(getEntity.getPublishedDate());
                dtoList.add(toDTOShortInfo(entity));
            }
        }
        return dtoList;
    }


    public List<ArticleDTO> getArticleByIdAndLang(String lang) {

        return null;
    }

    public List<ArticleDTO> get4MostReadArticle() {
        List<ArticleEntity> mostReadArticle = articleRepository.get4MostReadArticle();
        List<ArticleDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : mostReadArticle) {
            dtoList.add(toDTOShortInfo(entity));
        }
        return dtoList;
    }

    public List<ArticleDTO> getLast5ArticleByCategory(Integer categoryId) {
        List<ArticleEntity> articleList = articleRepository.findLast5ArticleByCategoryId(categoryId,5);
        List<ArticleDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : articleList) {
            dtoList.add(toDTOShortInfo(entity));
        }
        return dtoList;
    }


    public PageImpl<ArticleDTO> getAllByRegion(Integer page, Integer size, Integer regionId) {
        return getArticlePagination(page, size, regionId);
    }

    public PageImpl<ArticleDTO> getAllByCategory(Integer page, Integer size, Integer categoryId) {
        return getArticlePagination(page, size, categoryId);
    }

    @NotNull
    private PageImpl<ArticleDTO> getArticlePagination(Integer page, Integer size, Integer id) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        Pageable paging = PageRequest.of(page - 1, size, sort);

        Page<ArticleEntity> studentPage = articleRepository.findAllByRegionId(paging, id);
        List<ArticleEntity> entityList = studentPage.getContent();
        long totalElement = studentPage.getTotalElements();

        List<ArticleDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entityList) {
            dtoList.add(toDTOShortInfo(entity));
        }
        return new PageImpl<>(dtoList, paging, totalElement);
    }


    public Boolean increaseViewCount(String articleId) {
        get(articleId);
        return articleRepository.increaseViewCount(articleId) != 0;
    }

    public Boolean increaseShareCountByArticle(String articleId) {
        get(articleId);
        return articleRepository.increaseShareCount(articleId) != 0;
    }


    public PageImpl<ArticleDTO> filter(ArticleFilterDTO filter, Integer page, Integer size) {
        PaginationResultDTO<ArticleEntity> paginationResult = articleCustomRepository.filter(filter, page, size);
        List<ArticleDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : paginationResult.getList()) {
            dtoList.add(toDTOShortInfo(entity));
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        return new PageImpl<>(dtoList, pageable, paginationResult.getTotalSize());

    }
}
