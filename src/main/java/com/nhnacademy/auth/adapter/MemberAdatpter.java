package com.nhnacademy.auth.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.auth.dto.request.MemberAuthRequest;
import com.nhnacademy.auth.dto.response.MemberAuthResponse;
@FeignClient(url = "http://localhost:8080/bookstore",name = "MemberAdapter")
public interface MemberAdatpter {
	@PostMapping("/members/oauth")
	MemberAuthResponse createMember(@RequestBody MemberAuthRequest request);
}

