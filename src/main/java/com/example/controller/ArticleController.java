package com.example.controller;

import com.example.dto.article.ArticleCreateDTO;
import com.example.dto.article.ArticleDTO;
import com.example.dto.article.ArticleFilterDTO;
import com.example.enums.ProfileRole;
import com.example.service.ArticleService;
import com.example.utils.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/mod")
    public ResponseEntity<?> create(@Valid @RequestBody ArticleCreateDTO dto,
                                    HttpServletRequest request) {
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_MODERATOR);
        return ResponseEntity.ok(articleService.create(dto, profileId));
    }

    @DeleteMapping("/mod/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id,
                                          HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_MODERATOR);
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PutMapping("/pub/{id}")
    public ResponseEntity<Boolean> updateStatus(@Valid @PathVariable("id") String articleId,
                                                HttpServletRequest request) {
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_PUBLISHER);
        return ResponseEntity.ok(articleService.updateStatus(articleId, profileId));
    }


    @GetMapping("/getLast5Article/{id}")
    public ResponseEntity<List<ArticleDTO>> get5Article(@Valid @PathVariable("id") Integer typesId) {
        return ResponseEntity.ok(articleService.getLastArticle(typesId, 5));
    }


    @GetMapping("/getLast3Article/{id}")
    public ResponseEntity<List<ArticleDTO>> get3Article(@PathVariable("id") Integer typesId) {
        return ResponseEntity.ok(articleService.getLastArticle(typesId, 3));
    }


    @GetMapping("/get8ArticleNotIncludedInGivenList/list")
    public ResponseEntity<List<ArticleDTO>> get3Article(@RequestParam(value = "list") List<String> id) {
        return ResponseEntity.ok(articleService.get8ArticleNotIncludedInGivenList(id));
    }


    @GetMapping("/getArticleByIdAndLang")
    public ResponseEntity<List<ArticleDTO>> getArticleByIdAndLang(@RequestParam(value = "lang") String lang) {
        return ResponseEntity.ok(articleService.getArticleByIdAndLang(lang));
    }


    @GetMapping("/getArticleByTypeAndExceptGivenArticleId/{id}")
    public ResponseEntity<List<ArticleDTO>> getArticleByTypeAndExceptGivenArticleId(@RequestParam(value = "typeId") Integer typeId,
                                                                                    @PathVariable("id") Collection<String> id) {
        return ResponseEntity.ok(articleService.getArticleByTypeAndExceptGivenArticleId(typeId, id));
    }


    @GetMapping("/get4MostReadArticle")
    public ResponseEntity<List<ArticleDTO>> get4MostReadArticle() {
        return ResponseEntity.ok(articleService.get4MostReadArticle());
    }


    @GetMapping("/getByTypeAndRegion")
    public ResponseEntity<List<ArticleDTO>> getByTypeAndRegion(@RequestParam(value = "typeId") Integer typeId,
                                                               @RequestParam(value = "regionId") Integer regionId) {
        return ResponseEntity.ok(articleService.getByTypeAndRegion(typeId, regionId, 5));
    }


    @GetMapping("/paginationRegion")
    public ResponseEntity<PageImpl<ArticleDTO>> paginationRegion(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                 @RequestParam(value = "size", defaultValue = "2") Integer size,
                                                                 @RequestParam(value = "region", defaultValue = "2") Integer regionId) {
        return ResponseEntity.ok(articleService.getAllByRegion(page, size, regionId));
    }


    @GetMapping("/getLast5ArticleByCategory")
    public ResponseEntity<List<ArticleDTO>> getLast5ArticleByCategory(@RequestParam(value = "categoryId") Integer categoryId) {
        return ResponseEntity.ok(articleService.getLast5ArticleByCategory(categoryId));
    }


    @GetMapping("/paginationCategory")
    public ResponseEntity<PageImpl<ArticleDTO>> paginationCategory(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                   @RequestParam(value = "size", defaultValue = "2") Integer size,
                                                                   @RequestParam(value = "category", defaultValue = "2") Integer categoryId) {
        return ResponseEntity.ok(articleService.getAllByCategory(page, size, categoryId));
    }


    @PutMapping("/increaseViewCountByArticle/{id}")
    public ResponseEntity<Boolean> increaseViewCount(@PathVariable("id") String articleId) {
        return ResponseEntity.ok(articleService.increaseViewCount(articleId));
    }

    @PutMapping("/increaseShareCountByArticle/{id}")
    public ResponseEntity<Boolean> increaseShareCountByArticle(@PathVariable("id") String articleId) {
        return ResponseEntity.ok(articleService.increaseShareCountByArticle(articleId));
    }

    @PostMapping("/pub/filter")
    public ResponseEntity<PageImpl<ArticleDTO>> create(@RequestBody ArticleFilterDTO dto,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "2") Integer size) {
        PageImpl<ArticleDTO> result = articleService.filter(dto, page, size);
        return ResponseEntity.ok(result);
    }

}
