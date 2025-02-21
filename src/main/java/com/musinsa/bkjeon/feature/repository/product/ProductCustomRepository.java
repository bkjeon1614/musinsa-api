package com.musinsa.bkjeon.feature.repository.product;


import com.musinsa.bkjeon.feature.domain.Product;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface ProductCustomRepository {

    Product findFirstByBrandNoAndCategoryNo(Long brandNo, Long categoryNo);

    @EntityGraph(attributePaths = {"brand", "category"})
    List<Product> findAllByCategoryNo(Long categoryNo);

}