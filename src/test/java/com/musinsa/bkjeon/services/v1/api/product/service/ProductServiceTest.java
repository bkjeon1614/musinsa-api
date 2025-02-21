package com.musinsa.bkjeon.services.v1.api.product.service;

import com.musinsa.bkjeon.feature.enums.common.response.ResponseCode;
import com.musinsa.bkjeon.services.v1.api.common.response.ApiResponse;
import com.musinsa.bkjeon.services.v1.api.product.model.request.ProductRequest;
import com.musinsa.bkjeon.services.v1.api.product.model.request.ProductSearchRequest;
import com.musinsa.bkjeon.services.v1.api.product.model.response.CategoryMinPriceProductResponse;
import com.musinsa.bkjeon.services.v1.api.product.model.response.ProductResponse;
import com.musinsa.bkjeon.services.v1.api.product.model.response.SingleCategoryPriceRangeResponse;
import com.musinsa.bkjeon.services.v1.api.product.model.response.SingleBrandLowestPriceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("카테고리별 브랜드 및 가격 리스트 조회 테스트")
    void selectCategoryMinPriceProductListTest() {
        // given & when
        ApiResponse<CategoryMinPriceProductResponse> result = productService.selectCategoryMinPriceProductList();

        // then
        assertThat(result.getStatusCode()).isEqualTo(ResponseCode.OK.getHttpStatusCode());
        assertThat(result.getResponseMessage()).isEqualTo(ResponseCode.OK.getMessage());
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData().totalPrice()).isEqualTo("34,100");
        assertThat(result.getData().list().get(0).categoryName()).isEqualTo("스니커즈");
        assertThat(result.getData().list().get(0).brandName()).isEqualTo("G");
        assertThat(result.getData().list().get(0).price()).isEqualTo("9,000");
    }

    @Test
    @DisplayName("단일 브랜드 카테고리별 최저가 조회 테스트")
    void selectSingleBrandLowestPriceResultTest() {
        // given & when
        ApiResponse<SingleBrandLowestPriceResponse> result = productService.selectSingleBrandLowestPriceResult();

        // then
        assertThat(result.getStatusCode()).isEqualTo(ResponseCode.OK.getHttpStatusCode());
        assertThat(result.getResponseMessage()).isEqualTo(ResponseCode.OK.getMessage());
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData().최저가().브랜드()).isEqualTo("D");
        assertThat(result.getData().최저가().카테고리().get(0).카테고리()).isEqualTo("상의");
        assertThat(result.getData().최저가().카테고리().get(0).가격()).isEqualTo("10,100");
        assertThat(result.getData().최저가().총액()).isEqualTo("36,100");
    }

    @Test
    @DisplayName("카테고리명으로 최저/최고 가격 상품 조회 테스트")
    void selectSingleCategoryPriceRangeTest() {
        // given
        ProductSearchRequest request = ProductSearchRequest.builder().categoryName("상의").build();

        // when
        ApiResponse<SingleCategoryPriceRangeResponse> result = productService.selectSingleCategoryPriceRange(request);

        // then
        assertThat(result.getStatusCode()).isEqualTo(ResponseCode.OK.getHttpStatusCode());
        assertThat(result.getResponseMessage()).isEqualTo(ResponseCode.OK.getMessage());
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData().카테고리()).isEqualTo("상의");
        assertThat(result.getData().최저가().get(0).브랜드()).isEqualTo("C");
        assertThat(result.getData().최저가().get(0).가격()).isEqualTo("10,000");
        assertThat(result.getData().최고가().get(0).브랜드()).isEqualTo("I");
        assertThat(result.getData().최고가().get(0).가격()).isEqualTo("11,400");
    }

    @Test
    @DisplayName("상품 등록 테스트")
    @Transactional
    void insertProductTest() {
        // given
        ProductRequest request = ProductRequest.builder().brandNo(10L).categoryNo(1L).price(1111L).build();

        // when
        ApiResponse<ProductResponse> result = productService.insertProduct(request);

        // then
        assertThat(result.getStatusCode()).isEqualTo(ResponseCode.OK.getHttpStatusCode());
        assertThat(result.getResponseMessage()).isEqualTo(ResponseCode.OK.getMessage());
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData().brand().getBrandNo()).isEqualTo(10L);
        assertThat(result.getData().brand().getBrandName()).isEqualTo("J");
        assertThat(result.getData().category().getCategoryNo()).isEqualTo(1L);
        assertThat(result.getData().price()).isEqualTo(1111L);
    }

    @Test
    @DisplayName("상품 수정 테스트")
    @Transactional
    void updateProductTest() {
        // given
        ProductRequest request = ProductRequest.builder().brandNo(10L).categoryNo(2L).price(1111L).build();

        // when
        ApiResponse<ProductResponse> result = productService.updateProduct(2L, request);

        // then
        assertThat(result.getStatusCode()).isEqualTo(ResponseCode.OK.getHttpStatusCode());
        assertThat(result.getResponseMessage()).isEqualTo(ResponseCode.OK.getMessage());
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData().brand().getBrandNo()).isEqualTo(10L);
        assertThat(result.getData().category().getCategoryNo()).isEqualTo(2L);
        assertThat(result.getData().price()).isEqualTo(1111L);
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    @Transactional
    void deleteProductTest() {
        // given
        Long brandNo = 1L;
        String brandName = "A";
        Long categoryNo = 1L;
        String categoryName = "상의";
        Long price = 11200L;

        // when
        ApiResponse<ProductResponse> result = productService.deleteProduct(1L);

        // then
        assertThat(result.getStatusCode()).isEqualTo(ResponseCode.OK.getHttpStatusCode());
        assertThat(result.getResponseMessage()).isEqualTo(ResponseCode.OK.getMessage());
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData().brand().getBrandNo()).isEqualTo(brandNo);
        assertThat(result.getData().brand().getBrandName()).isEqualTo(brandName);
        assertThat(result.getData().category().getCategoryNo()).isEqualTo(categoryNo);
        assertThat(result.getData().category().getCategoryName()).isEqualTo(categoryName);
        assertThat(result.getData().price()).isEqualTo(price);
    }

}
