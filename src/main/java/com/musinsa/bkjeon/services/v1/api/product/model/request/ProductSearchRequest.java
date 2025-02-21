package com.musinsa.bkjeon.services.v1.api.product.model.request;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;

@Hidden
@Builder
public record ProductSearchRequest(String categoryName) {}