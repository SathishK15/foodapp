package com.sath.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.opensymphony.xwork2.ActionSupport;
import com.sath.dbconnectin.CommonSqlSet;

public class hotelname extends ActionSupport {
	private int hotel_id;
	private String hotel_name;
	private String responseMessage;
	public void setHotel_id(int hotel_id) {
		this.hotel_id = hotel_id;
	}
	public String execute() {
		// TODO Auto-generated method stub
		JsonArray jsonArray=new JsonArray();
		String query="select resturant_Names from resturant_details where resturant_Id=?";
		try {
			Connection con=CommonSqlSet.getconnection();
			PreparedStatement st=con.prepareStatement(query);
			st.setInt(1, hotel_id);
			jsonArray=CommonSqlSet.selectquery(query, hotel_id);
			if(jsonArray.size()>0)
			{
				JsonObject jsonObject =jsonArray.get(0).getAsJsonObject();
				
				hotel_name=jsonObject.get("resturant_Names").getAsString();
				return SUCCESS;
			}
			else {
                responseMessage = "No hotel found!";
                return ERROR;
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			responseMessage="error";
			e.printStackTrace();
			return ERROR;
		}
		
	}
	public String getHotel_name() {
		return hotel_name;
	}
	public String getResponseMessage() {
		return responseMessage;
	}

}
