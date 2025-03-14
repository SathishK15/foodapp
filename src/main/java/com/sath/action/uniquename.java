package com.sath.action;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.opensymphony.xwork2.ActionSupport;
import com.sath.dbconnectin.CommonSqlSet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class uniquename extends ActionSupport {
	private String username;
	private String responseMessage;

	public String execute() {
		// TODO Auto-generated method stub
		JsonArray jsonArray = new JsonArray();
		String query = "select * from manager_customer where name=? ";
		try {
			 jsonArray = CommonSqlSet.selectquery(query, username);
			if (jsonArray.size()>0) {
				System.out.println("fail");
				responseMessage = "failure";
			} else {
				System.out.println("succ");
				responseMessage = "success";
			}

		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
		
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

}
