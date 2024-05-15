package com.example.DLverificationservice.Service;


import com.example.DLverificationservice.Entity.DLnumberdto;
import com.example.DLverificationservice.Entity.Dlrequest;


public interface DLService {

	public Object getVerfication(Dlrequest request);
	
	public 	Object getFetchDetails(DLnumberdto dto);

}
