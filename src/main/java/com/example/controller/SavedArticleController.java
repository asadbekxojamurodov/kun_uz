package com.example.controller;

import com.example.dto.SavedArticleDTO;
import com.example.service.SavedArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/savedArticle")
public class SavedArticleController {

    @Autowired
    private SavedArticleService savedArticleService;

    @PostMapping("/create")
    public ResponseEntity<SavedArticleDTO> create(@Valid @RequestBody SavedArticleDTO dto){
        return ResponseEntity.ok(savedArticleService.create(dto));
    }

    @DeleteMapping("/delete/{aid}/{pid}")
    public ResponseEntity<Boolean> delete(@PathVariable("aid") String aid,
                                          @PathVariable("pid") Integer pid){
        return ResponseEntity.ok(savedArticleService.delete(aid,pid));
    }

    @GetMapping("/get/{pid}")
    public ResponseEntity<List<SavedArticleDTO>> get(@PathVariable("pid") Integer pid){
        return ResponseEntity.ok(savedArticleService.getList(pid));
    }

}
