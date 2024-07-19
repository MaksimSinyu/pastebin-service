package com.pastebin.service.api.service.impl;

import com.pastebin.service.api.exception.StorageException;
import com.pastebin.service.api.service.StorageService;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;

@RequiredArgsConstructor
public class MinioStorageService implements StorageService {
    private final MinioClient minioClient;
    private final String bucketName;

    @Override
    public void saveObject(String key, byte[] data) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(key)
                    .stream(new ByteArrayInputStream(data), data.length, -1)
                    .build());
        } catch (Exception e) {
            throw new StorageException("Failed to save object to MinIO", e);
        }
    }

    @Override
    public byte[] getObject(String key) {
        try {
            GetObjectResponse response = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(key)
                    .build());
            return response.readAllBytes();
        } catch (Exception e) {
            throw new StorageException("Failed to get object from MinIO", e);
        }
    }
}