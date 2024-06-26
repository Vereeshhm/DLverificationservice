package com.example.DLverificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.DLverificationservice.Entity.DLnumberdto;
import com.example.DLverificationservice.Entity.Dlrequest;
//import com.example.DLverificationservice.Response.DLResponse;
import com.example.DLverificationservice.Service.DLService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class Dlcontroller {

	@Autowired
	DLService dlService;

	@PostMapping("api/DL/Verification")
	public String Verification(@RequestBody Dlrequest request, HttpServletRequest request1, HttpServletResponse response) {

		return dlService.getVerfication(request,request1,response);
	}

	@PostMapping("api/DL/numberbased")
	public String fetchdetails(@RequestBody DLnumberdto dto, HttpServletRequest request, HttpServletResponse response) {
		
		return dlService.getFetchDetails(dto,  request,  response);
	}

}
