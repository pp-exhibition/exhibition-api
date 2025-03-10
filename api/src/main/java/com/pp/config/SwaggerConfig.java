package com.pp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!prod")
@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8080", description = "local"),
//                @Server(url = "NOT_FOUND", description = "stg")
        },
        info = @Info(
                title = "전시 커뮤니티",
                description = "전시 커뮤니티",
                version = "v1")
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "Bearer"
)
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi getAuthApi() {
        return GroupedOpenApi.builder()
                .group("auth")
                .pathsToMatch("/v1/api/auth/**")
                .build();
    }

}
