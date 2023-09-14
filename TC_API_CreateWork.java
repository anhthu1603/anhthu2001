package com.api.auto.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

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

public class TC_API_CreateWork {
	private String token;
	private Response response;
	private ResponseBody resBody;
	private JsonPath jsonBody;
	
	private String myWork = "Tester";
	private String myExperience = "6 month";
	private String myEducation = "FUNIX";
	
	@BeforeClass
	public void init() {
		String baseUrl = PropertiesFileUtils.getPropValue("baseurl");
		String createWorkPath = PropertiesFileUtils.getPropValue("createWorkPath");
		String token = PropertiesFileUtils.getTokenValue();
		
		RestAssured.baseURI = baseUrl;
		RestAssured.basePath = createWorkPath;
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("nameWork", myWork);
		body.put("experience", myExperience);
		body.put("education", myEducation);
		
		RequestSpecification request = RestAssured.given()
				.contentType(ContentType.JSON)
				.header("token", token)
				.body(body);
		
		response = request.post();
		resBody = response.body();
		jsonBody = resBody.jsonPath();
		
		System.out.println(" " + resBody.asPrettyString());
	}
	
	@Test
	public void TC01_StatusCodeChecked() {
		// kiểm chứng status code
		assertEquals(201, response.getStatusCode(), "Status check failed!");
	}
	
	@Test
	public void TC02_WorkIDChecked() {
		// kiểm chứng id
		if(jsonBody.get("id") == null)
			assertEquals(true, false, "WorkId check failed!");
	}
	
	@Test
	public void TC03_NameWorkChecked() {
		// kiểm chứng tên công việc nhận được có giống lúc tạo
		boolean isNameWorkMatch = jsonBody.get("nameWork") != null && jsonBody.get("nameWork").equals(myWork);
		assertEquals(true, isNameWorkMatch, "NameWork not match!");
		
	}
	
	@Test
	public void TC04_ExperienceChecked() {
		// kiểm chứng kinh nghiệm nhận được có giống lúc tạo
		boolean isExperienceMath = jsonBody.get("experience") != null && jsonBody.get("experience").equals(myExperience);
		assertEquals(true, isExperienceMath, "NameWork not match!");
	}
	
	@Test
	public void TC05_EducationChecked() {
		// kiểm chứng học vấn nhận được có giống lúc tạo
		boolean isEducationMath = jsonBody.get("education") != null && jsonBody.get("education").equals(myEducation);
		assertEquals(true, isEducationMath, "NameWork not match!");
	}
	
	
	@AfterClass
	public void aftertest() {
		RestAssured.baseURI = null;
		RestAssured.basePath = null;
	}
}












