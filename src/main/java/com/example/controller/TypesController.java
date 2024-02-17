package com.example.controller;

import com.example.dto.article.TypesDTO;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.service.TypesService;
import com.example.utils.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
public class TypesController {

    @Autowired
    private TypesService typesService;

    @PostMapping("/adm")
    public ResponseEntity<TypesDTO> create(@Valid @RequestBody TypesDTO dto,
                                           HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(typesService.create(dto));
    }

    @GetMapping("/adm")
    public ResponseEntity<List<TypesDTO>> allVisible(HttpServletRequest request){
        HttpRequestUtil.getProfileId(request,ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(typesService.getVisible());
    }

    @GetMapping("/adm/all")
    public ResponseEntity<List<TypesDTO>> getAll(HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(typesService.getAll());
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id, @RequestBody TypesDTO dto,
                                          HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(typesService.update(id, dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id,
                                          HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(typesService.delete(id));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<TypesDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                         @RequestParam(value = "size", defaultValue = "2") Integer size,
                                                         HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(typesService.pagination(page, size));
    }

    @GetMapping("/byLang")
    public ResponseEntity<List<TypesDTO>> getByLang(@RequestParam(value = "lang") AppLanguage language){
        return ResponseEntity.ok(typesService.getByLang(language));
    }



}
