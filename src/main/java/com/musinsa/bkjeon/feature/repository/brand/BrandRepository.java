package com.musinsa.bkjeon.feature.repository.brand;

import com.musinsa.bkjeon.feature.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findByBrandName(String brandName);

}