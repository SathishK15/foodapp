package com.sath.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.opensymphony.xwork2.ActionSupport;
import com.sath.dbconnectin.CommonSqlSet;

public class managerexist extends ActionSupport implements SessionAware {
	private String responseMessage;
	private Map<String, Object> session;

	public String execute() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		JsonArray jsonArray =new JsonArray();
		Integer id = (Integer) session.get("generatedId");
		System.out.println(id);
		String query = "select resturant_Names,resturant_Id from resturant_details where manager_id=?";
	
		try {
			jsonArray = CommonSqlSet.selectquery(query, id);

			if (jsonArray.size()>0) {
				JsonObject jsonObject =jsonArray.get(0).getAsJsonObject();
				String hotel_name = jsonObject.get("resturant_Names").getAsString();
				int res_id = jsonObject.get("resturant_Id").getAsInt();
				session.put("resturant_id", res_id);
				responseMessage = hotel_name;
			} else {
				responseMessage = "newuser";
			}

			return SUCCESS;

		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			responseMessage = "error";
			e.printStackTrace();
			return ERROR;

		} 

	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

}
