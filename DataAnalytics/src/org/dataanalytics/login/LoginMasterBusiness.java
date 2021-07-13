package org.dataanalytics.login;

import org.dataanalytics.utils.Utils;

public class LoginMasterBusiness {
	
	public long isValidUser(String userID , String password)
	{
		LoginMasterDCL loginMasterDCL = LoginMasterDCL.getLoginMasterDCL();
		return loginMasterDCL.isValidUser(Utils.encrypt(userID), Utils.encrypt(password));
	}

}
