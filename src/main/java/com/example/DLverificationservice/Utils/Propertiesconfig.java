package com.example.DLverificationservice.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:src/main/resources/application.properties")
public class Propertiesconfig {

	@Value("${DLV.URL}")                      
	private String DLVURL;

	public String getDLVURL() {
		return DLVURL;
	}

	public void setDLVURL(String dLVURL) {
		DLVURL = dLVURL;
	}

	@Value("${DLN.URL}")
	private String DLNURL;

	public String getDLNURL() {
		return DLNURL;
	}

	public void setDLNURL(String dLNURL) {
		DLNURL = dLNURL;
	}

}
