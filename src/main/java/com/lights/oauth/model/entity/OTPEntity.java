package com.lights.oauth.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "login_otp")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OTPEntity {
    @Id
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "otp", nullable = false)
    private String otp;
}
