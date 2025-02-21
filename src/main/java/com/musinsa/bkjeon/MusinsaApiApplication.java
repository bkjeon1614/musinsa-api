package com.musinsa.bkjeon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MusinsaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusinsaApiApplication.class, args);
    }

}
