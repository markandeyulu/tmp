package com.tmp.entity;

import java.io.Serializable;

public class UserPagePermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private UserRoleMapping userRoleMappingId;
	private AdminInfoKeyValueMapping page;
	private AdminInfoKeyValueMapping permission;
	private java.util.Date createdOn;
	private User createdBy;
	private java.util.Date updatedOn;
	private User updatedBy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserRoleMapping getUserRoleMappingId() {
		return userRoleMappingId;
	}

	public void setUserRoleMappingId(UserRoleMapping userRoleMappingId) {
		this.userRoleMappingId = userRoleMappingId;
	}

	public AdminInfoKeyValueMapping getPage() {
		return page;
	}

	public void setPage(AdminInfoKeyValueMapping page) {
		this.page = page;
	}

	public AdminInfoKeyValueMapping getPermission() {
		return permission;
	}

	public void setPermission(AdminInfoKeyValueMapping permission) {
		this.permission = permission;
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

}
