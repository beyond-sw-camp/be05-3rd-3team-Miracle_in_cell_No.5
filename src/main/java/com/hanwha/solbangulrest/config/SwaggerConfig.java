package com.hanwha.solbangulrest.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
			.group("public-apis")
			.pathsToMatch("/**")
			.build();
	}

	@Bean
	public OpenAPI customOpenApi() {
		return new OpenAPI()
			.info(new Info().title("Solbangul REST API").version("1.0").description("Solbangul REST API"))
			.addSecurityItem(new SecurityRequirement().addList("bearer-jwt"))
			.components(
				new Components()
					.addSecuritySchemes("bearerAuth", new SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")));
	}

}
