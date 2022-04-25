package com.qa.exceldriven;

import org.testng.annotations.Test;

import com.qa.files.ReusableMethods;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HashMapAndExcelDriven_11 {
	
	// Basic
	@Test
	public void addBookBasic() {
		RestAssured.baseURI = "http://216.10.245.166";
		Response resp = given()
							.header("Content-Type", "application/json")
							.body("{\r\n"
									+ "\"name\":\"Learn Playwright\",\r\n"
									+ "\"isbn\":\"sder\",\r\n"
									+ "\"aisle\":\"55513\",\r\n"
									+ "\"author\":\"John foe\"\r\n"
									+ "}")
							.log().all()
						.when()
							.post("Library/Addbook.php")
						.then()
							.assertThat()
								.statusCode(200)
								.and()
									.contentType(ContentType.JSON)
							.extract().response();
		
		JsonPath js = ReusableMethods.rawToJson(resp.asString());
		System.out.println(js.get("ID"));
		
		
	}
	
	// Using HashMap
	@Test
	public void addBookHashMap() {
		
		HashMap<String, Object> jsonAsMap = new HashMap<String, Object>();	// NOTE !!!! :: Nested JSON ==> Create nested HashMap
		jsonAsMap.put("name", "Learn Playwright");
		jsonAsMap.put("isbn", "sder");
		jsonAsMap.put("aisle", "55513");
		jsonAsMap.put("author", "John foe");
		
		RestAssured.baseURI = "http://216.10.245.166";
		Response resp = given()
							.header("Content-Type", "application/json")
							.body(jsonAsMap)
							.log().all()
						.when()
							.post("Library/Addbook.php")
						.then()
							.assertThat()
								.statusCode(200)
								.and()
									.contentType(ContentType.JSON)
							.extract().response();
		
		JsonPath js = ReusableMethods.rawToJson(resp.asString());
		System.out.println(js.get("ID"));		
		
	}
	

	@Test
	public void addBookExcel() throws IOException {
		
		DataDriven d = new DataDriven();
		ArrayList data = d.getData("AddBook", "RestAssured");
		
		HashMap<String, Object> jsonAsMap = new HashMap<String, Object>();	// NOTE !!!! :: Nested JSON ==> Create nested HashMap
		jsonAsMap.put("name", data.get(1));
		jsonAsMap.put("isbn", data.get(2));
		jsonAsMap.put("aisle", data.get(3));
		jsonAsMap.put("author", data.get(4));
		
		RestAssured.baseURI = "http://216.10.245.166";
		Response resp = given()
							.header("Content-Type", "application/json")
							.body(jsonAsMap)
							.log().all()
						.when()
							.post("Library/Addbook.php")
						.then()
							.assertThat()
								.statusCode(200)
								.and()
									.contentType(ContentType.JSON)
							.extract().response();
		
		JsonPath js = ReusableMethods.rawToJson(resp.asString());
		System.out.println(js.get("ID"));
		
		
	}


}