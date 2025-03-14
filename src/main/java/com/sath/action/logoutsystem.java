package com.sath.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class logoutsystem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute("generatedId");
			session.removeAttribute("cart_id");
			session.removeAttribute("username");
			session.removeAttribute("password");
			session.removeAttribute("role");
			session.removeAttribute("resturant_id");
			session.invalidate();
		}

		out.write("success");
	}
}
