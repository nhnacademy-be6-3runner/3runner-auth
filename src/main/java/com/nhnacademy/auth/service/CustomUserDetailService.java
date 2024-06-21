package com.nhnacademy.auth.service;

import com.nhnacademy.auth.dto.CustomUserDetails;
import com.nhnacademy.auth.dto.MemberAuthDTO;
import com.nhnacademy.auth.entity.Auth;
import com.nhnacademy.auth.entity.Member;
import com.nhnacademy.auth.entity.MemberAuth;
import com.nhnacademy.auth.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {
    // repository 에서 가져오기 -> 페잉 호출
    @Autowired
    MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username);
        if (member != null) {
            List<MemberAuth> list = member.getMemberAuthSet();
            List<String> auths = list.stream().map(MemberAuth::getAuth)
                    .map(Auth::getName)
                    .collect(Collectors.toList());

            return new CustomUserDetails(new MemberAuthDTO(member.getEmail(), member.getPassword(), auths));
        }
        return null;
    }
}
