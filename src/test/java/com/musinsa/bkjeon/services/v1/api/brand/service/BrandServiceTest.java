package com.musinsa.bkjeon.services.v1.api.brand.service;

import com.musinsa.bkjeon.feature.enums.common.response.ResponseCode;
import com.musinsa.bkjeon.services.v1.api.brand.model.request.BrandRequest;
import com.musinsa.bkjeon.services.v1.api.brand.model.response.BrandResponse;
import com.musinsa.bkjeon.services.v1.api.common.response.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BrandServiceTest {

    @Autowired
    private BrandService brandService;

    @Test
    @DisplayName("브랜드 등록 테스트")
    void insertBrandTest() {
        // given
        BrandRequest request = BrandRequest.builder().brandName("AB").build();

        // when
        ApiResponse<BrandResponse> result = brandService.insertBrand(request);

        // then
        assertThat(result.getStatusCode()).isEqualTo(ResponseCode.OK.getHttpStatusCode());
        assertThat(result.getResponseMessage()).isEqualTo(ResponseCode.OK.getMessage());
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData().brandName()).isEqualTo("AB");
    }

    @Test
    @DisplayName("브랜드 수정 테스트")
    void updateBrandTest() {
        // given
        BrandRequest request = BrandRequest.builder().brandName("AB").build();

        // when
        ApiResponse<BrandResponse> result = brandService.updateBrand(1L, request);

        // then
        assertThat(result.getStatusCode()).isEqualTo(ResponseCode.OK.getHttpStatusCode());
        assertThat(result.getResponseMessage()).isEqualTo(ResponseCode.OK.getMessage());
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData().brandName()).isEqualTo("AB");
    }

    @Test
    @DisplayName("브랜드 삭제 테스트")
    void deleteBrandTest() {
        // given
        Long brandNo = 1L;

        // when
        ApiResponse<BrandResponse> result = brandService.deleteBrand(1L);

        // then
        assertThat(result.getStatusCode()).isEqualTo(ResponseCode.OK.getHttpStatusCode());
        assertThat(result.getResponseMessage()).isEqualTo(ResponseCode.OK.getMessage());
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData().brandNo()).isEqualTo(brandNo);
    }

}
