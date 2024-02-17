package com.example.service;

import com.example.dto.PaginationResultDTO;
import com.example.dto.comment.CommentDTO;
import com.example.dto.comment.CommentFilterDTO;
import com.example.dto.comment.UpdateCommentDTO;
import com.example.entity.article.ArticleEntity;
import com.example.entity.comment.CommentEntity;
import com.example.entity.ProfileEntity;
import com.example.exp.AppBadException;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentCustomRepository;
import com.example.repository.CommentRepository;
import com.example.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentCustomRepository commentCustomRepository;


    public CommentDTO create(CommentDTO dto) {
        Optional<ArticleEntity> article = articleRepository.getByArticleId(dto.getArticleId());
        Optional<ProfileEntity> profile = profileRepository.getActiveProfile(dto.getProfileId());

        if (profile.isEmpty()) {
            throw new AppBadException("Profile not found");
        }

        if (article.isEmpty()) {
            throw new AppBadException("Article not found");
        }
        CommentEntity entity = new CommentEntity();

        if (dto.getReplyId() != null) {
            entity.setReplyId(dto.getReplyId());
        }

        entity.setContent(dto.getContent());
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(dto.getProfileId());
        commentRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(LocalDateTime.now());
        dto.setVisible(entity.isVisible());
        return dto;
    }

    public UpdateCommentDTO update(UpdateCommentDTO dto) {
        Optional<CommentEntity> commentOptional = commentRepository.findByArticleId(dto.getArticleId());
        if (commentOptional.isEmpty()) {
            throw new AppBadException("Comment not found");
        }
        CommentEntity entity = commentOptional.get();
        entity.setContent(dto.getContent());
        entity.setUpdatedDate(LocalDateTime.now());
        commentRepository.save(entity);

        dto.setId(dto.getId());
        return dto;
    }

    public UpdateCommentDTO update(Integer id, UpdateCommentDTO dto, Integer profileId) {

        CommentEntity entity = get(id);
        if (!entity.getProfileId().equals(profileId)) {
            log.warn("This profile has not left such a comment ");
            throw new AppBadException("This profile has not left such a comment ");
        }
        entity.setContent(dto.getContent());
        entity.setArticleId(dto.getArticleId());
        entity.setUpdatedDate(LocalDateTime.now());
        commentRepository.save(entity);
        return dto;
    }

    public Boolean delete(Integer id) {
        get(id);
        return commentRepository.deleteComment(id) != 0;
    }

    public List<CommentDTO> getList(String id) {
        List<CommentEntity> commentList = commentRepository.getByArticleIdAndVisibleTrue(id);
        List<CommentDTO> dtoList = new LinkedList<>();
        for (CommentEntity entity : commentList) {
            dtoList.add(toDTOShort(entity));
        }
        return dtoList;
    }

    public CommentEntity get(Integer id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            log.warn("Comment not found {} ", id);
            return new AppBadException("Comment not found");
        });
    }


    public CommentDTO toDTO(@NotNull CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdateDate(entity.getUpdatedDate());
        dto.setProfileId(entity.getProfileId());
        dto.setArticleId(entity.getArticleId());
        dto.setContent(entity.getContent());
        dto.setReplyId(entity.getReplyId());
        dto.setVisible(entity.isVisible());
        return dto;
    }
    //  id,profileId,articleId,
    //  content,replyId,createdDate,
    //  updatedDate,visible


    public CommentDTO toDTOShort(@NotNull CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        ProfileEntity profile = profileService.getProfileIdNameSurname(entity.getProfileId());
        dto.setProfile(profile);
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setReplyId(entity.getReplyId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(entity.isVisible());
        dto.setUpdateDate(entity.getUpdatedDate());
        return dto;
    }
    //  id,profile(id,name,surname),
    //  content,replyId,createdDate,
    //  updatedDate,visible


    public CommentDTO toDTOFull(@NotNull CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        ProfileEntity profile = profileService.getProfileIdNameSurname(entity.getProfileId());
        ArticleEntity articleEntity = articleService.get(entity.getArticleId());
        ArticleEntity article = new ArticleEntity();
        article.setId(articleEntity.getId());
        article.setTitle(articleEntity.getTitle());
        dto.setArticle(article);
        dto.setProfile(profile);
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setReplyId(entity.getReplyId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(entity.isVisible());
        dto.setUpdateDate(entity.getUpdatedDate());
        return dto;
    }
    //  id,profile(id,name,surname),
    //  article(id,title),content,replyId,
    //  createdDate,updatedDate,visible


    public PageImpl<CommentDTO> getPagination(Integer page, Integer size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        Pageable paging = PageRequest.of(page - 1, size, sort);

        Page<CommentEntity> studentPage = commentRepository.findAllBy(paging);
        List<CommentEntity> entityList = studentPage.getContent();
        long totalElement = studentPage.getTotalElements();

        List<CommentDTO> dtoList = new LinkedList<>();
        for (CommentEntity entity : entityList) {
            dtoList.add(toDTOFull(entity));
        }
        return new PageImpl<>(dtoList, paging, totalElement);
    }

    public PageImpl<CommentDTO> filter(CommentFilterDTO filter, Integer page, Integer size) {
        PaginationResultDTO<CommentEntity> paginationResult = commentCustomRepository.filter(filter, page, size);
        List<CommentDTO> dtoList = new LinkedList<>();
        for (CommentEntity entity : paginationResult.getList()) {
            dtoList.add(toDTO(entity));
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        return new PageImpl<>(dtoList, pageable, paginationResult.getTotalSize());
    }

    public List<CommentDTO> getByCommentId(Integer id) {
        get(id);
        List<CommentEntity> commentList = commentRepository.getById(id);
        List<CommentDTO> dtoList = new LinkedList<>();
        for (CommentEntity entity : commentList) {
            if (entity.getReplyId()!= null){
                CommentDTO dto = new CommentDTO();
                dto.setId(entity.getId());
                dto.setCreatedDate(entity.getCreatedDate());
                dto.setUpdateDate(entity.getUpdatedDate());
                ProfileEntity profile = profileService.getProfileIdNameSurname(entity.getProfileId());
                dto.setProfile(profile);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

}
