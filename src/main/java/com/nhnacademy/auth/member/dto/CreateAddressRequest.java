package com.nhnacademy.auth.member.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.ZonedDateTime;

/**
 * @Author -유지아
 * The type Create address request. -주소 추가에 대한 요청 record이다.
 */
@Builder
public record CreateAddressRequest(
        @NotNull Long memberId,
        @NotNull @Size(min = 1, max = 20) String name,
        @NotNull @Size(min = 1, max = 100) String country,
        @NotNull @Size(min = 1, max = 100) String city,
        @NotNull @Size(min = 1, max = 100) String state,
        @NotNull @Size(min = 1, max = 100) String road,
        @NotNull @Size(min = 1, max = 20) String postalCode
) {
}
