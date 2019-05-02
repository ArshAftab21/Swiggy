package com.swiggy.authentication.service;

import com.swiggy.authentication.exception.UserAlreadyExistsException;
import com.swiggy.authentication.exception.UserNotFoundException;
import com.swiggy.authentication.model.User;

public interface UserService {
	boolean saveUser(User user) throws UserAlreadyExistsException;

	User findByUserIdAndPassword(String userName, String password) throws UserNotFoundException;

	boolean checkUsernameAvaibility(User user);

}
