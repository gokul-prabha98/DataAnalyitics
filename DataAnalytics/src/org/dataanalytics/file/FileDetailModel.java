package org.dataanalytics.file;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class FileDetailModel {

	private long id;
	private long clientID;
	private String data;
	private String fileName;
	private String status;
	private String clientName;
	private long timeCreated;
	private String timeCreatedStr;
	private boolean isPublic;
	private String clientRole;
	
	
	
	public String getClientRole() {
		return clientRole;
	}
	public void setClientRole(String clientRole) {
		this.clientRole = clientRole;
	}
	public String getTimeCreatedStr() {
		return timeCreatedStr;
	}
	public void setTimeCreatedStr(String timeCreatedStr) {
		this.timeCreatedStr = timeCreatedStr;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = (JsonArray) jsonParser.parse(data);

		this.data = jsonArray.toString();
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(long timeCreated) {
		this.timeCreated = timeCreated;
	}
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
	
	
}
