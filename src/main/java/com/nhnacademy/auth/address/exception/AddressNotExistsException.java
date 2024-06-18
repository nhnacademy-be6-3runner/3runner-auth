package com.nhnacademy.auth.address.exception;

public class AddressNotExistsException extends RuntimeException {
    public AddressNotExistsException() {
        super("Address not exists");
    }
}
