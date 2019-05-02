package com.swiggy.authentication.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swiggy.authentication.model.User;

@Repository
public interface UserRepository extends CrudRepository<User,String> {

	@Query("from User u where u.userName=:userName")
	User findbyUsername(@Param("userName")String userName);

}
