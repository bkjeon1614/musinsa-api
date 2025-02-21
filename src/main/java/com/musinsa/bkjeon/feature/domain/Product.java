package com.musinsa.bkjeon.feature.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Comment("상품번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_no")
    private Long productNo;

    @Comment("상품가격")
    @Column(name = "price")
    private Long price;

    @Comment("브랜드 번호")
    @ManyToOne
    @JoinColumn(name = "brand_no", nullable = false)
    private Brand brand;

    @Comment("카테고리 번호")
    @ManyToOne
    @JoinColumn(name = "category_no", nullable = false)
    private Category category;

    public void toUpdateProduct(Long price, Brand brand, Category category) {
        this.price = price;
        this.brand = brand;
        this.category = category;
    }

}
