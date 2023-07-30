package com.lights.oauth.conroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lights.oauth.model.UserTokenPayload;
import com.lights.oauth.service.EmailService;
import com.lights.oauth.service.OAuthLoginService;
import com.lights.oauth.util.ApplicationConstants;
import com.lights.oauth.util.ApplicationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/api/oauth")
public class OAuthLoginController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private OAuthLoginService oAuthLoginService;
    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/email/verification")
    @CrossOrigin(origins = "*")
    ResponseEntity<?> loginGenerateOTP(@Validated @RequestParam String email) {
        log.info("event=LoginController POST received on /email/verification");
        final String otp = oAuthLoginService.loginGenerateOTPService(email);
        emailService.sendOTPToUser(email, otp);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/email/authentication")
    @CrossOrigin(origins = "*")
    ResponseEntity<?> loginAuthenticateOTP(@Validated @RequestParam String email, String otp) throws JsonProcessingException {
        log.info("event=LoginController POST received on /email/authentication");
        oAuthLoginService.loginAuthenticateOTPService(email, otp);
        final UserTokenPayload userTokenPayload = emailService.getUserProfile(email);
        final String token = ApplicationUtil.generateToken(userTokenPayload, ApplicationConstants.TOKEN_EXPIRY);
        return ResponseEntity.ok().body(Collections.singletonMap("token", token));
    }

}
