package com.pastebin.service.api.service;

import com.pastebin.service.api.exception.PasteNotFoundException;
import com.pastebin.service.api.model.Paste;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PasteService {
    private final StorageService storageService;
    private final CacheService cacheService;
    private final HashGeneratorClient hashGeneratorClient;
    private final MetricsClient metricsClient;
    private final RestTemplate restTemplate;

    public Paste getPaste(String hash) {
        metricsClient.incrementPasteViewed();
        Paste cachedPaste = cacheService.get(hash);
        if (cachedPaste != null) {
            return cachedPaste;
        }

        try {
            byte[] data = storageService.getObject(hash);
            Paste paste = new Paste(hash, new String(data, StandardCharsets.UTF_8));
            cacheService.set(hash, paste, 1, TimeUnit.HOURS);
            return paste;
        } catch (Exception e) {
            throw new PasteNotFoundException("Paste not found: " + hash);
        }
    }

    public Paste createPaste(String data) {
        metricsClient.incrementPasteCreated();
        String hash = hashGeneratorClient.generateHash(data);
        storageService.saveObject(hash, data.getBytes(StandardCharsets.UTF_8));
        Paste paste = new Paste(hash, data);
        cacheService.set(hash, paste, 1, TimeUnit.HOURS);
        return paste;
    }
}