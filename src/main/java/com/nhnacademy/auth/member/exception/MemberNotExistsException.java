package com.nhnacademy.auth.member.exception;

public class MemberNotExistsException extends RuntimeException{
    public MemberNotExistsException() {
        super("Member Not Exists");
    }
}
