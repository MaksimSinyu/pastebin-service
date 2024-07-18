package com.pastebin.service.api.client;

import io.minio.GetObjectArgs;
import io.minio.PutObjectArgs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "minio", url = "${minio.url}")
public interface MinioClient {

    @PutMapping("/{bucketName}/{objectName}")
    void putObject(@PathVariable("bucketName") String bucketName,
                   @PathVariable("objectName") String objectName,
                   @RequestBody byte[] data);

    @GetMapping("/{bucketName}/{objectName}")
    byte[] getObject(@PathVariable("bucketName") String bucketName,
                     @PathVariable("objectName") String objectName);
}