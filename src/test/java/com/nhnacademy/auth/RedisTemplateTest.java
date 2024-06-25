package com.nhnacademy.auth;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.auth.dto.TokenDetails;

@SpringBootTest
public class RedisTemplateTest {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private static final String TOKEN_DETAILS = "token_details_test";

	private ObjectMapper objectMapper = new ObjectMapper();

	@AfterEach
	public void cleanUp() {
		redisTemplate.delete(TOKEN_DETAILS);
	}

	@Test
	public void testRedisPutAndGet() throws JsonProcessingException {
		// Given
		String uuid = "test-uuid";
		TokenDetails tokenDetails = new TokenDetails("test@testtest.com", Arrays.asList("ROLE_USER"), 77777L);

		// When
		redisTemplate.opsForHash().put(TOKEN_DETAILS, uuid, objectMapper.writeValueAsString(tokenDetails));
		String data = (String)redisTemplate.opsForHash().get(TOKEN_DETAILS, uuid);
		TokenDetails retrievedTokenDetails = objectMapper.readValue(data, TokenDetails.class);
		// Then
		assertThat(retrievedTokenDetails).isNotNull();
		assertThat(retrievedTokenDetails.getEmail()).isEqualTo("test@testtest.com");
		assertThat(retrievedTokenDetails.getMemberId()).isEqualTo(77777L);
	}
}

