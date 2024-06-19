package com.nhnacademy.auth.entity.address.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public record AddressResponse (@Size(min = 1,max = 20) @NotNull String name,
                               @Size(min = 1, max = 100) @NotNull String country,
                               @Size(min = 1,max = 100) @NotNull String city,
                               @Size(min =1,max = 100) @NotNull String state,
                               @Size(min = 1,max = 100) @NotNull String road,
                               @Size(min = 1,max = 20) @NotNull String postalCode){

}

