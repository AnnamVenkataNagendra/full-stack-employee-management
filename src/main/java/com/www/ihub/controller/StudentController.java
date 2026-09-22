package com.www.ihub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.www.ihub.entity.StudentEntity;
import com.www.ihub.service.StudentService;
import com.www.ihub.token.StudentToken;

@RestController
@RequestMapping("/stu")
public class StudentController 
{
	@Autowired
	private StudentService service;
	
	@Autowired
	private StudentToken studentToken;
	
	@Autowired
	private AuthenticationManager manager;
	
	@PostMapping("/post")
	public ResponseEntity<?> entity(@RequestBody StudentEntity entity){
		
		service.studentDetails(entity);
		
		return ResponseEntity.status(HttpStatus.OK).body("Student data stored");
		
	}
	
	@GetMapping("/fetch")
	public List<StudentEntity> entities()
	{
		return service.entities();
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> responseEntity(@RequestBody StudentEntity entity){
		
		Authentication token=
				new UsernamePasswordAuthenticationToken(entity.getStuName(), entity.getStuPass());
 
       org.springframework.security.core.Authentication authentication=manager.authenticate(token);
		
		if(authentication.isAuthenticated()){
			
			return new ResponseEntity<String>(studentToken.generateToken(entity.getStuName()),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("User Login Falied",HttpStatus.UNAUTHORIZED);
		}
		
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> responseEntity(@PathVariable int id){
		
		boolean delete=service.deleteStudent(id);
		if(delete) {
			
			return ResponseEntity.ok("Student deleted successfully");
					
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stdent id not found");
		}
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> response(@PathVariable int id, @RequestBody StudentEntity entity)
	{
		boolean b=service.updataStudent(id,entity);
		if(b) {
			return ResponseEntity.ok("Student updated successfully");
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stdent was not updated");
		}
		
	}
}
