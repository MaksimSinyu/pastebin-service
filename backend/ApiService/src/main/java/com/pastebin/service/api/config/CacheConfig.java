package com.pastebin.service.api.config;

import com.pastebin.service.api.model.Paste;
import com.pastebin.service.api.service.CacheService;
import com.pastebin.service.api.service.impl.RedisCacheService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheConfig {
    @Bean
    public CacheService redisCacheService(RedisTemplate<String, Paste> redisTemplate) {
        return new RedisCacheService(redisTemplate);
    }
}