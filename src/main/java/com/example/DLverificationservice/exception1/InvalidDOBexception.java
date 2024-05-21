package com.example.DLverificationservice.exception1;

public class InvalidDOBexception extends RuntimeException{

	

	private static final long serialVersionUID = 1L;

	public InvalidDOBexception(String message) {
        super(message);
    }
}
