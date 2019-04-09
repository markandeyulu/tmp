/**
 * 
 */
package com.tmp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tmp.dao.ProfilesDAO;
import com.tmp.entity.Profile;

/**
 * @author SP00372151
 *
 */
@Service
@Qualifier("profileService")
public class ProfileService extends BaseService {
	

@Autowired(required = true)
@Qualifier("profilesDAO")
ProfilesDAO profilesDAO;


	public int writeDataIntoDB(ArrayList<Profile> profileList, String userId) {
		
		int result = 0;
		int profileListSize = profileList.size();
		int processRecordCount = 0;
		int profileMapingId = 0;
		
		for(Profile  profile : profileList) {
			
			int profileId = profilesDAO.isProfilesExist(profile, userId);
			
			if(profileId == 0){
				int id = profilesDAO.insertProfile(profile,userId);
				    profile.setId(id);
				    
				    System.out.println(" profile.getReqRefNo() *"+profile.getReqRefNo());
				    
					result = profilesDAO.insertProfileMapping(profile,profile.getReqRefNo(), userId);
					processRecordCount = processRecordCount +1;
			}
		
		}
		return processRecordCount - profileListSize;

	}
}
