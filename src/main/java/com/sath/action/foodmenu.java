package com.sath.action;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.opensymphony.xwork2.ActionSupport;
import com.sath.dbconnectin.CommonSqlSet;
import com.sath.model.MenuItem;

public class foodmenu extends ActionSupport implements SessionAware {
	 private Map<String, Object> session;
	 List<MenuItem> foodMenu = new ArrayList<>();
	public String execute(){
		// TODO Auto-generated method stub
// change after
		JsonArray jsonArray =new JsonArray();
		 Integer id = (Integer) session.get("generatedId");
		
		 try {

            String query = " SELECT rm.food, rm.price, rm.food_id \r\n"
    				+ "FROM resturant_menu rm\r\n"
    				+ "JOIN resturant_details rd ON rm.resturant_Id = rd.resturant_Id\r\n"
    				+ "JOIN manager_customer m ON rd.manager_id = m.id\r\n"
    				+ "WHERE m.id =  ?";
			 jsonArray =CommonSqlSet.selectquery(query, id);
			for(int i=0;i<jsonArray.size();i++) {
				JsonObject jsonObject =jsonArray.get(i).getAsJsonObject();
				System.out.println("came");
				 MenuItem menuItem = new MenuItem();
	                menuItem.setFood(jsonObject.get("food").getAsString());
	                menuItem.setPrice(jsonObject.get("price").getAsInt());
	                menuItem.setFoodid(jsonObject.get("food_id").getAsInt());

                System.out.println(menuItem);
                foodMenu.add(menuItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return SUCCESS; 
    }
// cahnge after
	public void setSession(Map<String, Object> session) {
        this.session = session;
    }
    public List<MenuItem> getFoodMenu() {
        return foodMenu;
    }
}
