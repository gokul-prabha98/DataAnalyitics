package org.dataanalytics.dbconnection;

import java.sql.Connection;

import org.apache.commons.dbcp2.BasicDataSource;
import org.dataanalytics.utils.Utils;

public class DataSource {

	private static BasicDataSource basicDataSource;
	private static Connection con;
	private static DataSource dataSource;
	private static String dataBaseName;
	private static String userName;
	private static String password;
	private static String jdbcUrl;
	static
	{
		try
		{
			dataBaseName = Utils.getAppPropertyValue("dbname");
			userName = Utils.getAppPropertyValue("username");
			password = Utils.getAppPropertyValue("password");
			jdbcUrl = Utils.getAppPropertyValue("jdbcurl");
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
	}

	private  DataSource()
	{
		connectServer();
	}
	private synchronized void connectServer()
	{
		try
		{
			basicDataSource = new BasicDataSource();

			basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

			basicDataSource.setUrl(jdbcUrl + dataBaseName);

			basicDataSource.setUsername(userName);

			basicDataSource.setPassword(password);

		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}

	}


	private static synchronized void createObject()
	{
		if(dataSource == null)
			dataSource = new DataSource();

	}
	
	public static Connection getConnection()
	{

		try
		{
			if(dataSource == null)
			{
				createObject();
			}

			return basicDataSource.getConnection();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
