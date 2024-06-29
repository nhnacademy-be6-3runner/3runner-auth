package com.nhnacademy.auth.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.auth.dto.TokenDetails;
import com.nhnacademy.auth.service.TokenService;
import com.nhnacademy.auth.util.JWTUtil;

@Service
public class TokenServiceImpl implements TokenService {
	private final String TOKEN_DETAILS = "token_details";
	private final String REFRESH_TOKEN = "refresh_token";
	private final Long ACCESS_TOKEN_TTL = 6000L; // 60 * 60 * 1000 = 3600000L
	private final Long REFRESH_TOKEN_TTL = 86400000L;
	private final JWTUtil jwtUtil;

	private final RedisTemplate<String, Object> redisTemplate;

	ObjectMapper objectMapper = new ObjectMapper();

	public TokenServiceImpl(JWTUtil jwtUtil, RedisTemplate<String, Object> redisTemplate) {
		this.jwtUtil = jwtUtil;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public List<String> generateToken(String username, List<String> auths, Long memberId) {
		String uuid = UUID.randomUUID().toString();

		TokenDetails tokenDetails = new TokenDetails(username, auths, memberId);
		try {
			redisTemplate.opsForHash().put(TOKEN_DETAILS, uuid, objectMapper.writeValueAsString(tokenDetails));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		// TODO redis expire 설정
		// redisTemplate.expire(TOKEN_DETAILS, ACCESS_TOKEN_TTL, TimeUnit.MICROSECONDS);

		String accessToken = jwtUtil.generateTokenWithUuid("ACCESS", uuid, ACCESS_TOKEN_TTL);
		String refreshToken = jwtUtil.generateTokenWithUuid("REFRESH", uuid, REFRESH_TOKEN_TTL);
		redisTemplate.opsForHash().put(REFRESH_TOKEN, uuid, refreshToken);
		return Arrays.asList(accessToken, refreshToken);
	}

	@Override
	public TokenDetails getTokenDetails(String uuid) {
		String data = (String)redisTemplate.opsForHash().get(TOKEN_DETAILS, uuid);
		try {
			return objectMapper.readValue(data, TokenDetails.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteTokenDetail(String uuid) {
		redisTemplate.opsForHash().delete(TOKEN_DETAILS, uuid);
	}

	@Override
	public String getRefreshToken(String uuid) {
		return (String)redisTemplate.opsForHash().get(REFRESH_TOKEN, uuid);
	}

	@Override
	public void deleteRefreshToken(String uuid) {
		redisTemplate.opsForHash().delete(REFRESH_TOKEN, uuid);
	}

	@Override
	public boolean existsRefreshToken(String uuid, String inputRefresh) {
		String storedRefresh = getRefreshToken(uuid);
		return Objects.nonNull(storedRefresh) && inputRefresh.equals(storedRefresh);
	}

}
