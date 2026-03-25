package com.kopanitskiy.security.exceptionHandling;

import org.springframework.dao.DataIntegrityViolationException;

public class UsernameExistsException extends DataIntegrityViolationException {
    public UsernameExistsException(String msg) {
        super(msg);
    }
}
