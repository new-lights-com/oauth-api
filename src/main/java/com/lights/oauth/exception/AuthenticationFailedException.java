package com.lights.oauth.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthenticationFailedException extends RuntimeException {


}
