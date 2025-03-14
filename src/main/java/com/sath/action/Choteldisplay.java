package com.sath.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.opensymphony.xwork2.ActionSupport;
import com.sath.dbconnectin.CommonSqlSet;
import com.sath.dbconnectin.Commonjedis;
import com.sath.model.Hotelname;
import com.sath.model.JSONObject;

import redis.clients.jedis.Jedis;

public class Choteldisplay extends ActionSupport implements SessionAware {
	List<Hotelname> names =new ArrayList<>();
	private Map<String, Object> session;

	public String execute() {
		// TODO Auto-generated method stub
		Map<String, String> hotellist=Commonjedis.hashget("hotels_list");
		// List<String> hotel_exists = jedis.lrange("hotels_list", 0, -1);
		System.out.println(hotellist);
		if (hotellist != null && !hotellist.isEmpty())  {
			System.out.println("hotels from redis.................");
			fetchfromredis();
		} else {
			System.out.println("hotels from mysql.........................");
			fetchfromDB();

		}
		return SUCCESS;

	}

	private void fetchfromredis() {
		// TODO Auto-generated method stub
		Map<String, String> hoteldata = Commonjedis.hashget("hotels_list");
		System.out.println("map" + hoteldata);
		for (String json : hoteldata.values()) {
			System.out.println(json);
			Hotelname hotels = new Gson().fromJson(json, Hotelname.class);
			names.add(hotels);
			
		}


	}

	private void fetchfromDB() {
		// TODO Auto-generated method stub
		JsonArray jsonArray = new JsonArray();
		String query = "select resturant_Names,address,resturant_Id from resturant_details ";
		try {

			jsonArray = CommonSqlSet.selectquery(query, null);
			// String query="INSERT INTO manager_customer(Names, password) VALUES(?, ?)";
			// out.println(value);

			for (int i = 0; i < jsonArray.size(); i++) {
				JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
				System.out.println("came");
				Hotelname name = new Hotelname();
				name.setName(jsonObject.get("resturant_Names").getAsString());
				name.setAddress(jsonObject.get("address").getAsString());
				name.setId(jsonObject.get("resturant_Id").getAsInt());
				System.out.println(name);
				String jsonch = new Gson().toJson(name);
				String hotel_id =("hotel_"+jsonObject.get("resturant_Id").getAsInt());
				Commonjedis.hashset("hotels_list",hotel_id , jsonch);
				System.out.println("Stored in Redis: key = " + "hotel_exists" + ", value = " + jsonch);

			}
			Commonjedis.hashexpire("hotels_list", 1900);
			fetchfromredis();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public List<Hotelname> getHotelname() {
		return names;
	}

}
