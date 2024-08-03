package com.pastebin.service.api.controller;

import com.pastebin.service.api.model.Paste;
import com.pastebin.service.api.service.PasteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/paste")
@RequiredArgsConstructor
@Slf4j
public class PasteController {
    private final PasteService pasteService;

    @PostMapping
    public ResponseEntity<Paste> createPaste(@RequestBody Map<String, String> payload) {
        String data = payload.get("data");
        if (data == null || data.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Paste paste = pasteService.createPaste(data);
        return ResponseEntity.ok(paste);
    }

    @GetMapping("/{hash}")
    public ResponseEntity<Paste> getPaste(@PathVariable String hash) {
        log.info("Fetching paste with hash: {}", hash);
        try {
            Paste paste = pasteService.getPaste(hash);
            log.info("Paste found: {}", paste);
            return ResponseEntity.ok(paste);
        } catch (Exception e) {
            log.error("Error fetching paste with hash: {}", hash, e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Paste>> searchPastes(
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Boolean isPublic,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime createdAfter,
            @RequestParam(required = false) String titleContains,
            Pageable pageable) {
        Page<Paste> pastes = pasteService.searchPastes(language, isPublic, createdAfter, titleContains, pageable);
        return ResponseEntity.ok(pastes);
    }
}