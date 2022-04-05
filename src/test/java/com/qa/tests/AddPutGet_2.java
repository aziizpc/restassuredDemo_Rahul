package com.qa.tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import com.qa.files.Payload;
import com.qa.files.ReusableMethods;

public class AddPutGet_2 {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		// Add Place
		
		String postResponse = 
		given()
			.log().all()
			.queryParam("key", "qaclick123")
			.header("Content-Type", "application/json")
			.body(Payload.addPlace())
		.when()
			.post("maps/api/place/add/json")	// Resource
		.then()
			.log().all()
			.assertThat()
				.statusCode(200)
				.body("scope", equalTo("APP"))
				.header("Server", equalTo("Apache/2.4.18 (Ubuntu)"))
			.extract().response().asString();
		
		System.out.println(postResponse);
				
		JsonPath js = ReusableMethods.rawToJson(postResponse); // For parsing JSON
		String placeId = js.getString("place_id");		
		System.out.println(placeId);
		
		System.out.println("-----------------------------------------------------------------------------");
		
		// Put Place
		
		String putAddress = "Illinois, USA";
		
		given()
			.log().all()
			.queryParam("key", "qaclick123")
			.queryParam("place_id", placeId)
			.header("Content-Type", "application/json")
			.body(Payload.putPlace(placeId, putAddress))
		.when()
			.put("maps/api/place/update/json")
		.then()
			.log().all()
			.assertThat()
				.statusCode(200)
				.body("msg", equalTo("Address successfully updated"));
		
		System.out.println("-----------------------------------------------------------------------------");
		
		// Get Place
		
		String getResponse = 
		given()
			.log().all()
			.queryParam("key", "qaclick123")
			.queryParam("place_id", placeId)
		.when()
			.get("maps/api/place/get/json")
		.then()
			.log().all()
			.assertThat()
				.statusCode(200)
			.extract().response().asString();
		
		JsonPath js1 = ReusableMethods.rawToJson(getResponse); // For parsing JSON
		String getLatitude = js1.getString("location.latitude");
		String getAddress = js1.getString("address");
		
		System.out.println("Latitude: " + getLatitude + "; Address: " + getAddress);		
		
		Assert.assertEquals(putAddress, getAddress);
		
		System.out.println("-----------------------------------------------------------------------------");
	}

}