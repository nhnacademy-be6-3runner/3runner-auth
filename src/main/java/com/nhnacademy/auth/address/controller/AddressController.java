package com.nhnacademy.auth.address.controller;

import com.nhnacademy.auth.entity.address.dto.UpdateAddressResponse;
import com.nhnacademy.auth.util.ApiResponse;
import com.nhnacademy.auth.address.service.AddressService;
import com.nhnacademy.auth.entity.address.Address;
import com.nhnacademy.auth.entity.address.dto.UpdateAddressRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PutMapping("/members/addresses")
    public ApiResponse<UpdateAddressResponse> updateAddress(@RequestHeader(name = "Address-Id") String addressId,
                                                            @RequestBody UpdateAddressRequest updateAddressRequest) {
        try {
            Address address = addressService.updateAddress(addressId, updateAddressRequest);
            UpdateAddressResponse updateAddressResponse = UpdateAddressResponse.builder()
                    .id(addressId)
                    .name(address.getName())
                    .build();
            return ApiResponse.success(updateAddressResponse);
        }
        catch (RuntimeException e) {
            return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @DeleteMapping("/members/addresses")
    public ApiResponse<Void> deleteAddress(@RequestHeader(name = "Address-Id") String addressId) {
        try {
            addressService.deleteAddress(addressId);
            return new ApiResponse<>(new ApiResponse.Header(true, HttpStatus.NO_CONTENT.value(), "Address deleted"));
        }
        catch (RuntimeException e) {
            return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
