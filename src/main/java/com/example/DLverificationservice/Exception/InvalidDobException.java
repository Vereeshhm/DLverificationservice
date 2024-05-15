package com.example.DLverificationservice.Exception;

public class InvalidDobException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

	public InvalidDobException(String message) {
        super(message);
    }

}
