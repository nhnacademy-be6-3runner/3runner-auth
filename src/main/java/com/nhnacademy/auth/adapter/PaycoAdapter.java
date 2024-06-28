package com.nhnacademy.auth.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.auth.dto.request.MemberAuthRequest;
import com.nhnacademy.auth.dto.response.MemberAuthResponse;
import com.nhnacademy.auth.entity.UserProfile;
import com.nhnacademy.auth.util.ApiResponse;

@FeignClient(url = "http://localhost:8080/bookstore/members/payco",name = "PaycoAdapter")
public interface PaycoAdapter {
	@PostMapping
	ApiResponse<MemberAuthResponse> paycoMember(@RequestBody UserProfile userProfile);
}


