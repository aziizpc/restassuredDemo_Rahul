// Using RequestSpecification, RequestSpecBuilder, ResponseSpecification & ResponseSpecBuilder
// Split the given()when()then() to given() and when()then()

package com.qa.serialization;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilderTest_10 {

	public static void main(String[] args) {
		
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
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
			.addQueryParam("key", "qaclick123")
			.setContentType(ContentType.JSON)
			.build();		
		
		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200)
			.expectContentType(ContentType.JSON).build();		
		
		System.out.println("--------------------------------------------------------------------");
		
		//RestAssured.baseURI = "https://rahulshettyacademy.com";	// Not needed
		
		String res1 =
		given()
			.spec(req)
			.body(ap)	// NOTE: Pass the Object as body
			.log().all()
		.when()
			.post("maps/api/place/add/json")
		.then()
			.log().all()
			.assertThat()
				.statusCode(200)
			.extract().response().asString();
		
		System.out.println(res1);
		
		System.out.println("--------------------------------------------------------------------");
		
		RequestSpecification reqspec =	// Again compacting [Note: This again is of type 'RequestSpecification']
			given()
				.spec(req)
				.body(ap)
				.log().all();		
		
		String res2 =		// Note: Type would be 'Response' (Here we are converting it to String using .asString]
			reqspec
			.when()
				.post("maps/api/place/add/json")
			.then()
				.log().all()
				.assertThat()
					.spec(res)
				.extract().response().asString();
				
		System.out.println(res2);
		
	}

}