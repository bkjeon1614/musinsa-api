package com.musinsa.bkjeon.feature.enums.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * API 응답 코드
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    OK(HttpStatus.OK.value(), "1000", HttpStatus.OK.getReasonPhrase()),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "1001", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
    INVALID_INPUT_VALUE_BINDING_ERROR(HttpStatus.FORBIDDEN.value(), "1002", HttpStatus.FORBIDDEN.getReasonPhrase()),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "1003", HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase()),
    NOT_CONTENT(HttpStatus.OK.value(), "1004", HttpStatus.NO_CONTENT.getReasonPhrase()),
    EXIST_BRAND(HttpStatus.BAD_REQUEST.value(), "2000", "이미 브랜드 데이터가 존재힙니다."),
    EMPTY_BRAND(HttpStatus.BAD_REQUEST.value(), "2001", "브랜드 데이터가 존재하지 않습니다."),
    EMPTY_BRAND_MIN_PRICE(HttpStatus.BAD_REQUEST.value(), "2002", "최저가 브랜드 데이터가 존재하지 않습니다."),
    EXIST_PRODUCT(HttpStatus.BAD_REQUEST.value(), "2100", "이미 상품 데이터가 존재힙니다."),
    EMPTY_PRODUCT(HttpStatus.BAD_REQUEST.value(), "2101", "상품 데이터가 존재하지 않습니다."),
    EXIST_CATEGORY(HttpStatus.BAD_REQUEST.value(), "2200", "이미 카테고리 데이터가 존재힙니다."),
    EMPTY_CATEGORY(HttpStatus.BAD_REQUEST.value(), "2201", "카테고리 데이터가 존재하지 않습니다.");

    private final int httpStatusCode;
    private final String statusCode;
    private final String message;

}