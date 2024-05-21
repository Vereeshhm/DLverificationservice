package com.example.DLverificationservice.ServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.DLverificationservice.Entity.DLnumberdto;
import com.example.DLverificationservice.Entity.Dlrequest;
import com.example.DLverificationservice.Exception.Dlnotfoundexception;
import com.example.DLverificationservice.Exception.Emptydobexception;
import com.example.DLverificationservice.Exception.Emptyissuedateexception;
import com.example.DLverificationservice.Exception.InvalidDlNumberException;
import com.example.DLverificationservice.Exception.InvalidDobException;
import com.example.DLverificationservice.Exception.InvalidIssuedateException;
import com.example.DLverificationservice.Service.DLService;
import com.example.DLverificationservice.Utils.Propertiesconfig;
import com.example.DLverificationservice.exception1.DLNotfoundexceptionn;
import com.example.DLverificationservice.exception1.InvalidDOBexception;
import com.example.DLverificationservice.exception1.InvalidDlnumberexception;
import com.example.DLverificationservice.exception1.InvalidDobformatException;
import com.example.DLverificationservice.exception1.UpstreamErrorException;

@Service
public class DLServiceImpl implements DLService {

	private final RestTemplate restTemplate;

	private final String apiKey;

	private static final Logger logger = LoggerFactory.getLogger(DLServiceImpl.class);

	@Autowired
	Propertiesconfig propertiesconfig;;

	@Autowired
	public DLServiceImpl(RestTemplate restTemplate, @Value("${api.key}") String apiKey) {
		this.restTemplate = restTemplate;
		this.apiKey = apiKey;
	}

	@Override
	public Object getVerfication(Dlrequest request) {
//		panerrorstatus panerrorstatus = new panerrorstatus();

		String APIURL = propertiesconfig.getDLVURL();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", apiKey); // Include API key directly without "Bearer" prefix
		String requestBody = "{\"number\": \"" + request.getNumber() + "\", \"dob\": \"" + request.getDob()
				+ "\", \"issueDate\": \"" + request.getIssueDate() + "\"}";

		HttpEntity<String> request1 = new HttpEntity<>(requestBody, headers);

		System.out.println("Requestbody  " + requestBody);
//		 Panresponse response = restTemplate.postForObject(APIURL, request, Panresponse.class);

		try {
			Object response = restTemplate.postForObject(APIURL, request1, Object.class);
			return response;
		} catch (HttpClientErrorException.BadRequest e) {
			String errorMessage = e.getResponseBodyAsString();
			// System.out.println("Error Response: " + errorMessage);
			logger.error("Error Response: {}", errorMessage);
			if (errorMessage.contains("Please provide a valid Driving License Number")) {
				throw new InvalidDlNumberException("Please provide a valid Driving License Number");
			} else if (errorMessage
					.contains("Please ensure the issueDate is entered in the correct format: DD/MM/YYYY\"")) {
				throw new InvalidIssuedateException(
						"Please ensure the issueDate is entered in the correct format: DD/MM/YYYY\"");
			} else if (errorMessage.contains("Please ensure the dob is entered in the correct format: DD/MM/YYYY\"")) {
				throw new InvalidDobException("Please ensure the dob is entered in the correct format: DD/MM/YYYY\"");
			} else if (errorMessage.contains("Please provide a valid dob with format: DD/MM/YYYY")) {
				throw new Emptydobexception("Please provide a valid dob with format: DD/MM/YYYY");
			} else if (errorMessage.contains("Please provide a valid issueDate with format: DD/MM/YYYY\"")) {
				throw new Emptyissuedateexception("Please provide a valid issueDate with format: DD/MM/YYYY\"");
			}

			else {

				throw e;
			}

		} catch (HttpClientErrorException.NotFound e) {
			String errorMessage = e.getResponseBodyAsString();
			logger.error("Error Response: {}", errorMessage);
			if (errorMessage.contains("DL Number not found")) {
				throw new Dlnotfoundexception("DL Number not found");
			} else {
				throw e;
			}
		}

		catch (HttpClientErrorException.Conflict e) {
			String errorMessage = e.getResponseBodyAsString();
			logger.error("Error Response: {}", errorMessage);
			if (errorMessage.contains("Upstream Down")) {
				throw new Dlnotfoundexception("Upstream Down");
			} else {
				throw e;
			}
		}
	}

	@Override
	public Object getFetchDetails(DLnumberdto dto) {
		String APIURL = propertiesconfig.getDLNURL();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", apiKey); // Include API key directly without "Bearer" prefix
		String requestBody = "{\"number\": \"" + dto.getNumber() + "\", \"dob\": \"" + dto.getDob() + "\"}";

		HttpEntity<String> request1 = new HttpEntity<>(requestBody, headers);

		System.out.println("Requestbody  " + requestBody);
//		Object response = restTemplate.postForObject(APIURL, request1, Object.class);
//	return response;

		try {
			Object response = restTemplate.postForObject(APIURL, request1, Object.class);
			return response;
		} catch (HttpClientErrorException.BadRequest e) {
			String errorMessage = e.getResponseBodyAsString();
			// System.out.println("Error Response: " + errorMessage);
			logger.error("Error Response: {}", errorMessage);
			if (errorMessage.contains("Please enter the DOB in proper format dd/mm/yyyy")) {
				throw new InvalidDobformatException("Please enter the DOB in proper format dd/mm/yyyy");
			} else if (errorMessage.contains("Please provide a valid dob with format: DD/MM/YYYY")) {
				throw new InvalidDOBexception("Please provide a valid dob with format: DD/MM/YYYY");
			} else if (errorMessage.contains("Please provide a valid Driving License Number")) {
				throw new InvalidDlnumberexception("Please provide a valid Driving License Number");
			}

			else {
				throw e;
			}
		}

		catch (HttpClientErrorException.NotFound e) {
			String errorMessage = e.getResponseBodyAsString();
			logger.error("Error Response: {}", errorMessage);
			if (errorMessage.contains("DL Number not found")) {
				throw new DLNotfoundexceptionn("DL Number not found");
			} else if (errorMessage.contains("Upstream Down")) {
				throw new UpstreamErrorException("Upstream Down");
			}

			else {
				throw e;
			}
		}

	}

}
