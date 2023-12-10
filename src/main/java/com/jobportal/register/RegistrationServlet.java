package com.jobportal.register;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public RegistrationServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("pass");
		String confirmPassword = request.getParameter("re_pass");
		RequestDispatcher dispatcher = null;
		Connection con = null;
		if (password == null || password.equals("")) {
			request.setAttribute("status", "invalid");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
		else if (email == null || email.equals("")) {
			request.setAttribute("status", "invalid");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
		else if (username == null || username.equals("")) {
			request.setAttribute("status", "invalid");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
		else if (!password.equals(confirmPassword)) {
			request.setAttribute("status", "confirm_password_invalid");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
			
		}
		else {
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal?useSSL=false", "root", "Emebet@1994");
				PreparedStatement pst = con.prepareStatement("insert into users(username, password, email) values(?,?,?)");
				pst.setString(1, username);
				pst.setString(2,password);
				pst.setString(3,email);
				
				int rowCount = pst.executeUpdate();
				
				dispatcher = request.getRequestDispatcher("registration.jsp");
				
				if (rowCount>0) {
					request.setAttribute("status", "success");
				} else {
					request.setAttribute("status", "failed");
				}
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (SQLException e) {
				
					e.printStackTrace();
				}
			}
		}

		
	}

}
