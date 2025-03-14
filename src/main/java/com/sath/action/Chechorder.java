package com.sath.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.google.gson.JsonArray;
import com.opensymphony.xwork2.ActionSupport;
import com.sath.dbconnectin.CommonSqlSet;

public class Chechorder extends ActionSupport implements SessionAware {
	private Map<String, Object> session;

	private String responseMessage;

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String execute() {
		// TODO Auto-generated method stub
		int cart_id = (int) session.get("cart_id");
		JsonArray jsonArray =new JsonArray();
		System.out.println(cart_id + "viewcart");
		String query = "select * from cart_food where cart_id=? ";
		try {

			jsonArray = CommonSqlSet.selectquery(query, cart_id);
			if (jsonArray.size()>0) {

				responseMessage = "success";

			} else {
				responseMessage = "failure";
			}

			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			responseMessage = "error";
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
}
