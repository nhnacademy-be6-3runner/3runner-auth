package com.nhnacademy.auth.member.repository;

import com.nhnacademy.auth.entity.address.Address;
import com.nhnacademy.auth.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByMember(Member member);
}



