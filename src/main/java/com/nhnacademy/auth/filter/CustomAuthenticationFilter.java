package com.nhnacademy.auth.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.auth.dto.CustomUserDetails;
import com.nhnacademy.auth.dto.request.LoginRequest;
import com.nhnacademy.auth.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final JWTUtil jwtUtil;
	private final AuthenticationManager authenticationManager;
	private final ObjectMapper objectMapper;

	public CustomAuthenticationFilter(JWTUtil jwtUtil, AuthenticationManager authenticationManager,
		ObjectMapper objectMapper) {
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
		this.objectMapper = objectMapper;
		this.setFilterProcessesUrl("/auth/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		// TODO email 과 password request 가져와야 함
		LoginRequest loginRequest = null;
		try {
			loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.email(),
			loginRequest.password());
		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		CustomUserDetails customUserDetails = (CustomUserDetails)authResult.getPrincipal();

		String username = customUserDetails.getUsername();
		Long memberId = customUserDetails.getMemberId();

		Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();
		String authority = auth.getAuthority();

		String access = jwtUtil.generateToken("access", username, authority, memberId, 600000L);
		String refresh = jwtUtil.generateToken("refresh", username, authority, memberId, 86400000L);

		// jwt 생성후 헤더에 붙여준다.
		response.addHeader("Authorization", "Bearer " + access);
		response.addCookie(createCookie("Refresh", refresh));
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json");
		response.getWriter().write("{\"token\": \"" + access + "\"}");

		SecurityContextHolder.getContext().setAuthentication(authResult);
	}

	private Cookie createCookie(String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(24 * 60 * 60);
		cookie.setHttpOnly(true);
		cookie.setPath("/");

		return cookie;
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
}
