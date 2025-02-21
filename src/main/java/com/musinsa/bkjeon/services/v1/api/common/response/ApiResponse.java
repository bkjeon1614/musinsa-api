package com.musinsa.bkjeon.services.v1.api.common.response;

import com.musinsa.bkjeon.feature.enums.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "API 공통 응답 모델")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    @Schema(description = "상태 코드")
    private int statusCode;

    @Schema(description = "응답 메시지")
    private String responseMessage;

    @Schema(description = "응답 결과")
    private T data;

    public static<T> ApiResponse<T> res(
        final T responseData
    ) {
        int statusCode = ResponseCode.OK.getHttpStatusCode();
        String responseMessage = ResponseCode.OK.getMessage();

        if (responseData == null) {
            statusCode = ResponseCode.NOT_CONTENT.getHttpStatusCode();
            responseMessage = ResponseCode.NOT_CONTENT.getMessage();
        }

        return ApiResponse.<T>builder()
            .statusCode(statusCode)
            .responseMessage(responseMessage)
            .data(responseData)
            .build();
    }

    public static<T> ApiResponse<T> res(
        final int statusCode,
        final String responseMessage
    ) {
        return ApiResponse.<T>builder()
            .statusCode(statusCode)
            .responseMessage(responseMessage)
            .build();
    }

    public static<T> ApiResponse<T> res(
        final int statusCode,
        final String responseMessage,
        final T responseData
    ) {
        return ApiResponse.<T>builder()
            .statusCode(statusCode)
            .responseMessage(responseMessage)
            .data(responseData)
            .build();
    }

}