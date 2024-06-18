package com.nhnacademy.auth.entity.member.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Builder
public class UpdateMemberRequest {
    private String password;
    private String name;
    private int age;
    private String phone;
    private String email;
    private ZonedDateTime birthday;
}
