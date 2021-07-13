package org.dataanalytics.file;

public enum FileDetailStatusEnum {
	
	ACTIVE("A"),
	IN_ACTIVE("I");
	
	
	private String type;
	
	
	private FileDetailStatusEnum(String type) {
		this.type = type;
	}


	public String getType() {
		return type;
	}
	
}
