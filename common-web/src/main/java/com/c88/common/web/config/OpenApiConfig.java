package com.c88.common.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;

@Slf4j
@Profile("!prod & !local & !k8s_local")
@Configuration
public class OpenApiConfig {

    public static final String LOCAL_HOST = "http://localhost:8082/";
    public static final String DEV_HOST = "https://dev-c88-gateway.hyu.tw/";
    public static final String STAGE_HOST = "https://stg-c88-gateway.hyu.tw/";

    public static final String STAGE = "k8s_stg";
    public static final String DEV = "k8s_dev";

    public static final String LOCAL = "local";
    public static final String K8S_LOCAL = "k8s_local";
    public static final String URL_HTTP = "http://";

    @Value("${server.port}")
    public int port;

    @Value("${spring.application.name}")
    public String path;

    @Value("${spring.profiles.active}")
    public String active;

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        StringBuilder builder = new StringBuilder();
        switch (active) {
            case LOCAL:
            case K8S_LOCAL:
                builder.append(LOCAL_HOST);
                break;
            case STAGE:
                builder.append(STAGE_HOST);
                break;
            case DEV:
                builder.append(DEV_HOST);
                break;
        }
        builder.append(path);
        log.info("swagger server url:{}", builder);
        server.setUrl(builder.toString());
        return new OpenAPI().addServersItem(server)
                .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                .schemaRequirement(HttpHeaders.AUTHORIZATION, new SecurityScheme().type(SecurityScheme.Type.HTTP)
                        .scheme("bearer").bearerFormat("JWT").in(SecurityScheme.In.HEADER).name(HttpHeaders.AUTHORIZATION));
    }
}
