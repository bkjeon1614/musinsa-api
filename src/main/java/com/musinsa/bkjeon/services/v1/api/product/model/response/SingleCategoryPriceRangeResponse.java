package com.musinsa.bkjeon.services.v1.api.product.model.response;

import lombok.Builder;

import java.util.List;

@Builder
public record SingleCategoryPriceRangeResponse(
    String 카테고리,
    List<PriceInfo> 최저가,
    List<PriceInfo> 최고가
) {

    @Builder
    public record PriceInfo(String 브랜드, String 가격) {}

}