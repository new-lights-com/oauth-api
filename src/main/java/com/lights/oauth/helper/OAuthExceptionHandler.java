package com.lights.oauth.helper;

import com.lights.oauth.exception.AuthenticationFailedException;
import com.lights.oauth.exception.IncorrectEmailException;
import com.lights.oauth.exception.InternalServerException;
import com.lights.oauth.model.response.AutheticateErrorResponse;
import com.lights.oauth.model.ErrorDetails;
import com.lights.oauth.model.response.ErrorResponse;
import com.lights.oauth.util.ApplicationUtil;
import com.lights.oauth.util.ExceptionsConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class OAuthExceptionHandler {

    @ExceptionHandler(AuthenticationFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleUserNotFoundException(AuthenticationFailedException e) {
        AutheticateErrorResponse errorResponse = AutheticateErrorResponse.builder()
                .errorCode("AUTHENTICATION_FAILED")
                .Message("Invalid UserId/Password")
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(IncorrectEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleUserNotFoundException(IncorrectEmailException e) {
        AutheticateErrorResponse errorResponse = AutheticateErrorResponse.builder()
                .errorCode("INCORRECT_EMAIL_ID")
                .Message("Please enter a valid Email Id ")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * @param ex
     * @return ResponseEntity
     * @description: This handler receives the control when MethodArgumentNotValidException is thrown. It usually
     * gets thrown
     * when model's annotation validations fail. We are using @Valid annotation on RequestBody on controller level,
     * when there is validation error in request body, controller throws the MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> processValidationError(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        List<ErrorDetails> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().stream().filter(FieldError.class::isInstance).map(FieldError.class::cast).forEach(fieldError -> {
            errors.add(ApplicationUtil.buildValidationError(fieldError.getDefaultMessage(), fieldError.getField()));
        });
        ErrorResponse errorResponse = ApplicationUtil.buildErrorResponse(ExceptionsConstants.VALIDATION_ERROR, errors);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * @param ex
     * @return ResponseEntity
     * @description: This handler receives the control when NoHandlerFoundException is thrown.
     * It usually gets thrown when invalid url is provided.
     * For ex. /abc
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.warn(ex.getMessage());
        String message = String.format("Could not find the %s method for URL %s", ex.getHttpMethod(),
                ex.getRequestURL());
        ErrorResponse errorResponse = ApplicationUtil.buildErrorResponse(message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * @param ex
     * @return ResponseEntity
     * @description: This handler receives the control when IllegalArgumentException is thrown.
     * Its is an custom exception we created to throw this exception when Object mapper fails to convert the
     * respective objects.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        List<ErrorDetails> errors = new ArrayList<>();
        errors.add(ApplicationUtil.buildValidationError(ExceptionsConstants.INVALID_JSON));
        ErrorResponse errorResponse = ApplicationUtil.buildErrorResponse(ExceptionsConstants.VALIDATION_ERROR, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * @param ex
     * @return ResponseEntity
     * @description: This handler receives the control when HttpMessageNotReadableException is thrown.
     * It usually gets thrown when invalid json body is provided.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleMissingParams(HttpMessageNotReadableException ex) {
        log.error(ex.getMessage());
        List<ErrorDetails> errors = new ArrayList<>();
        errors.add(ApplicationUtil.buildValidationError(ExceptionsConstants.INVALID_JSON));
        ErrorResponse errorResponse = ApplicationUtil.buildErrorResponse(ExceptionsConstants.VALIDATION_ERROR, errors);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * @param ex
     * @return ResponseEntity
     * @description: This handler receives the control when HttpMediaTypeNotSupportedException is thrown.
     * It usually gets thrown when invalid Content-Type header is provided
     * For ex. Content-Type = application/xml.
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        log.error(ex.getMessage());
        StringBuilder builder = new StringBuilder();
        if (ex.getContentType() == null) {
            builder.append(ex.getMessage());
        } else {
            builder.append(ex.getContentType());
            builder.append(" media type is not supported. Supported media types are ");
            ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        }
        ErrorResponse errorResponse = ApplicationUtil.buildErrorResponse(builder.toString());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error(ex.getMessage());
        ErrorResponse errorResponse = ApplicationUtil.buildErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    /**
     * @param ex
     * @return ResponseEntity
     * @description: This handler receives the control when rest of the exception occur which are not handled by
     * abouve handlers.
     * As of now it includes JsonMappingException when our custom converter fails, DataBaseException when there is DB
     * error.
     */
    @ExceptionHandler(value = {InternalServerException.class, Exception.class, Throwable.class})
    public ResponseEntity<?> handleAllExceptions(Throwable ex) {
        log.error(String.format("%s has occured", ex.getClass().getSimpleName()));
        log.error(ex.getMessage());
        ErrorResponse errorResponse = ApplicationUtil.buildErrorResponse(ExceptionsConstants.INTERNAL_SERVER_ERROR);
        return ResponseEntity.internalServerError().body(errorResponse);
    }

}
