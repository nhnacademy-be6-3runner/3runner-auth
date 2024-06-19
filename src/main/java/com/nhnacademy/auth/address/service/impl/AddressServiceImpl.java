package com.nhnacademy.auth.address.service.impl;

import com.nhnacademy.auth.address.exception.AddressNotExistsException;
import com.nhnacademy.auth.address.service.AddressService;
import com.nhnacademy.auth.entity.address.Address;
import com.nhnacademy.auth.address.dto.request.UpdateAddressRequest;
import com.nhnacademy.auth.address.repository.AddressRepository;
import com.nhnacademy.auth.entity.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Address service.
 *
 * @author 오연수, 유지아
 */
@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    /**
     * Save.
     *
     * @param address the address -Address값을 받아 repository에 저장한다.  null -컨트롤러에서 전체 주소조회해서 반환하기 때문에 void반환으로 해둠.
     * @author 유지아, 오연수 Save. -주소를 받아 저장한다.
     */
    public void save(Address address) {
        addressRepository.save(address);
    }

    /**
     * Find all list.
     *
     * @param member the member -Member요소를 가져온다.
     * @return the list -Address 리스트를 반환해준다.
     * @author 유지아  Find all list. -유저를 받아 유저에 저장된 주소들을 반환한다.
     */
    public List<Address> findAll(Member member) {
        return addressRepository.findByMember(member);
    }

    /**
     * Update address.
     *
     * @param addressId            주소 id
     * @param updateAddressRequest name, country, city, state, road, postalCode
     * @return the address
     * @author 오연수
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
     * @author 오연수
     */
    public void deleteAddress(String addressId) {
        Long id = Long.parseLong(addressId);
        addressRepository.deleteById(id);
    }
}
