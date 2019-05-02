package com.swiggy.model;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;

@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String userId;
	@ElementCollection
	@MapKeyColumn(name = "food")
	@Column(name = "quantity")
	@CollectionTable(name = "CartItems")
	private Map<Food, Integer> cartFood = new HashMap<Food, Integer>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Map<Food, Integer> getCartFood() {
		return cartFood;
	}

	public void setCartFood(Map<Food, Integer> cartFood) {
		this.cartFood = cartFood;
	}

	public Cart(String userId, Map<Food, Integer> cartFood) {
		super();
		this.userId = userId;
		this.cartFood = cartFood;
	}

	public Cart() {

	}

}
