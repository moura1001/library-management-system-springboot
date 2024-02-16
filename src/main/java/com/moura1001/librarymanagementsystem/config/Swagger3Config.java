package com.moura1001.librarymanagementsystem.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger3Config {
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("springlibrarymanagementsystem-public")
                .pathsToMatch("/api/books/**")
                .build();
    }
}
