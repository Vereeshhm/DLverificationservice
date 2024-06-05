package com.example.DLverificationservice.Service;


import com.example.DLverificationservice.Entity.DLnumberdto;
import com.example.DLverificationservice.Entity.Dlrequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public interface DLService {

	public String getVerfication(Dlrequest request, HttpServletRequest request1, HttpServletResponse response);
	
	public 	String getFetchDetails(DLnumberdto dto, HttpServletRequest request, HttpServletResponse response);

}
