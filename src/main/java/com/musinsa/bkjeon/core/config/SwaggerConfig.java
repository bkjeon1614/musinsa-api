package com.musinsa.bkjeon.core.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "Musinsa APIs V1(Latest)",
        description = "Musinsa APIs..",
        version = "v1",
        termsOfService = "terms of controller url",
        contact = @Contact(name = "bong keun - jeon",
            url = "https://bkjeon1614.tistory.com",
            email = "gcijdfdo@gmail.com")))
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi apiV1() {
        return GroupedOpenApi.builder()
            .group("v1")
            .packagesToScan("com.musinsa.bkjeon.services.v1.api")
            .build();
    }

}