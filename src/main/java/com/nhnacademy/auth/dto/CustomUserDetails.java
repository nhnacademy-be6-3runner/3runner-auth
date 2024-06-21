package com.nhnacademy.auth.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final MemberAuthDTO memberAuthDTO;

    public CustomUserDetails(MemberAuthDTO memberAuthDTO) {
        this.memberAuthDTO = memberAuthDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return memberAuthDTO.auth().getFirst();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return memberAuthDTO.password();
    }

    @Override
    public String getUsername() {
        return memberAuthDTO.email();
    }
}
