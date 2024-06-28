package com.nhnacademy.auth.dto.request;

import lombok.Builder;

@Builder
public record RefreshRequest(
	String refreshToken
) {
}
