package com.example.repository;

import com.example.dto.PaginationResultDTO;
import com.example.dto.article.ArticleFilterDTO;
import com.example.entity.article.ArticleEntity;
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
public class ArticleCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public PaginationResultDTO<ArticleEntity> filter(ArticleFilterDTO filter, Integer page, Integer size) {
        StringBuilder builder = new StringBuilder();

        Map<String, Object> params = new HashMap<>();

        if (filter.getId() != null) {
            builder.append("and id  =: id ");
            params.put("id", filter.getId() );
        }
        if (filter.getCategoryId() != null) {
            builder.append("and categoryId  =: categoryId ");
            params.put("categoryId",  filter.getCategoryId() );
        }
        if (filter.getModeratorId() != null) {
            builder.append("and moderatorId  =: moderatorId ");
            params.put("moderatorId",  filter.getModeratorId() );
        }
        if (filter.getRegionId() != null) {
            builder.append("and regionId  =: regionId ");
            params.put("regionId",  filter.getRegionId() );
        }
        if (filter.getPublisherId() != null) {
            builder.append("and publisherId  =: publisherId ");
            params.put("publisherId",  filter.getPublisherId() );
        }
        if (filter.getTitle() != null) {
            builder.append("and lower(title) like : title ");
            params.put("title", "%" + filter.getTitle().toLowerCase() + "%");
        }
        if (filter.getStatus() != null) {
            builder.append("and status  =: status ");
            params.put("status",  filter.getStatus() );
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
        if (filter.getPublishedDateFrom() != null && filter.getPublishedDateTo() != null) {
            LocalDateTime publishedDateFrom = LocalDateTime.of(LocalDate.from(filter.getPublishedDateFrom()), LocalTime.MIN);
            LocalDateTime publishedDateTo = LocalDateTime.of(LocalDate.from(filter.getPublishedDateTo()), LocalTime.MAX);
            builder.append("and publishedDate between :publishedDateFrom and :publishedDateTo ");
            params.put("publishedDateFrom", publishedDateFrom);
            params.put("publishedDateTo", publishedDateTo);
        } else if (filter.getPublishedDateFrom() != null) {
            LocalDateTime publishedDateFrom = LocalDateTime.of(LocalDate.from(filter.getPublishedDateFrom()), LocalTime.MIN);
            LocalDateTime publishedDateTo = LocalDateTime.of(LocalDate.from(filter.getPublishedDateFrom()), LocalTime.MAX);
            builder.append("and publishedDate between :publishedDateFrom and :publishedDateTo ");
            params.put("publishedDateFrom", publishedDateFrom);
            params.put("publishedDateTo", publishedDateTo);
        } else if (filter.getPublishedDateTo() != null) {
            LocalDateTime publishedDateTo = LocalDateTime.of(LocalDate.from(filter.getPublishedDateTo()), LocalTime.MAX);
            builder.append("and publishedDate <= :publishedDateTo ");
            params.put("publishedDateTo", publishedDateTo);
        }

        StringBuilder selectBuilder = new StringBuilder("FROM ArticleEntity ar where visible=true ");
        selectBuilder.append(builder);
        selectBuilder.append("order by createdDate desc ");

        StringBuilder countBuilder = new StringBuilder("Select count(ar) FROM ArticleEntity ar where visible=true ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());

        selectQuery.setMaxResults(size);             // limit
        selectQuery.setFirstResult((page - 1) * size);   // offset  (page - 1) * size

        Query countQuery = entityManager.createQuery(countBuilder.toString());


        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }
        List<ArticleEntity> entityList = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        return new PaginationResultDTO<ArticleEntity>(totalElements, entityList);


    }
}
