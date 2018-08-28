package com.tmp.entity;

import java.io.Serializable;

public class AdminInfoKeyValueMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private AdminInfoKey adminInfoKey;
	private AdminInfoValue adminInfoValue;
	private java.util.Date createdOn;
	private User createdBy;
	private java.util.Date updatedOn;
	private User updatedBy;

	public AdminInfoKey getAdminInfoKey() {
		return adminInfoKey;
	}

	public void setAdminInfoKey(AdminInfoKey adminInfoKey) {
		this.adminInfoKey = adminInfoKey;
	}

	public AdminInfoValue getAdminInfoValue() {
		return adminInfoValue;
	}

	public void setAdminInfoValue(AdminInfoValue adminInfoValue) {
		this.adminInfoValue = adminInfoValue;
	}

	public java.util.Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(java.util.Date createdOn) {
		this.createdOn = createdOn;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public java.util.Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(java.util.Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
