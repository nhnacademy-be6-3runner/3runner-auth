package com.nhnacademy.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nhnacademy.auth.adapter.LoginAdapter;
import com.nhnacademy.auth.dto.CustomUserDetails;
import com.nhnacademy.auth.dto.request.MemberAuthRequest;
import com.nhnacademy.auth.dto.response.MemberAuthResponse;

@Service
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	private LoginAdapter loginAdapter;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		MemberAuthResponse response = null;
		try {
			response = loginAdapter.memberLogin(new MemberAuthRequest(email));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new CustomUserDetails(response);
	}
}
