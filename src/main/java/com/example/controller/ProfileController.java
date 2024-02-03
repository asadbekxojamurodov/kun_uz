package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.enums.ProfileRole;
import com.example.service.ProfileService;
import com.example.utils.HttpRequestUtil;
import com.example.utils.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/adm")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto,
                                             HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.create(dto));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<Boolean> updateAdmin(@PathVariable("id") Integer id, @RequestBody ProfileDTO dto,
                                               HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.update(id, dto));
    }

    @PutMapping("/updateDetail")
    public ResponseEntity<Boolean> update(@RequestBody ProfileDTO dto,
                                          HttpServletRequest request) {
        Integer id = HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN, ProfileRole.USER,
                ProfileRole.MODERATOR, ProfileRole.PUBLISHER);
        return ResponseEntity.ok(profileService.update(id, dto));
    }

    @GetMapping("/adm")
    public ResponseEntity<List<ProfileDTO>> all(HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.getAll());
    }

    @GetMapping("/adm/allActive")
    public ResponseEntity<List<ProfileDTO>> allActive(HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.getActive());
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id,
                                          HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.delete(id));
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<ProfileDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "size", defaultValue = "2") Integer size,
                                                           HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.pagination(page, size));
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<ProfileDTO>> create(@RequestBody ProfileFilterDTO dto,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "2") Integer size) {
        PageImpl<ProfileDTO> result = profileService.filter(dto, page, size);
        return ResponseEntity.ok(result);
    }


}



