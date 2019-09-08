package com.microservices.assignment.banktestapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class BankTestAppApplicationTests {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static String gatewayURL = "http://localhost:8765";
	private static String username = "testuser";
	private static String jwtToken = "";
	private UUID accountNo = UUID.randomUUID();

	@Test
	@Order(1)
	void testSignupLoginSecurityService() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject signupJsonObject = new JSONObject();
		try {

			username = "waseem" + System.currentTimeMillis();
			signupJsonObject.put("username", username);
			signupJsonObject.put("password", "testpass");

			String signupGatewayUrl = gatewayURL + "/login-security-service/signup";

			HttpEntity<String> request = new HttpEntity<String>(signupJsonObject.toString(), headers);

			ResponseEntity<String> signedUpUser = restTemplate.postForEntity(signupGatewayUrl, request, String.class);

			assertEquals(signedUpUser.getStatusCode(), HttpStatus.CREATED);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	@Order(2)
	void testLoginLoginSecurityService() {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject signupJsonObject = new JSONObject();
		try {
			signupJsonObject.put("username", username);
			signupJsonObject.put("password", "testpass");

			String loginGatewayUrl = gatewayURL + "/login-security-service/login";

			HttpEntity<String> request = new HttpEntity<String>(signupJsonObject.toString(), headers);

			ResponseEntity<String> loggedInUser = restTemplate.postForEntity(loginGatewayUrl, request, String.class);

			assertEquals(loggedInUser.getStatusCode(), HttpStatus.OK);
			assertNotNull(loggedInUser.getHeaders().get("token").get(0));

			jwtToken = loggedInUser.getHeaders().get("token").get(0);

			logger.info("JWT Token : {}", jwtToken);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	@Order(3)
	void testLoginLoginWrongPasswordSecurityService() {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject signupJsonObject = new JSONObject();
		try {
			signupJsonObject.put("username", username);
			signupJsonObject.put("password", "wrongpass");

			String loginGatewayUrl = gatewayURL + "/login-security-service/login";

			HttpEntity<String> request = new HttpEntity<String>(signupJsonObject.toString(), headers);

			ResponseEntity<String> loggedInUser = restTemplate.postForEntity(loginGatewayUrl, request, String.class);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpClientErrorException httpException) {

			assertEquals(httpException.getStatusCode(), HttpStatus.UNAUTHORIZED);
		}

	}

	@Test
	@Order(4)
	public void testListAccounts() {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", jwtToken);

		String listAccountsGatewayUrl = gatewayURL + "/bank-service/accounts";

		HttpEntity<String> request = new HttpEntity<String>(headers);

		ResponseEntity<String> accountsList = restTemplate.exchange(listAccountsGatewayUrl, HttpMethod.GET, request,
				String.class);

		assertEquals(accountsList.getStatusCode(), HttpStatus.OK);

		logger.info("List Of Accounts Received: \n {}", accountsList.getBody());

	}

	@Test
	@Order(5)
	public void testListAccountsByPage() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", jwtToken);

		String listAccountsGatewayUrl = gatewayURL + "/bank-service/accounts/from/5/pagesize/4";

		HttpEntity<String> request = new HttpEntity<String>(headers);

		ResponseEntity<String> accountsList = restTemplate.exchange(listAccountsGatewayUrl, HttpMethod.GET, request,
				String.class);

		assertEquals(accountsList.getStatusCode(), HttpStatus.OK);

		logger.info("List Of Accounts Received: \n {}", accountsList.getBody());

	}

	@Test
	@Order(6)
	public void testAddNewAccount() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", jwtToken);
		JSONObject newAccountJSON = new JSONObject();
		try {

			newAccountJSON.put("id", "866876768768");
			accountNo = UUID.randomUUID();
			newAccountJSON.put("accountNo", accountNo.toString());
			newAccountJSON.put("name", "Waseem");
			newAccountJSON.put("lastName", "Ismail");
			newAccountJSON.put("balance", 783778883.22);

			String addAccountsGatewayUrl = gatewayURL + "/bank-service/accounts";

			HttpEntity<String> request = new HttpEntity<String>(newAccountJSON.toString(), headers);

			ResponseEntity<String> newAccount = restTemplate.exchange(addAccountsGatewayUrl, HttpMethod.PUT, request,
					String.class);

			assertEquals(newAccount.getStatusCode(), HttpStatus.CREATED);

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (HttpClientErrorException httpException) {

			assertEquals(httpException.getStatusCode(), HttpStatus.UNAUTHORIZED);
		}

	}

	@Test
	@Order(7)
	public void testUpdateAccount() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", jwtToken);
		JSONObject updateAccountJSON = new JSONObject();
		try {

			updateAccountJSON.put("accountNo", "13c5cf95-86d5-4d3d-b470-e47421872ef4");
			updateAccountJSON.put("name", "Waseem");
			updateAccountJSON.put("lastName", "Ismail");
			updateAccountJSON.put("balance", 11111.22);

			String addAccountsGatewayUrl = gatewayURL + "/bank-service/accounts";

			HttpEntity<String> request = new HttpEntity<String>(updateAccountJSON.toString(), headers);

			ResponseEntity<String> updatedAccount = restTemplate.exchange(addAccountsGatewayUrl, HttpMethod.POST,
					request, String.class);
			updateAccountJSON = new JSONObject(updatedAccount.toString());
			assertEquals(updatedAccount.getStatusCode(), HttpStatus.OK);
			assertEquals(11111.22, updateAccountJSON.getDouble("balance"));

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (HttpClientErrorException httpException) {

			assertEquals(httpException.getStatusCode(), HttpStatus.UNAUTHORIZED);
		}

	}

	@Test
	@Order(8)
	public void testGetAccountDetailsByAccountNo() {

	}

}
