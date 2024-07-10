package com.nhnacademy.auth.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class DormantObject {
	@JsonProperty("email")
	private String email;
	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("access")
	private String access;
	@JsonProperty("refresh")
	private String refresh;

}


