package com.musinsa.bkjeon.services.v1.api.product.model.response;

import com.musinsa.bkjeon.feature.domain.Brand;
import com.musinsa.bkjeon.feature.domain.Category;
import lombok.Builder;

@Builder
public record ProductResponse(Long productNo, Long price, Brand brand, Category category) {}
