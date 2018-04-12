package com.achain.conf;


import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @author yaoxiaohui
 * @desc redis config bean
 *
 */
@Slf4j
@Configuration
@EnableCaching
@EnableAutoConfiguration
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private Environment env;

    @Bean
    @ConfigurationProperties(prefix="spring.redis")
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.valueOf(env.getProperty("spring.redis.pool.max-active").trim()));
        config.setMaxWaitMillis(Integer.valueOf(env.getProperty("spring.redis.pool.max-wait").trim()));
        config.setMaxIdle(Integer.valueOf(env.getProperty("spring.redis.pool.max-idle").trim()));
        config.setMinIdle(Integer.valueOf(env.getProperty("spring.redis.pool.min-idle").trim()));
        return config;
    }

    @Bean
    @ConfigurationProperties(prefix="spring.redis")
    public JedisConnectionFactory getConnectionFactory(){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        factory.setHostName(env.getProperty("spring.redis.host"));
        factory.setPort(Integer.parseInt(env.getProperty("spring.redis.port").trim()));
        factory.setDatabase(Integer.parseInt(env.getProperty("spring.redis.database").trim()));
        log.info("JedisConnectionFactory bean init success.");
        return factory;
    }


    @Bean
    public RedisTemplate<?, ?> getRedisTemplate(){
        RedisTemplate<?,?> template = new StringRedisTemplate(getConnectionFactory());
        return template;
    }
}