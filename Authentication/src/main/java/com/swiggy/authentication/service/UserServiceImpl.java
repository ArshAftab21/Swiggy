package com.swiggy.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.swiggy.authentication.exception.UserAlreadyExistsException;
import com.swiggy.authentication.exception.UserNotFoundException;
import com.swiggy.authentication.model.User;
import com.swiggy.authentication.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder encryptor;

	public boolean saveUser(User user) throws UserAlreadyExistsException {
		boolean result=false;		
			User existingUser = userRepository.findbyUsername(user.getUserName());	
			if(!(existingUser==null))
			{
				throw new UserAlreadyExistsException("User with username already exists");
			}
			else
			{
				user.setPassword(encryptor.encode(user.getPassword()));
				userRepository.save(user);
				result=true;
			}
		
		return result;
	}

	public User findByUserIdAndPassword(String userName, String password) throws UserNotFoundException
	{
		User user = userRepository.findbyUsername(userName);
		if(!(user==null) && encryptor.matches(password, user.getPassword()))
		{
			return user;
		}
		
		else
		{
			throw new UserNotFoundException("UserName and Password Mismatch");
		}
	

	}

	@Override
	public boolean checkUsernameAvaibility(User user) {
		boolean result=false;
		User existingUser = userRepository.findbyUsername(user.getUserName().trim());	
		if(existingUser==null)
		{
			result=true;
		}
		return result;
	}

}
