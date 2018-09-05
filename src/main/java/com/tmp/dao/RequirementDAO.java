package com.tmp.dao;

import java.util.ArrayList;
import java.util.List;


import com.tmp.entity.Requirement;
import com.tmp.entity.RequirementProfileMapping;
import com.tmp.entity.Requirements;

public interface RequirementDAO {

	public Requirements getRequirements(String location, String account, String status);

	public Requirement getRequirement(String requirementId);

	public Requirements createRequirements(List<Requirement> requirements);

	public int createRequirement(Requirement requirement, String userId, String userName);

	public Requirements updateRequirements(List<Requirement> requirements);

	public int updateRequirement(Requirement requirement, String userId);

	public int deleteRequirement(ArrayList<String> requirementId);

	public Requirement getRequirementValues(String userId);
	
	public Requirement getRequirementValuesSubmit(Requirement requirement, String userId);
	
	public ArrayList<Requirement> getRequirementTable(String userId);
	
	public ArrayList<Requirement> getRequirementTable(Requirement requirement, String userId);
	
	public Requirement getRequirementValuesCustomerName(String name);
	
	public ArrayList<Requirement> getRequirementCustomerTable(String name);
	
	public ArrayList<RequirementProfileMapping> getRequirementProfile(String requirementId);
	
	public ArrayList<RequirementProfileMapping> getRequirementProfileName(String requirementId);	
	
	public int updateShortlistedProfile(Requirement requirement, String userId);

	public   ArrayList<Requirement>  getShortlistedProfiles(String requirementId);
}
