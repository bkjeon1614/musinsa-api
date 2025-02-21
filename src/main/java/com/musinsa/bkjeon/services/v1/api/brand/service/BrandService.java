package com.musinsa.bkjeon.services.v1.api.brand.service;

import com.musinsa.bkjeon.core.exception.ValidationException;
import com.musinsa.bkjeon.feature.domain.Brand;
import com.musinsa.bkjeon.feature.enums.common.response.ResponseCode;
import com.musinsa.bkjeon.feature.repository.brand.BrandRepository;
import com.musinsa.bkjeon.services.v1.api.brand.mapper.BrandMapper;
import com.musinsa.bkjeon.services.v1.api.brand.model.request.BrandRequest;
import com.musinsa.bkjeon.services.v1.api.brand.model.response.BrandResponse;
import com.musinsa.bkjeon.services.v1.api.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    @Transactional
    public ApiResponse<BrandResponse> insertBrand(BrandRequest request) {
        Brand brand = brandRepository.findByBrandName(request.brandName());
        if (Objects.nonNull(brand)) {
            throw new ValidationException(ResponseCode.EXIST_BRAND);
        }

        Brand result = brandRepository.save(BrandMapper.INSTANCE.toBrand(request));
        return ApiResponse.res(BrandMapper.INSTANCE.toBrandResponse(result));
    }

    @Transactional
    public ApiResponse<BrandResponse> updateBrand(Long brandNo, BrandRequest request) {
        Optional<Brand> brandInfo = brandRepository.findById(brandNo);
        if (brandInfo.isEmpty()) {
            throw new ValidationException(ResponseCode.EMPTY_BRAND);
        }

        Brand brand = brandInfo.get();
        brand.toUpdateBrandName(request.brandName());

        return ApiResponse.res(BrandMapper.INSTANCE.toBrandResponse(brand));
    }

    @Transactional
    public ApiResponse<BrandResponse> deleteBrand(Long brandNo) {
        Optional<Brand> brandInfo = brandRepository.findById(brandNo);
        if (brandInfo.isEmpty()) {
            throw new ValidationException(ResponseCode.EMPTY_BRAND);
        }

        brandRepository.deleteById(brandNo);
        return ApiResponse.res(BrandMapper.INSTANCE.toBrandResponse(brandInfo.get()));
    }

}
