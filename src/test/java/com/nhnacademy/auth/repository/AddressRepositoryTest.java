package com.nhnacademy.auth.repository;

import com.nhnacademy.auth.address.repository.AddressRepository;
import com.nhnacademy.auth.entity.address.Address;
import com.nhnacademy.auth.entity.address.dto.CreateAddressRequest;
import com.nhnacademy.auth.entity.member.Member;
import com.nhnacademy.auth.entity.member.dto.CreateMemberRequest;
import com.nhnacademy.auth.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AddressRepositoryTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private MemberRepository memberRepository;
    @BeforeEach
    void setUp() {
        entityManager.clear();
    }
    @Test
    void testSaveAndFindById(){
        CreateMemberRequest createMemberRequest = new CreateMemberRequest("email@naver.com","123456","j","01000000000",0, null);
        Member member = new Member(createMemberRequest);
        memberRepository.save(member);
        CreateAddressRequest createAddressRequest = new CreateAddressRequest(1L, "name", "country", "city","state","road","postalCode");
        Address address = new Address(createAddressRequest,member);

        Address savedAddress = addressRepository.save(address);
        List<Address> addressList = addressRepository.findByMember(member);
        assertThat(addressList.size()).isEqualTo(1);
    }

}



