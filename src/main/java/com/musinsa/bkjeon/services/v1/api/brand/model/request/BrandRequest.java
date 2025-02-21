package com.musinsa.bkjeon.services.v1.api.brand.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record BrandRequest(
    @NotEmpty(message = "브랜드명이 누락되었습니다.")
    String brandName
) {}
