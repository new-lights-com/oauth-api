package com.lights.oauth.service;

import com.lights.oauth.model.UserTokenPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOTPToUser(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("new-lights@gmail.com");
        message.setTo(toEmail);
        message.setSubject("OTP for New Lights");
        message.setText(otp);
        javaMailSender.send(message);
        System.out.println("Message sent successfully");
    }

    public UserTokenPayload getUserProfile(String email) {
        return UserTokenPayload.builder()
                .email(email)
                .build();
    }
}
