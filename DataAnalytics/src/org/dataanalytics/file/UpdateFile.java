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
 * Servlet implementation class UpdateFile
 */
@WebServlet("/update-file")
public class UpdateFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateFile() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ResponseModel model = new ResponseModel();
		try
		{
			String status = request.getParameter("status");
			String type = request.getParameter("type");
			String fid =  request.getParameter("fid");
			if(Utils.isEmptyString(status) || Utils.isEmptyString(type) || Utils.isEmptyString(fid) )
			{
				model.setErrorExist(true);
				Utils.writeServletResponse(response, model);
				return;
			}
			long fileDetailID = Utils.parseLong(fid);
			boolean _status = Utils.parseBoolean(status);
			int updated = 0;
			if(UpdateFileTypeEnum.REMOVE.getType().equals(type))
			{
				String statusStr = FileDetailStatusEnum.IN_ACTIVE.getType();
				if(!_status)
					statusStr = FileDetailStatusEnum.ACTIVE.getType();
				updated = FileDetailDCL.getFileDetailDCL().updateStausByID(fileDetailID, statusStr);
			}
			else
			{
				updated = FileDetailDCL.getFileDetailDCL().updateVisbilityByID(fileDetailID, !_status);
			}
			if(updated == 0)
				model.setErrorExist(true);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Utils.writeServletResponse(response, model);
		}
	}

}
