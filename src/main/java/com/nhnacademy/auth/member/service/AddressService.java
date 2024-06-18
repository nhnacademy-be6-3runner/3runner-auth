package com.nhnacademy.auth.member.service;

import com.nhnacademy.auth.entity.address.Address;
import com.nhnacademy.auth.entity.member.Member;
import com.nhnacademy.auth.member.repository.AddressRepository;
import com.nhnacademy.auth.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;



@RequiredArgsConstructor
@Service
public class AddressService {
    private final AddressRepository addressRepository;

    /**
     * @Author -유지아
     * Save. -주소를 받아 저장한다.
     *
     * @param address the address -Address값을 받아 repository에 저장한다.
     * @return null -컨트롤러에서 전체 주소조회해서 반환하기 때문에 void반환으로 해둠.
     */
    public void save(Address address) {
        addressRepository.save(address);
    }

    /**
     * @Author -유지아
     * Find all list. -유저를 받아 유저에 저장된 주소들을 반환한다.
     *
     * @param member the member -Member요소를 가져온다.
     * @return the list -Address 리스트를 반환해준다.
     */
    public List<Address> findAll(Member member) {
        return addressRepository.findByMember(member);
    }

}