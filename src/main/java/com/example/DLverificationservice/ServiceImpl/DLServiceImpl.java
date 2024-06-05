package com.example.DLverificationservice.ServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import com.example.DLverificationservice.Logentities.ApiLog;
import com.example.DLverificationservice.Logentities.ApiLogentity;
import com.example.DLverificationservice.Repository.ApiLogRepository;
import com.example.DLverificationservice.Repository.ApiLogRepository1;
import com.example.DLverificationservice.Service.DLService;
import com.example.DLverificationservice.Utils.Propertiesconfig;
import com.example.DLverificationservice.exception1.DLNotfoundexceptionn;
import com.example.DLverificationservice.exception1.InvalidDOBexception;
import com.example.DLverificationservice.exception1.InvalidDlnumberexception;
import com.example.DLverificationservice.exception1.InvalidDobformatException;
import com.example.DLverificationservice.exception1.UpstreamErrorException;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class DLServiceImpl implements DLService {

	private final RestTemplate restTemplate;

	private final String apiKey;
	
	@Autowired
	ApiLogRepository apiLogRepository;
	
	@Autowired
	ApiLogRepository1 apiLogRepository1;

	private static final Logger logger = LoggerFactory.getLogger(DLServiceImpl.class);

	@Autowired
	Propertiesconfig propertiesconfig;;

	@Autowired
	public DLServiceImpl(RestTemplate restTemplate, @Value("${api.key}") String apiKey) {
		this.restTemplate = restTemplate;
		this.apiKey = apiKey;
	}

	@Override
	public String getVerfication(Dlrequest request, HttpServletRequest request1, HttpServletResponse response) {


		String APIURL = propertiesconfig.getDLVURL();
		
		String requestUrl = request1.getRequestURI().toString();

		int statusCode = response.getStatus();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", apiKey); // Include API key directly without "Bearer" prefix
		String requestBody = "{\"number\": \"" + request.getNumber() + "\", \"dob\": \"" + request.getDob()
				+ "\", \"issueDate\": \"" + request.getIssueDate() + "\"}";
		
		Gson gson = new Gson();

		String requestBodyJson = gson.toJson(request);

		HttpEntity<String> request2 = new HttpEntity<>(requestBodyJson, headers);

		System.out.println("Requestbody  " + requestBody);
		ApiLog apiLog=new ApiLog();
		apiLog.setUrl(requestUrl);
		apiLog.setRequestBody(requestBodyJson);
		


		try {
			String response1 = restTemplate.postForObject(APIURL, request2, String.class);
			apiLog.setResponseBody(response1);
			apiLog.setStatusCode(HttpStatus.OK.value());
			return response1;
		} catch (HttpClientErrorException.BadRequest e) {
			String errorMessage = e.getResponseBodyAsString();
			apiLog.setResponseBody(errorMessage);
			apiLog.setStatusCode(e.getStatusCode().value());
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
			
			apiLog.setResponseBody(errorMessage);
			apiLog.setStatusCode(e.getStatusCode().value());
			logger.error("Error Response: {}", errorMessage);
			if (errorMessage.contains("DL Number not found")) {
				throw new Dlnotfoundexception("DL Number not found");
			} else {
				throw e;
			}
		}

		catch (HttpClientErrorException.Conflict e) {
			String errorMessage = e.getResponseBodyAsString();
			apiLog.setResponseBody(errorMessage);
			apiLog.setStatusCode(e.getStatusCode().value());
			logger.error("Error Response: {}", errorMessage);
			if (errorMessage.contains("Upstream Down")) {
				throw new Dlnotfoundexception("Upstream Down");
			} else {
				throw e;
			}
		}
		finally {
			apiLogRepository.save(apiLog);
		}
	}

	@Override
	public String getFetchDetails(DLnumberdto dto, HttpServletRequest request, HttpServletResponse response) {
		String APIURL = propertiesconfig.getDLNURL();
		
		String requestUrl = request.getRequestURI().toString();

		int statusCode = response.getStatus();


		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", apiKey); // Include API key directly without "Bearer" prefix
		String requestBody = "{\"number\": \"" + dto.getNumber() + "\", \"dob\": \"" + dto.getDob() + "\"}";

		Gson gson = new Gson();

		String requestBodyJson = gson.toJson(dto);

		
		HttpEntity<String> request1 = new HttpEntity<>(requestBodyJson, headers);

		System.out.println("Requestbody  " + requestBody);
		
		ApiLogentity apiLogentity=new ApiLogentity();
		apiLogentity.setUrl(requestUrl);
		apiLogentity.setRequestBody(requestBodyJson);


		try {
			String response1 = restTemplate.postForObject(APIURL, request1, String.class);
			apiLogentity.setResponseBody(response1);
			apiLogentity.setStatusCode(HttpStatus.OK.value());
			return response1;
		} catch (HttpClientErrorException.BadRequest e) {
			String errorMessage = e.getResponseBodyAsString();
			apiLogentity.setResponseBody(errorMessage);
			apiLogentity.setStatusCode(e.getStatusCode().value());
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
			apiLogentity.setResponseBody(errorMessage);
			apiLogentity.setStatusCode(e.getStatusCode().value());
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
		finally {
			apiLogRepository1.save(apiLogentity);
		}

	}

}
