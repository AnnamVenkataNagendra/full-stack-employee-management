package com.www.ihub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "student")
public class StudentEntity
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int stuId;
	
	private String stuName;
	
	private String stuPass;
	
	private String stuCollege;
	
	private String stuLocation; 
	
	public StudentEntity()
	{}
	public int getStuId() {
		return stuId;
	}
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}

	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}



	public String getStuPass() {
		return stuPass;
	}
	public void setStuPass(String stuPass) {
		this.stuPass = stuPass;
	}
    public String getStuCollege() {
		return stuCollege;
	}
	public void setStuCollege(String stuCollege) {
		this.stuCollege = stuCollege;
	}
	public String getStuLocation() {
		return stuLocation;
	}
	public void setStuLocation(String stuLocation) {
		this.stuLocation = stuLocation;
	}
}
