package com.tmp.dao;

import java.util.ArrayList;
import java.util.List;

import com.tmp.entity.Account;
import com.tmp.entity.DashboardRequirement;
import com.tmp.entity.Project;

public interface DashboardDAO {
	public ArrayList<DashboardRequirement> getRequirementJson(String userId);
	public DashboardRequirement getDashboardRequirement(String userId);
	public ArrayList<DashboardRequirement> getRequirementJson(DashboardRequirement dashboardRequirement, String userId);
	public DashboardRequirement getDashboardRequirement(DashboardRequirement dashboardRequirement, String userId);
	public List<Account> getAccountDetails(String userId);
	public List<Project> getProjectDetails(String userId);
	
}
