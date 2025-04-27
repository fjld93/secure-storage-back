package com.fjld.secure_storage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PermissionDeniedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PermissionDeniedException(String message) {
        super(message);
    }

}
