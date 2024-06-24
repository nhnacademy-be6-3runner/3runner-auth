package com.nhnacademy.auth.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

/**
 * JWT Utility Class
 *
 * @author 오연수
 */
@Component
public class JWTUtil {
	private final SecretKey secretKey;

	public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
		this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm());
	}

	/**
	 * Token 을 생성한다.
	 *
	 * @param category Access, or Refresh
	 * @param username 이메일
	 * @param auth 권한
	 * @param memberId 멤버 아이디
	 * @param expiredMs the expired ms
	 * @return token 값
	 */
	public String generateToken(String category, String username, String auth, Long memberId, Long expiredMs) {
		return Jwts.builder()
			.claim("category", category)
			.claim("username", username)
			.claim("memberId", memberId)
			.claim("auth", auth)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + expiredMs))
			.signWith(secretKey)
			.compact();
	}
}
