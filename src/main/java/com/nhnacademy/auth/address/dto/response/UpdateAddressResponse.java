package com.nhnacademy.auth.address.dto.response;

import lombok.Builder;

/**
 * The type Update address response.
 *
 * @author 오연수
 */
@Builder
public record UpdateAddressResponse (
    String id,
    String name
){}
