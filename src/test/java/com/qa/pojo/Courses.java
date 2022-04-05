package com.qa.pojo;

import java.util.List;

public class Courses {

	private List <WebAutomation> webAutomation;	// Note (As it's an array of elements)
	private List <Api> api;	// Note
	private List <Mobile> mobile;	// Note
	public List<WebAutomation> getWebAutomation() {
		return webAutomation;
	}
	public void setWebAutomation(List<WebAutomation> webAutomation) {
		this.webAutomation = webAutomation;
	}
	public List<Api> getApi() {
		return api;
	}
	public void setApi(List<Api> api) {
		this.api = api;
	}
	public List<Mobile> getMobile() {
		return mobile;
	}
	public void setMobile(List<Mobile> mobile) {
		this.mobile = mobile;
	}
	
}