package com.tmp.entity;

import java.io.Serializable;

public class Project implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int projectId;
	private String projectName;
	private int accountId;
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	

}
