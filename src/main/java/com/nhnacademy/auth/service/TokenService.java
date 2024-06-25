package com.nhnacademy.auth.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.auth.dto.TokenDetails;
import com.nhnacademy.auth.util.JWTUtil;

@Service
public class TokenService {
	private final String TOKEN_DETAILS = "token_details";
	private final String REFRESH_TOKEN = "refresh_token";
	private final Long ACCESS_TOKEN_TTL = 3600000L; // 60 * 60 * 1000
	private final Long REFRESH_TOKEN_TTL = 86400000L;
	private final JWTUtil jwtUtil;

	private final RedisTemplate<String, Object> redisTemplate;

	private final ObjectMapper objectMapper;

	public TokenService(JWTUtil jwtUtil, RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
		this.jwtUtil = jwtUtil;
		this.redisTemplate = redisTemplate;
		this.objectMapper = objectMapper;
	}

	public List<String> generateToken(String username, List<String> auths, Long memberId) throws
		JsonProcessingException {
		String uuid = UUID.randomUUID().toString();

		TokenDetails tokenDetails = new TokenDetails(username, auths, memberId);
		redisTemplate.opsForHash().put(TOKEN_DETAILS, uuid, objectMapper.writeValueAsString(tokenDetails));
		// expire 설정
		// redisTemplate.expire(TOKEN_DETAILS, ACCESS_TOKEN_TTL, TimeUnit.MICROSECONDS);

		String accessToken = jwtUtil.generateTokenWithUuid("ACCESS", uuid, ACCESS_TOKEN_TTL);
		String refreshToken = jwtUtil.generateTokenWithUuid("REFRESH", uuid, REFRESH_TOKEN_TTL);
		// redisTemplate.opsForHash().put(REFRESH_TOKEN, uuid, refreshToken);
		return Arrays.asList(accessToken, refreshToken);
	}

	public TokenDetails getTokenDetails(String uuid) throws JsonProcessingException {
		return (TokenDetails)redisTemplate.opsForHash().get(TOKEN_DETAILS, uuid);
	}

	public void deleteTokenDetail(String uuid) {
		redisTemplate.opsForHash().delete(TOKEN_DETAILS, uuid);
	}
}
