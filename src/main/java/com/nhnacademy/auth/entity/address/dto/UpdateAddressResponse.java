package com.nhnacademy.auth.entity.address.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateAddressResponse {
    private String id;
    private String name;
}
