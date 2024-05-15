package com.example.DLverificationservice.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class InvalidDlNumberException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidDlNumberException(String message) {
        super(message);
    }

	
}
