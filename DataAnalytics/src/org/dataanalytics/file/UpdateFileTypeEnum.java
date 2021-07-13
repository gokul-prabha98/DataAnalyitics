package org.dataanalytics.file;

public enum UpdateFileTypeEnum {
	
	VISIBILITY("V"),
	REMOVE("R");
	
	
	private String type;
	
	
	private UpdateFileTypeEnum(String type) {
		this.type = type;
	}


	public String getType() {
		return type;
	}
	
}
