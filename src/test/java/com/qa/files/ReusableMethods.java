package com.qa.files;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;

public class ReusableMethods {
	
	public static JsonPath rawToJson(String response) {
		JsonPath js = new JsonPath(response);
		return js;
	}
	
	public static XmlPath rawToXml(String response) {
		XmlPath x = new XmlPath(response);
		return x;
	}

}