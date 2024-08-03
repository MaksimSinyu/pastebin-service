package com.pastebin.service.api.service.impl;

import com.pastebin.service.api.exception.PasteNotFoundException;
import com.pastebin.service.api.model.Paste;
import com.pastebin.service.api.repository.PasteRepository;
import com.pastebin.service.api.service.*;
import com.pastebin.service.api.specification.PasteSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasteServiceImpl implements PasteService {
    private final PasteRepository pasteRepository;
    private final HashGeneratorClient hashGeneratorClient;
    private final StorageService storageService;
    private final CacheService cacheService;
    private final MetricsClient metricsClient;

    @Override
    public Paste createPaste(String data) {
        metricsClient.incrementPasteCreated();
        String hash = hashGeneratorClient.generateHash(data);
        storageService.saveObject(hash, data.getBytes(StandardCharsets.UTF_8));
        Paste paste = new Paste(hash, data);
        cacheService.set(hash, paste, 1, TimeUnit.HOURS);
        return paste;
    }

    @Override
    public Paste getPaste(String hash) {
        log.info("Fetching paste with hash: {}", hash);
        metricsClient.incrementPasteViewed();
        Paste cachedPaste = cacheService.get(hash, Paste.class);
        if (cachedPaste != null) {
            log.info("Paste found in cache: {}", cachedPaste);
            return cachedPaste;
        }

        try {
            byte[] data = storageService.getObject(hash);
            Paste paste = new Paste(hash, new String(data, StandardCharsets.UTF_8));
            cacheService.set(hash, paste, 1, TimeUnit.HOURS);
            log.info("Paste fetched from storage and cached: {}", paste);
            return paste;
        } catch (Exception e) {
            log.error("Error fetching paste with hash: {}", hash, e);
            throw new PasteNotFoundException("Paste not found: " + hash);
        }
    }

    @Override
    public Page<Paste> searchPastes(String language, Boolean isPublic, DateTime createdAfter,
                                    String titleContains, Pageable pageable) {
        Specification<Paste> spec = Specification
                .where(PasteSpecification.hasLanguage(language))
                .and(PasteSpecification.isPublic(isPublic))
                .and(PasteSpecification.createdAfter(createdAfter))
                .and(PasteSpecification.titleContains(titleContains));

        return pasteRepository.findAll(spec, pageable);
    }
}
