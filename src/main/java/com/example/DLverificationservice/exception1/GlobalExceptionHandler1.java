package com.example.DLverificationservice.exception1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.DLverificationservice.Exception.Emptydobexception;
import com.example.DLverificationservice.Exception.InvalidDlNumberException;
import com.example.DLverificationservice.Exception.Upstreamerrorexception;



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
	 @ExceptionHandler(UpstreamErrorException.class)
	    public ResponseEntity<Object> handleUpstreamErrorException(UpstreamErrorException ex) {
	        // Create a custom response body
		 String responseBody = "{\"error\": {"
              + "\"reason\": \"Error From Upstream\", "
              + "\"status\": 409, "
              + "\"message\": \"Upstream Down\", "
              + "\"type\": \"Conflict\", "
              + "\"statusCode\": 409, "
              + "\"name\": \"error\""
              + "}}";

	        		
	        // Return the custom response with HTTP status code 400
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
	    }
	 @ExceptionHandler(InvalidDlnumberexception.class)
	    public ResponseEntity<Object> handleInvalidPanNumberException(InvalidDlnumberexception ex) {
	        // Create a custom response body
	        String responseBody = "{\"error\": " 
	        		  + "{\"statusCode\": 400, " 
	  				+ "\"name\": \"Error\", "
	  				+ "\"message\": \"Please provide a valid Driving License Number.\", "
	  				+ "\"status\": 400}}";
	        		
	        // Return the custom response with HTTP status code 400
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	    }
	 @ExceptionHandler(InvalidDOBexception.class)
	    public ResponseEntity<Object> handleEmptydobexception(InvalidDOBexception ex) {
	        // Create a custom response body
	        String responseBody = "{\"error\": " 
	        		  + "{\"statusCode\": 400, " 
	  				+ "\"name\": \"Error\", "
	  				+ "\"message\": \"Please provide a valid dob with format: DD/MM/YYYY\", "
	  				+ "\"status\": 400}}";
	        		
	        // Return the custom response with HTTP status code 400
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	    }
	 
}
