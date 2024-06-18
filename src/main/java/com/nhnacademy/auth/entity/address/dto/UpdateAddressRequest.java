package com.nhnacademy.auth.entity.address.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateAddressRequest {
    private String name;
    private String country;
    private String city;
    private String state;
    private String road;
    private String postalCode;
}
