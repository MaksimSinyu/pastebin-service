package com.pastebin.service.api.service;

import java.util.concurrent.TimeUnit;

public interface CacheService {
    <T> void set(String key, T value, long expirationTime, TimeUnit timeUnit);
    <T> T get(String key, Class<T> type);
}
