package com.nhnacademy.auth.member.dto.response;

import lombok.Builder;

/**
 * The type Update member response.
 *
 * @author 오연수
 */
@Builder
public record UpdateMemberResponse (
        String id, String name
){}