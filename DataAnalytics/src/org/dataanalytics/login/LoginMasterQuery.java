package org.dataanalytics.login;

public class LoginMasterQuery {

	public static final String SELECT_BY_USERID_PASSWORD = "select clientid "
			+ " from loginmaster lm, clientdetail cd where loginid = ? and password = ? and lm.clientid = cd.id and cd.status = 'A' and lm.status = 'A' ";
	
	public static final String INSERT = "insert into loginmaster (loginid , password , clientid , status , timecreated) values (?,?,?,?,?)";
}
