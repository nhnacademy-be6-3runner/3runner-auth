package com.nhnacademy.auth.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.auth.adapter.PaycoAdapter;
import com.nhnacademy.auth.dto.request.DormantRequest;
import com.nhnacademy.auth.dto.response.LoginResponse;
import com.nhnacademy.auth.service.DormantService;
import com.nhnacademy.auth.service.OAuth2AuthenticationService;
import com.nhnacademy.auth.service.TokenService;
import com.nhnacademy.auth.service.UserProfileService;
import com.nhnacademy.auth.util.ApiResponse;
@RestController
public class DormantController {
	private final DormantService dormantService;

	public DormantController(DormantService dormantService) {
		this.dormantService = dormantService;
	}

	@PostMapping("/auth/dormant")
	public ApiResponse<Void> dormantCheck(@RequestBody DormantRequest request) {
		if (dormantService.checkVerificationCode(request.email(), request.code())) {
			return ApiResponse.success(null);
		} else {
			return ApiResponse.fail(400,null);
		}

	}
}
