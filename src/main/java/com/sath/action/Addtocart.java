package com.sath.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.google.gson.JsonArray;
import com.opensymphony.xwork2.ActionSupport;
import com.sath.dbconnectin.CommonSqlSet;
import com.sath.dbconnectin.Commonjedis;

import redis.clients.jedis.Jedis;

public class Addtocart extends ActionSupport implements SessionAware {
	private Map<String, Object> session;
	private int food_id;
	private int quantity;
	private String responseMessage;

	// Setters for food_id and quantity
	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	// Getter for responseMessage
	public String getResponseMessage() {
		return responseMessage;
	}

	@Override
	public String execute() {
		int cart_id = (int) session.get("cart_id");
		System.out.println(cart_id + "cartt");
		
		String query = "INSERT INTO cart_food(cart_id, food_id, quantity) VALUES(?, ?, ?)";
		String checkQuery = "SELECT quantity FROM cart_food WHERE cart_id = ? AND food_id = ?";
	//	 String updateQuery = "UPDATE cart_food SET quantity = ? WHERE cart_id = ? AND food_id = ?";
int rowsAffected=0;
		try {
			System.out.println("fppd"+food_id);
			
	JsonArray rs=CommonSqlSet.selectquery(checkQuery, cart_id,food_id);
			if (rs.size()>0) {
				responseMessage = "failure";
				return ERROR;
			} else {
				CommonSqlSet.insertquery(query,cart_id,food_id,quantity);
				responseMessage = "success";
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseMessage = "Database error occurred.";
			return ERROR;
		}finally {
			String fetchcart_id = ("cart:" + cart_id);
			Map<String, String> checkcart =Commonjedis.hashget(fetchcart_id);
			if (checkcart != null && !checkcart.isEmpty()) {
				Commonjedis.hashdel(fetchcart_id);
				System.out.println("addtocart from cart the redis");
			}
		}
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
