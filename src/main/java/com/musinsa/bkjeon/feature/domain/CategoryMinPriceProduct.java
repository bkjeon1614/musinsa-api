package com.musinsa.bkjeon.feature.domain;

import lombok.Builder;

@Builder
public record CategoryMinPriceProduct(String categoryName, String brandName, String price) {}