package com.nhnacademy.auth.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.nimbusds.jose.shaded.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {
	private String idNo;
	private String email;
	private String mobile;
	private String name;

	// 생성자
	public UserProfile(JsonNode jsonNode) {
		JsonNode memberNode = jsonNode.path("data").path("member");
		this.idNo = memberNode.path("idNo").asText();
		this.email = memberNode.path("email").asText();
		this.name = memberNode.path("name").asText();
		this.mobile = memberNode.path("mobile").asText();
	}

}
