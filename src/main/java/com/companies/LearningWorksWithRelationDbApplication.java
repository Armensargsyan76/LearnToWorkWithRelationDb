package com.companies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.companies.repository")
@EntityScan("com.companies.entity")
@SpringBootApplication
@ComponentScan(basePackages = "com.companies")
public class LearningWorksWithRelationDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningWorksWithRelationDbApplication.class, args);
    }
}
