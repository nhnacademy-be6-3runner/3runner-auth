package com.nhnacademy.auth.member.controller;

import com.nhnacademy.auth.entity.auth.Auth;
import com.nhnacademy.auth.entity.member.Member;
import com.nhnacademy.auth.entity.memberAuth.MemberAuth;
import com.nhnacademy.auth.entity.pointRecord.PointRecord;
import com.nhnacademy.auth.member.dto.CreateMemberRequest;
import com.nhnacademy.auth.member.service.AuthService;
import com.nhnacademy.auth.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.ZonedDateTime;
import java.util.List;
import com.nhnacademy.auth.member.service.PointService;
import com.nhnacademy.auth.member.service.MemberAuthService;


@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PointService pointRecordService;
    private final AuthService authService;
    private final MemberAuthService memberAuthService;


    /**
     * @author -유지아
     * Create member response entity.- 회원가입에 사용되는 함수이다.
     *
     * @param request the request - creatememberrequest를 받아 member를 생성한다.
     * @return the response entity - 멤버 정보에 대한 응답을 담아서 apiresponse로 응답한다.
     */
    @PostMapping("/members")
    public ResponseEntity<Member> createMember(@RequestBody CreateMemberRequest request) {
        Member member = new Member(request);
        Auth auth = authService.getAuth("USER");

        PointRecord pointRecord = new PointRecord(null,5000L,5000L,ZonedDateTime.now(),"회원가입 5000포인트 적립.",member);
        pointRecordService.save(pointRecord);
        memberAuthService.saveAuth(member,auth);
        return ResponseEntity.ok(memberService.save(member));
    }

    /**
     * @Author -유지아
     * Find by memberid response entity. -멤버아이디를 기반으로 멤버정보를 가져온다.
     *
     * @param memberId the member id -long형인 memberId값을 request header로 받는다.
     * @return the response entity -멤버 정보에 대한 응답을 담아서 apiresponse로 응답한다.
     */
//멤버를 저장해준다. + 멤버의 권한을 유저로 설정해준다.
    @GetMapping("/members")
    public ResponseEntity<Member> findById(@RequestHeader("member-id") Long memberId) {
        return ResponseEntity.ok(memberService.findById(memberId));
    }

    /**
     * @Author -유지아
     * Find by email and password response entity. -이메일과 비밀번호에 맞는 멤버정보를 반환한다.
     *
     * @param email    the email -이메일값을 문자열로 받는다.
     * @param password the password -비밀번호값을 문자열로 받는다.
     * @return the response entity -멤버 정보에 대한 응답을 담아서 apiresponse로 응답한다.
     */
//멤버아이디로 멤버정보를 가져온다.
    @GetMapping("/members/login")
    public ResponseEntity<Member> findByEmailAndPassword(
            @RequestHeader("email") String email,
            @RequestHeader("password") String password) {
        return ResponseEntity.ok(memberService.findByEmailAndPassword(email, password));
    }

    /**
     * @Author -유지아
     * Find auths list. -권한에 대한 리스트를 받아온다.
     *
     * @param memberId the member id -멤버아이디를 받는다.
     * @return the list -해당 유저에 대한 권한들을 응답에 담아 apiresponse로 응답한다.
     */
//아이디와 비밀번호로 유저를 가져온다.
    @GetMapping("/members/auths")
    public List<Auth> findAuths(@RequestHeader("member-id")Long memberId){
        return memberAuthService.findAllAuths(memberId);
    }
    //유저의 권한을 가져온다.

}

