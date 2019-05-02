package com.swiggy.authentication.service;

import java.util.Map;

import com.swiggy.authentication.model.User;

public interface JwtSecurityTokenGenerator {
	Map<String, String> generateToken(User user);
}
