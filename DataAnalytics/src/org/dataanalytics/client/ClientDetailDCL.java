package org.dataanalytics.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.dataanalytics.dbconnection.DataSource;
import org.dataanalytics.file.FileDetailQuery;
import org.dataanalytics.utils.Utils;

public class ClientDetailDCL {
	
	private static ClientDetailDCL clientDetailDCL;
	
	public static ClientDetailDCL getClientDetailDCL()
	{
		if(clientDetailDCL == null)
			clientDetailDCL = new ClientDetailDCL();
		
		return clientDetailDCL;
	}
	
	
	public boolean isAdmin(long id)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try
		{
			con = DataSource.getConnection();
			ps = con.prepareStatement(ClientDetailQuery.LOAD_ROLE_BY_ID);
			ps.setLong(1, id);
			rs =ps.executeQuery();
			
			if(rs.next() && rs.getString(1).equals(ClientRoleEnum.ADMIN.getType()))
			{
				return true;
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
		
		return false;
	}

	public ArrayList<ClientModel> loadSettingDetial()
	{
		ArrayList<ClientModel> modelList = new ArrayList<ClientModel>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try
		{
			con = DataSource.getConnection();
			ps = con.prepareStatement(ClientDetailQuery.LOAD_SETTINGS_DETAIL);
			rs =ps.executeQuery();
			while(rs.next())
			{
				ClientModel clientModel = new ClientModel();
				clientModel.setClientName(rs.getString("name"));
				clientModel.setRole(ClientRoleEnum.getName(rs.getString("role")));
				clientModel.setAddedDate(Utils.unixTimeToDate(rs.getLong("timecreated"), "dd MMMM yyyy"));
				clientModel.setSources(rs.getInt("sources"));
				modelList.add(clientModel);
			}
			return modelList;
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
		finally
		{
			Utils.closeConnection(con ,rs, ps);
		}
		
		return null;
	}


	public int insert(Connection con , ClientModel model) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			ps = con.prepareStatement(ClientDetailQuery.INSERT , PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, model.getClientName());
			ps.setString(2, model.getRole());
			ps.setString(3, "A");
			ps.setLong(4, Utils.getCurrentTime());
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
	
	public boolean isClientNameExist(String clientName)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try
		{
			con = DataSource.getConnection();
			ps = con.prepareStatement(ClientDetailQuery.LOAD_BY_NAME);
			ps.setString(1, clientName);
			rs =ps.executeQuery();
			
			if(rs.next())
			{
				return true;
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
		
		return false;
	}
	
}