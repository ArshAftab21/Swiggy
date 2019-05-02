package com.swiggy.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.swiggy.model.Cart;
import com.swiggy.model.Food;
import com.swiggy.repository.CartRepository;
import com.swiggy.repository.FoodRepository;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwiggyServiceTest {
	
	@Mock
	FoodRepository foodRepository;
	
	@Mock
	CartRepository cartRepository;
	
	private Food food;
	
	private Cart cart;
	
	private List<Food> foodList=new ArrayList<Food>();
	
	@InjectMocks
	SwiggyService service;
	
	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		food = new Food("Burger", "North Indian", "burger.jpg", 150, 2);
		foodList.add(food);
		Map<Food,Integer> map=new HashMap<Food, Integer>();
		map.put(food, 2);
		cart=new Cart("1",map);
		
	}
	
	@Test
	public void testGetFoodList()
	{
		when(foodRepository.findAll()).thenReturn(foodList);
		List<Food> fList=service.getFoodList();
		assertNotNull(fList);
		verify(foodRepository,times(1)).findAll();
	}
	
	@Test
	public void TestGetCartList()
	{
		when(cartRepository.findCartItemByUserid("1")).thenReturn(cart);
		List<Food> fList=service.getCartList("1");
		assertNotNull(fList);
		verify(cartRepository,times(1)).findCartItemByUserid(Mockito.anyString());
	}
	
	@Test
	public void testGetFoodById()
	{
		
		when(foodRepository.findOne(1)).thenReturn(food);
		Food searchedFood=service.getfoodByid(1);
		assertEquals(food, searchedFood);
		verify(foodRepository,times(1)).findOne(Mockito.anyInt());
	}
	
	@Test
	public void testNameExistsInCart()
	{
		when(cartRepository.findCartItemByUserid("1")).thenReturn(cart);
		boolean result=service.nameExistsInCart(food, "1");
		assertEquals("Should return true",true, result);
		verify(cartRepository,times(1)).findCartItemByUserid(Mockito.anyString());
	}
	
	@Test
	public void testNameExistsInCartWhenNot()
	{
		when(cartRepository.findCartItemByUserid("1")).thenReturn(null);
		boolean result=service.nameExistsInCart(food, "1");
		assertEquals("Should return false",false, result);
		verify(cartRepository,times(1)).findCartItemByUserid(Mockito.anyString());
	}
	
	@Test
	public void testUpdateCart()
	{
		food.setId(1);
		when(foodRepository.findOne(1)).thenReturn(food);
		when(cartRepository.findCartItemByUserid("1")).thenReturn(cart);
		service.updateCart(food,"1");
		verify(foodRepository,times(1)).findOne(Mockito.anyInt());
		verify(cartRepository,times(1)).findCartItemByUserid(Mockito.anyString());
	}
	
	@Test
	public void testDeleteFromCart()
	{
		food.setId(1);
		when(foodRepository.findOne(1)).thenReturn(food);
		when(cartRepository.findCartItemByUserid("1")).thenReturn(cart);
		boolean result=service.deleteFromCart(food, "1");
		assertEquals("Should return true",true, result);
		verify(foodRepository,times(1)).findOne(Mockito.anyInt());
		verify(cartRepository,times(1)).findCartItemByUserid(Mockito.anyString());
	}
	
	@Test
	public void testAddTocartwhenNoCartExistForUser()
	{
		food.setId(1);
		when(foodRepository.findOne(1)).thenReturn(food);
		when(cartRepository.findCartItemByUserid("1")).thenReturn(null);
		service.addToCart(food, "1");
		verify(foodRepository,times(1)).findOne(Mockito.anyInt());
		verify(cartRepository,times(1)).findCartItemByUserid(Mockito.anyString());
	}
	
	@Test
	public void testAddTocartwhenCartExistForUser()
	{
		food.setId(1);
		when(foodRepository.findOne(1)).thenReturn(food);
		when(cartRepository.findCartItemByUserid("1")).thenReturn(cart);
		service.addToCart(food, "1");
		verify(foodRepository,times(1)).findOne(Mockito.anyInt());
		verify(cartRepository,times(1)).findCartItemByUserid(Mockito.anyString());
	}

}
