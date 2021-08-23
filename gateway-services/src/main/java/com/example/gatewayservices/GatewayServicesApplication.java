package com.example.gatewayservices;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

//http://localhost:8762/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

@SpringBootApplication
@EnableEurekaClient
public class GatewayServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServicesApplication.class, args);
    }

    @Bean
    @Lazy(false)
    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters, RouteDefinitionLocator locator) {
        List<GroupedOpenApi> groups = new ArrayList<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-services")).forEach(routeDefinition -> {
            String name = routeDefinition.getId().replaceAll("-services", "");
            String test = name;
            swaggerUiConfigParameters.addGroup(name);
            GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build();
        });
        return groups;
    }
}
