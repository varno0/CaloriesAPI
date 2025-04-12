package ru.varno.CaloriesAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Bean
    @Scope("singleton")
    public JedisPool jedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(8); // Максимальное число подключений
        poolConfig.setMaxIdle(5);   // Максимальное число неактивных подключений
        poolConfig.setMinIdle(1);   // Минимальное число неактивных подключений
        poolConfig.setTestOnBorrow(true); // Проверка соединения при выдаче из пула
        poolConfig.setJmxEnabled(false);

        return new JedisPool(poolConfig, "localhost", 6379);
    }
}
