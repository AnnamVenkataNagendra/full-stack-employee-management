package com.www.ihub.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.www.ihub.service.StudentService;
import com.www.ihub.token.StudentToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter
{

	@Autowired
	private StudentToken studentToken;
	
	@Autowired
	private StudentService service;
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		String authHeader=req.getHeader("Authorization");
		
		if(authHeader !=null && authHeader.startsWith("Bearer ")) {
			
			String tokenValidate=authHeader.substring(7);
			
			String userName=studentToken.validateUserToken(tokenValidate);
			
			if(userName !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				
				UserDetails userDetails=service.loadUserByUsername(userName);
				
				if(studentToken.extractUserName(userDetails, userName)) {
					
					 UsernamePasswordAuthenticationToken auth =
	                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					 
					 SecurityContextHolder.getContext().setAuthentication(auth); 
				}
			}
		
			
		}
		filterChain.doFilter(req, response);
	}
	

}
