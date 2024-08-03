package com.pastebin.service.api.config;

import com.pastebin.service.api.service.StorageService;
import com.pastebin.service.api.service.impl.MinioStorageService;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Bean
    public MinioClient minioClient(
            @Value("${minio.url}") String minioUrl,
            @Value("${minio.access-key}") String accessKey,
            @Value("${minio.secret-key}") String secretKey) {
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();
    }

    @Bean
    public StorageService minioStorageService(
            MinioClient minioClient,
            @Value("${minio.bucket-name}") String bucketName) {
        return new MinioStorageService(minioClient, bucketName);
    }
}