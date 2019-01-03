package com.tmp.dao;

import java.util.List;


import com.tmp.entity.AccountMapping;
import com.tmp.entity.AdminInfoKeyValueMapping;
import com.tmp.entity.ConfigKeyValueMapping;
import com.tmp.entity.Profile;
import com.tmp.entity.ProjectMapping;
import com.tmp.entity.User;

public interface ConfigDAO {

	public ConfigKeyValueMapping getConfigKeyValueMapping(int id);
	
	public ConfigKeyValueMapping getConfigKeyValueMapping(String id);

	public AdminInfoKeyValueMapping getAdminInfoKeyValueMapping(int id);

	public AdminInfoKeyValueMapping getAdminInfoKeyValueMapping(String id);
	
	public List<ConfigKeyValueMapping> getConfigKeyValues(int id);
	
	public int getConfigKeyValues(int id, String values);

	public List<AdminInfoKeyValueMapping> getAdminInfoKeyValues(int id);

	public List<AccountMapping> getAccounts(String ibu);

	public AccountMapping getAccountMapping(int accountId);
	
	public AccountMapping getAccountMappingId(int accountId);

	public List<ProjectMapping> getProjects(String accountId);

	public ProjectMapping getProjectMapping(int projectId);
	
	public ProjectMapping getProjectMappingId(int accountId, int projectId);

	public User getUserId(int userId);
	
	public Profile getProfileName(int profileId);
	
}
