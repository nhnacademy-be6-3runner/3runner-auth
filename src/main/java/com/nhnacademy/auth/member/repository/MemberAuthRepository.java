package com.nhnacademy.auth.member.repository;

import com.nhnacademy.auth.entity.auth.Auth;
import com.nhnacademy.auth.entity.memberAuth.MemberAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberAuthRepository extends JpaRepository<MemberAuth, Long> {
    //List<Auth> findByMemberId(Long memberId);
    @Query("SELECT ma.auth FROM MemberAuth ma WHERE ma.member.id = :memberId")
    List<Auth> findByMemberId(@Param("memberId") Long memberId);
}
