package com.nhnacademy.auth.exceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nhnacademy.auth.exception.CustomFeignException;
import com.nhnacademy.auth.util.ApiResponse;

@RestControllerAdvice
public class WebControllerAdvice {

	@ExceptionHandler(CustomFeignException.class)
	public ApiResponse<ErrorResponseForm> runtimeExceptionHandler(RuntimeException e) {
		return ApiResponse.fail(500,
			new ApiResponse.Body<>(
				ErrorResponseForm.builder()
					.title(e.getMessage())
					.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.timestamp(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toString())
					.build()
			));
	}

}
