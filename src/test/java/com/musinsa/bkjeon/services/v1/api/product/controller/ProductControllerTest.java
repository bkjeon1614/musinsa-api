package com.musinsa.bkjeon.services.v1.api.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.bkjeon.feature.domain.Brand;
import com.musinsa.bkjeon.feature.domain.Category;
import com.musinsa.bkjeon.feature.domain.CategoryMinPriceProduct;
import com.musinsa.bkjeon.services.v1.api.common.response.ApiResponse;
import com.musinsa.bkjeon.services.v1.api.product.model.request.ProductRequest;
import com.musinsa.bkjeon.services.v1.api.product.model.request.ProductSearchRequest;
import com.musinsa.bkjeon.services.v1.api.product.model.response.CategoryMinPriceProductResponse;
import com.musinsa.bkjeon.services.v1.api.product.model.response.ProductResponse;
import com.musinsa.bkjeon.services.v1.api.product.model.response.SingleBrandLowestPriceResponse;
import com.musinsa.bkjeon.services.v1.api.product.model.response.SingleCategoryPriceRangeResponse;
import com.musinsa.bkjeon.services.v1.api.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tags({
    @Tag("integration"),
    @Tag("unit")
})
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    private static final String REQUEST_URL_PREFIX = "/v1/products";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("카테고리별 브랜드 및 가격 리스트 조회 테스트")
    void getCategoryMinPriceProductListTest() throws Exception {
        // given
        List<CategoryMinPriceProduct> list = new ArrayList<>();
        list.add(CategoryMinPriceProduct.builder()
            .categoryName("스니커즈")
            .brandName("G")
            .price("9,000")
            .build());

        CategoryMinPriceProductResponse response = CategoryMinPriceProductResponse.builder()
            .totalPrice("34,100")
            .list(list)
            .build();
        ApiResponse<CategoryMinPriceProductResponse> result = ApiResponse.res(response);
        given(productService.selectCategoryMinPriceProductList()).willReturn(result);

        // when, then
        mockMvc.perform(get(REQUEST_URL_PREFIX + "/category/minPriceProducts")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.totalPrice").value(result.getData().totalPrice()))
            .andExpect(jsonPath("$.data.list[0].categoryName")
                .value(result.getData().list().get(0).categoryName()))
            .andExpect(jsonPath("$.data.list[0].brandName")
                .value(result.getData().list().get(0).brandName()))
            .andExpect(jsonPath("$.data.list[0].price")
                .value(result.getData().list().get(0).price()))
            .andDo(print());
    }

    @Test
    @DisplayName("단일 브랜드 카테고리별 최저가 조회 테스트")
    void getSingleBrandLowestPriceResultTest() throws Exception {
        // given
        List<SingleBrandLowestPriceResponse.MinPrice.Category> list = new ArrayList<>();
        list.add(SingleBrandLowestPriceResponse.MinPrice.Category.builder().카테고리("상의").가격("10,100").build());

        SingleBrandLowestPriceResponse response = SingleBrandLowestPriceResponse.builder()
            .최저가(SingleBrandLowestPriceResponse.MinPrice.builder()
                .브랜드("D")
                .카테고리(list)
                .총액("36,100")
                .build())
            .build();
        ApiResponse<SingleBrandLowestPriceResponse> result = ApiResponse.res(response);
        given(productService.selectSingleBrandLowestPriceResult()).willReturn(result);

        // when, then
        mockMvc.perform(get(REQUEST_URL_PREFIX + "/singleBrand/lowestPrices")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.최저가.브랜드").value(result.getData().최저가().브랜드()))
            .andExpect(jsonPath("$.data.최저가.카테고리[0].카테고리")
                .value(result.getData().최저가().카테고리().get(0).카테고리()))
            .andExpect(jsonPath("$.data.최저가.카테고리[0].가격")
                .value(result.getData().최저가().카테고리().get(0).가격()))
            .andExpect(jsonPath("$.data.최저가.총액").value(result.getData().최저가().총액()))
            .andDo(print());
    }

    @Test
    @DisplayName("카테고리명으로 최저/최고 가격 상품 조회 테스트")
    void getSingleCategoryPriceRangeResultTest() throws Exception {
        // given
        List<SingleCategoryPriceRangeResponse.PriceInfo> minPriceProductList = new ArrayList<>();
        minPriceProductList.add(SingleCategoryPriceRangeResponse.PriceInfo.builder().브랜드("C").가격("10,000").build());
        List<SingleCategoryPriceRangeResponse.PriceInfo> maxPriceProductList = new ArrayList<>();
        maxPriceProductList.add(SingleCategoryPriceRangeResponse.PriceInfo.builder().브랜드("I").가격("11,400").build());

        String categoryName = "상의";
        SingleCategoryPriceRangeResponse response = SingleCategoryPriceRangeResponse.builder()
            .카테고리(categoryName)
            .최저가(minPriceProductList)
            .최고가(maxPriceProductList)
            .build();
        ApiResponse<SingleCategoryPriceRangeResponse> result = ApiResponse.res(response);
        given(productService.selectSingleCategoryPriceRange(ProductSearchRequest.builder()
            .categoryName(categoryName)
            .build()))
            .willReturn(result);

        // when, then
        mockMvc.perform(get(REQUEST_URL_PREFIX + "/singleCategory/priceRanges?categoryName=" + categoryName)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.카테고리").value(result.getData().카테고리()))
            .andExpect(jsonPath("$.data.최저가[0].브랜드")
                .value(result.getData().최저가().get(0).브랜드()))
            .andExpect(jsonPath("$.data.최저가[0].가격")
                .value(result.getData().최저가().get(0).가격()))
            .andExpect(jsonPath("$.data.최고가[0].브랜드")
                .value(result.getData().최고가().get(0).브랜드()))
            .andExpect(jsonPath("$.data.최고가[0].가격")
                .value(result.getData().최고가().get(0).가격()))
            .andDo(print());
    }

    @Test
    @DisplayName("상품 등록 테스트")
    void setProductTest() throws Exception {
        // given
        ProductResponse response = ProductResponse.builder()
            .brand(Brand.builder().brandNo(10L).brandName("J").build())
            .category(Category.builder().categoryNo(1L).build())
            .price(1111L)
            .build();
        ApiResponse<ProductResponse> result = ApiResponse.res(response);
        given(productService.insertProduct(any(ProductRequest.class))).willReturn(result);

        // when, then
        ProductRequest request = ProductRequest.builder().brandNo(10L).categoryNo(1L).price(1111L).build();
        mockMvc.perform(post(REQUEST_URL_PREFIX)
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.brand.brandNo").value(result.getData().brand().getBrandNo()))
            .andExpect(jsonPath("$.data.brand.brandName").value(result.getData().brand().getBrandName()))
            .andExpect(jsonPath("$.data.category.categoryNo")
                .value(result.getData().category().getCategoryNo()))
            .andExpect(jsonPath("$.data.price").value(result.getData().price()))
            .andDo(print());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void putProductTest() throws Exception {
        // given
        ProductResponse response = ProductResponse.builder()
            .brand(Brand.builder().brandNo(10L).build())
            .category(Category.builder().categoryNo(2L).build())
            .price(1111L)
            .build();
        ApiResponse<ProductResponse> result = ApiResponse.res(response);
        given(productService.updateProduct(any(Long.class), any(ProductRequest.class))).willReturn(result);

        // when, then
        ProductRequest request = ProductRequest.builder().brandNo(10L).categoryNo(2L).price(1111L).build();
        mockMvc.perform(put(REQUEST_URL_PREFIX + "/2")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.brand.brandNo").value(result.getData().brand().getBrandNo()))
            .andExpect(jsonPath("$.data.category.categoryNo")
                .value(result.getData().category().getCategoryNo()))
            .andExpect(jsonPath("$.data.price").value(result.getData().price()))
            .andDo(print());
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void delProductTest() throws Exception {
        // given
        ProductResponse response = ProductResponse.builder()
            .brand(Brand.builder().brandNo(1L).brandName("A").build())
            .category(Category.builder().categoryNo(1L).categoryName("상의").build())
            .price(11200L)
            .build();
        ApiResponse<ProductResponse> result = ApiResponse.res(response);
        given(productService.deleteProduct(any(Long.class))).willReturn(result);

        // when, then
        mockMvc.perform(delete(REQUEST_URL_PREFIX + "/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.brand.brandNo").value(result.getData().brand().getBrandNo()))
            .andExpect(jsonPath("$.data.brand.brandName").value(result.getData().brand().getBrandName()))
            .andExpect(jsonPath("$.data.category.categoryNo")
                .value(result.getData().category().getCategoryNo()))
            .andExpect(jsonPath("$.data.category.categoryName")
                .value(result.getData().category().getCategoryName()))
            .andExpect(jsonPath("$.data.price").value(result.getData().price()))
            .andDo(print());
    }

}
