package com.sath.model;

public class MenuItem2C {
	 private int food_id;
	    private String food;
	    private int price;

	 
	

		public int getFoodid() {
	        return food_id;
	    }

	    public void setFoodid(int foodid) {
	        this.food_id = foodid;
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
	        return "MenuItem { food_id=" + food_id + ", food='" + food + "', price=" + price + " }";
	    }
}
