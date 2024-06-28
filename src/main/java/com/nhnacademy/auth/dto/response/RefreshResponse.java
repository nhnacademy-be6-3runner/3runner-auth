package com.nhnacademy.auth.dto.response;

import lombok.Builder;

@Builder
public record RefreshResponse(
	String message,
	String accessToken
) {
}
