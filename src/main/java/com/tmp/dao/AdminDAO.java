package com.tmp.dao;

public interface AdminDAO {

	public int getRequirementAdminMappingValue(String adminRequirementValue, int keyId, String userId);
	
	public int getRequirementAdminProjectMapping(String requirementProjectValue, int keyId, String userId,String requirementAccountValue, int accNewMappingId, int accountId);
	
	public int getRequirementAdminAccountMapping(String requirementAccountValue, int keyId, String userId, int ibg, int ibu);
		
	public int getRequirementMappingValue(String requirementValue, int keyId, String userId);
	
}
