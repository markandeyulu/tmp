package com.tmp.entity;

import java.io.Serializable;

public class AccountMapping implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private AdminInfoKeyValueMapping organization;
	private AdminInfoKeyValueMapping ibg;
	private AdminInfoKeyValueMapping ibu;
	private AdminInfoKeyValueMapping account;
	
	private int accountId;
	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	private java.util.Date createdOn;
	private User createdBy;
	private java.util.Date updatedOn;
	private User updatedBy;

	public AdminInfoKeyValueMapping getOrganization() {
		return organization;
	}

	public void setOrganization(AdminInfoKeyValueMapping organization) {
		this.organization = organization;
	}

	public AdminInfoKeyValueMapping getIbg() {
		return ibg;
	}

	public void setIbg(AdminInfoKeyValueMapping ibg) {
		this.ibg = ibg;
	}

	public AdminInfoKeyValueMapping getIbu() {
		return ibu;
	}

	public void setIbu(AdminInfoKeyValueMapping ibu) {
		this.ibu = ibu;
	}

	public AdminInfoKeyValueMapping getAccount() {
		return account;
	}

	public void setAccount(AdminInfoKeyValueMapping account) {
		this.account = account;
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
