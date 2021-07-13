package org.dataanalytics.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dataanalytics.client.ClientModel;
import org.dataanalytics.dbconnection.DataSource;
import org.dataanalytics.file.FileDetailDO;
import org.dataanalytics.file.FileDetailQuery;
import org.dataanalytics.utils.Utils;

public class LoginMasterDCL {
	
	private static LoginMasterDCL loginMasterDCL;
	
	public static LoginMasterDCL getLoginMasterDCL()
	{
		if(loginMasterDCL == null)
			loginMasterDCL = new LoginMasterDCL();
		
		return loginMasterDCL;
	}
	
	public long isValidUser(String userID , String password)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try
		{
			con = DataSource.getConnection();
			ps = con.prepareStatement(LoginMasterQuery.SELECT_BY_USERID_PASSWORD);
			ps.setString(1, userID);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getLong("clientid");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Utils.closeConnection(con ,rs, ps);
		}
		
		return 0;
	}
	
	public int insert(Connection con , ClientModel model ) throws SQLException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = DataSource.getConnection();
			ps = con.prepareStatement(LoginMasterQuery.INSERT , PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, model.getLoginID());
			ps.setString(2, model.getPassword());
			ps.setInt(3, model.getClientID());
			ps.setString(4, "A");
			ps.setLong(5, Utils.getCurrentTime());
			ps.execute();
			rs =ps.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Utils.closeConnection(rs, ps);
		}
		
		return 0;
	}
	
}
