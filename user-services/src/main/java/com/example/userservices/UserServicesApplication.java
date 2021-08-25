package com.example.userservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableEurekaClient
@EnableMongoRepositories(basePackages="com.core.repository.mongo")
@EntityScan(basePackages="com.core.entity")
@ComponentScan(basePackages={"com.example"})
public class UserServicesApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServicesApplication.class, args);
    }
}
