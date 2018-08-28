package com.tmp.entity;

import java.io.Serializable;

public class AdminInfoValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String value;
	private java.util.Date createdOn;
	private User createdBy;
	private java.util.Date updatedOn;
	private User updatedBy;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public java.util.Date getCreatedon() {
		return createdOn;
	}

	public void setCreatedon(java.util.Date createdOn) {
		this.createdOn = createdOn;
	}

	public User getCreatedby() {
		return createdBy;
	}

	public void setCreatedby(User createdBy) {
		this.createdBy = createdBy;
	}

	public java.util.Date getUpdatedon() {
		return updatedOn;
	}

	public void setUpdatedon(java.util.Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public User getUpdatedby() {
		return updatedBy;
	}

	public void setUpdatedby(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
