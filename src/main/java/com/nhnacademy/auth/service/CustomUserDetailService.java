package com.nhnacademy.auth.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nhnacademy.auth.adapter.LoginAdapter;
import com.nhnacademy.auth.dto.CustomUserDetails;
import com.nhnacademy.auth.dto.request.MemberAuthRequest;
import com.nhnacademy.auth.dto.response.MemberAuthResponse;
import com.nhnacademy.auth.util.ApiResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 커스텀 유저 디테일 서비스
 *
 * @author 오연수
 */
@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	private LoginAdapter loginAdapter;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		ApiResponse<MemberAuthResponse> response = null;
		try {
			response = loginAdapter.memberLogin(new MemberAuthRequest(email));
			if (Objects.isNull(response)) {
				// TODO 뭔 처리를 해야..
				log.warn("response 값 비어있음");
			}
		} catch (Exception e) {
			throw new UsernameNotFoundException("이메일로 멤버를 찾을 수 없다.");
		}
		return new CustomUserDetails(response.getBody().getData());
	}
}
