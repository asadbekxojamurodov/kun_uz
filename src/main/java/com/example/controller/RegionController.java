package com.example.controller;

import com.example.config.CustomUserDetails;
import com.example.dto.RegionDTO;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.service.RegionService;
import com.example.utils.HttpRequestUtil;
import com.example.utils.SpringSecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @PostMapping("/adm")
    public ResponseEntity<RegionDTO> create(@Valid @RequestBody RegionDTO dto,
                                            HttpServletRequest request) {
//        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
      /*  Integer id = (Integer) request.getAttribute("id");
        ProfileRole role = (ProfileRole) request.getAttribute("role");

        if (!role.equals(ProfileRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }*/

        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(regionService.create(dto));
    }

    @GetMapping("/adm")
    public ResponseEntity<List<RegionDTO>> allVisible(HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(regionService.getVisible());
    }

    @GetMapping("/adm/all")
    public ResponseEntity<List<RegionDTO>> getAll(HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(regionService.getAll());
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id, @RequestBody RegionDTO dto,
                                          HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(regionService.update(id, dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(regionService.delete(id));
    }


    @GetMapping("/byLang")
    public ResponseEntity<List<RegionDTO>> getByLang(@RequestParam(value = "lang", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(regionService.getByLang(language));
    }

    @GetMapping("/change")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> change() {
        return ResponseEntity.ok("DONE");
    }

    @GetMapping("/change2")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public ResponseEntity<String> change2() {
        return ResponseEntity.ok("DONE");
    }


}

