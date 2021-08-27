package com.example.cartservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableEurekaClient
@EnableMongoRepositories(basePackages="com.core.repository.mongo")
@EnableJpaRepositories(basePackages="com.core.repository.mysql")
@EntityScan(basePackages="com.core.entity")
@ComponentScan(basePackages={"com.example"})
public class CartServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartServicesApplication.class, args);
    }

}
