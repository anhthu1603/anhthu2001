package com.api.auto.testcase;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.auto.utils.PropertiesFileUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class TC_API_Login {
	private String account, password;
	private Response response;
	private ResponseBody resBody;
	private JsonPath jsonBody;
	
	@BeforeClass
	public void init() {
		String baseUrl = PropertiesFileUtils.getPropValue("baseurl");
		String loginPath = PropertiesFileUtils.getPropValue("loginPath");
		account = PropertiesFileUtils.getPropValue("account");
		password = PropertiesFileUtils.getPropValue("password");
		
		RestAssured.baseURI = baseUrl;
		RestAssured.basePath = loginPath;
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("account", account);
		body.put("password", password);
		
		RequestSpecification request = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(body);
		
		//gọi POST và lấy về response
		response = request.post();
		resBody = response.body();
		jsonBody = resBody.jsonPath();
		
		System.out.println(" " + resBody.asPrettyString());
	}
	
	@Test
	public void TC01_StatusCodeChecked() {
//		kiểm tra status codee 200
		assertEquals(200, response.getStatusCode(), "Status check failed!");
	}
	
	@Test
	public void TC02_MessageChecked() {
//		kiểm chứng response body có chứa trường message không
		Assert.assertEquals(resBody.asString().contains("massage"),true,"Message check failed!");
		Assert.assertEquals(jsonBody.getString("message"),"Đăng nhập thành công","Content of message is not matched");
	}
	
	@Test 
	public void TC03_TokenChecked() {
//		kiểm chứng response body có chứa trường token không	, lưu lại token
		Assert.assertEquals(resBody.asString().contains("token"),true,"Token check failed!");
		PropertiesFileUtils.setToken(jsonBody.getString("token"));
	}
	
	@Test
	public void TC04_UserTypeChecked() {
//		kiểm chứng response body có chứa trường thông tin user và trường typ không
//		kiểm chứng trường type có phải là ứng viên
		 boolean isUserTypeValid = jsonBody.get("user.type") != null && jsonBody.get("user.type").equals("UNGVIEN");
		 assertEquals(true, isUserTypeValid, "User's type doesn't contain 'UNGVIEN'");
	
	}
	
	@Test
	public void TC05_AccountChecked() {
		// kiểm chứng response chứa thông tin user và trường account hay không
		// Kiểm chứng trường account có khớp với account đăng nhập
		// kiểm chứng response chứa thông tin user và trường password hay không
		// Kiểm chứng trường password có khớp với password đăng nhập
		  boolean isAccountMatch = jsonBody.get("user.account") != null && jsonBody.get("user.account").equals(account);
		  assertEquals(true, isAccountMatch, "Account not match!");
		  boolean isPasswordMatch = jsonBody.get("user.password") != null && jsonBody.get("user.password").equals(password);
		  assertEquals(true, isPasswordMatch, "Password not match!");
	

	}
	
	@AfterClass
	public void aftertest() {
		RestAssured.baseURI = null;
		RestAssured.basePath = null;
	}
}










