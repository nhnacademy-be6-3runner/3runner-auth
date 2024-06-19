package com.nhnacademy.auth.address.service;

import com.nhnacademy.auth.entity.address.Address;
import com.nhnacademy.auth.address.dto.request.UpdateAddressRequest;
import com.nhnacademy.auth.entity.member.Member;

import java.util.List;

public interface AddressService {
    void save(Address address);

    List<Address> findAll(Member member);

    Address updateAddress(String addressId, UpdateAddressRequest updateAddressRequest);

    void deleteAddress(String addressId);
}
