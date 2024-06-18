package com.nhnacademy.auth.member;

import com.nhnacademy.auth.entity.member.Member;
import com.nhnacademy.auth.entity.member.enums.Grade;
import com.nhnacademy.auth.entity.member.enums.Status;
import com.nhnacademy.auth.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.ZonedDateTime;
import java.util.Optional;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    public void saveTest() {
        Member member = Member.builder()
                .name("test-name")
                .password("test-password")
                .point(6000L)
                .age(100)
                .phone("01012345678")
                .birthday(ZonedDateTime.parse("2000-01-01T00:00:00Z"))
                .grade(Grade.General)
                .status(Status.Active)
                .created_at(ZonedDateTime.now())
                .email("test@email.com")
                .build();

        Member savedMember = memberRepository.save(member);

        Assertions.assertNotNull(savedMember.getId());
        Assertions.assertEquals("test-name", member.getName());
        Assertions.assertEquals("test-password", member.getPassword());
        Assertions.assertEquals("test@email.com", member.getEmail());
    }

    @Test
    public void findByIdTest() {
        Member member = Member.builder()
                .name("test-name")
                .password("test-password")
                .point(6000L)
                .age(100)
                .phone("01012345678")
                .birthday(ZonedDateTime.parse("2000-01-01T00:00:00Z"))
                .grade(Grade.General)
                .status(Status.Active)
                .created_at(ZonedDateTime.now())
                .email("test@email.com")
                .build();
        Member savedMember = memberRepository.save(member);

        Member foundMember = memberRepository.findById(savedMember.getId()).get();

        Assertions.assertNotNull(foundMember);

    }

    @Test
    public void updateTest() {
        Member member = Member.builder()
                .name("test-name")
                .password("test-password")
                .point(6000L)
                .age(100)
                .phone("01012345678")
                .birthday(ZonedDateTime.parse("2000-01-01T00:00:00Z"))
                .grade(Grade.General)
                .status(Status.Active)
                .created_at(ZonedDateTime.now())
                .email("test@email.com")
                .build();
        Member savedMember = memberRepository.save(member);
        Member foundMember = memberRepository.findById(savedMember.getId()).get();

        foundMember.setName("new-name");
        foundMember.setPassword("updated-password");
        foundMember.setEmail("update@email.com");
        memberRepository.save(foundMember);
        entityManager.flush();

        Assertions.assertEquals(savedMember.getId(), foundMember.getId());
        Assertions.assertEquals("new-name", foundMember.getName());
        Assertions.assertEquals("updated-password", foundMember.getPassword());
        Assertions.assertEquals("update@email.com", foundMember.getEmail());
    }

    @Test
    public void deleteTest() {
        Member member = Member.builder()
                .name("test-name")
                .password("test-password")
                .point(6000L)
                .age(100)
                .phone("01012345678")
                .birthday(ZonedDateTime.parse("2000-01-01T00:00:00Z"))
                .grade(Grade.General)
                .status(Status.Active)
                .created_at(ZonedDateTime.now())
                .email("test@email.com")
                .build();
        Member savedMember = memberRepository.save(member);
        entityManager.flush();
        memberRepository.delete(savedMember);
        entityManager.flush();

        Optional<Member> foundMember = memberRepository.findById(savedMember.getId());
        Assertions.assertTrue(foundMember.isEmpty());
    }
}
