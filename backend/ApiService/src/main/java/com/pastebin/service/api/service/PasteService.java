package com.pastebin.service.api.service;

import com.pastebin.service.api.model.Paste;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PasteService {
    Paste createPaste(String data);
    Paste getPaste(String hash);
    Page<Paste> searchPastes(String language, Boolean isPublic, DateTime createdAfter,
                             String titleContains, Pageable pageable);
}