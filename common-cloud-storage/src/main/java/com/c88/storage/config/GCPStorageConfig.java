package com.c88.storage.config;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Configuration
public class GCPStorageConfig {

    @Value("${cloud-storage.gcp.credentials-classpath}")
    private String gcpStorageKey;

    @Value("${cloud-storage.gcp.project}")
    private String projectId;

    @Bean
    public Storage provideStorageService() throws IOException {
        log.info("init google cloud storage");
        Resource resource = new DefaultResourceLoader().getResource(gcpStorageKey);
        Credentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        return StorageOptions.newBuilder()
                .setCredentials(credentials)
                .setProjectId(projectId)
                .build()
                .getService();
    }
}
