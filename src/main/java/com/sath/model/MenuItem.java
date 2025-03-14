package com.sath.model;

public class MenuItem {
	 private int foodid;
	    private String food;
	    private int price;

	 
	

		public int getFoodid() {
	        return foodid;
	    }

	    public void setFoodid(int foodid) {
	        this.foodid = foodid;
	    }

	    public String getFood() {
	        return food;
	    }

	    public void setFood(String food) {
	        this.food = food;
	    }

	    public int getPrice() {
	        return price;
	    }

	    public void setPrice(int price) {
	        this.price = price;
	    }

	    // Override toString() to print object details
	    @Override
	    public String toString() {
	        return "MenuItem { food_id=" + foodid + ", food='" + food + "', price=" + price + " }";
	    }
}
