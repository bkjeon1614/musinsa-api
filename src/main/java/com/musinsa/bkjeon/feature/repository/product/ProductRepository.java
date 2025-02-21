package com.musinsa.bkjeon.feature.repository.product;

import com.musinsa.bkjeon.feature.domain.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductCustomRepository {

    @EntityGraph(attributePaths = {"brand", "category"})
    List<Product> findAll();

}