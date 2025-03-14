package com.sath.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sath.dbconnectin.CommonSqlSet;

public class cartuser extends ActionSupport implements SessionAware {
	private Map<String, Object> session;
	private int cart_Id;
	private String responseMessage;

	public String getResponseMessage() {
		return responseMessage;
	}

	public int getCart_id() {
		return cart_Id;
	}
	public static int querygeneratedid(String query, int cust_id, int status) {
	    return CommonSqlSet.insertquerygeneratedid(query, (Object) cust_id, (Object) status);
	}
	public String execute() {
		// TODO Auto-generated method stub
		int Status=0;
		int cust_id = (int) session.get("generatedId");
		System.out.println(cust_id + "jda");
		String query = "insert into cart_user(customer_id,status) values(?,?) ";

		try {
			int cart_id = querygeneratedid(query, cust_id,Status); 
			
			if (cart_id > 0) {

					System.out.println("cart_isd" + cart_id);
					session.put("cart_id", cart_id);
					responseMessage = "success";
				
			} else {
				responseMessage = "error";
			}

			return SUCCESS;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			responseMessage = "error";
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
