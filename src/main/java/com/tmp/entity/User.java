package com.tmp.entity;

import java.io.Serializable;

import javax.validation.constraints.*;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	@NotNull(message = "Please enter your name.")
	private String name;
	@NotNull
	private String email;
	private int contactNo;
	@NotNull(message = "Please enter your password.")
	@Size(min = 6, max = 8, message = "Your password must between 6 to 8 characters")
	private String password;
	private java.util.Date createdOn;
	private int createdBy;
	private java.util.Date updatedOn;
	private int updatedBy;
	private String displayName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getContactno() {
		return contactNo;
	}

	public void setContactno(int contactNo) {
		this.contactNo = contactNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public java.util.Date getCreatedon() {
		return createdOn;
	}

	public void setCreatedon(java.util.Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getCreatedby() {
		return createdBy;
	}

	public void setCreatedby(int createdBy) {
		this.createdBy = createdBy;
	}

	public java.util.Date getUpdatedon() {
		return updatedOn;
	}

	public void setUpdatedon(java.util.Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public int getUpdatedby() {
		return updatedBy;
	}

	public void setUpdatedby(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	

}
