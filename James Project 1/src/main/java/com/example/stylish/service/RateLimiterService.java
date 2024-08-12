package com.example.stylish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimiterService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String RATE_LIMITER_PREFIX = "rate_limiter:";

    @Value("${rate.limit.maxRequests}")
    private int maxRequests;

    @Value("${rate.limit.timeWindow}")
    private int timeWindow; // Time window in seconds

    @Autowired
    public RateLimiterService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isAllowed(String ip) {
        String key = RATE_LIMITER_PREFIX + ip;
        Long requests = redisTemplate.opsForValue().increment(key);

        if (requests == 1) {
            redisTemplate.expire(key, Duration.ofSeconds(timeWindow));
        }

        return requests <= maxRequests;
    }
}
