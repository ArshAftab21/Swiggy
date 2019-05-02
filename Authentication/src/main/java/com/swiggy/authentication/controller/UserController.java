package com.swiggy.authentication.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiggy.authentication.model.User;
import com.swiggy.authentication.service.JwtSecurityTokenGenerator;
import com.swiggy.authentication.service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	JwtSecurityTokenGenerator generator;
	
	private static Logger log=LogManager.getLogger(UserController.class);


	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		try{
			userService.saveUser(user);
			log.debug("User registered");
			String response="User registered successfully";
			return new ResponseEntity<String>(response, HttpStatus.CREATED);
		} 
		catch(Exception e) {
			log.debug("User Not registered");
			String response=e.getMessage();
			return new ResponseEntity<String>(response, HttpStatus.CONFLICT);
		}

	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User loginDetail) {
		try {
		User user = userService.findByUserIdAndPassword(loginDetail.getUserName(), loginDetail.getPassword());		
		Map<String, String> map = generator.generateToken(user);
		log.debug("Token generated");
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		}
		
		catch(Exception e)
		{
			Map<String, String> map =new HashMap<String, String>();
			log.debug("Invalid credentials");
			map.put("message",e.getMessage());
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		}
		

	}

	@PostMapping("/checkUser")
	public ResponseEntity<String> checkUser(@RequestBody User user)
	{
		boolean result=userService.checkUsernameAvaibility(user);
		if(!result)
		{
			log.debug("user already exists");
			String response="User already exists";
			return new ResponseEntity<String>(response, HttpStatus.OK);
		}
		else
		{
			log.debug("Is a New User");
			String response="New User";
			return new ResponseEntity<String>(response, HttpStatus.OK);
		}
	}
}
