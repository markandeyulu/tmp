package com.tmp.entity;

import org.springframework.format.annotation.DateTimeFormat;

public class AssociateProject {
	
	private int id;
	private Associate associateId;
	private ProjectAccountMaster projectId;
	private String projectContractType;
	private String projectEndDate;
	private int projectIbuId;
	
	public int getProjectIbuId() {
		return projectIbuId;
	}
	public void setProjectIbuId(int projectIbuId) {
		this.projectIbuId = projectIbuId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Associate getAssociateId() {
		return associateId;
	}
	public void setAssociateId(Associate associateId) {
		this.associateId = associateId;
	}
	public ProjectAccountMaster getProjectId() {
		return projectId;
	}
	public void setProjectId(ProjectAccountMaster projectId) {
		this.projectId = projectId;
	}
	public String getProjectContractType() {
		return projectContractType;
	}
	public void setProjectContractType(String projectContractType) {
		this.projectContractType = projectContractType;
	}
	public String getProjectEndDate() {
		return projectEndDate;
	}
	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}
	
	

}
