package com.musinsa.bkjeon.services.v1.api.product.service;

import com.musinsa.bkjeon.core.exception.ValidationException;
import com.musinsa.bkjeon.feature.domain.Brand;
import com.musinsa.bkjeon.feature.domain.Category;
import com.musinsa.bkjeon.feature.domain.CategoryMinPriceProduct;
import com.musinsa.bkjeon.feature.domain.Product;
import com.musinsa.bkjeon.feature.enums.common.response.ResponseCode;
import com.musinsa.bkjeon.feature.repository.brand.BrandRepository;
import com.musinsa.bkjeon.feature.repository.category.CategoryRepository;
import com.musinsa.bkjeon.feature.repository.product.ProductRepository;
import com.musinsa.bkjeon.feature.utils.StringUtils;
import com.musinsa.bkjeon.services.v1.api.common.response.ApiResponse;
import com.musinsa.bkjeon.services.v1.api.product.mapper.ProductMapper;
import com.musinsa.bkjeon.services.v1.api.product.model.request.ProductRequest;
import com.musinsa.bkjeon.services.v1.api.product.model.request.ProductSearchRequest;
import com.musinsa.bkjeon.services.v1.api.product.model.response.CategoryMinPriceProductResponse;
import com.musinsa.bkjeon.services.v1.api.product.model.response.ProductResponse;
import com.musinsa.bkjeon.services.v1.api.product.model.response.SingleCategoryPriceRangeResponse;
import com.musinsa.bkjeon.services.v1.api.product.model.response.SingleBrandLowestPriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 카테고리별 브랜드 및 가격 리스트 조회
     */
    @Cacheable(value = "selectCategoryMinPriceProductList")
    @Transactional(readOnly = true)
    public ApiResponse<CategoryMinPriceProductResponse> selectCategoryMinPriceProductList() {
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) {
            throw new ValidationException(ResponseCode.EMPTY_PRODUCT);
        }

        // 카테고리별 최저 가격 추출
        Map<String, Long> categoryMinPriceMap = productList.stream()
            .collect(Collectors.toMap(
                product -> product.getCategory().getCategoryName(),
                Product::getPrice,
                Math::min
            ));

        // 최저 가격 상품 리스트 생성
        List<CategoryMinPriceProduct> categoryMinPriceList = new ArrayList<>(
            productList.stream()
                .filter(product -> categoryMinPriceMap.get(product.getCategory().getCategoryName()).equals(product.getPrice()))
                .collect(Collectors.toMap(
                    product -> product.getCategory().getCategoryName(),
                    product -> CategoryMinPriceProduct.builder()
                        .categoryName(product.getCategory().getCategoryName())
                        .brandName(product.getBrand().getBrandName())
                        .price(StringUtils.isLongToformatCurrency(product.getPrice()))
                        .build(),
                    (oldObj, newObj) -> newObj,
                    LinkedHashMap::new
                ))
                .values()
        );

        // 최저 가격 상품의 총합 계산
        long totalPrice = categoryMinPriceList.stream()
            .mapToLong(product -> Long.parseLong(product.price().replace(",", "")))
            .sum();

        return ApiResponse.res(CategoryMinPriceProductResponse.builder()
            .totalPrice(StringUtils.isLongToformatCurrency(totalPrice))
            .list(categoryMinPriceList)
            .build());
    }

    /**
     * 단일 브랜드 카테고리별 최저가 조회
     */
    @Cacheable(value = "selectSingleBrandLowestPriceResult")
    @Transactional(readOnly = true)
    public ApiResponse<SingleBrandLowestPriceResponse> selectSingleBrandLowestPriceResult() {
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) {
            throw new ValidationException(ResponseCode.EMPTY_PRODUCT);
        }

        // 브랜드별 총 가격 계산
        Map<String, Long> brandTotalPriceMap = productList.stream()
            .collect(Collectors.groupingBy(
                product -> product.getBrand().getBrandName(),
                Collectors.summingLong(Product::getPrice)
            ));

        // 최저가 브랜드 찾기
        Map.Entry<String, Long> minBrandEntry = brandTotalPriceMap.entrySet().stream()
            .min(Map.Entry.comparingByValue())
            .orElse(null);
        if (Objects.isNull(minBrandEntry)) {
            throw new ValidationException(ResponseCode.EMPTY_BRAND_MIN_PRICE);
        }
        String minBrand = minBrandEntry.getKey();
        Long minTotalPrice = minBrandEntry.getValue();

        // 최저가 브랜드의 상품 목록 가져오기
        List<SingleBrandLowestPriceResponse.MinPrice.Category> categoryProductList = productList.stream()
            .filter(product -> minBrand.equals(product.getBrand().getBrandName()))
            .map(product -> SingleBrandLowestPriceResponse.MinPrice.Category.builder()
                .카테고리(product.getCategory().getCategoryName())
                .가격(StringUtils.isLongToformatCurrency(product.getPrice()))
                .build())
            .toList();

        return ApiResponse.res(SingleBrandLowestPriceResponse.builder()
            .최저가(SingleBrandLowestPriceResponse.MinPrice.builder()
                .브랜드(minBrand)
                .카테고리(categoryProductList)
                .총액(StringUtils.isLongToformatCurrency(minTotalPrice))
                .build())
            .build());
    }

    /**
     * 카테고리명으로 최저/최고 가격 상품 조회
     */
    @Cacheable(value = "selectSingleCategoryPriceRange", key = "#request")
    @Transactional(readOnly = true)
    public ApiResponse<SingleCategoryPriceRangeResponse> selectSingleCategoryPriceRange(ProductSearchRequest request) {
        Category category = categoryRepository.findByCategoryName(request.categoryName());
        if (Objects.isNull(category)) {
            throw new ValidationException(ResponseCode.EMPTY_CATEGORY);
        }

        List<Product> productList = productRepository.findAllByCategoryNo(category.getCategoryNo());
        if (productList.isEmpty()) {
            throw new ValidationException(ResponseCode.EMPTY_PRODUCT);
        }

        Product minProduct = productList.stream().min(Comparator.comparing(Product::getPrice))
            .orElse(Product.builder().build());
        Product maxProduct = productList.stream().max(Comparator.comparing(Product::getPrice))
            .orElse(Product.builder().build());

        List<SingleCategoryPriceRangeResponse.PriceInfo> minPriceList = new ArrayList<>();
        minPriceList.add(SingleCategoryPriceRangeResponse.PriceInfo.builder()
            .브랜드(minProduct.getBrand().getBrandName())
            .가격(StringUtils.isLongToformatCurrency(minProduct.getPrice()))
            .build());

        List<SingleCategoryPriceRangeResponse.PriceInfo> maxPriceList = new ArrayList<>();
        maxPriceList.add(SingleCategoryPriceRangeResponse.PriceInfo.builder()
            .브랜드(maxProduct.getBrand().getBrandName())
            .가격(StringUtils.isLongToformatCurrency(maxProduct.getPrice()))
            .build());

        return ApiResponse.res(SingleCategoryPriceRangeResponse.builder()
            .카테고리(category.getCategoryName())
            .최저가(minPriceList)
            .최고가(maxPriceList)
            .build());
    }

    @Transactional
    public ApiResponse<ProductResponse> insertProduct(ProductRequest request) {
        Brand brand = brandRepository.findById(request.brandNo())
            .orElseThrow(() -> new ValidationException(ResponseCode.EMPTY_BRAND));
        Category category = categoryRepository.findById(request.categoryNo())
            .orElseThrow(() -> new ValidationException(ResponseCode.EMPTY_CATEGORY));

        Product productInfo = productRepository.findFirstByBrandNoAndCategoryNo(request.brandNo(),
            request.categoryNo());
        if (Objects.nonNull(productInfo)) {
            throw new ValidationException(ResponseCode.EXIST_PRODUCT);
        }

        Product product = Product.builder()
            .price(request.price())
            .brand(brand)
            .category(category)
            .build();
        Product result = productRepository.save(product);
        return ApiResponse.res(ProductMapper.INSTANCE.toProductResponse(result));
    }

    @Transactional
    public ApiResponse<ProductResponse> updateProduct(Long productNo, ProductRequest request) {
        Optional<Product> product = productRepository.findById(productNo);
        if (product.isEmpty()) {
            throw new ValidationException(ResponseCode.EMPTY_PRODUCT);
        }

        Brand brand = brandRepository.findById(request.brandNo())
            .orElseThrow(() -> new ValidationException(ResponseCode.EMPTY_BRAND));
        Category category = categoryRepository.findById(request.categoryNo())
            .orElseThrow(() -> new ValidationException(ResponseCode.EMPTY_CATEGORY));

        // 수정할 상품의 브랜드와 카테고리가 동일한게 있다면 같은 상품으로 취급하기 때문에 수정하지 않는다.
        Product updateProduct = product.get();
        if (Objects.equals(updateProduct.getBrand().getBrandNo(), request.brandNo())
            && Objects.equals(updateProduct.getCategory().getCategoryNo(), request.categoryNo())) {
            throw new ValidationException(ResponseCode.EXIST_PRODUCT);
        }

        updateProduct.toUpdateProduct(request.price(), brand, category);
        return ApiResponse.res(ProductMapper.INSTANCE.toProductResponse(updateProduct));
    }

    @Transactional
    public ApiResponse<ProductResponse> deleteProduct(Long productNo) {
        Optional<Product> product = productRepository.findById(productNo);
        if (product.isEmpty()) {
            throw new ValidationException(ResponseCode.EMPTY_PRODUCT);
        }

        productRepository.deleteById(productNo);
        return ApiResponse.res(ProductMapper.INSTANCE.toProductResponse(product.get()));
    }

}
