package org.dataanalytics.client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dataanalytics.dbconnection.DataSource;
import org.dataanalytics.login.LoginMasterDCL;
import org.dataanalytics.utils.ResponseModel;
import org.dataanalytics.utils.Utils;

/**
 * Servlet implementation class AddNewClient
 */
@WebServlet("/add-new-client")
public class AddNewClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddNewClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ResponseModel responseModel = new ResponseModel();
		responseModel.setErrorExist(true);
		Connection  con = null;
		try
		{
			ClientModel model = readFromRequest(request);
			if(model != null)
			{
				con = DataSource.getConnection();
				con.setAutoCommit(false);
				con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				int clientID = ClientDetailDCL.getClientDetailDCL().insert(con ,model);
				if(clientID == 0)
				{
					con.rollback();
					return;
				}
				model.setClientID(clientID);
				int loginMasterID = LoginMasterDCL.getLoginMasterDCL().insert(con, model);
				if(loginMasterID == 0)
				{
					con.rollback();
				}
				else
				{
					con.commit();
					responseModel.setErrorExist(false);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			Utils.writeServletResponse(response, responseModel);
		}

	}

	private ClientModel readFromRequest(HttpServletRequest request) {

		try
		{
			ClientModel model = new ClientModel();
			model.setClientName(request.getParameter("clientname"));
			model.setRole(request.getParameter("role"));
			model.setLoginID(request.getParameter("loginid"));
			model.setPassword(request.getParameter("password"));
			
			if(Utils.isEmptyString(model.getClientName()) ||Utils.isEmptyString(model.getRole()) ||
					Utils.isEmptyString(model.getLoginID()) || Utils.isEmptyString(model.getPassword()) )
				return null;
			if(!model.getRole().equals(ClientRoleEnum.ADMIN.getType()) && !model.getRole().equals(ClientRoleEnum.GUEST.getType()))
				return null;
			
			if(ClientDetailDCL.getClientDetailDCL().isClientNameExist(model.getClientName()))
			{
				System.out.println("client name already exist..");
				return null;
			}
			
			model.setLoginID(Utils.encrypt(model.getLoginID()));
			model.setPassword(Utils.encrypt(model.getPassword()));
			
			return model;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
