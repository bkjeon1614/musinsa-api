package com.musinsa.bkjeon.services.v1.api.product.model.response;

import com.musinsa.bkjeon.feature.domain.CategoryMinPriceProduct;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoryMinPriceProductResponse(String totalPrice, List<CategoryMinPriceProduct> list) {}