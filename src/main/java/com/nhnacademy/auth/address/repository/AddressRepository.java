package com.nhnacademy.auth.address.repository;

import com.nhnacademy.auth.entity.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
