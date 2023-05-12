package com.caja.ideal.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyExists extends DataIntegrityViolationException {
    /**
     * Constructor for DataIntegrityViolationException.
     *
     * @param msg the detail message
     */
    public AlreadyExists(String msg) {
        super(msg);
    }
}
