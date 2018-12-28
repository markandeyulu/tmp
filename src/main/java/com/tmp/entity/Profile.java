package com.tmp.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Profile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String reqRefNo;
	private String name;
	private String email;
	private String contactNo;
	private String currentCompany;
	private String location;
	private ConfigKeyValueMapping primarySkill;
	@DateTimeFormat(pattern="yyyy-MM-dd") private Date profileSharedDate;
	private String profileSharedDatestr;
	
	public String getProfileSharedDatestr() {
		return profileSharedDatestr;
	}

	public void setProfileSharedDatestr(String profileSharedDatestr) {
		this.profileSharedDatestr = profileSharedDatestr;
	}

	private String profileSharedBy;
	private int yearsOfExperience;
	private int relevantExperience;
	private int noticePeriod;
	private int currentCTC;
	private int expectedCTC;
	private ConfigKeyValueMapping isAllocated;
	private String isAllocated1;
	private Account account2;
	private Project project2;
	
	public Account getAccount2() {
		return account2;
	}

	public void setAccount2(Account account2) {
		this.account2 = account2;
	}

	public Project getProject2() {
		return project2;
	}

	public void setProject2(Project project2) {
		this.project2 = project2;
	}

	public String getIsAllocated1() {
		return isAllocated1;
	}

	public void setIsAllocated1(String isAllocated1) {
		this.isAllocated1 = isAllocated1;
	}

	public String getReqRefNo() {
		return reqRefNo;
	}

	public void setReqRefNo(String reqRefNo) {
		this.reqRefNo = reqRefNo;
	}

	private AccountMapping account;
	private ProjectMapping project;
	@DateTimeFormat(pattern="yyyy-MM-dd") private Date allocationStartDate;
	@DateTimeFormat(pattern="yyyy-MM-dd") private Date allocationEndDate;
	@DateTimeFormat(pattern="yyyy-MM-dd") private Date createdOn;
	private User createdBy;
	@DateTimeFormat(pattern="yyyy-MM-dd") private Date updatedOn;
	private User updatedBy;
	private ConfigKeyValueMapping profileSource;
	@DateTimeFormat(pattern="yyyy-MM-dd") private Date internalEvaluationResultDate; 
	private ConfigKeyValueMapping  initialEvaluationResult; 
	private String profileSharedCustomer; 
	@DateTimeFormat(pattern="yyyy-MM-dd") private Date profileSharedCustomerDate; 
	private ConfigKeyValueMapping  customerInterviewStatus; 
	private String remarks;
	private String primarySkillAdd;
	private String profileSourceAdd;
	private String initialEvaluationResultAdd;
	private String customerInterviewStatusAdd;
	private String Account1;
	private String Project1;
	
	public String getAccount1() {
		return Account1;
	}

	public void setAccount1(String account1) {
		Account1 = account1;
	}

	public String getProject1() {
		return Project1;
	}

	public void setProject1(String project1) {
		Project1 = project1;
	}

	public String getPrimarySkillAdd() {
		return primarySkillAdd;
	}

	public void setPrimarySkillAdd(String primarySkillAdd) {
		this.primarySkillAdd = primarySkillAdd;
	}

	public String getProfileSourceAdd() {
		return profileSourceAdd;
	}

	public void setProfileSourceAdd(String profileSourceAdd) {
		this.profileSourceAdd = profileSourceAdd;
	}

	public ConfigKeyValueMapping getProfileSource() {
		return profileSource;
	}

	public void setProfileSource(ConfigKeyValueMapping profileSource) {
		this.profileSource = profileSource;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getCurrentCompany() {
		return currentCompany;
	}

	public void setCurrentCompany(String currentCompany) {
		this.currentCompany = currentCompany;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public ConfigKeyValueMapping getPrimarySkill() {
		return primarySkill;
	}

	public void setPrimarySkill(ConfigKeyValueMapping primarySkill) {
		this.primarySkill = primarySkill;
	}

	public Date getProfileSharedDate() {
		return profileSharedDate;
	}

	public void setProfileSharedDate(Date profileSharedDate) {
		this.profileSharedDate = profileSharedDate;
	}

	public String getProfileSharedBy() {
		return profileSharedBy;
	}

	public void setProfileSharedBy(String profileSharedBy) {
		this.profileSharedBy = profileSharedBy;
	}

	public int getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public int getRelevantExperience() {
		return relevantExperience;
	}

	public void setRelevantExperience(int relevantExperience) {
		this.relevantExperience = relevantExperience;
	}

	public int getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(int noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public int getCurrentCTC() {
		return currentCTC;
	}

	public void setCurrentCTC(int currentCTC) {
		this.currentCTC = currentCTC;
	}

	public int getExpectedCTC() {
		return expectedCTC;
	}

	public void setExpectedCTC(int expectedCTC) {
		this.expectedCTC = expectedCTC;
	}

	public ConfigKeyValueMapping getIsAllocated() {
		return isAllocated;
	}

	public void setIsAllocated(ConfigKeyValueMapping isAllocated) {
		this.isAllocated = isAllocated;
	}

	public java.util.Date getAllocationStartDate() {
		return allocationStartDate;
	}

	public void setAllocationStartDate(java.util.Date allocationStartDate) {
		this.allocationStartDate = allocationStartDate;
	}

	public java.util.Date getAllocationEndDate() {
		return allocationEndDate;
	}

	public void setAllocationEndDate(java.util.Date allocationEndDate) {
		this.allocationEndDate = allocationEndDate;
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

	public Date getInternalEvaluationResultDate() {
		return internalEvaluationResultDate;
	}

	public void setInternalEvaluationResultDate(Date internalEvaluationResultDate) {
		this.internalEvaluationResultDate = internalEvaluationResultDate;
	}

	public String getProfileSharedCustomer() {
		return profileSharedCustomer;
	}

	public void setProfileSharedCustomer(String profileSharedCustomer) {
		this.profileSharedCustomer = profileSharedCustomer;
	}

	public Date getProfileSharedCustomerDate() {
		return profileSharedCustomerDate;
	}

	public void setProfileSharedCustomerDate(Date profileSharedCustomerDate) {
		this.profileSharedCustomerDate = profileSharedCustomerDate;
	}

	

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public AccountMapping getAccount() {
		return account;
	}

	public void setAccount(AccountMapping account) {
		this.account = account;
	}

	public ProjectMapping getProject() {
		return project;
	}

	public void setProject(ProjectMapping project) {
		this.project = project;
	}

	public ConfigKeyValueMapping getInitialEvaluationResult() {
		return initialEvaluationResult;
	}

	public void setInitialEvaluationResult(
			ConfigKeyValueMapping initialEvaluationResult) {
		this.initialEvaluationResult = initialEvaluationResult;
	}

	public ConfigKeyValueMapping getCustomerInterviewStatus() {
		return customerInterviewStatus;
	}

	public void setCustomerInterviewStatus(
			ConfigKeyValueMapping customerInterviewStatus) {
		this.customerInterviewStatus = customerInterviewStatus;
	}

	public String getInitialEvaluationResultAdd() {
		return initialEvaluationResultAdd;
	}

	public void setInitialEvaluationResultAdd(String initialEvaluationResultAdd) {
		this.initialEvaluationResultAdd = initialEvaluationResultAdd;
	}

	public String getCustomerInterviewStatusAdd() {
		return customerInterviewStatusAdd;
	}

	public void setCustomerInterviewStatusAdd(String customerInterviewStatusAdd) {
		this.customerInterviewStatusAdd = customerInterviewStatusAdd;
	}
	
	

}
