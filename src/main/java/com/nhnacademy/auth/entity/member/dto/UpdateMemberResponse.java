package com.nhnacademy.auth.entity.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@Builder
public class UpdateMemberResponse {
    private String id;
    private String name;
}
