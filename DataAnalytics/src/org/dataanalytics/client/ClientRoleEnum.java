package org.dataanalytics.client;

import org.dataanalytics.utils.Utils;

public enum ClientRoleEnum {
	
	ADMIN("A"),
	GUEST("G");
	
	
	private String type;
	
	
	private ClientRoleEnum(String type) {
		this.type = type;
	}


	public String getType() {
		return type;
	}
	
	public static String getName(String type)
	{
		if(Utils.isEmptyString(type))
			return GUEST.toString();
		
		for(ClientRoleEnum e : ClientRoleEnum.values())
		{
			if(e.getType().equals(type))
				return e.toString();
		}
		
		return GUEST.toString();
	}
	
}
