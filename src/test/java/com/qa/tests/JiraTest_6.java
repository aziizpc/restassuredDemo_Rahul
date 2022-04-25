// Take away: Path Parameter	|	SessionFilter	| 	Sending Files and its Header
// Note: .relaxedHTTPSValidation() method would be required while authentication in HTTPS server

package com.qa.tests;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Assert;

import com.qa.files.Payload;
import com.qa.files.ReusableMethods;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest_6 {

	public static void main(String[] args) {

		RestAssured.baseURI = "http://localhost:8080";
		
		// Login to Jira
		SessionFilter session = new SessionFilter();		// NOTE !!!
		String loginResponse = 
		given()
			.log().all()
			.header("Content-Type", "application/json")
			.body(Payload.loginToJira("aapc", "Test@1234"))
			.filter(session)			// NOTE
		.when()
			.post("rest/auth/1/session")
		.then()
			.log().all()
			.assertThat()
				.statusCode(200)
			.extract().response().asString();
		
		JsonPath js1 = ReusableMethods.rawToJson(loginResponse);
		String jSessionIdValue = js1.getString("session.value");
		System.out.println("JSession ID: " + jSessionIdValue);		
		
		// Create Issue		
		String createIssueResponse = 		
		given()
			.log().all()
			.header("Content-Type", "application/json")
			.body(Payload.createIssueInJira("RES", "Credit card defect")) // RES is project name
			.filter(session)
		.when()
			.post("rest/api/2/issue")
		.then()
			.log().all()
			.assertThat()
				.statusCode(201)
			.extract().response().asString();
		
		JsonPath js2 = ReusableMethods.rawToJson(createIssueResponse);
		String issueId = js2.getString("id");
		System.out.println("Issue ID: " + issueId);
		
		// Add Comment to issue
		String addCommentResponse =
		given()
			.log().all()
			.pathParam("issueId", issueId)	// Path Param
			.header("Content-Type", "application/json")				
			.body(Payload.addCommentinJiraIssue("This is a comment added using API.")) // RES is project name
			.filter(session)
		.when()
			.post("rest/api/2/issue/{issueId}/comment")	// issueId is fixed at runtime
		.then()
			.log().all()
			.assertThat()
				.statusCode(201)
			.extract().response().asString();

		JsonPath js3 = ReusableMethods.rawToJson(addCommentResponse);
		String addCommentId = js2.getString("id");
		System.out.println("Comment ID: " + addCommentId);
		
		// Add attachment to issue
		String addAttachmentResponse =
		given()
			.log().all()
			.pathParam("issueId", issueId)
			.header("Content-Type", "multipart/form-data")	// NOTE
			.header("X-Atlassian-Token", "no-check")
			.multiPart("file", new File(System.getProperty("user.dir") + "\\src\\test\\java\\com\\qa\\tests\\JiraTest_6.txt"))	// NOTE
			.filter(session)
		.when()
			.post("rest/api/2/issue/{issueId}/attachments")	// issueId is fixed at runtime
		.then()
			.log().all()
			.assertThat()
				.statusCode(200)
			.extract().response().asString();
	
		JsonPath js4 = ReusableMethods.rawToJson(addAttachmentResponse);
		String attachmentId = js3.getString("id");
		System.out.println("Attachment ID: " + attachmentId);
		
		// Get issue
		String getIssueResponse =
		given()
			.log().all()
			.pathParam("issueId", issueId)
			.queryParam("fields", "comment")	// We just want comment (See Jira API)
			.header("Content-Type", "application/json")
			.filter(session)
		.when()
			.get("rest/api/2/issue/{issueId}")
		.then()
			.log().all()
			.assertThat()
				.statusCode(200)
				.extract().response().asString();
		
		System.out.println(getIssueResponse);
		
		String expectedComment = "This is a comment added using API.";
		
		JsonPath js5 = ReusableMethods.rawToJson(getIssueResponse);
		int commentsCount = js5.getInt("fields.comment.comments.size()");	// Get number of comments (Obviously, 1 in this case)
		for (int i = 0 ; i < commentsCount ; i++) {
			String commentId = js5.get("fields.comment.comments[" + i + "].id");	// Get each ID (We have only one) 
			System.out.println(commentId);	// Print the ID
			if (commentId.equalsIgnoreCase(addCommentId)) {
				String commentBody = js5.get("fields.comment.comments[" + i + "].body");
				System.out.println(commentBody);
				Assert.assertEquals(commentBody, expectedComment);	// true
			}
		}
		
	}	

}