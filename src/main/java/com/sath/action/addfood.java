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

public class addfood extends ActionSupport implements SessionAware {
	private String responseMessage;
	private String food_name;
	private int foodId;
	private int hotel_id;
	private String food_price;
	private Map<String, Object> session;
	private int managerId;

	public int getmanagerId() {
		return managerId;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String execute() {
		// TODO Auto-generated method stub
		hotel_id = (int) session.get("resturant_id");
		managerId = (int) session.get("generatedId");



		try {

			String sql = "INSERT INTO resturant_menu (food, price, resturant_id) "
					+ "SELECT ?, ?, resturant_id FROM resturant_details WHERE manager_id = ?";

			int generated_id = CommonSqlSet.insertquerygeneratedid(sql, food_name, food_price, managerId);

			if (generated_id > 0) {
				// Get the generated food ID
				foodId=generated_id;
				System.out.println(foodId + "id");
				return SUCCESS;
			} else {
				responseMessage = "Error: Food item not added.";

			}
		} catch (NumberFormatException e) {
			responseMessage = "Error: Invalid price format.";
			e.printStackTrace();
		}finally {
			String hotel_identity=("hotel_"+hotel_id);

			String cachedhotel = Commonjedis.hashgetfield("hotels_food",hotel_identity);
			if (cachedhotel != null && !cachedhotel.isEmpty()) {
				Commonjedis.hashdelfield("hotels_food",hotel_identity);
				System.out.println("from hotelfood the redis is removed");
			}
		}
		return ERROR;

	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}

	public void setFood_price(String food_price) {
		this.food_price = food_price;
	}
}
