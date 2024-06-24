package com.nhnacademy.auth.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.auth.dto.request.MemberAuthRequest;
import com.nhnacademy.auth.dto.response.MemberAuthResponse;

/**
 * OpenFeign 사용한 로그인 어댑터
 */
@FeignClient(url = "http://localhost:8080/bookstore", name = "LoginAdapter")
public interface LoginAdapter {
	/**
	 * Member login api response.
	 *
	 * @param memberAuthRequest the member auth request
	 * @return the api response
	 */
	@PostMapping("/login")
	MemberAuthResponse memberLogin(@RequestBody MemberAuthRequest memberAuthRequest);
}