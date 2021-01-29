package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private boolean authenticate(String user, String pass) {
		return !(user == null || pass == null || pass.trim() == "");
	}
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = (String) request.getParameter("userName");
		String password = (String) request.getParameter("password");
		HttpSession session = request.getSession();
		//System.out.println("User: " + userName + " pass: " + password);
		if(authenticate(userName, password)) {
			session.setAttribute("user", userName);
			response.sendRedirect("Quiz.jsp");
			return;
		} else {
			response.sendRedirect("Login.jsp");
			return;
		}
	}
}
