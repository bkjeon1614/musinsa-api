package com.musinsa.bkjeon.services.v1.api.product.model.response;

import lombok.Builder;

import java.util.List;

@Builder
public record SingleBrandLowestPriceResponse(MinPrice 최저가) {

    @Builder
    public record MinPrice(String 브랜드, List<Category> 카테고리, String 총액) {

        @Builder
        public record Category(String 카테고리, String 가격) {}

    }

}