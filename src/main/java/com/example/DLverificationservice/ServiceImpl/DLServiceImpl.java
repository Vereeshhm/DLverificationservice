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
import com.example.DLverificationservice.Logentities.ApiLog;
import com.example.DLverificationservice.Logentities.ApiLogentity;
import com.example.DLverificationservice.Repository.ApiLogRepository;
import com.example.DLverificationservice.Repository.ApiLogRepository1;
import com.example.DLverificationservice.Service.DLService;
import com.example.DLverificationservice.Utils.Propertiesconfig;
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

		ApiLog apiLog = new ApiLog();
		String response1 = null;
		try {
			String APIURL = propertiesconfig.getDLVURL();

			String requestUrl = request1.getRequestURI().toString();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", apiKey); // Include API key directly without "Bearer" prefix
			String requestBody = "{\"number\": \"" + request.getNumber() + "\", \"dob\": \"" + request.getDob()
					+ "\", \"issueDate\": \"" + request.getIssueDate() + "\"}";

			Gson gson = new Gson();

			String requestBodyJson = gson.toJson(request);

			HttpEntity<String> request2 = new HttpEntity<>(requestBodyJson, headers);

			System.out.println("Requestbody  " + requestBody);

			apiLog.setUrl(requestUrl);
			apiLog.setRequestBody(requestBodyJson);

			response1 = restTemplate.postForObject(APIURL, request2, String.class);
			apiLog.setResponseBody(response1);
			apiLog.setStatusCode(HttpStatus.OK.value());
			return response1;
		}

		catch (HttpClientErrorException.TooManyRequests e) {
			// Handling Too Many Requests Exception specifically
			apiLog.setStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());

			response1 = e.getResponseBodyAsString();
			System.out.println(response1 + "Response");
			apiLog.setResponseBodyAsJson("API rate limit exceeded");
		} catch (HttpClientErrorException.Unauthorized e) {
			// Handling Unauthorized Exception specifically
			apiLog.setStatusCode(HttpStatus.UNAUTHORIZED.value());

			response1 = e.getResponseBodyAsString();
			System.out.println(response1 + "Response");
			apiLog.setResponseBodyAsJson("No API key found in request");

		}

		catch (HttpClientErrorException e) {
			apiLog.setStatusCode(e.getStatusCode().value());

			response1 = e.getResponseBodyAsString();
			System.out.println(response1 + " Response ");
			apiLog.setResponseBody(response1);
		}

		catch (Exception e) {
			apiLog.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

			response1 = e.getMessage();
			apiLog.setResponseBody(response1);
		} finally {
			apiLogRepository.save(apiLog);
		}
		return response1;
	}

	@Override
	public String getFetchDetails(DLnumberdto dto, HttpServletRequest request, HttpServletResponse response) {

		ApiLogentity apiLogentity = new ApiLogentity();
		String response1 = null;
		try {
			String APIURL = propertiesconfig.getDLNURL();

			String requestUrl = request.getRequestURI().toString();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", apiKey); // Include API key directly without "Bearer" prefix
			String requestBody = "{\"number\": \"" + dto.getNumber() + "\", \"dob\": \"" + dto.getDob() + "\"}";

			Gson gson = new Gson();

			String requestBodyJson = gson.toJson(dto);

			HttpEntity<String> request1 = new HttpEntity<>(requestBodyJson, headers);

			System.out.println("Requestbody  " + requestBody);

			apiLogentity.setUrl(requestUrl);
			apiLogentity.setRequestBody(requestBodyJson);

			response1 = restTemplate.postForObject(APIURL, request1, String.class);
			apiLogentity.setResponseBody(response1);
			apiLogentity.setStatusCode(HttpStatus.OK.value());
			return response1;
		} catch (HttpClientErrorException.TooManyRequests e) {
			// Handling Too Many Requests Exception specifically
			apiLogentity.setStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());

			response1 = e.getResponseBodyAsString();
			System.out.println(response1 + "Response");
			apiLogentity.setResponseBodyAsJson("API rate limit exceeded");
		} catch (HttpClientErrorException.Unauthorized e) {
			// Handling Unauthorized Exception specifically
			apiLogentity.setStatusCode(HttpStatus.UNAUTHORIZED.value());

			response1 = e.getResponseBodyAsString();
			System.out.println(response1 + "Response");
			apiLogentity.setResponseBodyAsJson("No API key found in request");

		}

		catch (HttpClientErrorException e) {
			apiLogentity.setStatusCode(e.getStatusCode().value());

			response1 = e.getResponseBodyAsString();
			System.out.println(response1 + " Response ");
			apiLogentity.setResponseBody(response1);
		}

		catch (Exception e) {
			apiLogentity.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

			response1 = e.getMessage();
			apiLogentity.setResponseBody(response1);
		}

		finally {
			apiLogRepository1.save(apiLogentity);
		}
		return response1;

	}

}
