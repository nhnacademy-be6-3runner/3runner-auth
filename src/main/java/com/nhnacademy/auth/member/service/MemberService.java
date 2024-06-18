package com.nhnacademy.auth.member.service;

import com.nhnacademy.auth.entity.member.Member;
import com.nhnacademy.auth.entity.member.dto.UpdateMemberRequest;
import com.nhnacademy.auth.entity.member.enums.Grade;
import com.nhnacademy.auth.entity.member.enums.Status;
import com.nhnacademy.auth.member.exception.MemberNotExistsException;
import com.nhnacademy.auth.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 멤버 업데이트
     *
     * @param memberId            the member id
     * @param updateMemberRequest password, name, age, phone, email, birthday
     * @return the member
     */
    public Member updateMember(String memberId, UpdateMemberRequest updateMemberRequest) {
        Long id = Long.parseLong(memberId);
        Member member = memberRepository.findById(id).orElseThrow(() -> new MemberNotExistsException());

        member.setPassword(updateMemberRequest.getPassword());
        member.setName(updateMemberRequest.getName());
        member.setAge(updateMemberRequest.getAge());
        member.setEmail(updateMemberRequest.getEmail());
        member.setPhone(updateMemberRequest.getPhone());
        member.setBirthday(updateMemberRequest.getBirthday());
        member.setModified_at(ZonedDateTime.now());

        return memberRepository.save(member);
    }

    /**
     * 멤버 탈퇴
     *
     * @param memberId the member id
     */
    public void deleteMember(String memberId) {
        Long id = Long.parseLong(memberId);
        Member member = memberRepository.findById(id).orElseThrow(() -> new MemberNotExistsException());

        member.setStatus(Status.Withdrawn);
        member.setDeleted_at(ZonedDateTime.now());

        memberRepository.save(member);
    }

    /**
     * Update member's status(활성, 탈퇴, 휴면).
     *
     * @param memberId the member id
     * @param status   the status
     * @return the member
     */
    public Member updateStatus(String memberId, Status status) {
        Long id = Long.parseLong(memberId);
        Member member = memberRepository.findById(id).orElseThrow(() -> new MemberNotExistsException());
        member.setStatus(status);
        member.setModified_at(ZonedDateTime.now());
        return memberRepository.save(member);
    }

    /**
     * Update member's grade(general, royal, gold, platinum).
     *
     * @param memberId the member id
     * @param grade    the grade
     * @return the member
     */
    public Member updateGrade(String memberId, Grade grade) {
        Long id = Long.parseLong(memberId);
        Member member = memberRepository.findById(id).orElseThrow(() -> new MemberNotExistsException());
        member.setGrade(grade);
        member.setModified_at(ZonedDateTime.now());
        return memberRepository.save(member);
    }

}
