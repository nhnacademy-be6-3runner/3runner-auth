package com.nhnacademy.auth.address;

import com.nhnacademy.auth.address.repository.AddressRepository;
import com.nhnacademy.auth.entity.address.Address;
import com.nhnacademy.auth.entity.member.Member;
import com.nhnacademy.auth.entity.member.enums.Grade;
import com.nhnacademy.auth.entity.member.enums.Status;
import com.nhnacademy.auth.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.ZonedDateTime;
import java.util.Optional;

@DataJpaTest
public class AddressRepositoryTest {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    EntityManager entityManager;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void updateTest() {
        Member member = Member.builder()
                .name("test-name")
                .password("test-password")
                .point(6000L)
                .age(100)
                .phone("01012345678")
                .birthday(ZonedDateTime.parse("2000-01-01T00:00:00Z"))
                .grade(Grade.General)
                .status(Status.Active)
                .created_at(ZonedDateTime.now())
                .email("test@email.com")
                .build();

        memberRepository.save(member);

        Address address = Address.builder()
                .name("test-address")
                .country("Korea")
                .city("Gwang-ju")
                .state("state")
                .road("test-road")
                .postalCode("12345")
                .member(member)
                .build();

        Address savedAddress = addressRepository.save(address);
        Address foundAddress = addressRepository.findById(savedAddress.getId()).get();

        foundAddress.setCountry("south-korea");
        foundAddress.setPostalCode("54321");

        addressRepository.save(foundAddress);
        entityManager.flush();
        entityManager.clear();

        Assertions.assertEquals(savedAddress.getId(), foundAddress.getId());
        Assertions.assertEquals("south-korea", foundAddress.getCountry());
        Assertions.assertEquals("54321", foundAddress.getPostalCode());
    }

    @Test
    public void deleteTest() {
        Member member = Member.builder()
                .name("test-name")
                .password("test-password")
                .point(6000L)
                .age(100)
                .phone("01012345678")
                .birthday(ZonedDateTime.parse("2000-01-01T00:00:00Z"))
                .grade(Grade.General)
                .status(Status.Active)
                .created_at(ZonedDateTime.now())
                .email("test@email.com")
                .build();

        memberRepository.save(member);

        Address address = Address.builder()
                .name("test-address")
                .country("Korea")
                .city("Gwang-ju")
                .state("state")
                .road("test-road")
                .postalCode("12345")
                .member(member)
                .build();

        Address savedAddress = addressRepository.save(address);
        entityManager.flush();
        entityManager.clear();

        addressRepository.delete(savedAddress);
        entityManager.flush();
        entityManager.clear();

        Optional<Address> foundAddress = addressRepository.findById(savedAddress.getId());

        Assertions.assertTrue(foundAddress.isEmpty());
    }
}
