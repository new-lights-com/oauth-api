package com.lights.oauth.service;


import com.lights.oauth.exception.AuthenticationFailedException;
import com.lights.oauth.exception.IncorrectEmailException;
import com.lights.oauth.model.entity.OTPEntity;
import com.lights.oauth.repository.OAuthLoginRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class OAuthLoginService {

    @Autowired
    private OAuthLoginRepo oAuthLoginRepo;

    public String loginGenerateOTPService(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            Random random = new Random();
            String numbers = "0123456789";
            int length = 6;
            char[] otp = new char[length];
            for (int i = 0; i < length; i++) {
                otp[i] = numbers.charAt(random.nextInt(numbers.length()));
            }
            OTPEntity oTPEntity = OTPEntity.builder().email(email).otp(String.valueOf(otp)).build();
            oAuthLoginRepo.save(oTPEntity);
            return String.valueOf(otp);
        } else {
            throw new IncorrectEmailException();
        }
    }

    public OTPEntity loginAuthenticateOTPService(String email, String otp) {
        OTPEntity data = oAuthLoginRepo.findByEmailAndOtp(email, otp);
        if (data == null) {
            throw new AuthenticationFailedException();
        }
        return data;
    }

}
