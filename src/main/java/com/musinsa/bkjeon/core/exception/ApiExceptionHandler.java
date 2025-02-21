package com.musinsa.bkjeon.core.exception;

import com.musinsa.bkjeon.feature.enums.common.response.ResponseCode;
import com.musinsa.bkjeon.services.v1.api.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Validation ExceptionHandler
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ApiResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.get(0).getDefaultMessage() + " (" + fieldErrors.get(0).getField() + ")";

        log.error("=================== handleMethodArgumentNotValidException Error !! " + e);

        return ApiResponse.builder()
            .statusCode(ResponseCode.INVALID_INPUT_VALUE_BINDING_ERROR.getHttpStatusCode())
            .responseMessage(message)
            .build();
    }

    /**
     * ModelAttribute ExceptionHandler
     */
    @ExceptionHandler(BindException.class)
    private ApiResponse<Object> handleBindException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = ResponseCode.INVALID_INPUT_VALUE_BINDING_ERROR.getMessage() + ": "
            + fieldErrors.get(0).getDefaultMessage();

        log.error("=================== handleBindException Error !!", e);

        return ApiResponse.builder()
            .statusCode(ResponseCode.INVALID_INPUT_VALUE_BINDING_ERROR.getHttpStatusCode())
            .responseMessage(message)
            .build();
    }

    /**
     * 미 지원 HTTP method ExceptionHandler
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private ApiResponse<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("=================== handleHttpRequestMethodNotSupportedException Error !!", e);
        return ApiResponse.builder()
            .statusCode(ResponseCode.METHOD_NOT_ALLOWED.getHttpStatusCode())
            .responseMessage(ResponseCode.METHOD_NOT_ALLOWED.getMessage())
            .build();
    }

    /**
     * NullPointerException ExceptionHandler
     */
    @ExceptionHandler(NullPointerException.class)
    private ApiResponse<Object> handleNpeException(NullPointerException e) {
        log.error("=================== handleNpeException Error !!", e);
        return ApiResponse.builder()
            .statusCode(ResponseCode.INTERNAL_SERVER_ERROR.getHttpStatusCode())
            .responseMessage(ResponseCode.INTERNAL_SERVER_ERROR.getMessage())
            .build();
    }

    /**
     * Exception ExceptionHandler
     */
    @ExceptionHandler(Exception.class)
    private ApiResponse<Object> handleException(Exception e) {
        log.error("=================== handleException Error !!", e);
        return ApiResponse.builder()
            .statusCode(ResponseCode.INTERNAL_SERVER_ERROR.getHttpStatusCode())
            .responseMessage(ResponseCode.INTERNAL_SERVER_ERROR.getMessage())
            .build();
    }

    /**
     * Product ExceptionHandler
     */
    @ExceptionHandler(ValidationException.class)
    private ApiResponse<Object> handleValidationException(ValidationException e) {
        log.error("=================== handleValidationException Error !!", e);
        return ApiResponse.builder()
            .statusCode(e.getStatusCode())
            .responseMessage(e.getMessage())
            .build();
    }

}