package com.musinsa.bkjeon.services.v1.api.product.controller;

import com.musinsa.bkjeon.services.v1.api.common.response.ApiResponse;
import com.musinsa.bkjeon.services.v1.api.product.model.request.ProductRequest;
import com.musinsa.bkjeon.services.v1.api.product.model.request.ProductSearchRequest;
import com.musinsa.bkjeon.services.v1.api.product.model.response.CategoryMinPriceProductResponse;
import com.musinsa.bkjeon.services.v1.api.product.model.response.ProductResponse;
import com.musinsa.bkjeon.services.v1.api.product.model.response.SingleCategoryPriceRangeResponse;
import com.musinsa.bkjeon.services.v1.api.product.model.response.SingleBrandLowestPriceResponse;
import com.musinsa.bkjeon.services.v1.api.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "product", description = "상품 API")
@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // brands-prices
    @Operation(summary = "카테고리별 브랜드 및 가격 리스트 조회",
        description = "카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API")
    @GetMapping("/category/minPriceProducts")
    public ResponseEntity<ApiResponse<CategoryMinPriceProductResponse>> getCategoryMinPriceProductList() {
        return ResponseEntity.ok(productService.selectCategoryMinPriceProductList());
    }

    @Operation(summary = "단일 브랜드 카테고리별 최저가 조회",
        description = "단일 브랜드로 모든 카테고리 상품에 대해 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API")
    @GetMapping("/singleBrand/lowestPrices")
    public ResponseEntity<ApiResponse<SingleBrandLowestPriceResponse>> getSingleBrandLowestPriceResult() {
        return ResponseEntity.ok(productService.selectSingleBrandLowestPriceResult());
    }

    @Operation(summary = "카테고리명으로 최저/최고 가격 상품 조회",
        description = "카테고리명으로 최저/최고 가격 상품 조회하는 API")
    @Parameter(name = "categoryName", description = "카테고리명")
    @GetMapping("/singleCategory/priceRanges")
    public ResponseEntity<ApiResponse<SingleCategoryPriceRangeResponse>> getSingleCategoryPriceRangeResult(ProductSearchRequest request) {
        return ResponseEntity.ok(productService.selectSingleCategoryPriceRange(request));
    }

    @Operation(summary = "상품 등록", description = "상품을 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> setProduct(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.insertProduct(request));
    }

    @Operation(summary = "상품 수정", description = "상품을 수정하는 API")
    @PutMapping("/{productNo}")
    public ResponseEntity<ApiResponse<ProductResponse>> putProduct(
        @PathVariable("productNo") Long productNo,
        @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(productNo, request));
    }

    @Operation(summary = "상품 삭제", description = "상품을 삭제하는 API")
    @DeleteMapping("/{productNo}")
    public ResponseEntity<ApiResponse<ProductResponse>> delProduct(@PathVariable("productNo") Long productNo) {
        return ResponseEntity.ok(productService.deleteProduct(productNo));
    }

}
