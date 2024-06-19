package com.nhnacademy.auth.address.exception;

public class AddressNotExistsException extends RuntimeException {
    public AddressNotExistsException() {
        super("존재하지 않는 주소입니다.");
    }
}
