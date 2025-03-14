package com.sath.filter;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.sath.dbconnectin.CommonSqlSet;



/**
 * Servlet Filter implementation class chechpassuser
 */
@WebFilter("/*")
public class checkpassuser extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession(false);
		String username = (session != null) ? (String) session.getAttribute("username") : null;
		String password = (session != null) ? (String) session.getAttribute("password") : null;
		String role = (session != null) ? (String) session.getAttribute("role") : null;
		

		String path = req.getRequestURI();
		
		if (path.contains("loginpage.html") ||path.contains("signuppage.html")|| path.contains("signupbac") || path.contains("loginstruts")||path.contains("namecheks")||path.endsWith(".css")||path.contains("signupfront.js") ) {
			
			chain.doFilter(request, response);
			return;
		}

		if (session == null || username == null || password == null || role == null) {
			
			res.sendRedirect("loginpage.html");
			return;
		}
		if (validateUser(username, password, role)) {
			chain.doFilter(request, response);
			return;
		} else {
			res.sendRedirect("loginpage.html");
			return;
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	private boolean validateUser(String username, String password, String role) {
		boolean isValid = false;
		String query = "select id from manager_customer where name=? and password=? and role=?";
		try {

			JsonArray jsonArray =CommonSqlSet.selectquery(query, username,password,role);
			if (jsonArray.size()>0) {
				isValid = true; 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isValid;
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
