package com.nhnacademy.auth.member;

import com.nhnacademy.auth.entity.member.Member;
import com.nhnacademy.auth.entity.member.dto.UpdateMemberRequest;
import com.nhnacademy.auth.entity.member.enums.Status;
import com.nhnacademy.auth.member.repository.MemberRepository;
import com.nhnacademy.auth.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateMember() {
        // Given
        Long memberId = 1L;
        UpdateMemberRequest updateRequest = UpdateMemberRequest.builder()
                .password("newPassword")
                .name("newName")
                .age(25)
                .email("new@example.com")
                .phone("12345678900")
                .birthday(ZonedDateTime.parse("2000-01-01T00:00:00Z"))
                .build();

        Member member = new Member();
        member.setId(memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // When
        Member updatedMember = memberService.updateMember(memberId.toString(), updateRequest);

        // Then
        assertNotNull(updatedMember);
        assertEquals("newPassword", updatedMember.getPassword());
        assertEquals("newName", updatedMember.getName());
        assertEquals(25, updatedMember.getAge());
        assertEquals("new@example.com", updatedMember.getEmail());
        assertEquals("12345678900", updatedMember.getPhone());
        assertEquals(ZonedDateTime.parse("2000-01-01T00:00:00Z"), updatedMember.getBirthday());

        verify(memberRepository, times(1)).findById(memberId);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void testDeleteMember() {
        // Given
        Long memberId = 1L;
        Member member = new Member();
        member.setId(memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // When
        memberService.deleteMember(memberId.toString());

        // Then
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository, times(1)).findById(memberId);
        verify(memberRepository, times(1)).save(memberCaptor.capture());

        Member deletedMember = memberCaptor.getValue();
        assertEquals(Status.Withdrawn, deletedMember.getStatus());
        assertNotNull(deletedMember.getDeleted_at());
    }

    @Test
    void testUpdateMemberNotFound() {
        // Given
        String memberId = "999";
        UpdateMemberRequest updateRequest = UpdateMemberRequest.builder().build();

        when(memberRepository.findById(Long.parseLong(memberId))).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            memberService.updateMember(memberId, updateRequest);
        });

        assertEquals("Member not found", exception.getMessage());
        verify(memberRepository, times(1)).findById(Long.parseLong(memberId));
        verify(memberRepository, times(0)).save(any(Member.class));
    }

    @Test
    void testDeleteMemberNotFound() {
        // Given
        String memberId = "999";

        when(memberRepository.findById(Long.parseLong(memberId))).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            memberService.deleteMember(memberId);
        });

        assertEquals("Member not found", exception.getMessage());
        verify(memberRepository, times(1)).findById(Long.parseLong(memberId));
        verify(memberRepository, times(0)).save(any(Member.class));
    }
}
