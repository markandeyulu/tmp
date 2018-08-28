package com.tmp.util;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmp.dao.AdminDAO;
import com.tmp.dao.ConfigDAO;
import com.tmp.dao.DashboardDAO;
import com.tmp.dao.LoginDAO;
import com.tmp.dao.ProfilesDAO;
import com.tmp.dao.ReportDAO;
import com.tmp.dao.RequirementDAO;
import com.tmp.entity.AdminInfoKeyValueMapping;
import com.tmp.entity.ConfigKeyValueMapping;
import com.tmp.entity.DashboardRequirement;
import com.tmp.entity.LoginRoleAccounts;
import com.tmp.entity.Profile;
import com.tmp.entity.Profiles;
import com.tmp.entity.Report;
import com.tmp.entity.Requirement;
import com.tmp.entity.RequirementProfileMapping;
import com.tmp.entity.Requirements;
import com.tmp.entity.User;
import com.tmp.entity.UserRoleMapping;

@Service
@Qualifier("tmpUtil")
public class TMPUtil {

	@Autowired(required = true)
	@Qualifier("reportDAO")
	ReportDAO reportDAO;

	@Autowired(required = true)
	@Qualifier("profilesDAO")
	ProfilesDAO profilesDAO;

	@Autowired(required = true)
	@Qualifier("requirementDAO")
	RequirementDAO requirementDAO;

	@Autowired(required = true)
	@Qualifier("configDAO")
	ConfigDAO configDAO;
	
	@Autowired(required = true)
	@Qualifier("adminDAO")
	AdminDAO adminDAO;

	public AdminDAO getAdminDAO() {
		return adminDAO;
	}

	public void setAdminDAO(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}

	@Autowired(required = true)
	@Qualifier("dashboardDAO")
	DashboardDAO dashboardDAO;

	@Autowired(required = true)
	@Qualifier("loginDAO")
	LoginDAO loginDAO;
	
	public String getRequirementJson(String userId) {
		ArrayList<DashboardRequirement> dashboardRequirementList = dashboardDAO.getRequirementJson(userId);
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("dashboardRequirementsJson", dashboardRequirementList);
			String jsonStr = jsonObj.toString();			
			//system.out.println("jsonStr : "+jsonStr);
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getDashboardJson(String userId) {
		DashboardRequirement dashboardRequirement = dashboardDAO.getDashboardRequirement(userId);
		ObjectMapper om=new ObjectMapper();
		try {
			String jsonStr = om.writeValueAsString(dashboardRequirement);			
			//system.out.println("jsonStr : "+jsonStr);
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getAccountDetails(String userId) {
		LoginRoleAccounts userAccounts = dashboardDAO.getAccountDetails(userId);
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("accountsJson", userAccounts.getListAccount());
			String jsonAccounts = jsonObj.toString();
			//system.out.println("jsonAccounts : "+jsonAccounts);
			return jsonAccounts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getProjectDetails(String userId) {
		LoginRoleAccounts userProjects = dashboardDAO.getProjectDetails(userId);
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("projectsJson", userProjects.getListProject());
			String jsonProjects = jsonObj.toString();
			//system.out.println("jsonProjects : "+jsonProjects);
			return jsonProjects;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getRequirementJson(DashboardRequirement dashboardRequirement, String userId) {
		ArrayList<DashboardRequirement> dashboardRequirementList = dashboardDAO.getRequirementJson(dashboardRequirement,userId);
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("dashboardRequirementsJson", dashboardRequirementList);
			String jsonStr = jsonObj.toString();
			//system.out.println("jsonStr : "+jsonStr);
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getDashboardJson(DashboardRequirement dashboardRequirement, String userId) {
		DashboardRequirement dashboardRequirementBlock = dashboardDAO.getDashboardRequirement(dashboardRequirement, userId);
		ObjectMapper om=new ObjectMapper();
		try {
			String jsonStr = om.writeValueAsString(dashboardRequirementBlock);			
			//system.out.println("jsonStr : "+jsonStr);
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String getRequirementTableJson(String userId) {
		ArrayList<Requirement> requirementList = requirementDAO.getRequirementTable(userId);
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("requirementtableJson", requirementList);
			String jsonStr = jsonObj.toString();			
			//system.out.println("jsonStr : "+jsonStr);
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getRequirementTableSubmitJson(Requirement requirement, String userId) {
		ArrayList<Requirement> requirementList = requirementDAO.getRequirementTable(requirement, userId);
		try {
			JSONObject jsonObj = new JSONObject();
            jsonObj.put("requirementtableJson", requirementList);
			String jsonStr = jsonObj.toString();			
			//system.out.println("jsonStr : "+jsonStr);
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getRequirementTableCustomernameJson(String name) {
		ArrayList<Requirement> requirementList = requirementDAO.getRequirementCustomerTable(name);
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("requirementtableJson", requirementList);
			String jsonStr = jsonObj.toString();			
			//system.out.println("jsonStr : "+jsonStr);
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getRequirementBlockJson(String userId) {
		Requirement requirement = requirementDAO.getRequirementValues(userId);
		ObjectMapper om=new ObjectMapper();
		try {
			String jsonStr = om.writeValueAsString(requirement);
			//system.out.println("jsonStr : "+jsonStr);
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getRequirementSubmitJson(Requirement requirement, String userId) {
		Requirement requirementsubmit = requirementDAO.getRequirementValuesSubmit(requirement, userId);
		ObjectMapper om=new ObjectMapper();
		try {
			
			String jsonStr = om.writeValueAsString(requirementsubmit);
			
			//system.out.println("jsonStr : "+jsonStr);
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getRequirementBlockJsonCustomername(String name) {
		Requirement requirement = requirementDAO.getRequirementValuesCustomerName(name);
		ObjectMapper om=new ObjectMapper();
		try {
			
			String jsonStr = om.writeValueAsString(requirement);
			
			//system.out.println("jsonStr : "+jsonStr);
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static ArrayList<String> extractIntegersFromString( final String source ) { 

		String[] integersAsText = source.split(",");

		ArrayList<String> results = new ArrayList<String>();

		int i = 0; 

		for ( String textValue : integersAsText ) {
			results.add( textValue ); 
			i++; 
		} 

		return results ; 
	} 

	public LoginDAO getLoginDAO() {
		return loginDAO;
	}

	public void setLoginDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

	public ProfilesDAO getProfilesDAO() {
		return profilesDAO;
	}

	public void setProfilesDAO(ProfilesDAO profilesDAO) {
		this.profilesDAO = profilesDAO;
	}

	public RequirementDAO getRequirementDAO() {
		return requirementDAO;
	}

	public void setRequirementDAO(RequirementDAO requirementDAO) {
		this.requirementDAO = requirementDAO;
	}

	public ConfigDAO getConfigDAO() {
		return configDAO;
	}

	public void setConfigDAO(ConfigDAO configDAO) {
		this.configDAO = configDAO;
	}

	public User getLoginDetails(String name, String password) {
		return loginDAO.getAuthenticateUser(name,  password);
	}
	
	public UserRoleMapping getUserRole(int userId) {
		return loginDAO.getUserRole(userId);
	}

	public Requirements getRequirements(String location, String account, String status) {
		return requirementDAO.getRequirements(location, account, status);
	}

	public Requirement getRequirement(String requirementId) {
		return requirementDAO.getRequirement(requirementId);
	}

	public ConfigKeyValueMapping getConfigKeyValue(int configKeyValueMappingId) {
		return configDAO.getConfigKeyValueMapping(configKeyValueMappingId);
	}

	public AdminInfoKeyValueMapping getAdminInfoKeyValue(int adminInfoKeyValueMappingId) {
		return configDAO.getAdminInfoKeyValueMapping(adminInfoKeyValueMappingId);
	}

	public String getConfigKeyValues(int configKeyId) {
		List<ConfigKeyValueMapping> list= configDAO.getConfigKeyValues(configKeyId);
	
		try {
			JSONObject jsonObj = new JSONObject();			
			jsonObj.put("locationJson", list);
			String jsonStr = jsonObj.toString();
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<AdminInfoKeyValueMapping> getAdminInfoKeyValues(int adminInfoKeyId) {
		return configDAO.getAdminInfoKeyValues(adminInfoKeyId);
	}

	public int createRequirement(Requirement requirement, String userId) {
		return requirementDAO.createRequirement(requirement, userId);
	}

	public int updateRequirement(Requirement requirement, String userId) {
		return requirementDAO.updateRequirement(requirement,userId);
	}

	public int deleteRequirement(String requirementId) {
		return requirementDAO.deleteRequirement(requirementId);
	}
	
	public String getProfiles() {
		Profiles profilesList = profilesDAO.getProfiles();
		try {
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("profilesJson", profilesList.getProfiles());
			String jsonStr = jsonObj.toString();
			////system.out.println("jsonStr : "+jsonStr);
			
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getRequirementRefNum(String userId) {
			
		Profiles profilesList = profilesDAO.getRequirementRefNum(userId);
			try {
				JSONObject requirementRefNoObj = new JSONObject();
				requirementRefNoObj.put("requirementRefNoJson", profilesList.getProfiles());
				String requirementRefNoObjStr = requirementRefNoObj.toString();
				//system.out.println("requirementRefNoObjStr : "+requirementRefNoObjStr);
				return requirementRefNoObjStr;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	
	public Profile getProfile(String profileId) {
		return profilesDAO.getProfile(profileId);
	}

	public int createProfile(Profile profile, String strUserId) {
		return profilesDAO.createProfile(profile, strUserId);
	}

	public int updateProfile(Profile profile,String userId) {
		return profilesDAO.updateProfile(profile,userId);
	}

	public int deleteProfile(String profileId) {
		return profilesDAO.deleteProfile(profileId);
	}

	public void addReport(Report reportBean, HttpServletResponse response) {
		try {
			reportDAO.getReport(reportBean,  response);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ReportDAO getReportDAO() {
		return reportDAO;
	}

	public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}

	public DashboardDAO getDashboardDAO() {
		return dashboardDAO;
	}

	public void setDashboardDAO(DashboardDAO dashboardDAO) {
		this.dashboardDAO = dashboardDAO;
	}
	
	public int isProfilesExist(Profile profile, String userId) {
		return profilesDAO.isProfilesExist(profile, userId);
	}
	
	public int isProfileMapingExist(int profileId, String refNo) {
		return profilesDAO.isProfileMapingExist(profileId, refNo);
	}
	
	public int insertProfile(Profile profile, String userId) {
		return profilesDAO.insertProfile(profile, userId);
	}

	public int createProfileRequirementMapping(Profile profile, String refNo, String userId) {
		return profilesDAO.insertProfileMapping(profile,refNo, userId);
	}
	
	public ArrayList<RequirementProfileMapping> getRequirementProfile(String requirementId) {
		ArrayList<RequirementProfileMapping> requirement=requirementDAO.getRequirementProfile(requirementId);
		return requirement;
	}
	
	public ArrayList<RequirementProfileMapping> getRequirementProfileName(String requirementId) {
		ArrayList<RequirementProfileMapping> requirement= requirementDAO.getRequirementProfileName(requirementId);
		///requirementDAO.getShortlistedProfiles( requirementId);
		return requirement;
	}
	
	
	public int updateShortlistedProfile(Requirement requirement, String userId) {
		return requirementDAO.updateShortlistedProfile(requirement,userId);
	}
	
	public ArrayList<Requirement> getShortlistedProfiles(String requirementId){
		return requirementDAO.getShortlistedProfiles(requirementId);
	}
}
