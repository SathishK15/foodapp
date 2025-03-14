package com.sath.model;

public class JSONObject {

	private int foodid;
private String food;
private int price;
private int quantity;
private int total;
	public void setfoodid(int int1) {
		// TODO Auto-generated method stub
		this.foodid=int1;
	}

	public void setfood(String string) {
		// TODO Auto-generated method stub
		this.food=string;
	}

	public void setprice(int int1) {
		// TODO Auto-generated method stub
		this.price=int1;
	}

	public void setquantity(int int1) {
		// TODO Auto-generated method stub
		this.quantity=int1;
		
	}

	public void settotal(int int1) {
		// TODO Auto-generated method stub
		this.total=int1;
	}

	public int getFoodid() {
		return foodid;
	}

	public String getFood() {
		return food;
	}

	public int getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getTotal() {
		return total;
	}
	 public String toString() {
	        return "MenuItem { food_id=" + foodid + ", food='" + food + "', price=" + price + " ,quantity=" + quantity + ",total=" + total + "}";
	    }
}
