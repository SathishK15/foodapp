package com.sath.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sath.dbconnectin.CommonSqlSet;
import com.sath.dbconnectin.Commonjedis;

import redis.clients.jedis.Jedis;

public class removefood extends ActionSupport implements SessionAware {
	private int food_id;
	private Map<String, Object> session;
	private String responseMessage;
	private int hotel_id;

	public int getFood_id() {
		return food_id;
	}

	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public String execute() {
		// TODO Auto-generated method stub
		hotel_id = (int) session.get("resturant_id");

		

		String query = "DELETE FROM resturant_menu WHERE food_id = ?";

		try {

			int rowsAffected = CommonSqlSet.deletequery(query, food_id);
			System.out.println("DDDD" + rowsAffected);

			if (rowsAffected > 0) {
				responseMessage = "success";
				System.out.println("success");
				return SUCCESS;
			} else {
				responseMessage = "error";
				return ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseMessage = "error: " + e.getMessage();
			return ERROR;
		}
		finally {
			String hotel_identity=("hotel_"+hotel_id);

			String cachedhotel = Commonjedis.hashgetfield("hotels_food",hotel_identity);
			if (cachedhotel != null && !cachedhotel.isEmpty()) {
				Commonjedis.hashdelfield("hotels_food",hotel_identity);
				System.out.println("from hotelfood the redis is removed");
			}
		}
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
