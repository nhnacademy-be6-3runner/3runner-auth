package com.nhnacademy.auth.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.auth.dto.request.MemberAuthRequest;
import com.nhnacademy.auth.dto.response.MemberAuthResponse;
import com.nhnacademy.auth.util.ApiResponse;

/**
 * OpenFeign 사용한 로그인 어댑터
 */
@FeignClient(url = "${feign.client.url}/bookstore", name = "MemberAdapter")
public interface MemberAdapter {

	@PutMapping("/members/lastLogin")
	ApiResponse<Void> lastLoginUpdate(@RequestBody Long memberId);
	@PutMapping("/members/lastLogin/dormantAwake")
		ApiResponse<Void> dormantAwake(@RequestBody String email);
}
