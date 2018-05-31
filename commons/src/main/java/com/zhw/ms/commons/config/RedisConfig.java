package com.zhw.ms.commons.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by Administrator on 2015/12/29 0029.
 */
@Configuration
public class RedisConfig {
    @Autowired
    private RedisProperties properties;

    /*@Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(properties.getHost());
        factory.setDatabase(properties.getDatabase());
        factory.setPassword(properties.getPassword());
        factory.setPort(properties.getPort());
        factory.setTimeout((int) (properties.getTimeout().getSeconds() * 1000));
        factory.setUsePool(true);
        return factory;
    }*/

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(properties.getHost());
        configuration.setDatabase(properties.getDatabase());
        configuration.setPort(properties.getPort());
        configuration.setPassword(RedisPassword.of(properties.getPassword()));
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate redisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory());
        //template.setKeySerializer(new StringRedisSerializer());
        //template.setHashKeySerializer(new StringRedisSerializer());
        //template.setValueSerializer(new StringRedisSerializer());
        //template.setHashValueSerializer(new StringRedisSerializer());
        return template;
    }
}
