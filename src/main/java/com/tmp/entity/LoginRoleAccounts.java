package com.tmp.entity;

import java.io.Serializable;
import java.util.List;

public class LoginRoleAccounts implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private List<DashboardRequirement> listAccount;
	private List<DashboardRequirement> listProject;

	public List<DashboardRequirement> getListAccount() {
		return listAccount;
	}
	public void setListAccount(List<DashboardRequirement> listAccount) {
		this.listAccount = listAccount;
	}
	public List<DashboardRequirement> getListProject() {
		return listProject;
	}
	public void setListProject(List<DashboardRequirement> listProject) {
		this.listProject = listProject;
	}
		
}
