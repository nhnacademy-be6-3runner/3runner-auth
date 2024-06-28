package com.nhnacademy.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhnacademy.auth.dto.TokenDetails;
import com.nhnacademy.auth.dto.request.RefreshRequest;
import com.nhnacademy.auth.dto.response.RefreshResponse;
import com.nhnacademy.auth.service.TokenService;
import com.nhnacademy.auth.util.ApiResponse;
import com.nhnacademy.auth.util.CookieUtil;
import com.nhnacademy.auth.util.JWTUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
public class TokenController {
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private TokenService tokenService;

	@ResponseBody
	@GetMapping("/test")
	public String test() {
		return "hello";
	}

	@PostMapping("/reissue")
	public ApiResponse<RefreshResponse> reissue(RefreshRequest refreshRequest, HttpServletRequest request,
		HttpServletResponse response) {

		//get refresh token
		String refresh = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("Refresh")) {
				refresh = cookie.getValue();
			}
		}
		if (refresh == null) {

			return ApiResponse.badRequestFail(new ApiResponse.Body<>(new RefreshResponse("refresh token null", null)));
		}

		//expired check
		try {
			jwtUtil.isExpired(refresh);
		} catch (ExpiredJwtException e) {

			return ApiResponse.badRequestFail(
				new ApiResponse.Body<>(new RefreshResponse("refresh token expired", null)));
		}

		// 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
		String category = jwtUtil.getCategory(refresh);

		if (!category.equals("refresh")) {

			return ApiResponse.badRequestFail(
				new ApiResponse.Body<>(new RefreshResponse("invalid refresh token", null)));
		}

		String uuid = jwtUtil.getUuid(refresh);

		// Redis 에 저장되어 있는지 확인 (refresh 토큰 내용도 비교)
		if (!tokenService.existsRefreshToken(uuid, refresh)) {

			return ApiResponse.badRequestFail(
				new ApiResponse.Body<>(new RefreshResponse("invalid refresh token", null)));
		}

		TokenDetails tokenDetails = null;

		tokenDetails = tokenService.getTokenDetails(uuid);

		// make new Access token
		List<String> tokens = tokenService.generateToken(tokenDetails.getEmail(), tokenDetails.getAuths(),
			tokenDetails.getMemberId());

		// 기존 uuid 삭제
		tokenService.deleteRefreshToken(uuid);
		tokenService.deleteTokenDetail(uuid);

		// TODO 블랙리스트

		// jwt 생성후 헤더에 붙여준다.
		response.addHeader("Authorization", "Bearer " + tokens.getFirst());
		response.addCookie(CookieUtil.createCookie("Refresh", refresh));
		response.setStatus(HttpStatus.OK.value());

		return ApiResponse.success(new RefreshResponse("토큰 재발급 완료", tokens.getFirst()));
	}
}
