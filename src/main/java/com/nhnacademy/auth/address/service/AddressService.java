package com.nhnacademy.auth.address.service;

import com.nhnacademy.auth.address.exception.AddressNotExistsException;
import com.nhnacademy.auth.entity.address.Address;
import com.nhnacademy.auth.entity.address.dto.UpdateAddressRequest;
import com.nhnacademy.auth.address.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    /**
     * Update address.
     *
     * @param addressId            주소 id
     * @param updateAddressRequest name, country, city, state, road, postalCode
     * @return the address
     */
    public Address updateAddress(String addressId, UpdateAddressRequest updateAddressRequest) {
        Long id = Long.parseLong(addressId);
        Address address = addressRepository.findById(id).orElseThrow(() -> new AddressNotExistsException());

        address.setName(updateAddressRequest.getName());
        address.setCountry(updateAddressRequest.getCountry());
        address.setCity(updateAddressRequest.getCity());
        address.setState(updateAddressRequest.getState());
        address.setRoad(updateAddressRequest.getRoad());
        address.setPostalCode(updateAddressRequest.getPostalCode());

        return addressRepository.save(address);
    }

    /**
     * Delete address.
     *
     * @param addressId 주소 id
     */
    public void deleteAddress(String addressId) {
        Long id = Long.parseLong(addressId);
        addressRepository.deleteById(id);
    }
}
