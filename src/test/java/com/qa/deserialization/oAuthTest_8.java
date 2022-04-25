// DESERIALIZATION (Response Payload -> POJO)
// Get the output as Java class and then we deal with GETTERS @ the END

package com.qa.deserialization;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class oAuthTest_8 {

	public static void main(String[] args) throws InterruptedException {
		
		// The following LOCs are commented as Google not allowing login through automation.
		// Use the URL: https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss and log in
		
//		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\driver\\chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
//		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss");
//		driver.findElement(By.cssSelector("input[type = 'email']")).sendKeys("testautomation");
//		driver.findElement(By.cssSelector("input[type = 'email']")).sendKeys(Keys.ENTER);
//		Thread.sleep(5000);
//		driver.findElement(By.cssSelector("input[type = 'password']")).sendKeys("testautomation");
//		driver.findElement(By.cssSelector("input[type = 'password']")).sendKeys(Keys.ENTER);
//		Thread.sleep(5000);
//		String url = driver.getCurrentUrl();
		
		String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AX4XfWgslp-Kwd2r6BdRPJ1FbXxu3tFGN6MzzW2ROScpja4qRwdiVGpFkXYxOEIJcdp8CQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=1&prompt=none"; // Passing the url directly after logging in manually :)
		
		String partialCode = url.split("code=")[1];
		String code = partialCode.split("&scope")[0];
		System.out.println("Code: " + code);
		
		String accessTokenResponse =
		given()
			.urlEncodingEnabled(false) // We need to disable all encodings as we are going to pass 'code' which may contains % and all (Special chars).
			.queryParams("code", code)
			.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
			.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
			.queryParams("grant_type", "authorization_code")
		.when()
			.log().all()
			.post("https://www.googleapis.com/oauth2/v4/token").asString();	// Note: asString without 'extract' (No 'then()' too)
		
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.getString("access_token");
		System.out.println("Access Token: " + accessToken);
			
					
		GetCourse gc = 	// Note (Receive as Object)
		given()
			.queryParam("access_token", accessToken)
			.expect().defaultParser(Parser.JSON)	// Note: As we are dealing with JSON
		.when()
			.get("https://rahulshettyacademy.com/getCourse.php")
			.as(GetCourse.class);	// NOTE !!!!!
		
		// Print LinkedIn
		System.out.println("LinkedIn: " + gc.getLinkedIn());
		
		// Print Instructor
		System.out.println("Instructor: " + gc.getInstructor());
		
		// Print the second course name
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());		// getApi().get(1) as it's a List
		
		// Iteration: Get the price of 'SoapUI Webservices Testing' (We don't know the index)
		List<Api> apiCourses = gc.getCourses().getApi();  
		for (int i = 0 ; i < apiCourses.size() ; i++) {
			if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices Testing")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		
		// Print all the course titles in WebAutomation
		List<WebAutomation> webAutomationCourses = gc.getCourses().getWebAutomation();
		for (int i = 0 ; i < webAutomationCourses.size() ; i++) {
			System.out.println(webAutomationCourses.get(i).getCourseTitle());
		}
		
		// Check if the values are as expected
		String[] expectedCourseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};
		ArrayList <String> webAutomationCourseTitles = new ArrayList<String>();
		for (int i = 0 ; i < webAutomationCourses.size() ; i++) {
			webAutomationCourseTitles.add(webAutomationCourses.get(i).getCourseTitle());
		}
		List<String> expectedCourseTitlesArrayList =  Arrays.asList(expectedCourseTitles);
		
		Assert.assertTrue(expectedCourseTitlesArrayList.equals(webAutomationCourseTitles)); // Order of the contents doesn't matter
		
		
	}

}