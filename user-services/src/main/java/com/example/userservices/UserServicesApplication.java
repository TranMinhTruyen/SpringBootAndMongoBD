package com.example.userservices;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
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
@EntityScan(basePackages="com.core.entity")
@ComponentScan(basePackages={"com.example"})
@OpenAPIDefinition(info =
    @Info(title = "User-Auth API", version = "1.0", description = "Documentation Organization API v1.0")
)
public class UserServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServicesApplication.class, args);
    }

}
