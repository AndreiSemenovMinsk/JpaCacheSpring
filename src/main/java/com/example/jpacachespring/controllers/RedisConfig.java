package com.example.jpacachespring.controllers;

import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfig {

    /*@Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }*/
    /*@Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration(
                Arrays.asList("localhost:6379", "localhost:6380", "localhost:6381",
                        "localhost:6382", "localhost:6383", "localhost:6384",
                        "localhost:6385", "localhost:6386", "localhost:6387",
                        "localhost:6388"));
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10); // Максимальное количество соединений в пуле
        poolConfig.setMaxIdle(10);
        poolConfig.setMinIdle(1);

        return new JedisConnectionFactory(clusterConfig, poolConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }*/
}
