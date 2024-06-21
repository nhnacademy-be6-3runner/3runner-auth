package com.nhnacademy.auth.dto;

import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Builder
public record MemberAuthDTO(
        @NotNull String email,
        @NotNull @Size(min = 1, max = 50) String password,
        @NotNull List<String> auth
){
}