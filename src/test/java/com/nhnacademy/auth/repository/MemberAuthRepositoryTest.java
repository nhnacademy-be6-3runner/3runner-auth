package com.nhnacademy.auth.repository;

import com.nhnacademy.auth.entity.auth.Auth;
import com.nhnacademy.auth.entity.member.Member;
import com.nhnacademy.auth.entity.memberAuth.MemberAuth;
import com.nhnacademy.auth.member.dto.CreateMemberRequest;
import com.nhnacademy.auth.member.repository.AuthRepository;
import com.nhnacademy.auth.member.repository.MemberAuthRepository;
import com.nhnacademy.auth.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberAuthRepositoryTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MemberAuthRepository memberAuthRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AuthRepository authRepository;

    @BeforeEach
    void setUp() {
        entityManager.clear();
    }

    @Test
    void testFindByMemberId() {
        // Create and save member
        CreateMemberRequest createMemberRequest = new CreateMemberRequest("email@naver.com","123456","j","01000000000",0, null);
        Member member = new Member(createMemberRequest);
        member = memberRepository.save(member);

        // Create and save auth
        Auth auth = new Auth();
        auth.setName("ADMIN");
        auth = authRepository.save(auth);

        // Create and save MemberAuth
        MemberAuth memberAuth = new MemberAuth();
        memberAuth.setMember(member);
        memberAuth.setAuth(auth);
        memberAuthRepository.save(memberAuth);

        // Fetch MemberAuth by memberId
        List<Auth> authList = memberAuthRepository.findByMemberId(member.getId());

        assertThat(authList).isNotNull();
        assertThat(authList).hasSize(1);
        assertThat(authList.get(0)).isEqualTo(auth);
    }
}
