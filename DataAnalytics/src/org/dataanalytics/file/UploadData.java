package org.dataanalytics.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.dataanalytics.utils.ResponseModel;
import org.dataanalytics.utils.Utils;


/**
 * Servlet implementation class UploadData
 */
@WebServlet("/upload-data")
public class UploadData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UploadData() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long clientID = Utils.getClientIDFromRequest(request);
		ResponseModel responseModel = new ResponseModel();

		if(clientID == 0)
		{
			responseModel.setErrorExist(true);
			responseModel.setResponseData("-1");
			return;
		}
		try
		{
			if (ServletFileUpload.isMultipartContent(request))
			{
				ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
				List<FileItem> items = upload.parseRequest(request);
				String fileName = request.getParameter("filename");
				if(fileName != null)
				{
					for (FileItem item : items) {
						if(!Utils.isEmptyString(item.getName()))
						{
							if(Utils.isEmptyString(fileName))
								fileName = item.getName();
							
							System.out.println("Inserting file name " + fileName);
							FileDetailBusiness business = new FileDetailBusiness();
							long insertedID = business.insert(clientID, fileName, item.getString());
							if(insertedID ==0 )
								responseModel.setErrorExist(true);
							break;//can support multiple file in feature
						}
						else
						{
							System.out.println("Empty file name..");
						}
					}
				responseModel.setResponseData("success");
				}
				else
					responseModel.setErrorExist(true);
			}
			else 
			{
				System.out.println("can't load file from the request");
				responseModel.setResponseData("can't add data");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			Utils.writeServletResponse(response, responseModel);
		}

	}
}
