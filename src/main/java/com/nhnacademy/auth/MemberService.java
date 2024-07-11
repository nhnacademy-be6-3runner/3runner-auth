package com.nhnacademy.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhnacademy.auth.adapter.MemberAdapter;
import com.nhnacademy.auth.util.ApiResponse;

@Service
public class MemberService {

	private final MemberAdapter memberAdapter;


	public MemberService(MemberAdapter memberAdapter) {
		this.memberAdapter = memberAdapter;
	}

	public ApiResponse<Void> setLastLogin(Long userId) {

		return memberAdapter.lastLoginUpdate(userId);

	}
}
