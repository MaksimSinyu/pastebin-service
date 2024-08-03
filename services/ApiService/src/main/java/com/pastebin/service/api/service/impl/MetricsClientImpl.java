package com.pastebin.service.api.service.impl;

import com.pastebin.service.api.service.MetricsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MetricsClientImpl implements MetricsClient {

    private final RestTemplate restTemplate;

    @Value("${metrics.service.url}")
    private String metricsServiceUrl;

    @Override
    public void incrementPasteCreated() {
        try {
            restTemplate.postForObject(metricsServiceUrl + "/api/v1/metrics/paste-created", null, Void.class);
        } catch (Exception e) {
            System.err.println("Error sending paste created metric: " + e.getMessage());
        }
    }

    @Override
    public void incrementPasteViewed() {
        restTemplate.postForObject(metricsServiceUrl + "/api/v1/metrics/paste-viewed", null, Void.class);
    }
}