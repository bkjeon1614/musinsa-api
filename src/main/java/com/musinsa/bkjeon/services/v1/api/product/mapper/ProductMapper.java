package com.musinsa.bkjeon.services.v1.api.product.mapper;

import com.musinsa.bkjeon.feature.domain.Product;
import com.musinsa.bkjeon.services.v1.api.product.model.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponse toProductResponse(Product product);

}