package com.example.DLverificationservice.exception1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler1 {

	
	 @ExceptionHandler(InvalidDobformatException.class)
	    public ResponseEntity<Object> handleInvalidDobformatException(InvalidDobformatException ex) {
	        // Create a custom response body
	        String responseBody = "{\"error\": " 
	        		  + "{\"statusCode\": 400, " 
	  				+ "\"name\": \"Error\", "
	  				+ "\"message\": \"Please enter the DOB in proper format dd/mm/yyyy\", "
	  				+ "\"status\": 400}}";
	        		
	        // Return the custom response with HTTP status code 400
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	    }
	 @ExceptionHandler(DLNotfoundexceptionn.class)
	    public ResponseEntity<Object> handleDLNotfoundexceptionn(DLNotfoundexceptionn ex) {
	        // Create a custom response body
	        String responseBody = "{\"error\": " 
	        		  + "{\"statusCode\": 404, " 
	  				+ "\"name\": \"Error\", "
	  				+ "\"message\": \"DL Number not found\","
	  				+ "\"status\": 404}}";
	        		
	        // Return the custom response with HTTP status code 400
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
	    }
	 
	 
	 
}
