package com.nhnacademy.auth.address;

import com.nhnacademy.auth.address.exception.AddressNotExistsException;
import com.nhnacademy.auth.address.repository.AddressRepository;
import com.nhnacademy.auth.address.service.AddressService;
import com.nhnacademy.auth.entity.address.Address;
import com.nhnacademy.auth.entity.address.dto.UpdateAddressRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateAddressTest() {
        // Given
        String addressId = "1";
        UpdateAddressRequest updateAddressRequest = UpdateAddressRequest.builder()
                .name("Updated Name")
                .country("Updated Country")
                .city("Updated City")
                .state("Updated State")
                .road("Updated Road")
                .postalCode("123456")
                .build();

        Address address = Address.builder()
                .name("Old Name")
                .build();

        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        // When
        Address updatedAddress = addressService.updateAddress(addressId, updateAddressRequest);

        // Then
        assertEquals("Updated Name", updatedAddress.getName());
        assertEquals("Updated Country", updatedAddress.getCountry());
        assertEquals("Updated City", updatedAddress.getCity());
        assertEquals("Updated State", updatedAddress.getState());
        assertEquals("Updated Road", updatedAddress.getRoad());
        assertEquals("123456", updatedAddress.getPostalCode());

        verify(addressRepository, times(1)).findById(anyLong());
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void updateAddress_NotFoundTest() {
        // Given
        String addressId = "1";
        UpdateAddressRequest updateAddressRequest = UpdateAddressRequest.builder()
                .name("Updated Name")
                .build();

        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(AddressNotExistsException.class, () ->
                addressService.updateAddress(addressId, updateAddressRequest)
        );

        verify(addressRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteAddressTest() {
        // Given
        String addressId = "1";

        // When
        addressService.deleteAddress(addressId);

        // Then
        verify(addressRepository, times(1)).deleteById(anyLong());
    }
}
