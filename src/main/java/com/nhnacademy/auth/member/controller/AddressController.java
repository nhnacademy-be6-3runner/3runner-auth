package com.nhnacademy.auth.member.controller;

import com.nhnacademy.auth.entity.address.Address;
import com.nhnacademy.auth.entity.member.Member;
import com.nhnacademy.auth.member.dto.CreateAddressRequest;
import com.nhnacademy.auth.member.service.AddressService;
import com.nhnacademy.auth.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Address controller.
 */
@RestController
@RequiredArgsConstructor
public class AddressController {
    private final MemberService memberService;
    private final AddressService addressService;

    /**
     * Create address response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping("/address/create")
    public ResponseEntity<List<Address>> createAddress(@RequestBody CreateAddressRequest request) {
        Member member = memberService.findById(request.memberId());
        Address address = new Address(request,member);
        addressService.save(address);
        return ResponseEntity.ok(addressService.findAll(member));
    }

    /**
     * Find all addresses response entity.
     *
     * @param memberId the member id
     * @return the response entity
     */
//주소를 추가한다.
    @GetMapping("/address/getAll")
    public ResponseEntity<List<Address>> findAllAddresses(@RequestHeader("member-id") Long memberId) {
        Member member = memberService.findById(memberId);
        return ResponseEntity.ok(addressService.findAll(member));
    }
    //멤버의 주소를 가져온다.

}


