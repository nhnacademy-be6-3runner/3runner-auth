package com.nhnacademy.auth.member.repository;

import com.nhnacademy.auth.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    //C1: 멤버 저장
    Member findByEmailAndPassword(String email, String password);
    //R1: 멤버 이메일과 페스워드로 멤버 반환
}