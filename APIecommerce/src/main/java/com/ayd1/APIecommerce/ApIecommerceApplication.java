package com.ayd1.APIecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ApIecommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApIecommerceApplication.class, args);
    }
  
}
