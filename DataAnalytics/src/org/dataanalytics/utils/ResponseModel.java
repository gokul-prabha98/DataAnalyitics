package org.dataanalytics.utils;

public class ResponseModel {

	private String responseCode;
	private boolean isErrorExist;
	private Object responseData;
	
	
	public Object getResponseData() {
		return responseData;
	}
	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public boolean isErrorExist() {
		return isErrorExist;
	}
	public void setErrorExist(boolean isErrorExist) {
		this.isErrorExist = isErrorExist;
	}
	
	
}
