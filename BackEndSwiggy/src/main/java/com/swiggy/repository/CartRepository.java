package com.swiggy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swiggy.model.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {

	@Query("from Cart c where c.userId=:userId")
	Cart findCartItemByUserid(@Param("userId") String userId);

}
