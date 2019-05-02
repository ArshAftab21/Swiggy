package com.swiggy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.swiggy.model.Cart;
import com.swiggy.model.Food;
import com.swiggy.repository.CartRepository;
import com.swiggy.repository.FoodRepository;

@Service("service")
public class SwiggyService {

	@Autowired
	FoodRepository foodRepository;

	@Autowired
	CartRepository cartRepository;

	@Transactional
	public List<Food> getFoodList() {
		List<Food> foodList = (List<Food>) foodRepository.findAll();
		if (foodList == null) {
			return null;
		}
		return foodList;

	}

/*	public void addFoodItem() {
		Food f1 = new Food("Burger", "North Indian", "burger.jpg", 150, 20);
		foodRepository.save(f1);
		Food f2 = new Food("Pizza", "North Indian", "pizza.jpg", 350, 30);
		foodRepository.save(f2);
		Food f3 = new Food("Chicken Biryani", "Mughlai", "biryani.jpg", 200, 25);
		Food f4 = new Food("Egg Roll", "North Indian", "eggroll.jpg", 60, 15);
		Food f5 = new Food("Noodles", "Chinese", "noodles.jpg", 120, 18);
		Food f6 = new Food("Maccroni", "French", "macroni.jpg", 150, 17);
		Food f7 = new Food("Butter Paneer", "North Indian", "butterpaneer.jpg", 250, 30);
		Food f8 = new Food("Butter Chicken", "Mughlai", "butterchicken.jpg", 350, 28);
		Food f9 = new Food("Hot Dog", "Fast Food", "hotdog.jpg", 150, 25);
		Food f10 = new Food("Sandwitch", "French", "sandwitch.jpg", 200, 35);
		Food f11 = new Food("Aloo Parathe", "Punjabi", "parathe.jpg", 70, 40);
		Food f12 = new Food("Chole Bhature", "Punjabi", "cholebhature.jpg", 100, 20);
		foodRepository.save(f3);
		foodRepository.save(f4);
		foodRepository.save(f5);
		foodRepository.save(f6);
		foodRepository.save(f7);
		foodRepository.save(f8);
		foodRepository.save(f9);
		foodRepository.save(f10);
		foodRepository.save(f11);
		foodRepository.save(f12);
	}*/

	@Transactional
	public List<Food> getCartList(String userId) {
		List<Food> foodList = new ArrayList<Food>();
		Cart cart = cartRepository.findCartItemByUserid(userId);
		if (cart == null) {
			return null;
		} else {
			Map<Food, Integer> cartFood = cart.getCartFood();
			for (Map.Entry<Food, Integer> entry : cartFood.entrySet()) {
				Food food = new Food();
				food.setQuantity(entry.getValue());
				food.setPrice(entry.getValue() * entry.getKey().getPrice());
				food.setName(entry.getKey().getName());
				food.setDescription(entry.getKey().getDescription());
				food.setImage(entry.getKey().getImage());
				food.setId(entry.getKey().getId());
				foodList.add(food);
				System.out.println(entry.getKey().getId());
			}
		}
		return foodList;

	}

	@Transactional
	public Food getfoodByid(int id) {
		Food food = foodRepository.findOne(id);
		return food;
	}

	@Transactional
	public void addToCart(Food food, String userId) {
		int id = food.getId();
		Food oldfood = foodRepository.findOne(id);
		Cart cart = cartRepository.findCartItemByUserid(userId);
		if (cart == null) {
			Cart cartNew = new Cart();
			cartNew.setUserId(userId);
			Map<Food, Integer> cartFood = cartNew.getCartFood();
			cartFood.put(oldfood, food.getQuantity());
			cartRepository.save(cartNew);
		} else {
			Map<Food, Integer> cartFood = cart.getCartFood();
			cartFood.put(oldfood, food.getQuantity());
			cartRepository.save(cart);
		}
		oldfood.setQuantity(oldfood.getQuantity() - food.getQuantity());
		foodRepository.save(oldfood);

	}

	@Transactional
	public boolean nameExistsInCart(Food food, String userId) {
		boolean result = false;
		Cart cart = cartRepository.findCartItemByUserid(userId);
		if (cart == null) {
			result = false;
		} else {
			Map<Food, Integer> cartFood = cart.getCartFood();
			for (Map.Entry<Food, Integer> entry : cartFood.entrySet()) {
				if (entry.getKey().getId() == food.getId()) {
					result = true;
				}
			}
		}

		return result;
	}

	@Transactional
	public void updateCart(Food food, String userId) {
		Food foodToUpdate=new Food();
		Food oldFood = foodRepository.findOne(food.getId());
		Cart cart = cartRepository.findCartItemByUserid(userId);
		Map<Food, Integer> cartFood = cart.getCartFood();
		for (Map.Entry<Food, Integer> entry : cartFood.entrySet()) {
			if (entry.getKey().getId() == food.getId()) {
				int oldQuantity = entry.getValue();
				entry.setValue(oldQuantity + food.getQuantity());
				
			} else {
				continue;
			}
		}
		
		cartRepository.save(cart);
		oldFood.setQuantity(oldFood.getQuantity() - food.getQuantity());

		foodRepository.save(oldFood);

	}

	@Transactional
	public int getTotalCost(String userId) {
		int price = 0;

		Cart cart = cartRepository.findCartItemByUserid(userId);
		if (cart == null) {
			return 0;
		}
		Map<Food, Integer> cartFood = cart.getCartFood();
		for (Map.Entry<Food, Integer> entry : cartFood.entrySet()) {
			price = price + (entry.getKey().getPrice() * entry.getValue());
		}

		return price;

	}

	@Transactional
	public boolean deleteFromCart(Food food, String userId) {
		boolean result=false;
		Food foodtoDelete = null;
		Food oldFood = foodRepository.findOne(food.getId());
		oldFood.setQuantity(oldFood.getQuantity() + food.getQuantity());
		Cart cart = cartRepository.findCartItemByUserid(userId);
		Map<Food, Integer> cartFood = cart.getCartFood();
		for (Map.Entry<Food, Integer> entry : cartFood.entrySet()) {
			if (entry.getKey().getId() == food.getId()) {
				int oldQuantity = entry.getValue();
				entry.setValue(oldQuantity - food.getQuantity());
				if (entry.getValue() == 0) {
					foodtoDelete = entry.getKey();
				}
			}

		}
		if (!(foodtoDelete == null)) {
			cartFood.remove(foodtoDelete);
		}
		cartRepository.save(cart);
		foodRepository.save(oldFood);
		result=true;
		return result;

	}

	@Transactional
	public void placeOrder(String userId) {
		Cart cart = cartRepository.findCartItemByUserid(userId);
		cartRepository.delete(cart);

	}

}
