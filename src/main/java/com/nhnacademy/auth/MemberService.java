package com.nhnacademy.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhnacademy.auth.adapter.MemberAdapter;



@Service
public class MemberService {

	private final MemberAdapter memberAdapter;


	public MemberService(MemberAdapter memberAdapter) {
		this.memberAdapter = memberAdapter;
	}

	public void setLastLogin(Long userId) {
		try {
			memberAdapter.lastLoginUpdate(userId);;
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
