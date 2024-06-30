package com.nhnacademy.auth.exceptionHandler;

import lombok.Builder;

@Builder
public record ErrorResponseForm(String title, int status, String timestamp) {
}