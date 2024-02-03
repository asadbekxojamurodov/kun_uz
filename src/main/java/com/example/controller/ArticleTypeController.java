package com.example.controller;

import com.example.dto.ArticleTypeDTO;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.service.ArticleTypeService;
import com.example.utils.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {

    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("/adm")
    public ResponseEntity<ArticleTypeDTO> create(@RequestBody ArticleTypeDTO dto,
                                                 HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.create(dto));
    }

    @GetMapping("/adm")
    public ResponseEntity<List<ArticleTypeDTO>> allVisible(HttpServletRequest request){
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.getVisible());
    }

    @GetMapping("/adm/all")
    public ResponseEntity<List<ArticleTypeDTO>> getAll(HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.getAll());
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id, @RequestBody ArticleTypeDTO dto,
                                          HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.update(id, dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id,
                                          HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.delete(id));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<ArticleTypeDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(value = "size", defaultValue = "2") Integer size,
                                                               HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.pagination(page, size));
    }

    @GetMapping("/byLang")
    public ResponseEntity<List<ArticleTypeDTO>> getByLang(@RequestParam(value = "lang") AppLanguage language){
        return ResponseEntity.ok(articleTypeService.getByLang(language));
    }



}
