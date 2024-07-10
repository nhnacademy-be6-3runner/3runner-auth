package com.nhnacademy.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.auth.MemberService;
import com.nhnacademy.auth.adapter.MemberAdapter;
import com.nhnacademy.auth.adapter.PaycoAdapter;
import com.nhnacademy.auth.dto.request.DormantRequest;
import com.nhnacademy.auth.dto.response.LoginResponse;
import com.nhnacademy.auth.entity.DormantObject;
import com.nhnacademy.auth.service.DormantService;
import com.nhnacademy.auth.service.OAuth2AuthenticationService;
import com.nhnacademy.auth.service.TokenService;
import com.nhnacademy.auth.service.UserProfileService;
import com.nhnacademy.auth.util.ApiResponse;
import com.nhnacademy.auth.util.CookieUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class DormantController {
	private final DormantService dormantService;

	private final MemberAdapter memberAdapter;

	public DormantController(DormantService dormantService, MemberAdapter memberAdapter) {
		this.dormantService = dormantService;
		this.memberAdapter = memberAdapter;
	}

	@PostMapping("/auth/dormant")
	public ApiResponse<Void> dormantCheck(@RequestBody DormantRequest request) {
		DormantObject resultObject = dormantService.checkVerificationCode(request.email(), request.code());
		if (resultObject != null) {
			HttpServletResponse servletResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
			//여기다가 애들 해줘야하ㄹㅁㄷㅈㅁㄷㅈㄹㄷㅈㅁㄹ
			if (servletResponse != null) {
				servletResponse.addHeader("Authorization", "Bearer " + resultObject.getAccess());
				servletResponse.addCookie(CookieUtil.createCookie("Refresh", resultObject.getRefresh()));
				servletResponse.setStatus(HttpStatus.OK.value());

				servletResponse.setStatus(HttpServletResponse.SC_OK);
				servletResponse.setContentType("application/json;charset=UTF-8");
				memberAdapter.dormantAwake(request.email());

				return ApiResponse.success(null);
			}else{
				return ApiResponse.fail(400,null);
			}
		} else {
			return ApiResponse.fail(400,null);
		}

	}
}



