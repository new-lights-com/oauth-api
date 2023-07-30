package com.lights.oauth.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lights.oauth.model.ErrorDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private UUID errorId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorDetails> errors;
    private String message;
}
