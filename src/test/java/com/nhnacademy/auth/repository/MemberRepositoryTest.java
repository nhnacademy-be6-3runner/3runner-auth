package com.nhnacademy.auth.repository;

import com.nhnacademy.auth.entity.member.Member;
import com.nhnacademy.auth.member.dto.CreateMemberRequest;
import com.nhnacademy.auth.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MemberRepository memberRepository;
    @BeforeEach
    void setUp() {
        entityManager.clear();
    }
    @Test
    void testSaveAndFindByEmailAndPassword(){
        CreateMemberRequest createMemberRequest = new CreateMemberRequest("email@naver.com","123456","j","01000000000",0, null);
        Member member = new Member(createMemberRequest);
        Member savedMember = memberRepository.save(member);
        Member foundMember = memberRepository.findByEmailAndPassword(member.getEmail(), member.getPassword());
        assertThat(foundMember).isEqualTo(savedMember);
    }
}
