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

    @PostMapping("")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto,
                                             @RequestHeader(value = "Authorization") String jwt) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(profileService.create(dto));
    }

    @PutMapping("/byAdmin/{id}")
    public ResponseEntity<Boolean> updateAdmin(@PathVariable("id") Integer id, @RequestBody ProfileDTO dto,
                                               @RequestHeader(value = "Authorization") String jwt) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(profileService.update(id, dto));
    }

    @PutMapping("/updateDetail")
    public ResponseEntity<Boolean> update(@RequestBody ProfileDTO dto,
                                          HttpServletRequest request) {
        Integer id = HttpRequestUtil.getProfileId(request);
        return ResponseEntity.ok(profileService.update(id, dto));
    }

    @GetMapping("")
    public ResponseEntity<List<ProfileDTO>> all(@RequestHeader(value = "Authorization") String jwt) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(profileService.getAll());
    }

    @GetMapping("/allActive")
    public ResponseEntity<List<ProfileDTO>> allActive(@RequestHeader(value = "Authorization") String jwt) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(profileService.getActive());
    }


    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<ProfileDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                           @RequestHeader(value = "Authorization") String jwt) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(profileService.pagination(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id,
                                          @RequestHeader(value = "Authorization") String jwt) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(profileService.delete(id));
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<ProfileDTO>> create(@RequestBody ProfileFilterDTO dto,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                       @RequestHeader(value = "Authorization") String jwt) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        PageImpl<ProfileDTO> result = profileService.filter(dto, page, size);
        return ResponseEntity.ok(result);
    }


}



