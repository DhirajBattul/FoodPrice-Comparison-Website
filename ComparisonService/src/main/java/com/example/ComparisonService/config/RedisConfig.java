package com.example.ComparisonService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${app.cache.price.ttl-minutes:30}")
    private int ttlMinutes;

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {

        // Allow deserialization of your DTO package

        PolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator
                .builder()
                .allowIfBaseType("com.example.ComparisonService") // your DTOs
                .allowIfBaseType("java.util")                     // collections
                .allowIfBaseType("java.math")                     // BigDecimal
                .allowIfBaseType("java.time")                     // LocalDateTime
                .allowIfBaseType("java.lang")                     // String, Boolean etc
                .build();

        // Use builder with enableDefaultTyping — embeds @class in JSON
        // so Redis knows exactly which class to deserialize back to
        RedisSerializer<Object> serializer = GenericJacksonJsonRedisSerializer
                .builder()
                .enableDefaultTyping(typeValidator)
                .build();

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(ttlMinutes))
                .disableCachingNullValues()
                .serializeKeysWith(
                    RedisSerializationContext.SerializationPair
                        .fromSerializer(RedisSerializer.string())
                )
                .serializeValuesWith(
                    RedisSerializationContext.SerializationPair
                        .fromSerializer(serializer)
                );

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }
}