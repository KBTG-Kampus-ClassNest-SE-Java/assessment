package com.kbtg.bootcamp.posttest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Lottery API",
                version = "1.0",
                description = "Lottery API"
        )
)
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Lottery API")
            .version("1.0")
            .description("Lottery API")
        ).components(new Components().addSecuritySchemes("Authorization", securityScheme()))
        .addSecurityItem(new SecurityRequirement().addList("Authorization"));
  }

  public SecurityScheme securityScheme() {
    return new SecurityScheme()
        .name("Authorization")
        .type(SecurityScheme.Type.HTTP)
        .in(SecurityScheme.In.HEADER)
        .bearerFormat("JWT")
        .scheme("bearer");
  }



}
