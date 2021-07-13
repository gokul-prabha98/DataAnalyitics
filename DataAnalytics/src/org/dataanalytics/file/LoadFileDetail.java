package org.dataanalytics.file;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dataanalytics.utils.ResponseModel;
import org.dataanalytics.utils.Utils;

/**
 * Servlet implementation class LoadFileDetail
 */
@WebServlet("/load-file-detail")
public class LoadFileDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoadFileDetail() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String fid = request.getParameter("fid");
		ResponseModel responseModel = new ResponseModel();
		if(Utils.isEmptyString(fid))
		{
			responseModel.setErrorExist(true);
			Utils.writeServletResponse(response, responseModel);
			return;
		}
		
		long _fid = Utils.parseLong(fid);
		FileDetailModel fileDetailModel = FileDetailDCL.getFileDetailDCL().loadByID(_fid);
		if(fileDetailModel != null)
		{
			fileDetailModel.setTimeCreatedStr(Utils.unixTimeToDate(fileDetailModel.getTimeCreated() , "dd MMMM yyyy") );
			responseModel.setResponseData(fileDetailModel);
		}
		else
			responseModel.setErrorExist(true);
		Utils.writeServletResponse(response, responseModel);
		
	}

}
