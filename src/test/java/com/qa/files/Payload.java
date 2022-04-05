package com.qa.files;

public class Payload {
	
	public static String addPlace() {
		return "{\r\n"
				+ "  \"location\": {\r\n"
				+ "    \"lat\": -43.383494,\r\n"
				+ "    \"lng\": 43.427362\r\n"
				+ "  },\r\n"
				+ "  \"accuracy\": 50,\r\n"
				+ "  \"name\": \"Azeez House\",\r\n"
				+ "  \"phone_number\": \"(+91) 123 456 6789\",\r\n"
				+ "  \"address\": \"29, side layout, cohen 09\",\r\n"
				+ "  \"types\": [\r\n"
				+ "    \"shoe park\",\r\n"
				+ "    \"shop\"\r\n"
				+ "  ],\r\n"
				+ "  \"website\": \"http://google.com\",\r\n"
				+ "  \"language\": \"French-IN\"\r\n"
				+ "}";
	}
	
	public static String putPlace(String placeId, String address) {		// NOTE		
		String str = String.format("{\r\n"
				+ "\"place_id\": \"%s\" ,\r\n"		// Note
				+ "\"address\": \"%s\" ,\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}", placeId, address);		
		return str;
	}
	
	public static String coursePrice() {
		return "{\r\n"
				+ "  \"dashboard\": {\r\n"
				+ "    \"purchaseAmount\": 910,\r\n"
				+ "    \"website\": \"aziizpc.com\"\r\n"
				+ "  },\r\n"
				+ "  \"courses\": [\r\n"
				+ "    {\r\n"
				+ "      \"title\": \"Selenium Python\",\r\n"
				+ "      \"price\": 50,\r\n"
				+ "      \"copies\": 6\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"title\": \"Cypress\",\r\n"
				+ "      \"price\": 40,\r\n"
				+ "      \"copies\": 4\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"title\": \"RPA\",\r\n"
				+ "      \"price\": 45,\r\n"
				+ "      \"copies\": 10\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
	}
	
	public static String addBook(String isbn, String aisle) {		
		String s = "{\r\n"
				+ "\"name\":\"Learn Playwright\",\r\n"
				+ "\"isbn\":\"" + isbn + "\",\r\n"
				+ "\"aisle\":\"" + aisle + "\",\r\n"
				+ "\"author\":\"John foe\"\r\n"
				+ "}";
		return s;		
	}
	
	public static String deleteBook (String id) {
		return "{\r\n"
				+ " \r\n"
				+ "\"ID\" : \""+id+"\"\r\n"
				+ " \r\n"
				+ "}";
	}
	
	public static String loginToJira (String username, String password) {
		String str = String.format("{ \"username\": \"%s\", \"password\": \"%s\" }", username, password);
		return str;
	}
	
	public static String createIssueInJira (String key, String summary) {
		String str = String.format("{\r\n"
				+ "        \"fields\": {\r\n"
				+ "        \"project\": {\r\n"
				+ "            \"key\": \"%s\"\r\n"
				+ "        },\r\n"
				+ "        \"summary\": \"%s\",\r\n"
				+ "        \"description\": \"Creating issue using REST API\",\r\n"
				+ "        \"issuetype\": {\r\n"
				+ "            \"name\": \"Bug\"\r\n"
				+ "        }\r\n"
				+ "    }\r\n"
				+ "}", key, summary);
		return str;
	}
	
	public static String addCommentinJiraIssue (String comment) {
		String str = String.format("{\r\n"
				+ "    \"body\": \"%s\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}", comment);
		
		return str;
	}

}