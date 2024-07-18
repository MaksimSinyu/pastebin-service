package com.pastebin.service.api.service;

import com.pastebin.service.api.client.MinioClient;
import com.pastebin.service.api.model.Paste;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class PasteService {

    @Value("${hash.generator.url}")
    private String hashGeneratorUrl;

    @Value("${minio.bucket-name}")
    private String bucketName;

    private final RestTemplate restTemplate;
    private final MinioClient minioClient;

    public Paste getPaste(String hash) {
        try {
            byte[] data = minioClient.getObject(bucketName, hash);
            return new Paste(hash, new String(data, StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Failed to get paste", e);
        }
    }

    public Paste createPaste(String data) {
        String hash = restTemplate.postForObject(hashGeneratorUrl, data, String.class);

        saveToMinIO(hash, data);

        return new Paste(hash, data);
    }

    private void saveToMinIO(String hash, String data) {
        try {
            minioClient.putObject(bucketName, hash, data.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.err.println("Failed to save paste to MinIO. Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save paste", e);
        }
    }
}