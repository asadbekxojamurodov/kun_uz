package com.example.repository;

import com.example.dto.PaginationResultDTO;
import com.example.dto.comment.CommentFilterDTO;
import com.example.entity.comment.CommentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public PaginationResultDTO<CommentEntity> filter(CommentFilterDTO filter, Integer page, Integer size) {
        StringBuilder builder = new StringBuilder();

        Map<String, Object> params = new HashMap<>();

        if (filter.getId() != null) {
            builder.append("and id  =: id ");
            params.put("id", filter.getId() );
        }
        if (filter.getProfileId() != null) {
            builder.append("and profileId  =: profileId ");
            params.put("profileId",  filter.getProfileId() );
        }
        if (filter.getArticleId() != null) {
            builder.append("and articleId  =: articleId ");
            params.put("articleId",  filter.getArticleId() );
        }

        if (filter.getCreatedDateFrom() != null && filter.getCreatedDateTo() != null) {
            LocalDateTime createdDateFrom = LocalDateTime.of(LocalDate.from(filter.getCreatedDateFrom()), LocalTime.MIN);
            LocalDateTime createdDateTo = LocalDateTime.of(LocalDate.from(filter.getCreatedDateTo()), LocalTime.MAX);
            builder.append("and createdDate between :createdDateFrom and :createdDateTo ");
            params.put("createdDateFrom", createdDateFrom);
            params.put("createdDateTo", createdDateTo);
        } else if (filter.getCreatedDateFrom() != null) {
            LocalDateTime createdDateFrom = LocalDateTime.of(LocalDate.from(filter.getCreatedDateFrom()), LocalTime.MIN);
            LocalDateTime createdDateTo = LocalDateTime.of(LocalDate.from(filter.getCreatedDateFrom()), LocalTime.MAX);
            builder.append("and createdDate between :createdDateFrom and :createdDateTo ");
            params.put("createdDateFrom", createdDateFrom);
            params.put("createdDateTo", createdDateTo);
        } else if (filter.getCreatedDateTo() != null) {
            LocalDateTime createdDateTo = LocalDateTime.of(LocalDate.from(filter.getCreatedDateTo()), LocalTime.MAX);
            builder.append("and createdDate <= :createdDateTo ");
            params.put("createdDateTo", createdDateTo);
        }

        StringBuilder selectBuilder = new StringBuilder("FROM CommentEntity ar where visible=true ");
        selectBuilder.append(builder);
        selectBuilder.append("order by createdDate desc ");

        StringBuilder countBuilder = new StringBuilder("Select count(ar) FROM CommentEntity ar where visible=true ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());

        selectQuery.setMaxResults(size);             // limit
        selectQuery.setFirstResult((page - 1) * size);   // offset  (page - 1) * size

        Query countQuery = entityManager.createQuery(countBuilder.toString());


        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }
        List<CommentEntity> entityList = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        return new PaginationResultDTO<CommentEntity>(totalElements, entityList);


    }

}
