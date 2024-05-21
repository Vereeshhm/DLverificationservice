package com.example.DLverificationservice.exception1;

public class UpstreamErrorException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

	public UpstreamErrorException(String message) {
        super(message);
    }
	
}
