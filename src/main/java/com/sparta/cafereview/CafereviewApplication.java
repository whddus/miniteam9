package com.sparta.cafereview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CafereviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafereviewApplication.class, args);
    }
}