package com.nhnacademy.auth.member.controller;

import com.nhnacademy.auth.util.ApiResponse;
import com.nhnacademy.auth.entity.member.Member;
import com.nhnacademy.auth.entity.member.dto.UpdateMemberRequest;
import com.nhnacademy.auth.entity.member.dto.UpdateMemberResponse;
import com.nhnacademy.auth.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {
    MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PutMapping("/members")
    public ApiResponse<UpdateMemberResponse> updateMember(@RequestHeader(name = "Member-Id") String memberId,
                                                             @Valid @RequestBody UpdateMemberRequest updateMemberRequest) {
        try {
            Member updatedMember = memberService.updateMember(memberId, updateMemberRequest);
            UpdateMemberResponse updateMemberResponse = UpdateMemberResponse.builder()
                    .id(String.valueOf(updatedMember.getId()))
                    .name(updatedMember.getName()).build();
            return ApiResponse.success(updateMemberResponse);
        } catch (RuntimeException e) {
            return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @DeleteMapping("/members")
    public ApiResponse<Void> deleteMember(@RequestHeader(name = "Member-Id") String memberId) {
        try {
            memberService.deleteMember(memberId);
            return new ApiResponse<>(new ApiResponse.Header(true, HttpStatus.NO_CONTENT.value(), "Member deleted"));
        } catch (RuntimeException e) {
            return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

}
