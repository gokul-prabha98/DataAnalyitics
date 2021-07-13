package org.dataanalytics.client;

public class ClientDetailQuery {
	
	public static final String LOAD_ROLE_BY_ID = "select role from clientdetail where id = ?";
	
	public static final String LOAD_SETTINGS_DETAIL = "select name , role , cd.timecreated , count(fd.id) as sources from clientdetail cd left join " + 
			" filedetail fd on fd.clientid = cd.id and cd.status = 'A' and fd.status = 'A' group by cd.id ";
	
	public static final String INSERT = "insert into clientdetail (name , role ,status, timecreated ) values (?,?,?,?)";
	
	
	public static final String LOAD_BY_NAME = "select id from clientdetail where name = ?";

}
