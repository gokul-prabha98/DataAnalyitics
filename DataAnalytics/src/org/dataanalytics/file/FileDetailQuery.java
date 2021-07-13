package org.dataanalytics.file;

public class FileDetailQuery {

	public static final String LOAD_ALL = "select id , clientid , data , filename , status , ispublic , timecreated from filedetail ";
	
	public static final String INSERT = "insert into filedetail (clientid , data , filename, status, ispublic , timecreated) "
			+ " values(?,?,?,?,?,?)";
	
	public static final String LOAD_BY_CLIENTID = "select fd.id , filename from filedetail fd, clientdetail cd where  fd.status = 'A' and cd.id = ?" + 
			" and ((cd.role = 'A') || (fd.clientid = cd.id || ispublic = true))";
	
	public static final String LOAD_BY_ID = "select clientid , data , filename , fd.status , ispublic , fd.timecreated , cd.name , cd.role from filedetail fd , clientdetail cd "
			+ " where fd.id = ? and cd.id = fd.clientid and fd.status = 'A'";
	
	public static final String UPDATE_STATUS_BY_ID = "update filedetail set status = ? where id = ?";
	
	public static final String UPDATE_VISIBITLITY_BY_ID = "update filedetail set ispublic = ? where id = ?";
}
