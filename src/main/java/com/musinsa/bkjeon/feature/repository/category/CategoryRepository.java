package com.musinsa.bkjeon.feature.repository.category;

import com.musinsa.bkjeon.feature.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryName(String categoryName);

}