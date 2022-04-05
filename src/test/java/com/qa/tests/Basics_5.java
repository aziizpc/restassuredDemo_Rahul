// Reading data directly from file --> Check body()

package com.qa.tests;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Basics_5 {

	public static void main(String[] args) throws IOException {

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		given()
			.log().all()
			.queryParam("key", "qaclick123")
			.header("Content-Type", "application/json")
			.body(new String (Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "\\src\\test\\java\\com\\qa\\tests\\Basics_5.json"))))
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