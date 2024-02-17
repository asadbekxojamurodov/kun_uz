package com.example.controller;

import com.example.dto.auth.AuthDTO;
import com.example.dto.auth.RegistrationDTO;
import com.example.dto.profile.ProfileDTO;
import com.example.enums.AppLanguage;
import com.example.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Authorization Api list", description = "Api list for Authorization")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    @Operation( summary = "Api for login", description = "this api used for authorization")
    public ResponseEntity<ProfileDTO> login(@Valid @RequestBody AuthDTO auth,
                                            @RequestHeader(value = "Accept-Language",defaultValue = "UZ")
                                            AppLanguage appLanguage) {
        log.trace("Login In Trace");
        log.debug("Login In Debug");
        log.info("Login {} ", auth.getEmail());
        log.warn("Login {} ", auth.getEmail());
        log.error("Login {} ", auth.getEmail());
        return ResponseEntity.ok(authService.auth(auth,appLanguage));
    }

    @PostMapping("/registrationByEmail")
    public ResponseEntity<Boolean> registration(@RequestBody RegistrationDTO dto) {
        log.info("registration {} ", dto.getEmail());
        return ResponseEntity.ok(authService.registrationByEmail(dto));
    }

    @PostMapping("/registrationByPhoneNumber")
    public ResponseEntity<Boolean> registrationByPhoneNumber(@RequestBody RegistrationDTO dto) {
        log.info("registration {} ", dto.getEmail());
        return ResponseEntity.ok(authService.registrationByPhoneNumber(dto));
    }


    @GetMapping("/verification/email/{jwt}")
    @Operation( summary = "Api for verification by email", description = "this api used for authorization")
    public ResponseEntity<String> emailVerification(@PathVariable("jwt") String jwt) {
        log.info("emailVerification {} ", jwt);
        return ResponseEntity.ok(authService.emailVerification(jwt));
    }




}
