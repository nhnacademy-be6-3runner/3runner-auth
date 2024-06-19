package com.nhnacademy.auth.entity.member.dto;

import com.nhnacademy.auth.entity.member.enums.Grade;
import com.nhnacademy.auth.entity.member.enums.Status;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Builder
@Getter
public record GetMemberResponse (@NotNull @Size(min = 1, max = 50) String password,
                                 @NotNull Long point,
                                 @NotNull @Size(min = 1, max = 10) String name,
                                 int age,
                                 @NotNull @Size(min = 1, max = 11) String phone,
                                 @NotNull @Column(unique = true) String email,
                                 ZonedDateTime birthday,
                                 @NotNull Grade grade,
                                 ZonedDateTime last_login_date,
                                 @NotNull ZonedDateTime created_at,
                                 ZonedDateTime modified_at){
}
