package com.tmp.entity;

public class Skill {
	private int id;
	private String primarySkillName;
	private String primarySkillDescription;
	private String secondarySkillName;
	private String secondarySkillDescription;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPrimarySkillName() {
		return primarySkillName;
	}
	public void setPrimarySkillName(String primarySkillName) {
		this.primarySkillName = primarySkillName;
	}
	public String getPrimarySkillDescription() {
		return primarySkillDescription;
	}
	public void setPrimarySkillDescription(String primarySkillDescription) {
		this.primarySkillDescription = primarySkillDescription;
	}
	public String getSecondarySkillName() {
		return secondarySkillName;
	}
	public void setSecondarySkillName(String secondarySkillName) {
		this.secondarySkillName = secondarySkillName;
	}
	public String getSecondarySkillDescription() {
		return secondarySkillDescription;
	}
	public void setSecondarySkillDescription(String secondarySkillDescription) {
		this.secondarySkillDescription = secondarySkillDescription;
	}
	

}
