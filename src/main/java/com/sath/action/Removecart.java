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
import com.sath.dbconnectin.Commonjedis;

import redis.clients.jedis.Jedis;

public class Removecart extends ActionSupport implements SessionAware {
	private Map<String, Object> session;
	private int cart_Id;
	private int food_id;

	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}

	private String responseMessage;

	public String getResponseMessage() {
		return responseMessage;
	}

	public int getCart_id() {
		return cart_Id;
	}

	public String execute() throws SQLException {
		// TODO Auto-generated method stub
		int cart_id = (int) session.get("cart_id");
		System.out.println(cart_id + "viewcart");
		
		
		String query = "DELETE FROM cart_food WHERE food_id = ? AND cart_id = ?";

		try {
			int rowsAffected = CommonSqlSet.deletequery(query, food_id, cart_id);

			System.out.println(rowsAffected);

			if (rowsAffected > 0) {
				responseMessage = "success";
			}
			else {
				responseMessage = "failure";
			}
		}catch (Exception e)
		{
			responseMessage="error";
			e.printStackTrace();
		}finally {
			String fetchcart_id = ("cart:" + cart_id);
			Map<String, String> checkcart =Commonjedis.hashget(fetchcart_id);
			if (checkcart != null && !checkcart.isEmpty()) {
				Commonjedis.hashdel(fetchcart_id);
				System.out.println("remoed from cart the redis");
			}
		}
		return SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
