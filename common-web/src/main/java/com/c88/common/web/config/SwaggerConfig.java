package com.c88.common.web.config;

import io.swagger.v3.oas.models.media.DateTimeSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenApiCustomiser openAPICustomiser() {
        return openApi -> {
            openApi.getComponents().getSchemas().forEach((s, schema) -> {
                Map<String, Schema> properties = schema.getProperties();
                if (properties == null) {
                    properties = Map.of();
                }
                for (String propertyName : properties.keySet()) {
                    Schema propertySchema = properties.get(propertyName);
                    if (propertySchema instanceof DateTimeSchema) {
                        properties.replace(propertyName, new StringSchema()
                                .example(LocalDateTime.now(ZoneId.of("+7")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                .title(propertySchema.getTitle())
                                .description(propertySchema.getDescription()));//copies original description
                    }
                }
            });
        };
    }

}
