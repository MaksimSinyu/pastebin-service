package com.pastebin.service.api.service.impl;

import com.pastebin.service.api.model.Paste;
import com.pastebin.service.api.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class RedisCacheService implements CacheService {
    private final RedisTemplate<String, Paste> redisTemplate;

    @Override
    public void set(String key, Paste paste, long expirationTime, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, paste, expirationTime, timeUnit);
    }

    @Override
    public Paste get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}