package com.sath.action;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.opensymphony.xwork2.ActionSupport;
import com.sath.dbconnectin.CommonSqlSet;

import org.apache.struts2.interceptor.SessionAware;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class checkcartuser extends ActionSupport implements SessionAware {
    private int food_id;
    private String responseMessage;
    private Map<String, Object> session;

    @Override
    public String execute() {
    
    	JsonArray jsonArray=new JsonArray();
    	if (session.containsKey("cart_id")) { 
    	    int cust_id = (int) session.get("generatedId");
            System.out.println(cust_id+"jda");
            String query = "SELECT resturant_Id FROM resturant_menu WHERE food_id = ?";
            String query1 = "SELECT cu.cart_id FROM cart_user cu " +
                            "JOIN cart_food cf ON cu.cart_id = cf.cart_id " +
                            "JOIN resturant_menu rm ON cf.food_id = rm.food_id " +
                            "WHERE cu.customer_id = ? AND rm.resturant_Id = ? AND cu.status = 0";
            int resturant_id = 0;

                    try {
                    	jsonArray = CommonSqlSet.selectquery(query, food_id);
                        if (jsonArray.size()>0) {
                        	JsonObject jsonObject =jsonArray.get(0).getAsJsonObject();
                            resturant_id = jsonObject.get("resturant_Id").getAsInt();
                            System.out.println(resturant_id);
                        } else {
                            responseMessage = "Restaurant not found for the given food ID.";
                            return ERROR;
                        }
                    }catch(Exception e){
                    	
                    }
                

                // Check if the customer has an active cart for the restaurant

                    try {jsonArray = CommonSqlSet.selectquery(query1,cust_id,resturant_id); 
                        if (jsonArray.size()>0) 
                        {
                            responseMessage = "success";
                        } else {
                            responseMessage = "failure";
                        }
                    
            } catch (Exception e) {
                e.printStackTrace();
                responseMessage = "An error occurred while processing your request.";
                return ERROR;
            }
    	} else {
    	    responseMessage = "failure"; // cart_id not present
    	}
    	return SUCCESS;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
