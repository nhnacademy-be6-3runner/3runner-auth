package com.nhnacademy.auth.member.exception;

public class MemberNotExistsException extends RuntimeException{
    public MemberNotExistsException() {
        super("존재하지 않는 멤버입니다.");
    }
}
