package com.example.service;

import com.example.dto.JwtDTO;
import com.example.dto.SmsSendDTO;
import com.example.dto.auth.AuthDTO;
import com.example.dto.auth.RegistrationDTO;
import com.example.dto.profile.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.entity.message.SmsHistoryEntity;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.EmailSendHistoryRepository;
import com.example.repository.ProfileRepository;
import com.example.repository.SmsHistoryRepository;
import com.example.utils.JWTUtil;
import com.example.utils.MD5Util;
import com.example.utils.RandomUtil;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private SmsServerService smsServerService;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private EmailSendHistoryRepository emailSendHistoryRepository;

    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    @Autowired
    private ResourceBundleMessageSource resourceBundleMessageSource;

    @Autowired
    private RecourseBundleService recourseBundleService;


    public ProfileDTO auth(AuthDTO profile, AppLanguage appLanguage) {

        Optional<ProfileEntity> optional = profileRepository.findByEmailAndPassword(profile.getEmail(),
                MD5Util.encode(profile.getPassword()));

        if (optional.isEmpty()) {
            log.warn("Email or Password is wrong {}", profile.getEmail());
            throw new AppBadException(recourseBundleService.getMessage("email.password.wrong", appLanguage));
        }

        ProfileEntity entity = optional.get();

        if (!entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            log.warn("Profile not active {}", entity.getEmail());
            throw new AppBadException("Profile not active");
        }

        ProfileDTO dto = new ProfileDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole());
        dto.setJwt(JWTUtil.encode(entity.getEmail(), entity.getRole()));

        return dto;
    }

    public Boolean registrationByEmail(RegistrationDTO dto) {
        // validation

        // check
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(optional.get());  // delete
                // or
                //send verification code (email/sms)
            } else {
                throw new AppBadException("Email exists");
            }
        }

        LocalDateTime from = LocalDateTime.now().minusMinutes(1);
        LocalDateTime to = LocalDateTime.now();

        if (emailSendHistoryRepository.countSendEmail(dto.getEmail(), from, to) >= 3) {
            throw new AppBadException("To many attempt. Please try after 1 minute.");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setStatus(ProfileStatus.REGISTRATION);
        entity.setRole(ProfileRole.ROLE_USER);
        profileRepository.save(entity);
        //send verification code (email/sms)

        String jwt = JWTUtil.encodeForEmail(entity.getId());

        String text = """
                <h1 style="text-align: center"> Click here %s</h1>
                <p style="background-color: #029f2e; color: white; padding: 30px">To complete registration please link to the following link</p>
                <a style=" background-color: #1609c5;
                  color: white;
                  padding: 14px 25px;
                  text-align: center;
                  text-decoration: none;
                  display: inline-block;" href="http://localhost:8080/auth/verification/email/%s
                ">Click</a>
                <br>
                """;
        text = String.format(text, entity.getName(), jwt);
        mailSenderService.sendEmail(dto.getEmail(), "Complete registration", text);

        return true;
    }

    public String emailVerification(String jwt) {
        try {
            JwtDTO jwtDTO = JWTUtil.decode(jwt);

            Optional<ProfileEntity> optional = profileRepository.findById(jwtDTO.getId());
            if (optional.isEmpty()) {
                log.warn("Profile not found {}", jwtDTO.getId());
                throw new AppBadException("Profile not found");
            }
            ProfileEntity entity = optional.get();
            if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
                log.warn("Profile in wrong status {}", entity.getStatus());
                throw new AppBadException("Profile in wrong status");
            }
            profileRepository.updateStatus(entity.getId(), ProfileStatus.ACTIVE);
        } catch (JwtException e) {
            log.warn("Please tyre again. {}", jwt);
            throw new AppBadException("Please try again.");
        }
        return null;
    }


    public Boolean registrationByPhoneNumber(RegistrationDTO dto) {

        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(optional.get());
            } else {
                throw new AppBadException("Email exists");
            }
        }

        LocalDateTime from = LocalDateTime.now().minusMinutes(1);
        LocalDateTime to = LocalDateTime.now();

        if (smsHistoryRepository.countSendSMS(dto.getPhone(), from, to) >= 3) {
            throw new AppBadException("To many attempt. Please try after 1 minute.");
        }


        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setStatus(ProfileStatus.REGISTRATION);
        entity.setRole(ProfileRole.ROLE_USER);
        profileRepository.save(entity);

        //send verification code (email/sms)

        String code = RandomUtil.getRandomSmsCode();
        smsServerService.send(dto.getPhone(), "Kun uz Test verification code: ", code);

        return true;
    }




}
