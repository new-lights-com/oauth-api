package com.lights.oauth.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserTokenPayload {
    private String userId;
    private String username;
    private String teamId;
    private String displayName;
    private String realName;
    private String title;
    private String email;
    private String imageOriginal;
}

