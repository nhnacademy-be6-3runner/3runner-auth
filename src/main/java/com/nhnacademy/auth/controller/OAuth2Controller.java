package com.nhnacademy.auth.controller;

import java.net.URI;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import com.fasterxml.jackson.databind.JsonNode;
import com.nhnacademy.auth.adapter.PaycoAdapter;
import com.nhnacademy.auth.dto.response.MemberAuthResponse;
import com.nhnacademy.auth.entity.UserProfile;
import com.nhnacademy.auth.service.OAuth2AuthenticationService;
import com.nhnacademy.auth.service.TokenService;
import com.nhnacademy.auth.service.UserProfileService;
import com.nhnacademy.auth.util.ApiResponse;
import com.nimbusds.jose.shaded.gson.JsonObject;

import jakarta.servlet.http.HttpSession;
import reactor.core.publisher.Mono;

@RestController
public class OAuth2Controller {
	private final OAuth2AuthenticationService oAuth2AuthenticationService;
	private final UserProfileService userProfileService;
	private final PaycoAdapter paycoAdapter;
	private final TokenService tokenService;
	public OAuth2Controller(OAuth2AuthenticationService oAuth2AuthenticationService, UserProfileService userProfileService,PaycoAdapter paycoAdapter,TokenService tokenService) {
		this.oAuth2AuthenticationService = oAuth2AuthenticationService;
		this.userProfileService = userProfileService;
		this.paycoAdapter = paycoAdapter;
		this.tokenService = tokenService;
	}
	@PostMapping("/auth/oauth2/callback")
	public ApiResponse<Void> handleOAuth2Redirect(@RequestBody String code) {

		// 사용자 프로필 정보 획득
		JsonNode jsonNode = oAuth2AuthenticationService.getToken(code).block();
		String client_id = "3RDUR8qJyORVrsI2PdkInS1";
		String access_token = Objects.requireNonNull(jsonNode).get("access_token").asText();

		//headerparameter에 넘긴다.
		JsonNode returnData = oAuth2AuthenticationService.getUserDate(client_id,access_token).block();
		UserProfile userProfile = new UserProfile(returnData);

		//토큰만든다음 헤더에 넣고 보낸다.

		//발급한 토큰을 header로 보내야하려나? 어캐 넘기지..
		// 최종 페이지로 리디렉션//아마 gateway로 넘긴다...?그리고 최종페이지로 가는 요청 만들어야할듯?
		return new ApiResponse<>(new ApiResponse.Header(true,200,"Hi"));
	}
}
