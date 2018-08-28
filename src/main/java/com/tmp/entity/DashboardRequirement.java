package com.tmp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author SS00500641
 *
 */
public class DashboardRequirement implements Serializable {

	private static final long serialVersionUID = 1L;
	private String CustomerName;
	private int LeadGeneration;
	private int ProfileSourcing;
	private int InternalEvaluation;
	private int CustomerEvaluation;
	private int OfferProcessing;
	private int Onboarded;
	private int PositionsLost;
	private int TotalPositions;
	private int TotalActive;

	private int leadcount;
	private int profilecount;
	private int techevalcount;
	private int custevalcount;
	private int offercount;
	private int boardingcount;
	private int totalcount;

	private String account;
	private String location; 

	private AdminInfoKeyValueMapping userAccountName;
	private AccountMapping userAccount;

	private AdminInfoKeyValueMapping userProjectName;
	private ProjectMapping userProject;
	
	public AdminInfoKeyValueMapping getUserProjectName() {
		return userProjectName;
	}
	public void setUserProjectName(AdminInfoKeyValueMapping userProjectName) {
		this.userProjectName = userProjectName;
	}
	public ProjectMapping getUserProject() {
		return userProject;
	}
	public void setUserProject(ProjectMapping userProject) {
		this.userProject = userProject;
	}
	
	public AdminInfoKeyValueMapping getUserAccountName() {
		return userAccountName;
	}
	public void setUserAccountName(AdminInfoKeyValueMapping userAccountName) {
		this.userAccountName = userAccountName;
	}
	public AccountMapping getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(AccountMapping userAccount) {
		this.userAccount = userAccount;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
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
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	public int getLeadGeneration() {
		return LeadGeneration;
	}
	public void setLeadGeneration(int leadGeneration) {
		LeadGeneration = leadGeneration;
	}
	public int getProfileSourcing() {
		return ProfileSourcing;
	}
	public void setProfileSourcing(int profileSourcing) {
		ProfileSourcing = profileSourcing;
	}
	public int getInternalEvaluation() {
		return InternalEvaluation;
	}
	public void setInternalEvaluation(int internalEvaluation) {
		InternalEvaluation = internalEvaluation;
	}
	public int getCustomerEvaluation() {
		return CustomerEvaluation;
	}
	public void setCustomerEvaluation(int customerEvaluation) {
		CustomerEvaluation = customerEvaluation;
	}
	public int getOfferProcessing() {
		return OfferProcessing;
	}
	public void setOfferProcessing(int offerProcessing) {
		OfferProcessing = offerProcessing;
	}
	public int getOnboarded() {
		return Onboarded;
	}
	public void setOnboarded(int onboarded) {
		Onboarded = onboarded;
	}
	public int getPositionsLost() {
		return PositionsLost;
	}
	public void setPositionsLost(int positionsLost) {
		PositionsLost = positionsLost;
	}
	public int getTotalPositions() {
		return TotalPositions;
	}
	public void setTotalPositions(int totalPositions) {
		TotalPositions = totalPositions;
	}
	public int getTotalActive() {
		return TotalActive;
	}
	public void setTotalActive(int totalActive) {
		TotalActive = totalActive;
	}




}



