package org.dataanalytics.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dataanalytics.client.ClientDetailDCL;
import org.dataanalytics.utils.ResponseModel;
import org.dataanalytics.utils.Utils;

/**
 * Servlet implementation class login
 */
@WebServlet("/client-login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ResponseModel responseModel = new ResponseModel();
		try
		{
			String userID = request.getParameter("uid");
			String password = request.getParameter("pass");
			boolean rememberMe = Utils.parseBoolean(request.getParameter("remember"));
			LoginMasterBusiness business = new LoginMasterBusiness();
			long clientID = business.isValidUser(userID, password);

			System.out.println("login client : " + clientID);

			if(clientID !=0)
			{	
				HttpSession session = request.getSession();
				String cid = Utils.parseString(clientID);
				System.out.println("setting session value cid" + cid);
				session.setAttribute("cid", cid);//need to encrypt the clientid
				session.setAttribute("isadmin", String.valueOf(ClientDetailDCL.getClientDetailDCL().isAdmin(clientID)));
				Cookie _userID =null;
				Cookie _password=null;
				if(rememberMe)
				{
					_userID = new Cookie("uid", userID);
					_password= new Cookie("pass", password);
				}
				else
				{
					_userID = new Cookie("uid", "");
					_password= new Cookie("pass", "");
				}
				_userID.setPath("/");
				_password.setPath("/");
				_userID.setMaxAge(-1);
				_password.setMaxAge(-1);
				
				response.addCookie( _userID );
				response.addCookie( _password );

			}
			else
				responseModel.setErrorExist(true);
			response.setContentType("text/html");
			Utils.writeServletResponse(response,responseModel );
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
