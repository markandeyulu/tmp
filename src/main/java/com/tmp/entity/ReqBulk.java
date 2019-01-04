package com.tmp.entity;

import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;

public class ReqBulk implements Serializable {
	 
		private String band;
		private String customerName;
		private String projectName;
		private int position;
		@DateTimeFormat(pattern="yyyy-MM-dd") private java.util.Date expectedDOJ;
		
		private int reqType;
		private int so;
		private int jo;
		private String city;
		private int pSkill;
		private int skillCategory;
		private String jobDescription;
		private int ibg;
		private int ibu;
		private int experience;
		private double billingRate;
		private int projectDuration;
		private String pid;
		private int criticality;
		private String intimatedBy;
		private String intimatorEmail;
		private int intimationMode;
		private int opportunityStatus;
		@DateTimeFormat(pattern="yyyy-MM-dd") private java.util.Date plannedClosureDate;
		@DateTimeFormat(pattern="yyyy-MM-dd") private java.util.Date actualClosureDate;
		private String remarks;
		private int positionStatus;
		@DateTimeFormat(pattern="yyyy-MM-dd") private java.util.Date  intiDate;
		private String actualOwner;
		private String activityOwner;
		private int location;
		
		public int getLocation() {
			return location;
		}
		public void setLocation(int location) {
			this.location = location;
		}
		public java.util.Date getPlannedClosureDate() {
			return plannedClosureDate;
		}
		public void setPlannedClosureDate(java.util.Date plannedClosureDate) {
			this.plannedClosureDate = plannedClosureDate;
		}
		public java.util.Date getActualClosureDate() {
			return actualClosureDate;
		}
		public void setActualClosureDate(java.util.Date actualClosureDate) {
			this.actualClosureDate = actualClosureDate;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		public int getPositionStatus() {
			return positionStatus;
		}
		public void setPositionStatus(int positionStatus) {
			this.positionStatus = positionStatus;
		}
		public String getBand() {
			return band;
		}
		public void setBand(String band) {
			this.band = band;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public String getProjectName() {
			return projectName;
		}
		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}
		public int getPosition() {
			return position;
		}
		public void setPosition(int position) {
			this.position = position;
		}
		public java.util.Date getExpectedDOJ() {
			return expectedDOJ;
		}
		public void setExpectedDOJ(java.util.Date expectedDOJ) {
			this.expectedDOJ = expectedDOJ;
		}
		public int getReqType() {
			return reqType;
		}
		public void setReqType(int reqType) {
			this.reqType = reqType;
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
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public int getpSkill() {
			return pSkill;
		}
		public void setpSkill(int pSkill) {
			this.pSkill = pSkill;
		}
		public int getSkillCategory() {
			return skillCategory;
		}
		public void setSkillCategory(int skillCategory) {
			this.skillCategory = skillCategory;
		}
		public String getJobDescription() {
			return jobDescription;
		}
		public void setJobDescription(String jobDescription) {
			this.jobDescription = jobDescription;
		}
		public int getIbg() {
			return ibg;
		}
		public void setIbg(int ibg) {
			this.ibg = ibg;
		}
		public int getIbu() {
			return ibu;
		}
		public void setIbu(int ibu) {
			this.ibu = ibu;
		}
		public int getExperience() {
			return experience;
		}
		public void setExperience(int experience) {
			this.experience = experience;
		}
		public double getBillingRate() {
			return billingRate;
		}
		public void setBillingRate(double billingRate) {
			this.billingRate = billingRate;
		}
		public int getProjectDuration() {
			return projectDuration;
		}
		public void setProjectDuration(int projectDuration) {
			this.projectDuration = projectDuration;
		}
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
		public int getCriticality() {
			return criticality;
		}
		public void setCriticality(int criticality) {
			this.criticality = criticality;
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
		public int getIntimationMode() {
			return intimationMode;
		}
		public void setIntimationMode(int intimationMode) {
			this.intimationMode = intimationMode;
		}
		public java.util.Date getIntiDate() {
			return intiDate;
		}
		public void setIntiDate(java.util.Date intiDate) {
			this.intiDate = intiDate;
		}
		public int getOpportunityStatus() {
			return opportunityStatus;
		}
		public void setOpportunityStatus(int opportunityStatus) {
			this.opportunityStatus = opportunityStatus;
		}
		public String getActualOwner() {
			return actualOwner;
		}
		public void setActualOwner(String actualOwner) {
			this.actualOwner = actualOwner;
		}
		public String getActivityOwner() {
			return activityOwner;
		}
		public void setActivityOwner(String activityOwner) {
			this.activityOwner = activityOwner;
		}
		
		
}



