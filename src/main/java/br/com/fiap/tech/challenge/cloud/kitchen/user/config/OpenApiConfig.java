package br.com.fiap.tech.challenge.cloud.kitchen.user.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI cloudKitchenUsersOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cloud Kitchen Users API")
                        .description("API responsável pelo gerenciamento de usuários da plataforma Cloud Kitchen.")
                        .version("v1")
                        .contact(new Contact()
                                .name("FIAP Tech Challenge")
                                .email("contato@cloud-kitchen.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação do projeto Cloud Kitchen")
                        .url("https://api.cloud-kitchen.com/docs"));
    }
}
