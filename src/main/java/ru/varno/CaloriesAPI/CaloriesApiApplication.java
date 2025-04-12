package ru.varno.CaloriesAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@SpringBootApplication
public class CaloriesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaloriesApiApplication.class, args);
	}
}
