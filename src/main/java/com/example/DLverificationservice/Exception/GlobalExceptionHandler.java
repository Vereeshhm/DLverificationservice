package com.example.DLverificationservice.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler {

	
	 @ExceptionHandler(InvalidDlNumberException.class)
	    public ResponseEntity<Object> handleInvalidPanNumberException(InvalidDlNumberException ex) {
	        // Create a custom response body
	        String responseBody = "{\"error\": " 
	        		  + "{\"statusCode\": 400, " 
	  				+ "\"name\": \"Error\", "
	  				+ "\"message\": \"Please provide a valid Driving License Number.\", "
	  				+ "\"status\": 400}}";
	        		
	        // Return the custom response with HTTP status code 400
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	    }
	 
	 @ExceptionHandler(InvalidIssuedateException.class)
	    public ResponseEntity<Object> handleInvalidIssuedateException(InvalidIssuedateException ex) {
	        // Create a custom response body
	        String responseBody = "{\"error\": " 
	        		  + "{\"statusCode\": 400, " 
	  				+ "\"name\": \"Error\", "
	  				+ "\"message\": \"Please ensure the issueDate is entered in the correct format: DD/MM/YYYY\", "
	  				+ "\"status\": 400}}";
	        		
	        // Return the custom response with HTTP status code 400
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	    }
	 @ExceptionHandler(InvalidDobException.class)
	    public ResponseEntity<Object> handleInvalidDobException(InvalidDobException ex) {
	        // Create a custom response body
	        String responseBody = "{\"error\": " 
	        		  + "{\"statusCode\": 400, " 
	  				+ "\"name\": \"Error\", "
	  				+ "\"message\": \"Please ensure the dob is entered in the correct format: DD/MM/YYYY\", "
	  				+ "\"status\": 400}}";
	        		
	        // Return the custom response with HTTP status code 400
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	    }
	 
	 @ExceptionHandler(Emptydobexception.class)
	    public ResponseEntity<Object> handleEmptydobexception(Emptydobexception ex) {
	        // Create a custom response body
	        String responseBody = "{\"error\": " 
	        		  + "{\"statusCode\": 400, " 
	  				+ "\"name\": \"Error\", "
	  				+ "\"message\": \"Please provide a valid dob with format: DD/MM/YYYY\", "
	  				+ "\"status\": 400}}";
	        		
	        // Return the custom response with HTTP status code 400
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	    }
	 
	 @ExceptionHandler(Emptyissuedateexception.class)
	    public ResponseEntity<Object> handleEmptyissuedateexception(Emptyissuedateexception ex) {
	        // Create a custom response body
	        String responseBody = "{\"error\": " 
	        		  + "{\"statusCode\": 400, " 
	  				+ "\"name\": \"Error\", "
	  				+ "\"message\": \"Please provide a valid issueDate with format: DD/MM/YYYY\", "
	  				+ "\"status\": 400}}";
	        		
	        // Return the custom response with HTTP status code 400
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	    }
	 
	 @ExceptionHandler(Dlnotfoundexception.class)
	    public ResponseEntity<Object> handleDlnotfoundexception(Dlnotfoundexception ex) {
	        // Create a custom response body
	        String responseBody = "{\"error\": " 
	        		  + "{\"statusCode\": 404, " 
	  				+ "\"name\": \"Error\", "
	  				+ "\"message\": \"DL Number not found \","
	  				+ "\"status\": 404}}";
	        		
	        // Return the custom response with HTTP status code 400
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
	    }
	
	 
	 @ExceptionHandler(Upstreamerrorexception.class)
	    public ResponseEntity<Object> handleUpstreamerrorexception(Upstreamerrorexception ex) {
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
	

	 
	 
	 
}
