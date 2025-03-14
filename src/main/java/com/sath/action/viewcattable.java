package com.sath.action;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.opensymphony.xwork2.ActionSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.sath.dbconnectin.CommonSqlSet;
import com.sath.dbconnectin.Commonjedis;
import com.sath.model.JSONObject;
import com.sath.model.MenuItem;

import redis.clients.jedis.Jedis;

public class viewcattable extends ActionSupport implements SessionAware {
	private Map<String, Object> session;
	private List<JSONObject> allmenu;

	private int total;

	public String execute() {
		allmenu = new ArrayList<>();
		if (!session.containsKey("cart_id")) { 
			return ERROR;
			}
		int cart_id = (int) session.get("cart_id");
		System.out.println(cart_id + "mjhg");
		
		String fetchcart_id = ("cart:" + cart_id);
		Map<String, String> cachedcart = Commonjedis.hashget(fetchcart_id);
		if (cachedcart != null && !cachedcart.isEmpty()) {
			System.out.println("Fetching cart from Redis...");
			fetchfromredis(fetchcart_id);
		} else {
			System.out.println("Fetching cart from MySQL...");
			fetchfromDB(cart_id, fetchcart_id);
		}
		return SUCCESS;
	}

	private void fetchfromredis(String fetchcart_id) {
		// TODO Auto-generated method stub
	total=0;
		Map<String, String> cartData = Commonjedis.hashget(fetchcart_id);
		System.out.println("map" + cartData);
		for (String json : cartData.values()) {
			System.out.println(json);
			JSONObject menu = new Gson().fromJson(json, JSONObject.class);
			allmenu.add(menu);
			total += menu.getTotal();
		}
	}

	private void fetchfromDB(int cart_id, String cachedCart) {
		// TODO Auto-generated method stub
		JsonArray jsonArray = new JsonArray();
		String query = "SELECT cf.food_id, rm.food, rm.price, cf.quantity, " + "(rm.price * cf.quantity) AS total "
				+ "FROM cart_food cf " + "INNER JOIN resturant_menu rm ON cf.food_id = rm.food_id where cf.cart_id= ? ";

		try {
			jsonArray = CommonSqlSet.selectquery(query, cart_id);
			for (int i = 0; i < jsonArray.size(); i++) {
				JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
				// JSONObject menu = new JSONObject();
				JSONObject menuItem = new JSONObject();
				menuItem.setfood(jsonObject.get("food").getAsString());
				menuItem.setprice(jsonObject.get("price").getAsInt());
				menuItem.setfoodid(jsonObject.get("food_id").getAsInt());
				menuItem.setquantity(jsonObject.get("quantity").getAsInt());
				menuItem.settotal(jsonObject.get("total").getAsInt());

				System.out.println(menuItem);
				total += (jsonObject.get("total").getAsInt());
				String fetchfood_id = ("food_" + jsonObject.get("food_id").getAsInt());

				System.out.println(cachedCart);
				System.out.println(fetchfood_id);
				System.out.println(menuItem);
				// jedisss
				String menuitem = new Gson().toJson(menuItem);
				Commonjedis.hashset(cachedCart, fetchfood_id, menuitem);
				System.out.println(
						"Stored in Redis: Key = " + cachedCart + ", Field = " + fetchfood_id + ", Value = " + menuitem);

			}
			fetchfromredis(cachedCart);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	// Getters for JSON response
	public List<JSONObject> getAllmenu() {
		return allmenu;
	}

	public int getTotal() {
		return total;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
