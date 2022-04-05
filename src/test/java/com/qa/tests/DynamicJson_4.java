package com.qa.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.files.Payload;
import com.qa.files.ReusableMethods;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DynamicJson_4 {
	
	// Data Provider
	@DataProvider(name = "BooksData")
	public Object[][] getData() {
		return new Object[][] {{"qwerty", "4356"}, {"caytr", "9886"}, {"ygmnt", "5609"}};
	}
	
	// Add Book	
	@Test (dataProvider = "BooksData")
	public void addBook(String isbn, String aisle) {
		
		RestAssured.baseURI = "http://216.10.245.166";
		
		String response = 
		given()
			.log().all()
			.header("Content-Type", "application/json")
			.body(Payload.addBook(isbn, aisle))
		.when()
			.post("Library/Addbook.php")
		.then()
			.log().all()
			.assertThat()
				.statusCode(200)
				.body("ID", equalTo(isbn+aisle))
			.extract().response().asString();
		
//		JsonPath js = new JsonPath(response);
//		String id = js.getString("Msg");
//		System.out.println(id);
		
	}
	
	// Delete Book
	
	@Test(dataProvider = "BooksData")
	public void deleteBook(String isbn, String aisle) {
		
		RestAssured.baseURI = "http://216.10.245.166";
		
		String response = 
		given()
			.log().all()
			.header("Content-Type", "application/json")
			.body(Payload.deleteBook(isbn+aisle))
		.when()
			.post("Library/DeleteBook.php")
		.then()
			.log().all()
			.assertThat()
				.statusCode(200)
				.body("msg", equalTo("book is successfully deleted"))
			.extract().response().asString();
		
//		JsonPath js = ReusableMethods.rawToJson(response);
//		String msg = js.get("msg");	// .get()
//		System.out.println(msg);	
		
	}

}