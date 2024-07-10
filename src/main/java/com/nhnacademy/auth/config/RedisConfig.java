package com.nhnacademy.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> sessionRedisTemplate = new RedisTemplate<>();
		sessionRedisTemplate.setConnectionFactory(redisConnectionFactory);

		sessionRedisTemplate.setKeySerializer(new StringRedisSerializer());
		sessionRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

		sessionRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
		sessionRedisTemplate.setHashValueSerializer(new StringRedisSerializer());
		return sessionRedisTemplate;
	}
	@Bean
	public RedisTemplate<String, String> dormantRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
			RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
			redisTemplate.setConnectionFactory(redisConnectionFactory);

			// 모든 데이터를 문자열(String)로 직렬화하여 저장하기 위한 설정
			redisTemplate.setKeySerializer(new StringRedisSerializer());
			redisTemplate.setValueSerializer(new StringRedisSerializer());

			redisTemplate.setHashKeySerializer(new StringRedisSerializer());
			redisTemplate.setHashValueSerializer(new StringRedisSerializer());

			return redisTemplate;

	}

}
