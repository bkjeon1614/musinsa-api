package com.musinsa.bkjeon.services.v1.api.brand.controller;

import com.musinsa.bkjeon.services.v1.api.brand.model.request.BrandRequest;
import com.musinsa.bkjeon.services.v1.api.brand.model.response.BrandResponse;
import com.musinsa.bkjeon.services.v1.api.brand.service.BrandService;
import com.musinsa.bkjeon.services.v1.api.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "brand", description = "브랜드 API")
@RestController
@RequestMapping("/v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @Operation(summary = "브랜드 등록", description = "브랜드 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponse<BrandResponse>> setBrand(@Valid @RequestBody BrandRequest request) {
        return ResponseEntity.ok(brandService.insertBrand(request));
    }

    @Operation(summary = "브랜드 수정", description = "브랜드 정보를 수정하는 API")
    @Parameter(name = "brandNo", description = "브랜드 번호", required = true)
    @PutMapping("/{brandNo}")
    public ResponseEntity<ApiResponse<BrandResponse>> putBrand(
        @PathVariable("brandNo") Long brandNo,
        @Valid @RequestBody BrandRequest request) {
        return ResponseEntity.ok(brandService.updateBrand(brandNo, request));
    }

    @Operation(summary = "브랜드 삭제", description = "브랜드 정보를 삭제하는 API")
    @Parameter(name = "brandNo", description = "브랜드 번호", required = true)
    @DeleteMapping("/{brandNo}")
    public ResponseEntity<ApiResponse<BrandResponse>> delBrand(@PathVariable("brandNo") Long brandNo) {
        return ResponseEntity.ok(brandService.deleteBrand(brandNo));
    }

}