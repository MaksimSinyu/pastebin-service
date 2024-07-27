package com.pastebin.service.api.config;

import com.pastebin.service.api.service.CacheService;
import com.pastebin.service.api.service.impl.RedisCacheService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class CacheConfig {
    @Bean
    public CacheService redisCacheService(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        return new RedisCacheService(redisTemplate, objectMapper);
    }
}
