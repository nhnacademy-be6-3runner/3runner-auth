package com.nhnacademy.auth.service;

import java.util.List;

import com.nhnacademy.auth.dto.TokenDetails;

public interface TokenService {
	List<String> generateToken(String username, List<String> auths, Long memberId);

	TokenDetails getTokenDetails(String uuid);

	void deleteTokenDetail(String uuid);

	String getRefreshToken(String uuid);

	void deleteRefreshToken(String uuid);

	boolean existsRefreshToken(String uuid, String inputRefresh);

}
