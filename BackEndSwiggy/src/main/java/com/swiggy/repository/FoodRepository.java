package com.swiggy.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.swiggy.model.Food;

@Repository
public interface FoodRepository extends CrudRepository<Food, Integer> {

	@Query("from Food f where f.name=:name")
	Food findByName(@Param("name") String name);

}
