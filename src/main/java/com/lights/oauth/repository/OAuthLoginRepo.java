package com.lights.oauth.repository;

import com.lights.oauth.model.entity.OTPEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthLoginRepo extends JpaRepository<OTPEntity, Long> {

    OTPEntity save(OTPEntity OtpEntity);

    OTPEntity findByEmailAndOtp(String email, String otp);
}
