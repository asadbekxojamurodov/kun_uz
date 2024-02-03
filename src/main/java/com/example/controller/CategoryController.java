package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.service.CategoryService;
import com.example.utils.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/adm")
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO dto,
                                              HttpServletRequest request){
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<Boolean> updateAdmin(@PathVariable("id") Integer id, @RequestBody CategoryDTO dto,
                                               HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id,
                                          HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @GetMapping("/adm")
    public ResponseEntity<List<CategoryDTO>> all(HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/byLang")
    public ResponseEntity<List<CategoryDTO>> getByLang(@RequestParam(value = "lang", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(categoryService.getByLang(language));
    }




}
