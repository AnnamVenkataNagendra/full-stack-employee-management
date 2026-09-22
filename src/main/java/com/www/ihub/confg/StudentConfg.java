package com.www.ihub.confg;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.www.ihub.service.StudentService;

@Configuration
public class StudentConfg 
{
	
	@Autowired
	private StudentService service;
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain chain(HttpSecurity security) throws Exception {

	    security
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/stu/login","/stu/post").permitAll()
	            .anyRequest().authenticated());

	    return security.build();
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		
		provider.setPasswordEncoder(encoder());
		provider.setUserDetailsService(service);
		
		return provider;
		
	}
	
	@Bean
	public AuthenticationManager authentication(AuthenticationConfiguration auth) throws Exception {
		
		return auth.getAuthenticationManager();
	}
	
}
