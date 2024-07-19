package com.pastebin.service.api.service;

import com.pastebin.service.api.model.Paste;

import java.util.concurrent.TimeUnit;

public interface CacheService {
    void set(String key, Paste paste, long expirationTime, TimeUnit timeUnit);
    Paste get(String key);
}