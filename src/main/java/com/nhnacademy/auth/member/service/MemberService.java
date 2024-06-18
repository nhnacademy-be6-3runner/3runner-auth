package com.nhnacademy.auth.member.service;

import com.nhnacademy.auth.entity.member.Member;
import com.nhnacademy.auth.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * @Author -유지아
     * Save member. -멤버값을 받아와 저장한다.(이메일 중복하는걸로 확인하면 좋을듯)
     *
     * @param member the member -Member값을 받아온다.
     * @return the member -저장 후 member값을 그대로 반환한다.
     */
    public Member save(Member member) {
        //이메일 중복 확인안해도 되려나,,
        return memberRepository.save(member);
    }

    /**
     * @Author -유지아
     * Find by id member. -memberid를 받아 멤버자체를 가져온다.
     *
     * @param id the id -long형인 memberid를 받는다.
     * @return the member -member 반환
     */
    public Member findById(Long id) {
        if(memberRepository.findById(id).isPresent()){
            return memberRepository.findById(id).get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
        }
    }

    /**
     * @Author -유지아
     * Find by email and password member. -이메일과 패스워드 값으로 조회한다.
     *
     * @param email    the email -string 이메일 값을 받는다.
     * @param password the password -string 비밀번호 값을 받는다.
     * @return the member -해당하는 member를 반환한다.
     */
    public Member findByEmailAndPassword(String email, String password) {
        if(memberRepository.findByEmailAndPassword(email,password) !=null){
            return memberRepository.findByEmailAndPassword(email,password);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
        }
    }
}