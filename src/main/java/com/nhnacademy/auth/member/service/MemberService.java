package com.nhnacademy.auth.member.service;

import com.nhnacademy.auth.entity.member.Member;
import com.nhnacademy.auth.entity.member.enums.Grade;
import com.nhnacademy.auth.entity.member.enums.Status;
import com.nhnacademy.auth.member.dto.request.UpdateMemberRequest;

public interface MemberService {
    Member save(Member member);

    Member findById(Long id);

    Member findByEmailAndPassword(String email, String password);

    Member updateMember(String memberId, UpdateMemberRequest updateMemberRequest);

    void deleteMember(String memberId);

    Member updateStatus(String memberId, Status status);

    Member updateGrade(String memberId, Grade grade);
}
