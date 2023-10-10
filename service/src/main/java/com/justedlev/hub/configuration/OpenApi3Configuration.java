//package com.justedlev.hub.configuration;
//
//import com.justedlev.hub.configuration.properties.ApplicationProperties;
//import com.justedlev.hub.configuration.properties.keycloak.KeycloakProperties;
//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import io.swagger.v3.oas.models.security.OAuthFlow;
//import io.swagger.v3.oas.models.security.OAuthFlows;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import io.swagger.v3.oas.models.servers.Server;
//import io.swagger.v3.oas.models.servers.ServerVariables;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//import java.util.Map;
//
//@Configuration
//@OpenAPIDefinition
//@RequiredArgsConstructor
//public class OpenApi3Configuration {
//    private final ApplicationProperties applicationProperties;
//    private final KeycloakProperties keycloakProperties;
//
//    @Bean
//    public OpenAPI openAPI(
//            @Value("${server.port}") final Integer serverPort,
//            @Value("${server.servlet.context-path}") final String contextPath
//    ) {
//        final var securitySchemeName = "bearer-token";
//        final var info = new Info()
//                .title("Justedlev Hub API")
//                .version("1.0")
//                .contact(new Contact()
//                        .email("justedlevhub@gmail.com")
//                        .name("JustedlevHub")
//                )
//                .description("This API exposes endpoints to manage tutorials.")
////                        .termsOfService("http://localhost:8080/terms")
//                .license(new License()
//                        .name("MIT License")
//                        .url("https://choosealicense.com/licenses/mit/")
//                );
//        var securityItem = new SecurityRequirement()
//                .addList(securitySchemeName);
//        var component = new Components()
//                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
//                        .name(securitySchemeName)
//                        .description("JWT auth")
//                        .type(SecurityScheme.Type.OAUTH2)
//                        .scheme("bearer")
//                        .bearerFormat("JWT")
//                        .in(SecurityScheme.In.COOKIE)
//                        .flows(new OAuthFlows()
//                                .clientCredentials(new OAuthFlow()
//                                        .tokenUrl(keycloakProperties.getEndpoints().getToken().toString())
//                                        .refreshUrl(keycloakProperties.getEndpoints().getToken().toString())
//                                )
//                                .password(new OAuthFlow()
//                                        .tokenUrl(keycloakProperties.getEndpoints().getToken().toString())
//                                        .refreshUrl(keycloakProperties.getEndpoints().getToken().toString())
//                                )
//                        )
//                );
//        var servers = List.of(
//                ServerBuilder.builder()
//                        .url("http://localhost:" + serverPort + contextPath)
//                        .description("Local ENV")
//                        .build(),
//                ServerBuilder.builder()
//                        .url("http://users:" + serverPort + contextPath)
//                        .description("Docker ENV")
//                        .build()
//        );
//
//        return new OpenAPI()
//                .info(info)
//                .addSecurityItem(securityItem)
//                .components(component)
//                .servers(servers);
//    }
//
//    private static final class ServerBuilder {
//        private final Server server = new Server();
//
//        public static ServerBuilder builder() {
//            return new ServerBuilder();
//        }
//
//        ServerBuilder url(String url) {
//            server.setUrl(url);
//            return this;
//        }
//
//        ServerBuilder description(String description) {
//            server.setDescription(description);
//            return this;
//        }
//
//        ServerBuilder extensions(Map<String, Object> extensions) {
//            server.setExtensions(extensions);
//            return this;
//        }
//
//        ServerBuilder variables(ServerVariables variables) {
//            server.setVariables(variables);
//            return this;
//        }
//
//        public Server build() {
//            return server;
//        }
//    }
//}
