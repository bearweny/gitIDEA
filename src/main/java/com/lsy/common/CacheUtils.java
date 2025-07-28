package com.lsy.common;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class CacheUtils {
    private static final Cache<String, String> cache = Caffeine.newBuilder()
            .initialCapacity(10)                 // 初始容量
            .maximumSize(100)                   // 最大容量
            .expireAfterWrite(5, TimeUnit.MINUTES) // 写入后5分钟过期
            .build();
    private CacheUtils() {

    }

    /**
     * 存储数据
     * @param key
     * @param value
     */
    public static void setKey(String key,String value) {
        cache.put(key,value);
    }

    /**
     * 根据key获取数据
     *
     * @return
     */
    public static String getKey(String key) {
        return cache.getIfPresent(key);
    }
}

