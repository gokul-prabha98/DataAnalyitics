package org.dataanalytics.file;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.dataanalytics.dbconnection.DataSource;
import org.dataanalytics.utils.Utils;

public class FileDetailDCL {
	
	private static FileDetailDCL fileDetailDCL;
	
	public static FileDetailDCL getFileDetailDCL()
	{
		if(fileDetailDCL == null)
			fileDetailDCL = new FileDetailDCL();
		
		return fileDetailDCL;
	}
	
	public long insert(FileDetailDO fileDetailDO) throws SQLException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try
		{
			con = DataSource.getConnection();
			con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			con.setAutoCommit(false);
			ps = con.prepareStatement(FileDetailQuery.INSERT , PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setLong(1, fileDetailDO.getClientID());
			ps.setBlob(2, Utils.stringToBlob(fileDetailDO.getData(), con));
			ps.setString(3, fileDetailDO.getFileName());
			ps.setString(4, fileDetailDO.getStatus());
			ps.setBoolean(5, fileDetailDO.isPublic());
			ps.setLong(6, fileDetailDO.getTimeCreated());
			ps.execute();
			con.commit();
			rs =ps.getGeneratedKeys();
			if(rs.next())
				return rs.getLong(1);
		}
		catch(Exception e)
		{
			if(con != null)
				con.rollback();
			e.printStackTrace();
		}
		finally
		{
			Utils.closeConnection(con , rs, ps);
		}
		
		return 0;
	}
	public Map<String, String> loadByClientID(long clientID)
	{
		Map<String, String> res = new HashMap<String, String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try
		{
			con = DataSource.getConnection();
			ps = con.prepareStatement(FileDetailQuery.LOAD_BY_CLIENTID);
			ps.setLong(1, clientID);
			rs =ps.executeQuery();
			
			while(rs.next())
			{
				res.put( Utils.parseString(rs.getLong("id")) , rs.getString("filename"));
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
		finally
		{
			Utils.closeConnection(con ,rs, ps);
		}
		
		return res;
	}
	
	public FileDetailModel loadByID(long id)
	{
		FileDetailModel fileDetailModel = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try
		{
			con = DataSource.getConnection();
			ps = con.prepareStatement(FileDetailQuery.LOAD_BY_ID);
			ps.setLong(1, id);
			rs =ps.executeQuery();
			
			if(rs.next())
			{
				fileDetailModel = new FileDetailModel();
				fileDetailModel.setClientID(rs.getLong("clientid"));
				fileDetailModel.setClientName(rs.getString("name"));
				fileDetailModel.setData(Utils.convertBlobToString(rs.getBlob("data")));
				fileDetailModel.setFileName(rs.getString("filename"));
				fileDetailModel.setTimeCreated(rs.getLong("timecreated"));
				fileDetailModel.setPublic(rs.getBoolean("ispublic"));
				fileDetailModel.setClientRole(rs.getString("role"));
				fileDetailModel.setId(id);
				return fileDetailModel;
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
		finally
		{
			Utils.closeConnection(con ,rs, ps);
		}
		
		return fileDetailModel;
	}
	
	
	public int updateStausByID(long id , String status)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try
		{
			con = DataSource.getConnection();
			ps = con.prepareStatement(FileDetailQuery.UPDATE_STATUS_BY_ID);
			ps.setString(1, status);
			ps.setLong(2, id);
			return ps.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
		finally
		{
			Utils.closeConnection(con ,rs, ps);
		}
		
		return 0;
	}
	public int updateVisbilityByID(long id , boolean isPublic)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try
		{
			con = DataSource.getConnection();
			ps = con.prepareStatement(FileDetailQuery.UPDATE_VISIBITLITY_BY_ID);
			ps.setBoolean(1, isPublic);
			ps.setLong(2, id);
			return ps.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
		finally
		{
			Utils.closeConnection(con ,rs, ps);
		}
		
		return 0;
	}

	
}

