package com.tijapay.posgateway.utils;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

public class CacheConfig {

    public static final String TOKEN_DETAILS_CACHE = "TOKEN_DETAILS_CACHE";

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager(TOKEN_DETAILS_CACHE);
        return cacheManager;
    }
}
