package com.swiggy.authentication.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.swiggy.authentication.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service("generator")
public class JwtSecurityTokenGeneratorImpl implements JwtSecurityTokenGenerator {

	@Override
	public Map<String, String> generateToken(User user) {
		String jwtToken = "";
		jwtToken = Jwts.builder().setSubject(Integer.toString(user.getId())).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS384, "secretkey").compact();
		Map<String, String> map = new HashMap<String, String>();
		map.put("token", jwtToken);
		map.put("message", "User successfully logged in");

		return map;
	}

}
