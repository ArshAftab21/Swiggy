package com.swiggy.controller;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.model.Food;
import com.swiggy.service.SwiggyService;

@RunWith(SpringRunner.class)
@WebMvcTest(SwiggyController.class)
@ContextConfiguration(classes=com.swiggy.TestSwiggyApplication.class)
public class SwiggyControllerTest {
	
	@Autowired
	private transient MockMvc mvc;

	@InjectMocks
	private SwiggyController controller;
	
	@MockBean
	private transient SwiggyService service;
	
	private transient Food food;
	
	static List<Food> foodList;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		mvc=MockMvcBuilders.standaloneSetup(controller).build();
		foodList=new ArrayList<Food>();
		food = new Food("Burger", "North Indian", "burger.jpg", 150, 20);
		foodList.add(food);
		food = new Food("Chicken Biryani", "Mughlai", "biryani.jpg", 200, 25);
		foodList.add(food);
				
	}
	
	@Test
	public void testGetFoodList() throws Exception
	{
		when(service.getFoodList()).thenReturn(foodList);
		mvc.perform(get("/getFoodItems").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		verify(service,times(1)).getFoodList();
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void testGetCartList() throws Exception
	{
		String token="eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTU0NzE5MTQ4fQ.q0XEzlHklFr02jroj5zzHq3uXgP3eU9Hr0NItvs4wkr8RmSPiCnMX-3NIWtKQQw1";
		when(service.getCartList("1")).thenReturn(foodList);
		mvc.perform(get("/getCartItems").header("authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		verify(service,times(1)).getCartList("1");
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void testDeleteFromCart() throws Exception
	{
		String token="eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTU0NzE5MTQ4fQ.q0XEzlHklFr02jroj5zzHq3uXgP3eU9Hr0NItvs4wkr8RmSPiCnMX-3NIWtKQQw1";
		when(service.getTotalCost("1")).thenReturn(120);
		mvc.perform(get("/totalCost").header("authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		verify(service,times(1)).getTotalCost("1");
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void TestDeleteFromCart() throws Exception
	{
		String token="eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTU0NzE5MTQ4fQ.q0XEzlHklFr02jroj5zzHq3uXgP3eU9Hr0NItvs4wkr8RmSPiCnMX-3NIWtKQQw1";
		Food fod = new Food("Burger", "North Indian", "burger.jpg", 150, 2);
		when(service.deleteFromCart(fod, "1")).thenReturn(true);
		mvc.perform(post("/delete").header("authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(jsonToString(food))).andExpect(status().isOk());
		verify(service,times(1)).deleteFromCart(Mockito.any(Food.class),Mockito.anyString());
		verifyNoMoreInteractions(service);	
	}
	
	@Test
	public void testAddToCartWhenItemExists() throws Exception
	{
		String token="eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTU0NzE5MTQ4fQ.q0XEzlHklFr02jroj5zzHq3uXgP3eU9Hr0NItvs4wkr8RmSPiCnMX-3NIWtKQQw1";
		Food fod = new Food("Burger", "North Indian", "burger.jpg", 150, 2);
		when(service.nameExistsInCart(fod,"1")).thenReturn(true);
		mvc.perform(post("/add").header("authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(jsonToString(fod))).andExpect(status().isOk());
	    verify(service,times(1)).nameExistsInCart(Mockito.any(Food.class),Mockito.anyString());
	   	
	}
	

	private static String jsonToString(final Object obj) {
		String result;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent=mapper.writeValueAsString(obj);
			result = jsonContent;
		} catch (JsonProcessingException e) {
			result = "Json processing error";
		}
		return result;

	}
	
	
	

}
