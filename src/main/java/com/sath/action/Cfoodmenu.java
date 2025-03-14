package com.sath.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.opensymphony.xwork2.ActionSupport;
import com.sath.dbconnectin.CommonSqlSet;
import com.sath.dbconnectin.Commonjedis;
import com.sath.model.JSONObject;
import com.sath.model.MenuItem;
import com.sath.model.MenuItem2C;

import redis.clients.jedis.Jedis;

public class Cfoodmenu extends ActionSupport {
	private int hotel_id;
	List<MenuItem2C> foodMenus = new ArrayList<>();

	public void setHotel_id(int hotel_id) {
		this.hotel_id = hotel_id;
	}

	public String execute() {
		// TODO Auto-generated method stub
		String hotel_identity = ("hotel_" + hotel_id);
		String fetchhotel = ("hotels_food");
		String cachedhotel = Commonjedis.hashgetfield(fetchhotel, hotel_identity);
		System.out.println(cachedhotel);
		if (cachedhotel != null && !cachedhotel.isEmpty()) {
			System.out.println("Fetching food from Redis...");
			fetchfromredis(fetchhotel);
		} else {
			System.out.println("Fetching food from MySQL...");
			fetchfromDB(hotel_id, fetchhotel);
		}
		return SUCCESS;

	}

	private void fetchfromredis(String fetchhotels) {
		String hotel_identity = ("hotel_" + hotel_id);

		String hoteldata = Commonjedis.hashgetfield(fetchhotels, hotel_identity);
		System.out.println("map" + hoteldata);

		JsonArray jsonArray = JsonParser.parseString(hoteldata).getAsJsonArray();

		for (int i = 0; i < jsonArray.size(); i++) {
			MenuItem2C menu = new Gson().fromJson(jsonArray.get(i), MenuItem2C.class);
			System.out.println("Menu Item: " + menu);
			foodMenus.add(menu); 
		}

		// TODO Auto-generated method stub

	}

	private void fetchfromDB(int hotel_id2, String fetchhotels) {
		// TODO Auto-generated method stub
		String query = " SELECT food ,price,food_id from resturant_menu where resturant_Id=?";
		JsonArray menuJsonArray = new JsonArray();
		try {

			JsonArray jsonArray = CommonSqlSet.selectquery(query, hotel_id);

			/*for (int i = 0; i < jsonArray.size(); i++) {

				// ✅ Display food name and price with a remove button
				JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
				MenuItem menuItem = new MenuItem();
				menuItem.setFood(jsonObject.get("food").getAsString());
				menuItem.setPrice(jsonObject.get("price").getAsInt());
				menuItem.setFoodid(jsonObject.get("food_id").getAsInt());
				System.out.println(menuItem);

				// String menuJson = jsonObject.toString();
				// String menuitem = new Gson().toJson(menuItem);
				menuJsonArray.add(new Gson().toJsonTree(menuItem));

			}
			String menuJsonString = new Gson().toJson(menuJsonArray);
			*/
			String menuJsonString = jsonArray.toString();
			String fetchfood_id = ("hotel_" + hotel_id);
			Commonjedis.hashset(fetchhotels, fetchfood_id, menuJsonString);
			System.out.println("Stored in Redis: Key = " + fetchhotels + ", Field = " + fetchfood_id + ", Value = "
					+ menuJsonString);
			Commonjedis.hashexpire(fetchhotels, 1900);
			fetchfromredis(fetchhotels);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<MenuItem2C> getFoodMenus() {
		return foodMenus;
	}
}
