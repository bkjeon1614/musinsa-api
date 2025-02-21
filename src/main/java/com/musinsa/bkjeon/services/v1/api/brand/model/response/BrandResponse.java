package com.musinsa.bkjeon.services.v1.api.brand.model.response;

import lombok.Builder;

@Builder
public record BrandResponse(Long brandNo, String brandName) {}
