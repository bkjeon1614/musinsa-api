package com.musinsa.bkjeon.feature.repository.product;

import com.musinsa.bkjeon.feature.domain.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.musinsa.bkjeon.feature.domain.QBrand.brand;
import static com.musinsa.bkjeon.feature.domain.QCategory.category;
import static com.musinsa.bkjeon.feature.domain.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Product findFirstByBrandNoAndCategoryNo(Long brandNo, Long categoryNo) {
        return jpaQueryFactory.selectFrom(product)
            .join(product.brand, brand).fetchJoin()
            .join(product.category, category).fetchJoin()
            .where(brand.brandNo.eq(brandNo), category.categoryNo.eq(categoryNo))
            .fetchOne();
    }

    @Override
    public List<Product> findAllByCategoryNo(Long categoryNo) {
        return jpaQueryFactory.selectFrom(product)
            .join(product.brand, brand).fetchJoin()
            .where(category.categoryNo.eq(categoryNo))
            .fetch();
    }

}