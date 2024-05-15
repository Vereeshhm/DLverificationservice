package com.example.DLverificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.DLverificationservice.Entity.DLnumberdto;
import com.example.DLverificationservice.Entity.Dlrequest;
//import com.example.DLverificationservice.Response.DLResponse;
import com.example.DLverificationservice.Service.DLService;

@RestController
public class Dlcontroller {

	
	
	
	@Autowired
	DLService dlService;
	
	@PostMapping("api/DL/Verification")
	public Object Verification(@RequestBody Dlrequest request)
	{
	
		
//		DLResponse response=dlService.getVerfication(request);
		return ResponseEntity.ok().body(dlService.getVerfication(request));
	}
	

	@PostMapping("api/DL/numberbased")
	public Object fetchdetails(@RequestBody DLnumberdto dto)
	{
		return ResponseEntity.ok().body(dlService.getFetchDetails(dto));
	}
	
	
	
}
