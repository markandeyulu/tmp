package com.tmp.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.tmp.util.EnumClasses;

public class Requirement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private EnumClasses.Critical criticality;
	
	private EnumClasses.SkillCategory skillCategory;
	private EnumClasses.PrimarySkill primarySkill;
	private EnumClasses.IntimationMode intimationMode;
	private EnumClasses.RequirementType requirementType;
	private EnumClasses.Location location;
	private EnumClasses.PositionStatus status;
	private EnumClasses.OpportunityStatus oppurtunityStatus;
	
	private String criticality3;
	private String skillCategory3;
	private String primarySkill3;
	private String intimationMode3;
	private String requirementType3;
	private String location3;
	private String status3;
	private String oppurtunityStatus3;
	
	//private ConfigKeyValueMapping criticality;
	//private ConfigKeyValueMapping skillCategory;
	//private ConfigKeyValueMapping primarySkill;
	//private ConfigKeyValueMapping status;
	//private ConfigKeyValueMapping oppurtunityStatus;
	private ConfigKeyValueMapping internalEvaluation;
	private String jobDescription;
	//private ConfigKeyValueMapping criticality;
	//private ConfigKeyValueMapping location;
	private String city;
	private double billingRate;
	@DateTimeFormat(pattern="yyyy-MM-dd") private java.util.Date intimationDate;
	private String intimatedBy;
	@NotNull
	private String intimatorEmail;
	//private ConfigKeyValueMapping intimationMode;
	//private ConfigKeyValueMapping requirementType;
	@DateTimeFormat(pattern="yyyy-MM-dd") private java.util.Date expectedDOJ;
	@DateTimeFormat(pattern="yyyy-MM-dd") private java.util.Date actualClosureDate;
	private int so;
	private int jo;
	private Profile shortlistedProfile_id;
	//private ConfigKeyValueMapping status;
	private String activityOwner;
	private String activityOwnerEmail;
	private String actualOwner;
	private String actualOwnerEmail;
	private String remarks;
	private String customerName;
	private Account account;
	private Project project;
	@DateTimeFormat(pattern="yyyy-MM-dd") private java.util.Date createdOn;
	private User createdBy;
	@DateTimeFormat(pattern="yyyy-MM-dd") private java.util.Date updatedOn;
	private User updatedBy;
	//private ConfigKeyValueMapping oppurtunityStatus;
	private int leadcount;
	private int profilecount;
	private int techevalcount;
	private int custevalcount;
	private int offercount;
	private int boardingcount;
	private int totalcount;
	private String projectAdd;
	private String account1;
	private String location1; 
	
	private String criticality1;
	private String primarySkill1;
	private String location2;
	private String status1;
	private String band;
	private String quantity;
	private String ibg_cdg;
	private String ibu_cdu;
	private String yearExperience;
	private String projectDuration;
	private String pid_crmid_so;
	
	private String skillCategoryAdd1;
	private String skillCategoryAdd2;
	private String skillCategoryAdd3;
	private String skillCategoryAdd4;	
	private String intimationModeAdd;
	private String requirementTypeAdd;
	
	private int profileshared;
	private String shortlistedProfiles;
	private String assignedProfiles;
	//private ConfigKeyValueMapping internalEvaluation;
	
	private int internalEvaluation1;
	
	private String customershortlisted;
	private String oppurtunitystatus;
	private int shortlistProfileId;
	
	
	public int getInternalEvaluation1() {
		return internalEvaluation1;
	}

	public void setInternalEvaluation1(int internalEvaluation1) {
		this.internalEvaluation1 = internalEvaluation1;
	}

	public ConfigKeyValueMapping getInternalEvaluation() {
		return internalEvaluation;
	}

	public void setInternalEvaluation(ConfigKeyValueMapping internalEvaluation) {
		this.internalEvaluation = internalEvaluation;
	}

	public int getShortlistProfileId() {
		return shortlistProfileId;
	}

	public void setShortlistProfileId(int shortlistProfileId) {
		this.shortlistProfileId = shortlistProfileId;
	}

	public String getAssignedProfiles() {
		return assignedProfiles;
	}

	public void setAssignedProfiles(String assignedProfiles) {
		this.assignedProfiles = assignedProfiles;
	}

	public String getShortlistedProfiles() {
		return shortlistedProfiles;
	}

	public void setShortlistedProfiles(String shortlistedProfiles) {
		this.shortlistedProfiles = shortlistedProfiles;
	}

	/*public ConfigKeyValueMapping getOppurtunityStatus() {
		return oppurtunityStatus;
	}

	public void setOppurtunityStatus(ConfigKeyValueMapping oppurtunityStatus) {
		this.oppurtunityStatus = oppurtunityStatus;
	}*/

	public int getProfileshared() {
		return profileshared;
	}

	public void setProfileshared(int profileshared) {
		this.profileshared = profileshared;
	}

	

	public String getCustomershortlisted() {
		return customershortlisted;
	}

	public void setCustomershortlisted(String customershortlisted) {
		this.customershortlisted = customershortlisted;
	}

	public String getOppurtunitystatus() {
		return oppurtunitystatus;
	}

	public void setOppurtunitystatus(String oppurtunitystatus) {
		this.oppurtunitystatus = oppurtunitystatus;
	}

	public void setYearExperience(String yearExperience) {
		this.yearExperience = yearExperience;
	}

	public String getProjectDuration() {
		return projectDuration;
	}

	public void setProjectDuration(String projectDuration) {
		this.projectDuration = projectDuration;
	}

	public String getPid_crmid_so() {
		return pid_crmid_so;
	}

	public void setPid_crmid_so(String pid_crmid_so) {
		this.pid_crmid_so = pid_crmid_so;
	}
	
	public String getCriticality1() {
		return criticality1;
	}

	public void setCriticality1(String criticality1) {
		this.criticality1 = criticality1;
	}

	public String getPrimarySkill1() {
		return primarySkill1;
	}

	public void setPrimarySkill1(String primarySkill1) {
		this.primarySkill1 = primarySkill1;
	}

	public String getLocation2() {
		return location2;
	}

	public void setLocation2(String location2) {
		this.location2 = location2;
	}

	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}

	public int getLeadcount() {
		return leadcount;
	}

	public void setLeadcount(int leadcount) {
		this.leadcount = leadcount;
	}

	public int getProfilecount() {
		return profilecount;
	}

	public void setProfilecount(int profilecount) {
		this.profilecount = profilecount;
	}

	public int getTechevalcount() {
		return techevalcount;
	}

	public void setTechevalcount(int techevalcount) {
		this.techevalcount = techevalcount;
	}

	public int getCustevalcount() {
		return custevalcount;
	}

	public void setCustevalcount(int custevalcount) {
		this.custevalcount = custevalcount;
	}

	public int getOffercount() {
		return offercount;
	}

	public void setOffercount(int offercount) {
		this.offercount = offercount;
	}

	public int getBoardingcount() {
		return boardingcount;
	}

	public void setBoardingcount(int boardingcount) {
		this.boardingcount = boardingcount;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public String getAccount1() {
		return account1;
	}

	public void setAccount1(String account1) {
		this.account1 = account1;
	}

	public String getLocation1() {
		return location1;
	}

	public void setLocation1(String location1) {
		this.location1 = location1;
	}

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
	public EnumClasses.Critical getCriticality() {
		return criticality;
	}

	public void setCriticality(EnumClasses.Critical criticality) {
		this.criticality = criticality;
	}
	
	/*public ConfigKeyValueMapping getCriticality() {
		return criticality;
	}

	public void setCriticality(ConfigKeyValueMapping criticality) {
		this.criticality = criticality;
	}
	 
	public ConfigKeyValueMapping getSkillCategory() {
		return skillCategory;
	}

	public void setSkillCategory(ConfigKeyValueMapping skillCategory) {
		this.skillCategory = skillCategory;
	}

	public ConfigKeyValueMapping getPrimarySkill() {
		return primarySkill;
	}

	public void setPrimarySkill(ConfigKeyValueMapping primarySkill) {
		this.primarySkill = primarySkill;
	}*/

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	/*public ConfigKeyValueMapping getLocation() {
		return location;
	}

	public void setLocation(ConfigKeyValueMapping location) {
		this.location = location;
	}*/

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getBillingRate() {
		return billingRate;
	}

	public void setBillingRate(double billingRate) {
		this.billingRate = billingRate;
	}

	public java.util.Date getIntimationDate() {
		return intimationDate;
	}

	public void setIntimationDate(java.util.Date intimationDate) {
		this.intimationDate = intimationDate;
	}

	public String getIntimatedBy() {
		return intimatedBy;
	}

	public void setIntimatedBy(String intimatedBy) {
		this.intimatedBy = intimatedBy;
	}

	public String getIntimatorEmail() {
		return intimatorEmail;
	}

	public void setIntimatorEmail(String intimatorEmail) {
		this.intimatorEmail = intimatorEmail;
	}

	/*public ConfigKeyValueMapping getIntimationMode() {
		return intimationMode;
	}

	public void setIntimationMode(ConfigKeyValueMapping intimationMode) {
		this.intimationMode = intimationMode;
	}

	public ConfigKeyValueMapping getRequirementType() {
		return requirementType;
	}

	public void setRequirementType(ConfigKeyValueMapping requirementType) {
		this.requirementType = requirementType;
	}
	*/
	public java.util.Date getExpectedDOJ() {
		return expectedDOJ;
	}

	public void setExpectedDOJ(java.util.Date expectedDOJ) {
		this.expectedDOJ = expectedDOJ;
	}

	public java.util.Date getActualClosureDate() {
		return actualClosureDate;
	}

	public void setActualClosureDate(java.util.Date actualClosureDate) {
		this.actualClosureDate = actualClosureDate;
	}

	public int getSo() {
		return so;
	}

	public void setSo(int so) {
		this.so = so;
	}

	public int getJo() {
		return jo;
	}

	public void setJo(int jo) {
		this.jo = jo;
	}

	public Profile getShortlistedProfile_id() {
		return shortlistedProfile_id;
	}

	public void setShortlistedProfile_id(Profile shortlistedProfile_id) {
		this.shortlistedProfile_id = shortlistedProfile_id;
	}

	/*public ConfigKeyValueMapping getStatus() {
		return status;
	}

	public void setStatus(ConfigKeyValueMapping status) {
		this.status = status;
	}*/

	public String getActivityOwner() {
		return activityOwner;
	}

	public void setActivityOwner(String activityOwner) {
		this.activityOwner = activityOwner;
	}

	public String getActivityOwnerEmail() {
		return activityOwnerEmail;
	}

	public void setActivityOwnerEmail(String activityOwnerEmail) {
		this.activityOwnerEmail = activityOwnerEmail;
	}

	public String getActualOwner() {
		return actualOwner;
	}

	public void setActualOwner(String actualOwner) {
		this.actualOwner = actualOwner;
	}

	public String getActualOwnerEmail() {
		return actualOwnerEmail;
	}

	public void setActualOwnerEmail(String actualOwnerEmail) {
		this.actualOwnerEmail = actualOwnerEmail;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getIbg_cdg() {
		return ibg_cdg;
	}

	public void setIbg_cdg(String ibg_cdg) {
		this.ibg_cdg = ibg_cdg;
	}

	public String getIbu_cdu() {
		return ibu_cdu;
	}

	public void setIbu_cdu(String ibu_cdu) {
		this.ibu_cdu = ibu_cdu;
	}

	public String getYearExperience() {
		return yearExperience;
	}

	public String getSkillCategoryAdd1() {
		return skillCategoryAdd1;
	}

	public void setSkillCategoryAdd1(String skillCategoryAdd1) {
		this.skillCategoryAdd1 = skillCategoryAdd1;
	}

	public String getSkillCategoryAdd2() {
		return skillCategoryAdd2;
	}

	public void setSkillCategoryAdd2(String skillCategoryAdd2) {
		this.skillCategoryAdd2 = skillCategoryAdd2;
	}

	public String getSkillCategoryAdd3() {
		return skillCategoryAdd3;
	}

	public void setSkillCategoryAdd3(String skillCategoryAdd3) {
		this.skillCategoryAdd3 = skillCategoryAdd3;
	}

	public String getSkillCategoryAdd4() {
		return skillCategoryAdd4;
	}

	public void setSkillCategoryAdd4(String skillCategoryAdd4) {
		this.skillCategoryAdd4 = skillCategoryAdd4;
	}

	public String getIntimationModeAdd() {
		return intimationModeAdd;
	}

	public void setIntimationModeAdd(String intimationModeAdd) {
		this.intimationModeAdd = intimationModeAdd;
	}

	public String getRequirementTypeAdd() {
		return requirementTypeAdd;
	}

	public void setRequirementTypeAdd(String requirementTypeAdd) {
		this.requirementTypeAdd = requirementTypeAdd;
	}
	public String getProjectAdd() {
		return projectAdd;
	}

	public void setProjectAdd(String projectAdd) {
		this.projectAdd = projectAdd;
	}

	public EnumClasses.SkillCategory getSkillCategory() {
		return skillCategory;
	}

	public void setSkillCategory(EnumClasses.SkillCategory skillCategory) {
		this.skillCategory = skillCategory;
	}

	public EnumClasses.PrimarySkill getPrimarySkill() {
		return primarySkill;
	}

	public void setPrimarySkill(EnumClasses.PrimarySkill primarySkill) {
		this.primarySkill = primarySkill;
	}

	public EnumClasses.IntimationMode getIntimationMode() {
		return intimationMode;
	}

	public void setIntimationMode(EnumClasses.IntimationMode intimationMode) {
		this.intimationMode = intimationMode;
	}

	public EnumClasses.RequirementType getRequirementType() {
		return requirementType;
	}

	public void setRequirementType(EnumClasses.RequirementType requirementType) {
		this.requirementType = requirementType;
	}

	public EnumClasses.Location getLocation() {
		return location;
	}

	public void setLocation(EnumClasses.Location location) {
		this.location = location;
	}

	public String getCriticality3() {
		return criticality3;
	}

	public void setCriticality3(String criticality3) {
		this.criticality3 = criticality3;
	}

	public String getSkillCategory3() {
		return skillCategory3;
	}

	public void setSkillCategory3(String skillCategory3) {
		this.skillCategory3 = skillCategory3;
	}

	public String getPrimarySkill3() {
		return primarySkill3;
	}

	public void setPrimarySkill3(String primarySkill3) {
		this.primarySkill3 = primarySkill3;
	}

	public String getIntimationMode3() {
		return intimationMode3;
	}

	public void setIntimationMode3(String intimationMode3) {
		this.intimationMode3 = intimationMode3;
	}

	public String getRequirementType3() {
		return requirementType3;
	}

	public void setRequirementType3(String requirementType3) {
		this.requirementType3 = requirementType3;
	}

	public String getLocation3() {
		return location3;
	}

	public void setLocation3(String location3) {
		this.location3 = location3;
	}

	public String getStatus3() {
		return status3;
	}

	public void setStatus3(String status3) {
		this.status3 = status3;
	}

	public String getOppurtunityStatus3() {
		return oppurtunityStatus3;
	}

	public void setOppurtunityStatus3(String oppurtunityStatus3) {
		this.oppurtunityStatus3 = oppurtunityStatus3;
	}

	public void setStatus(EnumClasses.PositionStatus status) {
		this.status = status;
	}

	public void setOppurtunityStatus(EnumClasses.OpportunityStatus oppurtunityStatus) {
		this.oppurtunityStatus = oppurtunityStatus;
	}

	public EnumClasses.PositionStatus getStatus() {
		return status;
	}

	public EnumClasses.OpportunityStatus getOppurtunityStatus() {
		return oppurtunityStatus;
	}
	
	
	
	
}
