package com.musinsa.bkjeon.services.v1.api.brand.mapper;

import com.musinsa.bkjeon.feature.domain.Brand;
import com.musinsa.bkjeon.services.v1.api.brand.model.request.BrandRequest;
import com.musinsa.bkjeon.services.v1.api.brand.model.response.BrandResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BrandMapper {

    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

    BrandResponse toBrandResponse(Brand brand);
    Brand toBrand(BrandRequest brandRequest);

}