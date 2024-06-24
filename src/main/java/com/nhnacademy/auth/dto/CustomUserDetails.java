package com.nhnacademy.auth.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nhnacademy.auth.dto.response.MemberAuthResponse;

public class CustomUserDetails implements UserDetails {

	private final MemberAuthResponse memberAuthResponse;

	public CustomUserDetails(MemberAuthResponse memberAuthResponse) {
		this.memberAuthResponse = memberAuthResponse;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();

		collection.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return memberAuthResponse.auth().getFirst();
			}
		});
		return collection;
	}

	@Override
	public String getPassword() {
		return memberAuthResponse.password();
	}

	@Override
	public String getUsername() {
		return memberAuthResponse.email();
	}

	public Long getMemberId() {
		return memberAuthResponse.memberId();
	}
}
