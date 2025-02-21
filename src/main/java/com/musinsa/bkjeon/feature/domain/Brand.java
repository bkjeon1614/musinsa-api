package com.musinsa.bkjeon.feature.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand {

    @Comment("브랜드 번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_no")
    private Long brandNo;
    
    @Comment("브랜드명")
    @Column(name = "brand_name", nullable = false)
    private String brandName;

    public void toUpdateBrandName(String brandName) {
        this.brandName = brandName;
    }

}
