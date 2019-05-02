package com.swiggy.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.swiggy.model.Cart;
import com.swiggy.model.Food;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CartRepositoryTest {

	@Autowired
	CartRepository cartRepository;

	 
	private Cart cart;
	
	@Test
	public void testFindCartItemByUserId()
	{
		Map<Food,Integer> map=new HashMap<Food, Integer>();
		cart=new Cart("4",map);
		cartRepository.save(cart);
		Cart searchedCart=cartRepository.findCartItemByUserid(cart.getUserId());
		assertThat(cart.equals(searchedCart));
	}
	
	@Test
	public void testFindCartItemByUserIdWhenNotExist()
	{
		Cart searchedCart=cartRepository.findCartItemByUserid("5");
		assertEquals(null,searchedCart);
	}
	
}
