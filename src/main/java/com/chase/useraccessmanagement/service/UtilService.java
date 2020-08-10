package com.chase.useraccessmanagement.service;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class UtilService {

	public static Claims jwtParser(String jwtToken) {
		String[] splitToken = jwtToken.split("\\.");
		String accessTocken = splitToken[0].replace("Bearer ", "");
		Claims claims = Jwts.parser().parseClaimsJwt(accessTocken + "." + splitToken[1] + ".").getBody();
		return claims;
	}
	
}
