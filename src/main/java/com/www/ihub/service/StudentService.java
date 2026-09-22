package com.www.ihub.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.www.ihub.entity.StudentEntity;
import com.www.ihub.repo.StudentRepo;

@Service
public class StudentService implements UserDetailsService 

{

	
	@Autowired
	private StudentRepo repo;
	
	@Autowired
	 private BCryptPasswordEncoder encoder;
	
	
	public void studentDetails(StudentEntity entity) {
		
		String name=encoder.encode(entity.getStuPass());
		entity.setStuPass(name);
		repo.save(entity);
	}
	
	public List<StudentEntity> entities()
	{
		List<StudentEntity> list=repo.findAll();
		
		if(list.isEmpty()) {
			
			new Exception("Data is empty");
		}

		return list;
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		StudentEntity entity=repo.findByStuName(username);
		
		if(entity == null) {
			new Exception("Invalied User name");
		}
		
		return new User(entity.getStuName(),entity.getStuPass(),
				List.of(new SimpleGrantedAuthority("ROLE_USER")));
	}
	
	public boolean deleteStudent(int id) {
	
		if(!repo.existsById(id)) {
			return false;
		}
		  repo.deleteById(id);
	        return true;
	}

	public boolean updataStudent(int id ,StudentEntity entity) {
		Optional<StudentEntity> op =repo.findById(id);
		
		if(op.isPresent()) {
			StudentEntity st=op.get();
			st.setStuName(entity.getStuName());
			st.setStuCollege(entity.getStuCollege());
			st.setStuLocation(entity.getStuLocation());
		
			repo.save(st);
			return true;
		}
		return false;
	}
}