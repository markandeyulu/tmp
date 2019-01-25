package com.tmp.entity;

import java.sql.Date;

public class Associate {

	private int id = 0;
	private String empId = null;
	private String empName = null;
	private String gender = null;
	private String htrFlag = null;
	private String empIBU = null;
	private String empIBG = null;
	private String band = null;
	private double totalExperience = 0;
	private double techmExperience = 0;
	private String currentCoutnry = null;
	private String currentLocationCity = null;
	private String onsiteOffshore = null;
	private String startDate = null;
	private String endDate = null;
	private String projectId = null;
	private String projectDescription = null;
	private String projectEndDate = null;
	private String projectContractType = null;
	private String projectIBU = null;
	private String billablityStatus = null;
	private String customerId = null;
	private String customerName = null;
	private String supervisorName = null;
	private String projectManagerName = null;
	private String programManagerName = null;
	private String customerGroupName = null;
	private String primarySkill = null;
	private String secondarySkill = null;
	private String certifications = null;
	private String currentProjectRole = null;
	
	private int ibuId;
	private int primarySkillId;
	private int secondarySkillId;
	private int projId;
	private int associateProjectDetails;
	
	
	
	
	
	/**
	 * @return the empId
	 */
	public String getEmpId() {
		return empId;
	}
	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	/**
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the htrFlag
	 */
	public String getHtrFlag() {
		return htrFlag;
	}
	/**
	 * @param htrFlag the htrFlag to set
	 */
	public void setHtrFlag(String htrFlag) {
		this.htrFlag = htrFlag;
	}
	/**
	 * @return the empIBU
	 */
	public String getEmpIBU() {
		return empIBU;
	}
	/**
	 * @param empIBU the empIBU to set
	 */
	public void setEmpIBU(String empIBU) {
		this.empIBU = empIBU;
	}
	/**
	 * @return the empIBG
	 */
	public String getEmpIBG() {
		return empIBG;
	}
	/**
	 * @param empIBG the empIBG to set
	 */
	public void setEmpIBG(String empIBG) {
		this.empIBG = empIBG;
	}
	/**
	 * @return the band
	 */
	public String getBand() {
		return band;
	}
	/**
	 * @param band the band to set
	 */
	public void setBand(String band) {
		this.band = band;
	}
	/**
	 * @return the totalExperience
	 */
	public Double getTotalExperience() {
		return totalExperience;
	}
	/**
	 * @param totalExperience the totalExperience to set
	 */
	public void setTotalExperience(double totalExperience) {
		this.totalExperience = totalExperience;
	}
	/**
	 * @return the techmExperience
	 */
	public double getTechmExperience() {
		return techmExperience;
	}
	/**
	 * @param techmExperience the techmExperience to set
	 */
	public void setTechmExperience(double techmExperience) {
		this.techmExperience = techmExperience;
	}
	/**
	 * @return the currentCoutnry
	 */
	public String getCurrentCoutnry() {
		return currentCoutnry;
	}
	/**
	 * @param currentCoutnry the currentCoutnry to set
	 */
	public void setCurrentCoutnry(String currentCoutnry) {
		this.currentCoutnry = currentCoutnry;
	}
	/**
	 * @return the currentLocationCity
	 */
	public String getCurrentLocationCity() {
		return currentLocationCity;
	}
	/**
	 * @param currentLocationCity the currentLocationCity to set
	 */
	public void setCurrentLocationCity(String currentLocationCity) {
		this.currentLocationCity = currentLocationCity;
	}
	/**
	 * @return the onsiteOffshore
	 */
	public String getOnsiteOffshore() {
		return onsiteOffshore;
	}
	/**
	 * @param onsiteOffshore the onsiteOffshore to set
	 */
	public void setOnsiteOffshore(String onsiteOffshore) {
		this.onsiteOffshore = onsiteOffshore;
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the projectId
	 */
	public String getProjectId() {
		return projectId;
	}
	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * @return the projectDescription
	 */
	public String getProjectDescription() {
		return projectDescription;
	}
	/**
	 * @param projectDescription the projectDescription to set
	 */
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	/**
	 * @return the projectEndDate
	 */
	public String getProjectEndDate() {
		return projectEndDate;
	}
	/**
	 * @param projectEndDate the projectEndDate to set
	 */
	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}
	/**
	 * @return the projectContractType
	 */
	public String getProjectContractType() {
		return projectContractType;
	}
	/**
	 * @param projectContractType the projectContractType to set
	 */
	public void setProjectContractType(String projectContractType) {
		this.projectContractType = projectContractType;
	}
	/**
	 * @return the projectIBU
	 */
	public String getProjectIBU() {
		return projectIBU;
	}
	/**
	 * @param projectIBU the projectIBU to set
	 */
	public void setProjectIBU(String projectIBU) {
		this.projectIBU = projectIBU;
	}
	/**
	 * @return the billablityStatus
	 */
	public String getBillablityStatus() {
		return billablityStatus;
	}
	/**
	 * @param billablityStatus the billablityStatus to set
	 */
	public void setBillablityStatus(String billablityStatus) {
		this.billablityStatus = billablityStatus;
	}
	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return the supervisorName
	 */
	public String getSupervisorName() {
		return supervisorName;
	}
	/**
	 * @param supervisorName the supervisorName to set
	 */
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	/**
	 * @return the projectManagerName
	 */
	public String getProjectManagerName() {
		return projectManagerName;
	}
	/**
	 * @param projectManagerName the projectManagerName to set
	 */
	public void setProjectManagerName(String projectManagerName) {
		this.projectManagerName = projectManagerName;
	}
	/**
	 * @return the programManagerName
	 */
	public String getProgramManagerName() {
		return programManagerName;
	}
	/**
	 * @param programManagerName the programManagerName to set
	 */
	public void setProgramManagerName(String programManagerName) {
		this.programManagerName = programManagerName;
	}
	/**
	 * @return the customerGroupName
	 */
	public String getCustomerGroupName() {
		return customerGroupName;
	}
	/**
	 * @param customerGroupName the customerGroupName to set
	 */
	public void setCustomerGroupName(String customerGroupName) {
		this.customerGroupName = customerGroupName;
	}
	/**
	 * @return the primarySkill
	 */
	public String getPrimarySkill() {
		return primarySkill;
	}
	/**
	 * @param primarySkill the primarySkill to set
	 */
	public void setPrimarySkill(String primarySkill) {
		this.primarySkill = primarySkill;
	}
	/**
	 * @return the secondarySkill
	 */
	public String getSecondarySkill() {
		return secondarySkill;
	}
	/**
	 * @param secondarySkill the secondarySkill to set
	 */
	public void setSecondarySkill(String secondarySkill) {
		this.secondarySkill = secondarySkill;
	}
	/**
	 * @return the certifications
	 */
	public String getCertifications() {
		return certifications;
	}
	/**
	 * @param certifications the certifications to set
	 */
	public void setCertifications(String certifications) {
		this.certifications = certifications;
	}
	/**
	 * @return the currentProjectRole
	 */
	public String getCurrentProjectRole() {
		return currentProjectRole;
	}
	/**
	 * @param currentProjectRole the currentProjectRole to set
	 */
	public void setCurrentProjectRole(String currentProjectRole) {
		this.currentProjectRole = currentProjectRole;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIbuId() {
		return ibuId;
	}
	public void setIbuId(int ibuId) {
		this.ibuId = ibuId;
	}
	public int getPrimarySkillId() {
		return primarySkillId;
	}
	public void setPrimarySkillId(int primarySkillId) {
		this.primarySkillId = primarySkillId;
	}
	public int getSecondarySkillId() {
		return secondarySkillId;
	}
	public void setSecondarySkillId(int secondarySkillId) {
		this.secondarySkillId = secondarySkillId;
	}
	public int getAssociateProjectDetails() {
		return associateProjectDetails;
	}
	public void setAssociateProjectDetails(int associateProjectDetails) {
		this.associateProjectDetails = associateProjectDetails;
	}
	public int getProjId() {
		return projId;
	}
	public void setProjId(int projId) {
		this.projId = projId;
	}

	
}



