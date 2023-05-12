package com.caja.ideal.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class ResourceNotFound extends DataIntegrityViolationException {
    /**
     * Constructor for DataIntegrityViolationException.
     *
     * @param msg the detail message
     */
    public ResourceNotFound(String msg) {
        super(msg);
    }
}
