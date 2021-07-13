package org.dataanalytics.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dataanalytics.utils.ResponseModel;
import org.dataanalytics.utils.Utils;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Logout() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ResponseModel responseModel = new ResponseModel();
		try
		{
			Cookie _userID = new Cookie("uid", "");
			Cookie _password= new Cookie("pass", "");
			_userID.setPath("/");
			_password.setPath("/");
			_userID.setMaxAge(-1);
			_password.setMaxAge(-1);

			response.addCookie( _userID );
			response.addCookie( _password );
			
			HttpSession s = request.getSession();
			s.invalidate();
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
