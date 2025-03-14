package com.sath.filter;

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

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet Filter implementation class logincheck
 */
public class logincheck extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
   
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        PrintWriter out = res.getWriter();

        
        String username = req.getParameter("username");
        String password = req.getParameter("password");
    

      
        if (username == null || username.trim().isEmpty()) {
            out.write("Username is required");
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            out.write("Password is required");
            return;
        }

        if (username.length() < 3) {
            out.write("Username must be at least 3 characters long");
            return;
        }

        if (password.length() < 3) {
            out.write("Password must be at least 6 characters long");
            return;
        }

    
        chain.doFilter(request, response);
    }
		

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
