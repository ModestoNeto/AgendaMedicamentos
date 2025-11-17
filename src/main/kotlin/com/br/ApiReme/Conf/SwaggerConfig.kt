package com.br.ApiReme.Conf

import io.swagger.v3.oas.models.OpenAPI
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.models.info.Info
@Configuration
class SwaggerConfig {
    @Bean
    fun apiInfo(): OpenAPI =
        OpenAPI().info(
            Info()
                .title("API - Api Remedio lembrete")
                .description("Documentação da API de lembretes de medicamentos")
                .version("1.0.0")
        )
}