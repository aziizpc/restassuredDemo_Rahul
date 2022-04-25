package com.qa.tests;

import java.util.HashMap;
import java.util.Map;

import com.qa.files.Payload;

import io.restassured.path.json.JsonPath;

public class ComplexJsonParse_3 {

	public static void main(String[] args) {

		JsonPath js = new JsonPath(Payload.coursePrice());	// Note

		// Print number of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println("Number of courses: " + count);

		// Print purchase amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("\nPurchase Amount: " + purchaseAmount);

		// Print Title of the first course
		String firstCourseTitle = js.getString("courses[0].title");
		System.out.println("\nFirst Course Title: " + firstCourseTitle);

		// Print all course titles and their respective prices
		System.out.println("\nAll course titles and prices: ");
		for (int i = 0; i < count; i++) {
			String courseTitle = js.getString("courses[" + i + "].title");
			int coursePrice = js.getInt("courses[" + i + "].price");
			System.out.println(courseTitle + " " + coursePrice);
		}

		// Print number of copies sold by RPA Course
		System.out.println("\nNumber of copies sold by RPA Course: ");
		int copiesCount = 0;
		for (int i = 0; i < count; i++) {
			if (js.getString("courses[" + i + "].title").equals("RPA")) {
				copiesCount = js.getInt("courses[" + i + "].copies");
				break;
			}
		}
		System.out.println(copiesCount);

		// Verify if sum of all course prices matches with purchase amount
		System.out.println("\nPrice verification: ");
		int sum = 0;
		for (int i = 0; i < count; i++) {
			sum = sum + ((js.getInt("courses[" + i + "].price")) * (js.getInt("courses[" + i + "].copies")));
		}
		System.out.println(sum);
		if (sum == purchaseAmount) {
			System.out.println("Verification successful :)");
		}
		else {
			System.out.println("Verification failed :(");
		}
		
	}
	
}