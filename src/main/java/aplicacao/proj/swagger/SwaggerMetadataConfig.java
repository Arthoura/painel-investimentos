package aplicacao.proj.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerMetadataConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Investimentos")
                        .version("v1.0")
                        .description("Documentação da API para gestão de investimentos")
                        .contact(new Contact()
                                .name("Arthur")
                                .email("arthur.arruda@caixa.gov.br")
                                .url("https://github.com/Arthoura/painel-investimentos")))
                        .components(new Components()
                                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization")))
                        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));


}


}