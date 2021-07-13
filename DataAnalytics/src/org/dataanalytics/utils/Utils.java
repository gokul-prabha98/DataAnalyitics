package org.dataanalytics.utils;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;


public class Utils {

	private static Properties properties;
	private static final String key = "aesEncryptionKey";
	private static final String initVector = "encryptionIntVec";



	private static void loadAppProperty()
	{
		try
		{
			Utils utils = new Utils();
			InputStream in = utils.getClass().getClassLoader().getResourceAsStream("app.properties");
			properties =new Properties();  
			properties.load(in);
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
	}
	public static String getAppPropertyValue(String key)
	{
		if(properties == null)
			loadAppProperty();
		return properties.getProperty(key);
	}

	public static void closeConnection(Connection con , ResultSet rs , PreparedStatement ps)
	{
		try
		{
			if(rs != null)
				rs.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
		try
		{
			if(ps != null)
				ps.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
		try
		{
			if(con != null)
				con.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
	}
	public static void closeConnection(ResultSet rs , PreparedStatement ps)
	{
		try
		{
			if(rs != null)
				rs.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
		try
		{
			if(ps != null)
				ps.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
	}

	public static String encrypt(String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String decrypt(String encrypted) {
		if(isEmptyString(encrypted))
			return null;
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static void writeServletResponse(HttpServletResponse response, Object data)
	{
		try
		{
			String responseData = objectToString(data);
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write(responseData);
			response.getWriter().flush();

		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
	}

	public static boolean isEmptyString(String data)
	{
		return (data == null || data.trim().equals(""));
	}

	public static boolean parseBoolean(String value)
	{
		boolean res = false;
		try
		{
			if(!isEmptyString(value))
				res = Boolean.parseBoolean(value);
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
		return res;
	}

	public static Blob stringToBlob(String str , Connection conn)
	{
		try
		{
			Blob blob = conn.createBlob();
			byte[] b =  str.getBytes("UTF-8");
			blob.setBytes(1, str.getBytes("UTF-8"));
			return blob;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String convertBlobToString(Blob ablob)
	{
		try
		{
			return new String(ablob.getBytes(1l, (int) ablob.length())); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";

	}


	public static String parseString(long number)
	{
		try
		{
			String s=String.valueOf(number);
			return s;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;

	}
	public static long getClientIDFromRequest(HttpServletRequest request) {
		try
		{
			String clientID=(String)request.getSession(false).getAttribute("cid");
			System.out.println("reading clientid from session :" + clientID );
			if(isEmptyString(clientID))
				return 0;
			return Long.parseLong(clientID);  
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return 0;

	}

	public static long getCurrentTime()
	{
		return Instant.now().getEpochSecond();
	}


	public static String objectToString(Object o) 
	{
		String  objectResponse ="";
		try 
		{
			GsonBuilder gsonBuilder = new GsonBuilder();  
			gsonBuilder.serializeNulls().setLongSerializationPolicy(LongSerializationPolicy.STRING);
			Gson gson = gsonBuilder.create();
			objectResponse = gson.toJson( o );

		} 
		catch (Exception e) 
		{
		}

		return objectResponse;
	}

	public static long parseLong(String number)
	{
		long res = 0;
		try
		{
			res=Long.parseLong(number);  
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return res;
	}

	public static String unixTimeToDate(long unixSeconds, String format)
	{
		try
		{
			Date date = new java.util.Date(unixSeconds*1000L); 
			SimpleDateFormat sdf = new java.text.SimpleDateFormat(format); 
			sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+5")); 
			return sdf.format(date);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		List<List<String>> list = new ArrayList<List<String>>();
		list.toString();

		return "";
	}
	public static String csvToJsonString(String data) {
		if(isEmptyString(data))
		{
			System.out.println("Empty csv can't convert csv to json");
			return "";
		}
		try
		{
			String[] arr = data.split("\r\n");  // need to use inbuild function
			System.out.println("Array length : " + arr.length);
			JSONArray jsonArr = new JSONArray();
			for(String line : arr)
			{
				String[] commaSeparated = line.split(",");
				ArrayList<String> l = new ArrayList<String>();
				for(String val : commaSeparated)
				{
					l.add(val == null ? " " : val);
				}
				jsonArr.put(l);
			}

			System.out.println(jsonArr.length());
			return jsonArr.toString();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";

	}

}
