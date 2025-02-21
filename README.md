## Musinsa 과제

### 구현범위
- 브랜드와 카테고리가 존재하면 하나의 상품으로 취급 (중복 X)
- 주어진 데이터 기준으로 각 카테고리, 브랜드, 상품 테이블 생성
  - H2 DB 사용 
  - JPA + QueryDSL 을 사용하여 CRUD 구현
- 조회성 데이터 캐시 처리 및 그 외 처리에 대한 프로세스는 비동기 처리 
- Swagger 를 통한 API 문서화
- API 공통 Exception Handler 구현
- 결과 모델의 항목이 한글로 되어있는 경우 동일하게 실제 결과 항목도 한글로 구현

### 구현기능
- 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
- 단일 브랜드로 모든 카테고리 상품에 대해 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
- 카테고리명으로 최저/최고 가격 상품 조회하는 API
- 상품 등록/수정/삭제
- 브랜드 등록/수정/삭제

### 개발환경
- Java 17
  - Java 17 선택 이유
    - 향후 다른 LTS 버전(Ex: Java 21) 전환 시 사이드이펙트 최소화
    - Support 기간
    - Spring Boot 3.0 부터는 Java 17 이상부터 지원 https://spring.io/blog/2022/05/24/preparing-for-spring-boot-3-0/
    - 핀포인트 지원버전 (Java 17 이하) https://github.com/pinpoint-apm/pinpoint
- Spring Boot 3.3.2
- Gradle 8.12.1

### Info
- [API Document(Swagger)](http://localhost:9090/swagger-ui/index.html)
- [H2-CONSOLE(Local DB)](http://localhost:9090/h2-console)
    - url: jdbc:h2:mem:testdb
    - username: sa

### IDE Setting
- File -> Settings.. -> Editor -> File Encoding -> "Project Encoding": UTF-8, "Default encoding for properties files": UTF-8
- File -> Settings.. > "Enable annotation processing": Check
- Edit Configuration > Spring Boot > MusinsaApiApplication > Active Profiles > `local` 기입 (default: local)

### 실행
- IDE
  - Spring Boot Application Run => MusinsaApiApplication.java
- Not IDE
  ```
  nohup java -jar musinsa-api-1.0.0.jar
  ```

### 빌드
```
gradlew :clean :build
```

### 테스트
```
// 유닛 테스트
gradle: unitTest

// 통합 테스트
gradle :integrationTest
```

### 테스트방법
[Swagger](http://localhost:9090/swagger-ui/index.html) 를 통하여 진행 부탁 드리겠습니다.
1. [브랜드 추가](http://localhost:9090/swagger-ui/index.html#/brand/setBrand)
2. [브랜드 수정](http://localhost:9090/swagger-ui/index.html#/brand/putBrand)
3. [브랜드 삭제](http://localhost:9090/swagger-ui/index.html#/brand/delBrand)
4. [추가된 브랜드로 상품 추가](http://localhost:9090/swagger-ui/index.html#/product/setProduct)
5. [상품 수정](http://localhost:9090/swagger-ui/index.html#/product/putProduct)
6. [상품 삭제](http://localhost:9090/swagger-ui/index.html#/product/delProduct)
7. [카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API](http://localhost:9090/swagger-ui/index.html#/product/getCategoryMinPriceProductList)
8. [단일 브랜드로 모든 카테고리 상품에 대해 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API](http://localhost:9090/swagger-ui/index.html#/product/getSingleBrandLowestPriceResult)
9. [카테고리명으로 최저/최고 가격 상품 조회하는 API](http://localhost:9090/swagger-ui/index.html#/product/getSingleCategoryPriceRangeResult)
 