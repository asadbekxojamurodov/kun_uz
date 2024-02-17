package com.example.controller;

import com.example.dto.article.ArticleLikeDTO;
import com.example.service.ArticleLikeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articleLike")
public class ArticleLikeController {

    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping("/like")
    public ResponseEntity<ArticleLikeDTO> createLike(@Valid @RequestBody ArticleLikeDTO dto) {
        return ResponseEntity.ok(articleLikeService.createLike(dto));
    }

    @PostMapping("/dislike")
    public ResponseEntity<ArticleLikeDTO> createDislike(@Valid @RequestBody ArticleLikeDTO dto) {
        return ResponseEntity.ok(articleLikeService.createDislike(dto));
    }

    @DeleteMapping("/remove/{pId}/{aId}")
    public ResponseEntity<Boolean> remove(@Valid @PathVariable("pId") Integer pId,
                                          @PathVariable("aId") String aId) {
        return ResponseEntity.ok(articleLikeService.remove(pId, aId));
    }




}