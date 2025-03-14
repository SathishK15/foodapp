package com.sath.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sath.dbconnectin.CommonSqlSet;
import com.sath.dbconnectin.Commonjedis;

import redis.clients.jedis.Jedis;

public class resturantdetails extends ActionSupport implements SessionAware{
	 private String hotel_name;
	 private String ph_num;
	 private String address;
	 private String status;  
	 private Map<String, Object> session;

	    public String execute() {
	    	
	    	//
	    	int rest_id=0;
	    	 Integer id = (Integer) session.get("generatedId");
	        System.out.println("Manager ID: " + id);
	        try {
	        	
	            String query = "INSERT INTO resturant_details(resturant_Names, address, phone_number, manager_id) VALUES (?, ?, ?, ?)";
	            

	            
	             rest_id = CommonSqlSet.insertquerygeneratedid(query, hotel_name,address,ph_num,id);
	            System.out.println("Insert Status: " + rest_id);

	            if (rest_id > 0) {
	            	session.put("resturant_id", rest_id);
	                status = "success";  
	            } else {
	                status = "failure";
	            }
	        } catch (Exception e) {
	            status = "error";
	            e.printStackTrace();
	        }finally {
	        	String fetchhotel_id = ("hotels_list");

	        	Map<String, String> cachedhotel = Commonjedis.hashget(fetchhotel_id);
	        	System.out.println(cachedhotel);
	        	if (cachedhotel != null && !cachedhotel.isEmpty()){
					Commonjedis.hashdel("hotels_list");
					System.out.println("from hotels the redis is removed.........");
				}	
	        }
	        System.out.println(status); 
	        return SUCCESS;  
	    }

	  
	  public void setSession(Map<String, Object> session) {
	        this.session= session;
	    }
	    public String getStatus() {
	        return status;
	    }

	    public void setHotel_name(String hotel_name) {
	        this.hotel_name = hotel_name;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

	    public void setPh_num(String ph_num) {
	        this.ph_num = ph_num;
	    }
	}