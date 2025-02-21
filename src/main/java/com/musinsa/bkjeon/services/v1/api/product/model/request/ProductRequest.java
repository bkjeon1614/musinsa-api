package com.musinsa.bkjeon.services.v1.api.product.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record ProductRequest(
    @NotNull(message = "카테고리번호가 누락되었습니다.")
    @Min(message = "0보다 큰 카테고리번호를 입력하여 주시길 바랍니다.", value = 1)
    Long categoryNo,

    @NotNull(message = "브랜드번호가 누락되었습니다.")
    @Min(message = "0보다 큰 브랜드번호를 입력하여 주시길 바랍니다.", value = 1)
    Long brandNo,

    @NotNull(message = "가격이 누락되었습니다.")
    @PositiveOrZero(message = "0보다 큰 가격을 입력하여 주시길 바랍니다.")
    Long price
) {}