package com.nhnacademy.auth.address;

import com.nhnacademy.auth.address.controller.AddressController;
import com.nhnacademy.auth.address.service.AddressService;
import com.nhnacademy.auth.entity.address.Address;
import com.nhnacademy.auth.entity.address.dto.UpdateAddressRequest;
import com.nhnacademy.auth.entity.address.dto.UpdateAddressResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AddressController.class)
public class AddressControllerTest {

    MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();

    }

    @Test
    void updateAddress() throws Exception {
        Address address = Address.builder()
                .name("New Address Name")
                .build();

        UpdateAddressResponse updateAddressResponse = UpdateAddressResponse.builder()
                .id("1")
                .name("New Address Name")
                .build();

        Mockito.when(addressService.updateAddress(anyString(), any(UpdateAddressRequest.class)))
                .thenReturn(address);

        String requestJson = "{ \"name\": \"New Address Name\", \"country\": \"Country\", \"city\": \"City\", \"state\": \"State\", \"road\": \"Road\", \"postalCode\": \"12345\" }";

        mockMvc.perform(MockMvcRequestBuilders.put("/members/addresses")
                        .header("Address-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteAddress() throws Exception {
        Mockito.doNothing().when(addressService).deleteAddress(anyString());

        mockMvc.perform(MockMvcRequestBuilders.delete("/members/addresses")
                        .header("Address-Id", "1"))
                .andDo(print())
                .andExpect(content().json("{\"header\":{\"resultCode\":204,\"resultMessage\":\"Address deleted\",\"successful\":true}}"));
    }
}
