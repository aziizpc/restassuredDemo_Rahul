package com.qa.tests;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.qa.files.Payload;

public class Basics_1 {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		given()
			.log().all()
			.queryParam("key", "qaclick123")	// KEY-VALUE auth
			.header("Content-Type", "application/json")
			.body(Payload.addPlace())
		.when()
			.post("maps/api/place/add/json")
		.then()
			.log().all()
			.assertThat()
				.statusCode(200)
				.body("scope", equalTo("APP"))
				.header("Server", equalTo("Apache/2.4.18 (Ubuntu)"));
	}

}