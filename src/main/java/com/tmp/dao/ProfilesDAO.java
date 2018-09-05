package com.tmp.dao;

import java.util.ArrayList;
import java.util.List;

import com.tmp.entity.Profile;
import com.tmp.entity.Profiles;

public interface ProfilesDAO {

	public Profiles getProfiles();
	
	public Profiles getRequirementRefNum(String userId);
	
	public Profile getProfile(String profileId);

	public Profiles createProfiles(List<Profile> profiles);

	public int createProfile(Profile profile, String strUserId);

	public Profiles updateProfiles(List<Profile> profiles);

	public int updateProfile(Profile profile,String userId);

	public int deleteProfile(ArrayList<String>  profileId);
	
	public int insertProfile(Profile profile, String userId);
	
	public int insertProfileMapping(Profile profile, String refNo, String userId);
	
	public int isProfilesExist(Profile profile, String userId);
	
	public int isProfileMapingExist(int profileId, String refNo);

	public int getRefId(String refId);
}
