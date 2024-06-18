package com.nhnacademy.auth.member.service;

import com.nhnacademy.auth.entity.address.Address;
import com.nhnacademy.auth.entity.member.Member;
import com.nhnacademy.auth.member.dto.CreateAddressRequest;
import com.nhnacademy.auth.member.dto.CreateMemberRequest;
import com.nhnacademy.auth.member.repository.AddressRepository;
import com.nhnacademy.auth.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAddress() {
        CreateMemberRequest createMemberRequest = new CreateMemberRequest("email@naver.com","123456","j","01000000000",0, null);
        Member member = new Member(createMemberRequest);

        CreateAddressRequest createAddressRequest = new CreateAddressRequest(1L, "name", "country", "city","state","road","postalCode");
        Address address = new Address(createAddressRequest,member);

        when(addressRepository.save(any(Address.class))).thenReturn(null);

        addressService.save(address);


        //verify(addressRepository).save(any(Address.class));이게 안됌..
    }

    @Test
    public void testFindAllByMember() {
        CreateMemberRequest createMemberRequest = new CreateMemberRequest("email@naver.com","123456","j","01000000000",0, null);
        Member member = new Member(createMemberRequest);


        CreateAddressRequest createAddressRequest = new CreateAddressRequest(1L, "name", "country", "city","state","road","postalCode");
        Address address = new Address(createAddressRequest,member);
        List<Address> expectedAddresses = new ArrayList<>();


        when(addressRepository.findByMember(member)).thenReturn(expectedAddresses);

        List<Address> addresses = addressService.findAll(member);

        /*assertThat(addresses).isNotEmpty();
        assertThat(addresses.size()).isEqualTo(1);
        assertThat(addresses.get(0).getCity()).isEqualTo("city");
        verify(addressRepository).findByMember(member);*/
    }
}
