package com.musinsa.bkjeon.core.exception;

import com.musinsa.bkjeon.feature.enums.common.response.ResponseCode;
import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private final int statusCode;
    private final String message;

    public ValidationException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.statusCode = responseCode.getHttpStatusCode();
        this.message = responseCode.getMessage();
    }

}