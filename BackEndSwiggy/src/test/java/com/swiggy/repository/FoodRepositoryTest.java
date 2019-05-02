package com.swiggy.repository;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.swiggy.model.Food;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FoodRepositoryTest {
	
	@Autowired
	private transient FoodRepository foodRepository;

	
	private Food food;
	
	@Test
	public void testFindByName()
	{
		food= new Food("Burgerss", "North Indian", "burger.jpg", 150, 2);
		foodRepository.save(food);
		Food searchedFood=foodRepository.findByName(food.getName());
		assertThat(food.equals(searchedFood));
	}
	
	
	

}
