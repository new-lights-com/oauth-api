package com.lights.oauth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lights.oauth.model.ErrorDetails;
import com.lights.oauth.model.response.ErrorResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ApplicationUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String generateToken(Object payload, Integer expiry) throws JsonProcessingException {
        String token = Jwts.builder()
                .setSubject(objectMapper.writeValueAsString(payload))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiry))
                .signWith(SignatureAlgorithm.HS256, "key".getBytes()).compact();
        return token;
    }

    public static Claims verifyToken(String token) {
        Claims claimsJws = Jwts.parser()
                .setSigningKey("key".getBytes())
                .parseClaimsJws(token)
                .getBody();
        return claimsJws;
    }

    public static ErrorResponse buildErrorResponse(String message, List<ErrorDetails> errors) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorId(UUID.randomUUID());
        errorResponse.setErrors(errors);
        errorResponse.setMessage(message);
        return errorResponse;
    }

    public static ErrorResponse buildErrorResponse(String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorId(UUID.randomUUID());
        errorResponse.setMessage(message);
        return errorResponse;
    }

    public static ErrorDetails buildValidationError(String message, String field) {
        ErrorDetails error = new ErrorDetails();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setField(field);
        error.setMessage(message);
        return error;
    }

    public static ErrorDetails buildValidationError(String message) {
        ErrorDetails error = new ErrorDetails();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(message);
        return error;
    }
}
