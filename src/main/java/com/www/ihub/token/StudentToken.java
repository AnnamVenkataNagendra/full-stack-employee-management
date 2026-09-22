package com.www.ihub.token;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class StudentToken {

	
	private static final String SECRET =
            "U3VwZXJTZWNyZXRLZXlGb3JKV1RfMTIzNDU2Nzg5MF9TZWN1cmVLZXk=";

    private final SecretKey secretKey =
            Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET));
	
	public String generateToken(String name) {
		
		return Jwts.builder()
				 .setSubject(name)
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
	                .signWith(secretKey, SignatureAlgorithm.HS256)
	                .compact();
	}
	
	public String validateUserToken(String name)
	{
		
		String token=Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(name)
				.getBody()
				.getSubject();
		
		System.out.println(token);

		return token;
		
	}
	
	public Date userLoginData(String name) {
		return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(name)
				.getBody()
				.getExpiration();
	}
	
	public boolean extractUserName(UserDetails userDetails, String name) {
		
		return userDetails.getUsername().equals(validateUserToken(name)) && userLoginData(name).after(new Date());
	}
	
}
