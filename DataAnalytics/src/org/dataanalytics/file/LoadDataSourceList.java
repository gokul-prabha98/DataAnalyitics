package org.dataanalytics.file;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dataanalytics.utils.ResponseModel;
import org.dataanalytics.utils.Utils;

/**
 * Servlet implementation class LoadDataSource
 */
@WebServlet("/load-data-list")
public class LoadDataSourceList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoadDataSourceList() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		Cookie c[]=request.getCookies(); 
	       //Displaying User name value from cookie
	       for(Cookie _c : c)
	       {
	    	   System.out.println(_c.getValue());
	       }

		ResponseModel responseModel = new ResponseModel();
		try
		{
			long clientID = Utils.getClientIDFromRequest(request);
			if(clientID == 0)
			{
				responseModel.setErrorExist(true);
				responseModel.setResponseData("-1");
			}
			else
				responseModel.setResponseData( FileDetailDCL.getFileDetailDCL().loadByClientID(clientID));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Utils.writeServletResponse(response, responseModel);

		}
	}

}
