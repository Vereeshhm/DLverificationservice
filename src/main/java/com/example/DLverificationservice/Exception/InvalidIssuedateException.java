package com.example.DLverificationservice.Exception;

public class InvalidIssuedateException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

	public InvalidIssuedateException(String message) {
        super(message);
    }
}
