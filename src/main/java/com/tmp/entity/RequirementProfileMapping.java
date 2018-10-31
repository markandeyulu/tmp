package com.tmp.entity;

import java.io.Serializable;

public class RequirementProfileMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Requirement requirementId;
	private Profile profileId;
	private ConfigKeyValueMapping internalEvaluationResult;
	private ConfigKeyValueMapping customerInterviewStatus;
	private String remarks;
	private java.util.Date createdOn;
	private User createdBy;
	private java.util.Date updatedOn;
	private User updatedBy;
    private String internalEvaluationResultUpload;
    private String customerInterviewStatusUpload;
	private String requirementId1;
	private String name;
	private String profileSharedCustomer;
	
	
	public String getProfileSharedCustomer() {
		return profileSharedCustomer;
	}

	public void setProfileSharedCustomer(String profileSharedCustomer) {
		this.profileSharedCustomer = profileSharedCustomer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRequirementId1() {
		return requirementId1;
	}

	public void setRequirementId1(String requirementId1) {
		this.requirementId1 = requirementId1;
	}

	public String getInternalEvaluationResultUpload() {
		return internalEvaluationResultUpload;
	}

	public void setInternalEvaluationResultUpload(
			String internalEvaluationResultUpload) {
		this.internalEvaluationResultUpload = internalEvaluationResultUpload;
	}

	public String getCustomerInterviewStatusUpload() {
		return customerInterviewStatusUpload;
	}

	public void setCustomerInterviewStatusUpload(
			String customerInterviewStatusUpload) {
		this.customerInterviewStatusUpload = customerInterviewStatusUpload;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Requirement getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Requirement requirementId) {
		this.requirementId = requirementId;
	}

	public Profile getProfileId() {
		return profileId;
	}

	public void setProfileId(Profile profileId) {
		this.profileId = profileId;
	}

	public ConfigKeyValueMapping getInternalEvaluationResult() {
		return internalEvaluationResult;
	}

	public void setInternalEvaluationResult(ConfigKeyValueMapping internalEvaluationResult) {
		this.internalEvaluationResult = internalEvaluationResult;
	}

	public ConfigKeyValueMapping getCustomerInterviewStatus() {
		return customerInterviewStatus;
	}

	public void setCustomerInterviewStatus(ConfigKeyValueMapping customerInterviewStatus) {
		this.customerInterviewStatus = customerInterviewStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
