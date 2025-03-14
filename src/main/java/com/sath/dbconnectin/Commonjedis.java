package com.sath.dbconnectin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class Commonjedis {

	public static Jedis getconnection() {
		// TODO Auto-generated method stub
		return new Jedis("localhost", 6379);
	}
	 public static Map<String, String> hashget(String key) {
		 Map<String, String> jedismap=null;
		 Jedis jedis=null;
	        try { jedis = getconnection();  
	       jedismap=jedis.hgetAll(key);
	           
	        }
	        catch(Exception e){
	        	e.printStackTrace();
	        }
	        finally {
	        	if (jedis != null) {
		            jedis.close();
		            jedis = null; 
		            System.out.println("Jedis connection closed.");
		        }
	        }
	        return (jedismap != null) ? jedismap : new HashMap<>();
	    }

	    public static void hashset(String key, String fetcheditem, String value) {
	    	Jedis jedis=null;
	        try { jedis = getconnection();  
	            jedis.hset(key, fetcheditem, value);
	        }
	        catch(Exception e){
	        	e.printStackTrace();
	        }
	        finally {
	        	 if (jedis != null) {
			            jedis.close();
			            jedis = null; 
			            System.out.println("Jedis connection closed.");
			        }
	        }
	    }
		
		public static void hashdel(String fetcheditem) {
			// TODO Auto-generated method stub
			Jedis jedis =null;
			 try {jedis = getconnection() ;
				 jedis.del(fetcheditem);
		        }
			 catch(Exception e){
		        	e.printStackTrace();
		        }
		        finally {
		        	if (jedis != null) {
			            jedis.close();
			            jedis = null; // Reset to null so a new connection can be established when needed
			            System.out.println("Jedis connection closed.");
			        }
		        }
		}
		public static String hashgetfield(String key,String field) {
			 String jedismap=null;
			 Jedis jedis=null;
		        try { jedis = getconnection();  
		       jedismap=jedis.hget(key,field);
		           
		        }
		        catch(Exception e){
		        	e.printStackTrace();
		        }
		        finally {
		        	if (jedis != null) {
			            jedis.close();
			            jedis = null; 
			            System.out.println("Jedis connection closed.");
			        }
		        }
		        return (jedismap != null) ? jedismap : null;
		    }
		public static void hashdelfield(String fetcheditem,String field) {
			// TODO Auto-generated method stub
			Jedis jedis =null;
			 try {jedis = getconnection() ;
				 jedis.del(fetcheditem,field);
		        }
			 catch(Exception e){
		        	e.printStackTrace();
		        }
		        finally {
		        	if (jedis != null) {
			            jedis.close();
			            jedis = null; 
			            System.out.println("Jedis connection closed.");
			        }
		        }
		}
		public static void hashexpire(String fetcheditem,int time) {
			// TODO Auto-generated method stub
			Jedis jedis =null;
			 try { jedis = getconnection(); 
				 jedis.expire(fetcheditem, time);
		        }
			 catch(Exception e){
		        	e.printStackTrace();
		        }
		        finally {
		        	if (jedis != null) {
			            jedis.close();
			            jedis = null; 
			            System.out.println("Jedis connection closed.");
			        }
		        }
		}
		
		
		public static boolean exists(String key) {
			// TODO Auto-generated method stub
			
			 boolean ans=false;
			 Jedis jedis =null;
			 try { jedis = getconnection(); 
				ans= jedis.exists("hotels_list");
		        }
			 catch(Exception e){
		        	e.printStackTrace();
		        }
		        finally {
		        	if (jedis != null) {
			            jedis.close();
			            jedis = null; 
			            System.out.println("Jedis connection closed.");
			        }
		        }
			 return ans;
		}
		
}