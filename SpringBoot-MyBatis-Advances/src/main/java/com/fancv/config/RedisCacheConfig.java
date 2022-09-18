package com.fancv.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Value("${cache.default.expire-time:2000}")
    private int defaultExpireTime;
    @Value("${cache.user.expire-time:1000}")
    private int userCacheExpireTime;
    @Value("${cache.user.name:cache}")
    private String userCacheName;

   /* *//**
     * Redis缓存管理器
     *
     * @param lettuceConnectionFactory
     * @return
     *//*
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory lettuceConnectionFactory) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        // 设置缓存管理器管理的缓存的默认过期时间
        defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(defaultExpireTime))
                // 设置 key为string序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 设置value为json序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                // 不缓存空值
                .disableCachingNullValues();

        Set<String> cacheNames = new HashSet<>();
        cacheNames.add(userCacheName);

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put(userCacheName, defaultCacheConfig.entryTtl(Duration.ofSeconds(userCacheExpireTime)));
        configMap.put("users", defaultCacheConfig.entryTtl(Duration.ofSeconds(400)));

        RedisCacheManager cacheManager = RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(configMap)
                .build();
        return cacheManager;
    }*/


  /*  *//**
     * Redis缓存管理器
     *
     * @param lettuceConnectionFactory
     * @return
     *//*
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory lettuceConnectionFactory) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        // 设置缓存管理器管理的缓存的默认过期时间
        defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(defaultExpireTime))
                // 设置 key为string序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 设置value为json序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                // 不缓存空值
                .disableCachingNullValues();

        Set<String> cacheNames = new HashSet<>();
        cacheNames.add(userCacheName);

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put(userCacheName, defaultCacheConfig.entryTtl(Duration.ofSeconds(userCacheExpireTime)));
        configMap.put("users", defaultCacheConfig.entryTtl(Duration.ofSeconds(400)));

        RedisCacheManager cacheManager = RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(configMap)
                .build();
        return cacheManager;
    }
*/
    /**
     * 自定义缓存的redis的KeyGenerator【key生成策略】
     * 注意: 该方法只是声明了key的生成策略,需在@Cacheable注解中通过keyGenerator属性指定具体的key生成策略
     * 可以根据业务情况，配置多个生成策略
     * 如: @Cacheable(value = "key", keyGenerator = "cacheKeyGenerator")
     */
    @Bean
    public KeyGenerator keyGenerator() {
        /**
         * target: 类
         * method: 方法
         * params: 方法参数
         */
        return (target, method, params) -> {
            //获取代理对象的最终目标对象
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getSimpleName()).append(":");
            sb.append(method.getName()).append(":");
            //调用SimpleKey的key生成器
            Object key = SimpleKeyGenerator.generateKey(params);
            return sb.append(key);
        };
    }


}
