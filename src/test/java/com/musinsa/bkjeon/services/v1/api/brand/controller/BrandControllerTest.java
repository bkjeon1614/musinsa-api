package com.musinsa.bkjeon.services.v1.api.brand.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.bkjeon.services.v1.api.brand.model.request.BrandRequest;
import com.musinsa.bkjeon.services.v1.api.brand.model.response.BrandResponse;
import com.musinsa.bkjeon.services.v1.api.brand.service.BrandService;
import com.musinsa.bkjeon.services.v1.api.common.response.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
@WebMvcTest(BrandController.class)
class BrandControllerTest {

    private static final String REQUEST_URL_PREFIX = "/v1/brands";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService brandService;

    @Test
    @DisplayName("브랜드 등록")
    void setBrandTest() throws Exception {
        // given
        BrandResponse response = BrandResponse.builder().brandName("AB").build();
        ApiResponse<BrandResponse> result = ApiResponse.res(response);
        given(brandService.insertBrand(any(BrandRequest.class))).willReturn(result);

        // when, then
        BrandRequest request = BrandRequest.builder().brandName("AB").build();
        mockMvc.perform(post(REQUEST_URL_PREFIX)
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.brandName").value(result.getData().brandName()))
            .andDo(print());
    }

    @Test
    @DisplayName("브랜드 수정")
    void putBrandTest() throws Exception {
        // given
        BrandResponse response = BrandResponse.builder().brandName("AC").build();
        ApiResponse<BrandResponse> result = ApiResponse.res(response);
        given(brandService.updateBrand(any(Long.class), any(BrandRequest.class))).willReturn(result);

        // when, then
        BrandRequest request = BrandRequest.builder().brandName("AC").build();
        mockMvc.perform(put(REQUEST_URL_PREFIX + "/10")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.brandNo").value(result.getData().brandNo()))
            .andExpect(jsonPath("$.data.brandName").value(result.getData().brandName()))
            .andDo(print());
    }

    @Test
    @DisplayName("브랜드 삭제")
    void delBrandTest() throws Exception {
        // given
        BrandResponse response = BrandResponse.builder().brandNo(10L).build();
        ApiResponse<BrandResponse> result = ApiResponse.res(response);
        given(brandService.deleteBrand(any(Long.class))).willReturn(result);

        // when, then
        mockMvc.perform(delete(REQUEST_URL_PREFIX + "/10")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.brandNo").value(result.getData().brandNo()))
            .andDo(print());
    }

}
