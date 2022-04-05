package com.qa.files;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
	
	public static JsonPath rawToJson(String response) {
		JsonPath js = new JsonPath(response);
		return js;
	}

}