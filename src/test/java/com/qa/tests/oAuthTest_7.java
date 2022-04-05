package com.qa.tests;

import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.path.json.JsonPath;

public class oAuthTest_7 {

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
		
		String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AX4XfWidPO2In9mz541xuETE6K7czAD9PwAkOfrOzzhYjaJ7eBaNar5hGYogSyfNhLWw5A&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none"; // Passing the url directly after logging in manually :)
		
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
			
					
		String response = 
		given()
			.queryParam("access_token", accessToken)
		.when()
			.get("https://rahulshettyacademy.com/getCourse.php")
			.asString();	// Note: asString without 'extract' (No 'then()' too)
		System.out.println(response);

	}

}