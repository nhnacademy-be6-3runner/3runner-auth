package com.nhnacademy.auth.address.controller;

import com.nhnacademy.auth.address.dto.request.CreateAddressRequest;
import com.nhnacademy.auth.address.dto.response.UpdateAddressResponse;
import com.nhnacademy.auth.entity.member.Member;
import com.nhnacademy.auth.member.service.impl.MemberServiceImpl;
import com.nhnacademy.auth.util.ApiResponse;
import com.nhnacademy.auth.address.service.impl.AddressServiceImpl;
import com.nhnacademy.auth.entity.address.Address;
import com.nhnacademy.auth.address.dto.request.UpdateAddressRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The type Address controller.
 *
 * @author 오연수, 유지아
 */
@RestController
@RequiredArgsConstructor
public class AddressController {
    private final MemberServiceImpl memberService;
    private final AddressServiceImpl addressServiceImpl;

    /**
     * Create address response entity.
     *
     * @param request the request
     * @author 유지아
     * @return the response entity
     *
     */
    @PostMapping("/address/create")
    public ResponseEntity<List<Address>> createAddress(@RequestBody CreateAddressRequest request) {
        Member member = memberService.findById(request.memberId());
        Address address = new Address(request,member);
        addressServiceImpl.save(address);
        return ResponseEntity.ok(addressServiceImpl.findAll(member));
    }

    /**
     * Find all addresses response entity.
     *
     * @author 유지아
     * @param memberId the member id
     * @return the response entity
     */
//주소를 추가한다.
    @GetMapping("/address/getAll")
    public ResponseEntity<List<Address>> findAllAddresses(@RequestHeader("member-id") Long memberId) {
        Member member = memberService.findById(memberId);
        return ResponseEntity.ok(addressServiceImpl.findAll(member));
    }
    //멤버의 주소를 가져온다.

    /**
     * 주소 업데이트
     *
     * @param addressId            the address id
     * @param updateAddressRequest name, country, city, state, road, postalCode
     * @return the api response - UpdateAddressResponse DTO
     * @author 오연수
     */
    @PutMapping("/members/addresses")
    public ApiResponse<UpdateAddressResponse> updateAddress(@RequestHeader(name = "Address-Id") String addressId,
                                                            @RequestBody UpdateAddressRequest updateAddressRequest) {
        try {
            Address address = addressServiceImpl.updateAddress(addressId, updateAddressRequest);
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


    /**
     * 주소 삭제
     *
     * @param addressId the address id
     * @return the api response - Void
     * @author 오연수
     */
    @DeleteMapping("/members/addresses")
    public ApiResponse<Void> deleteAddress(@RequestHeader(name = "Address-Id") String addressId) {
        try {
            addressServiceImpl.deleteAddress(addressId);
            return new ApiResponse<>(new ApiResponse.Header(true, HttpStatus.NO_CONTENT.value(), "Address deleted"));
        }
        catch (RuntimeException e) {
            return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
