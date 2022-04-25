// SERIALIZATION (POJO -> Request Payload)
// We will deal with SETTERS @ the BEGINNING and receive the output as Response/ String

package com.qa.serialization;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;

public class SerializeTest_9 {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		AddPlace ap = new AddPlace();
		ap.setAccuracy(50);
		ap.setName("Azeez House");
		ap.setAddress("29, side layout, cohen 09");
		ap.setPhone_number("(+91) 123 456 6789");
		ap.setWebsite("http://google.com");
		ap.setLanguage("French-IN");
		
		List <String> typesList = new ArrayList<String>();	// Using ArrayList as we need to pass array of values
		typesList.add("shoe park");
		typesList.add("shop");		
		ap.setTypes(typesList);
		
		Location loc = new Location();
		loc.setLat(-43.383494);
		loc.setLng(43.427362);
		ap.setLocation(loc);

		String res =
		given()
			.contentType("application/json")
			.queryParam("key", "qaclick123")
			.body(ap)	// NOTE: Pass the Object as body
			.log().all()
		.when()
			.post("maps/api/place/add/json")
		.then()
			.log().all()
			.assertThat()
				.statusCode(200)
			.extract().response().asString();
		
		System.out.println(res);

	}

}