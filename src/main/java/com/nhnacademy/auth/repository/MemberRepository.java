package com.nhnacademy.auth.repository;

import com.nhnacademy.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
}
