package com.example.service;

import com.example.dto.AttachDTO;
import com.example.dto.SavedArticleDTO;
import com.example.dto.article.ArticleDTO;
import com.example.entity.AttachEntity;
import com.example.entity.SavedArticleEntity;
import com.example.entity.article.ArticleEntity;
import com.example.exp.AppBadException;
import com.example.repository.SavedArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SavedArticleService {

    @Autowired
    private SavedArticleRepository savedArticleRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private AttachService attachService;

    public SavedArticleDTO create(SavedArticleDTO dto) {

        Optional<SavedArticleEntity> optional = savedArticleRepository.findByArticleIdAndProfileId(dto.getArticleId(), dto.getProfileId());
        if (optional.isPresent()){
            throw new AppBadException("Already saved");
        }

        ArticleEntity articleEntity = articleService.get(dto.getArticleId());
        profileService.get(dto.getProfileId());

        SavedArticleEntity entity = new SavedArticleEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(dto.getProfileId());
        entity.setPhotoId(articleEntity.getPhotoId());

        savedArticleRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(LocalDateTime.now());
        return dto;
    }

    public Boolean delete(String articleId,Integer profileId) {
        articleService.get(articleId);
        return savedArticleRepository.deleteByArticleIdAndProfileId(articleId,profileId) != 0;
    }

    public SavedArticleEntity get(Integer id) {
        Optional<SavedArticleEntity> optional = savedArticleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Article not found");
        }
        return optional.get();
    }


    public List<SavedArticleDTO> getList(Integer pid) {
        List<SavedArticleEntity> profileList = savedArticleRepository.findByProfileId(pid);
        List<SavedArticleDTO> dtoList = new LinkedList<>();
        for (SavedArticleEntity entity : profileList) {
            SavedArticleDTO dto = new SavedArticleDTO();
            dto.setId(entity.getId());

            ArticleEntity articleEntity = articleService.get(entity.getArticleId());

            ArticleDTO articleShortInfo = new ArticleDTO();
            articleShortInfo.setId(articleEntity.getId());
            articleShortInfo.setTitle(articleEntity.getTitle());
            articleShortInfo.setDescription(articleEntity.getDescription());
            dto.setArticle(articleShortInfo);

            AttachEntity attachEntity = attachService.get(entity.getPhotoId());

            AttachDTO attach = new AttachDTO();
            attach.setId(attachEntity.getId());
            attach.setPath(attachEntity.getPath());
            dto.setPhoto(attach);
            dtoList.add(dto);
        }
        return dtoList;
    }

}
