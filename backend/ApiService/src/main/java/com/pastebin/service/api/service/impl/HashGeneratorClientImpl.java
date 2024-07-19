package com.pastebin.service.api.service.impl;

import com.pastebin.service.api.service.HashGeneratorClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class HashGeneratorClientImpl implements HashGeneratorClient {
    private final RestTemplate restTemplate;

    @Value("${hash.generator.url}")
    private String hashGeneratorUrl;

    @Override
    public String generateHash(String data) {
        return restTemplate.postForObject(hashGeneratorUrl, data, String.class);
    }
}