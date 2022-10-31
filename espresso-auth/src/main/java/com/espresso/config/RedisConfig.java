package com.espresso.config;

import com.espresso.commons.utils.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * Rewrite the Redis serialization method and use the Json method:
     * When our data is stored in Redis, our keys and values are serialized to the database through the Serializer provided by Spring. RedisTemplate uses JdkSerializationRedisSerializer by default, and StringRedisTemplate uses StringRedisSerializer by default.
     * Spring Data JPA provides us with the following Serializers:
     * GenericToStringSerializer, Jackson2JsonRedisSerializer, JacksonJsonRedisSerializer, JdkSerializationRedisSerializer, OxmSerializer, StringRedisSerializer.
     * Here we will configure RedisTemplate ourselves and define Serializer.
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);

        // The serialization of the set value (value) adopts FastJsonRedisSerializer.
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        // The serialization of the set key (key) uses StringRedisSerializer.
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
