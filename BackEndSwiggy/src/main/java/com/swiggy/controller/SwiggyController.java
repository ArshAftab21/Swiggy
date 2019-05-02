package com.swiggy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.swiggy.model.Food;
import com.swiggy.service.SwiggyService;

import io.jsonwebtoken.Jwts;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SwiggyController {
	
	private static Logger log=LogManager.getLogger(SwiggyController.class);
	@Autowired
	SwiggyService service;

	@RequestMapping(value = "/")
	public String addItem() {
	 //service.addFoodItem();
		return "abcd";
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "getFoodItems", produces = "application/json")
	public List<Food> getFoodList(HttpServletRequest request) {
		List<Food> foodList=new ArrayList<Food>();	
			foodList = service.getFoodList();
			log.debug("getFoodItems"+foodList);	
		return foodList;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "getCartItems", produces = "application/json")
	public List<Food> getCartList(HttpServletRequest request) {
		String userId = getUserid(request);
		List<Food> cartList = service.getCartList(userId);
		if (cartList==null) {
			return null;
		}
		log.debug("getCartItems"+cartList);
		return cartList;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value = "add")
	public ResponseEntity<String> addtoCart(@RequestBody Food food, HttpServletRequest request) {
		String userId = getUserid(request);
		ResponseEntity<String> responseEntity;
		String response = "";
		if (!service.nameExistsInCart(food, userId)) {
			service.addToCart(food, userId);
            log.debug("Inside Add to cart");
			response = "Added to cart";
		} else {
			System.out.println("update");
			service.updateCart(food, userId);
			 log.debug("Inside update cart");
			response = "cart Updated";
		}

		responseEntity = new ResponseEntity<String>(response, HttpStatus.OK);
		return responseEntity;

	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "totalCost")
	public int getTotalCost(HttpServletRequest request) {
		String userId = getUserid(request);
		int price = service.getTotalCost(userId);
		 log.debug(userId+"totalcost"+price);
		return price;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value = "delete")
	public ResponseEntity<String> deleteFromCart(@RequestBody Food food, HttpServletRequest request) {
		log.debug("inside remove from Cart");
		String userId = getUserid(request);
		ResponseEntity<String> responseEntity;
		String response = "";
		boolean result=service.deleteFromCart(food, userId);
		response = "Removed from Cart";
		responseEntity = new ResponseEntity<String>(response, HttpStatus.OK);
		return responseEntity;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value = "placeOrder")
	public ResponseEntity<Map<String, String>> placeOrder(HttpServletRequest request) {
		String userId = getUserid(request);
		ResponseEntity<Map<String, String>> responseEntity;
		Map<String, String> map1 = new HashMap<>();
		service.placeOrder(userId);
		map1.put("message", "Your Order has been placed Successfully");
		responseEntity = new ResponseEntity<Map<String, String>>(map1, HttpStatus.OK);
		return responseEntity;

	}

	@CrossOrigin(origins = "http://localhost:4200")
	private String getUserid(HttpServletRequest request) {
		final String authHeader = (request.getHeader("Authorization"));
		final String token = authHeader.substring(7);
		String userId = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody().getSubject();
		log.debug(userId);
		return userId;
	}

}
