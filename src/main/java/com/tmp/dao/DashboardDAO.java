package com.tmp.dao;

import java.util.ArrayList;

import com.tmp.entity.DashboardRequirement;
import com.tmp.entity.LoginRoleAccounts;

public interface DashboardDAO {
	public ArrayList<DashboardRequirement> getRequirementJson(String userId);
	public DashboardRequirement getDashboardRequirement(String userId);
	public ArrayList<DashboardRequirement> getRequirementJson(DashboardRequirement dashboardRequirement, String userId);
	public DashboardRequirement getDashboardRequirement(DashboardRequirement dashboardRequirement, String userId);
	public LoginRoleAccounts getAccountDetails(String userId);
	public LoginRoleAccounts getProjectDetails(String userId);
	
}
