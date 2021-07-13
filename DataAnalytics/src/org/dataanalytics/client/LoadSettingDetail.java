package org.dataanalytics.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dataanalytics.utils.ResponseModel;
import org.dataanalytics.utils.Utils;

@WebServlet("/load-setting-detail")
public class LoadSettingDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoadSettingDetail() {
		super();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ResponseModel responseModel = new ResponseModel();
		long clientID = Utils.getClientIDFromRequest(request);
		boolean isAdmin = isAdmin(clientID);
		if(!isAdmin)
		{
			System.out.println("can't load settings detail for the client :" + clientID);
			responseModel.setErrorExist(true);
			Utils.writeServletResponse(response, responseModel);
			return; 
		}
		try
		{
			responseModel.setResponseData( ClientDetailDCL.getClientDetailDCL().loadSettingDetial());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			Utils.writeServletResponse(response, responseModel);
		}
	}

	public boolean isAdmin(long clientID)
	{
		try
		{
			if(clientID == 0)
			{
				System.out.println("can't read clientid from session..");
				return false;
			}
			return ClientDetailDCL.getClientDetailDCL().isAdmin(clientID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

}
