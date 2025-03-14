package com.sath.model;

public class Hotelname {

	private int id;
	private String address;
	private String name;
	
	public int getId() {
		return id;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String string) {
		// TODO Auto-generated method stub
		this.name=string;
	}
	public void setAddress(String string) {
		// TODO Auto-generated method stub
		this.address=string;
		
	}
	public void setId(int int1) {
		// TODO Auto-generated method stub
		this.id=int1;
		
	}
	 public String toString() {
	        return "hotelname { id=" + id + ", name='" + name + "', address=" + address + " }";
	    }
	
}
