package com.pastebin.service.api.service;

public interface MetricsClient {
    void incrementPasteCreated();
    void incrementPasteViewed();
}