package com.tmp.util;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
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
import com.tmp.entity.Account;
import com.tmp.entity.AdminInfoKeyValueMapping;
import com.tmp.entity.ConfigKeyValueMapping;
import com.tmp.entity.DashboardRequirement;
import com.tmp.entity.Profile;
import com.tmp.entity.Profiles;
import com.tmp.entity.Project;
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
	
	@Autowired(required = true)
	@Qualifier("tmpDAOUtil")
	TMPDAOUtil tmpDAOUtil;
	
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public TMPDAOUtil getTmpDAOUtil() {
		return tmpDAOUtil;
	}

	public void setTmpDAOUtil(TMPDAOUtil tmpDAOUtil) {
		this.tmpDAOUtil = tmpDAOUtil;
	}
	
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
		List<Account> userAccounts = dashboardDAO.getAccountDetails(userId);
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("accountsJson", userAccounts);
			String jsonAccounts = jsonObj.toString();
			//system.out.println("jsonAccounts : "+jsonAccounts);
			return jsonAccounts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getProjectDetails(String userId) {
		List<Project> userProjects = dashboardDAO.getProjectDetails(userId);
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("projectsJson", userProjects);
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
	
	public String getAccountList() {
		ArrayList<Account> accountListJson = requirementDAO.getAccountList();
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("accountListJson", accountListJson);
			String jsonStr = jsonObj.toString();			
			//system.out.println("jsonStr : "+jsonStr);
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getProjectList() {
		ArrayList<Project> projectListJson = requirementDAO.getProjectList();
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("projectListJson", projectListJson);
			String jsonStr = jsonObj.toString();			
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

	public int createRequirement(Requirement requirement, String userId, String userName) {
		return requirementDAO.createRequirement(requirement, userId, userName);
	}

	public int updateRequirement(Requirement requirement, String userId) {
		return requirementDAO.updateRequirement(requirement,userId);
	}

	public int deleteRequirement(ArrayList<String> requirementId) {
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

	public int getRefId(String RefId) {
		return profilesDAO.getRefId(RefId);
	}
	
	public int createProfile(Profile profile, String strUserId) {
		return profilesDAO.createProfile(profile, strUserId);
	}

	public int updateProfile(Profile profile,String userId) {
		int reqStatus = requirementDAO.getRequirement(profile.getReqRefNo()).getStatus().getId();
		ArrayList<RequirementProfileMapping> profiles = null;

		if(reqStatus == 15 || reqStatus == 16 || reqStatus == 17 || reqStatus == 18 || reqStatus==19) {
			profiles = requirementDAO.getRequirementProfile(profile.getReqRefNo());
		}
		return profilesDAO.updateProfile(profile,userId,reqStatus,profiles);
	}

	public int deleteProfile(ArrayList<String> profileId) {
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
	
	@Deprecated
	public void updateRequirementStatus(Profile profile, String refNo, String userId) {
		profilesDAO.updateRequirementStatus(profile.getId(),refNo,60, "No", 61, userId);
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
	
	public int getKeyByValue(String searchFor, String searchValue) {
		
		int searchKey = 0;
		
		if(StringUtils.isNotBlank(searchValue)) {
			System.out.println("SearchValue "+ searchValue);
			searchKey = getConfigKeyIdByName(getSearchIndex(searchFor), searchValue);
		}
		
		return searchKey;
	}
	
	static int getSearchIndex(String searchFor) {
		
		int searchIndex = 0;
		if(StringUtils.isNotBlank(searchFor)) {
		if(searchFor.equalsIgnoreCase("location"))
			searchIndex = 2;
		if(searchFor.equalsIgnoreCase("criticality"))
			searchIndex = 1;
		if(searchFor.equalsIgnoreCase("positionstatus"))
			searchIndex = 5;
		if(searchFor.equalsIgnoreCase("opportunitystatus"))
			searchIndex = 6;
		if(searchFor.equalsIgnoreCase("primaryskill"))
			searchIndex = 11;
		if(searchFor.equalsIgnoreCase("skillcategory"))
			searchIndex = 10;
		if(searchFor.equalsIgnoreCase("intimationmode"))
			searchIndex = 3;
		if(searchFor.equalsIgnoreCase("reqtype"))
			searchIndex = 4;
		}
		return searchIndex;
	}
	
	public int getConfigKeyIdByName(int searchId, String configValue) {
		
		//List<ConfigKeyValueMapping> list= configDAO.getConfigKeyValues(searchId);
		
		int configKey = configDAO.getConfigKeyValues(searchId, configValue);
		
		/*for(ConfigKeyValueMapping obj : list) {
			
			//if(obj.getConfigValue().getValue().equalsIgnoreCase(configValue)) {
			if(obj.getConfigValue().getValue().toLowerCase().contains(configValue.toLowerCase()) || 
					configValue.toLowerCase().contains(obj.getConfigValue().getValue().toLowerCase())) {
				configKey = obj.getId();
				return configKey;
			}
			
			
		}*/
		
		if( configKey == 0) {
			configKey = insertConfigTableValues(searchId, configValue);
		}
		
		return configKey;
		
	}
	
	public int insertConfigTableValues(int searchId, String configValue) {
		
		// insert values into INSERT INTO `config_value` VALUES (1,'Critical','2018-04-18 18:30:00',1,'0000-00-00 00:00:00',NULL);
		// get primary key form config_value table
		// insert INSERT INTO `config_key_value_mapping` VALUES (1,1,1,'2018-04-18 18:30:00',1,'0000-00-00 00:00:00',NULL);
		
		StringBuffer sql = new StringBuffer("INSERT INTO CONFIG_VALUE(VALUE, CREATED_ON, CREATED_BY) VALUES(?, ?, ?) ");
		StringBuffer sql1 = new StringBuffer("INSERT INTO CONFIG_KEY_VALUE_MAPPING(CONFIG_KEY_ID, CONFIG_VALUE_ID, CREATED_ON, CREATED_BY) VALUES(?, ?, ?, ?)");

		Connection conn = null;
		int generatedKey = 0;
		int value;
		try {
			conn = dataSource.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, configValue);
			ps.setTimestamp(2, tmpDAOUtil.getCurrentTimestamp());
			ps.setInt(3, 1);
		    value = ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			
			if (rs.next()) {
			    generatedKey = rs.getInt(1);
			}
			//system.out.println("Inserted ADMIN_INFO_VALUE record's ID: " + generatedKey);
			rs.close();
			ps.close();
			
			PreparedStatement ps1 = conn.prepareStatement(sql1.toString(),Statement.RETURN_GENERATED_KEYS);
			ps1.setInt(1, searchId);
			ps1.setInt(2, generatedKey);
			ps1.setTimestamp(3, tmpDAOUtil.getCurrentTimestamp());
			ps1.setInt(4, 1);
		    value = ps1.executeUpdate();
		    ResultSet rs1 = ps1.getGeneratedKeys();
			
			if (rs1.next()) {
			    generatedKey = rs1.getInt(1);
			}
			rs1.close();
			ps1.close();
 
		} catch (SQLException sqlException) {
			sqlException.getMessage();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlException) {
				}
			}
		}
		return generatedKey;
		
	}
	
	public int getAccountIdByName(String accountName,  String userId) {
		
		int accountId = 0;
 		if(StringUtils.isNotBlank(accountName)) {
 			System.out.println("Account Name : "+accountName);
			accountId = getAccountByName(accountName, userId);
		}
 		
 		
 		
		return accountId;
	}
	
	public int getAccountByName(String accountName, String userId) {
		StringBuffer sql = new StringBuffer("SELECT * FROM tmp.account_master WHERE LOWER(ACCOUNT_NAME) like ? ");
			
		Connection conn = null;
		Account account = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int accountId = 0;
		try {
			conn = dataSource.getConnection();
		    ps = conn.prepareStatement(sql.toString());
			ps.setString(1, accountName.toLowerCase());
		    rs = ps.executeQuery();
			
			if (rs != null) {
				if (rs.next()) {
					accountId = rs.getInt("ACCOUNT_ID");
					/*account = new Account();
					account.setAccountId(rs.getInt("ACCOUNT_ID"));
					account.setAccountName(rs.getString("ACCOUNT_NAME"));*/
				}
			}
			
			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			if (conn != null) {
				try {
					rs.close();
					ps.close();
					conn.close();
				} catch (SQLException sqlException) {}
			}
		}
		if(accountId == 0) {
			accountId = insertForAccountName(accountName);
					StringBuffer sql2 = new StringBuffer("INSERT INTO user_account_mapping (USER_ID,ACCOUNT_ID) VALUES (?,?)");

					
					try {
						conn = dataSource.getConnection();
						
						ps = conn.prepareStatement(sql2.toString(),Statement.RETURN_GENERATED_KEYS);
						ps.setString(1, userId);
						ps.setInt(2, accountId);
						
					    ps.executeUpdate();
						
						
						
			 
					} catch (SQLException sqlException) {
						sqlException.getMessage();
					} finally {
						if (conn != null) {
							try {
								rs.close();
								ps.close();
								conn.close();
							} catch (SQLException sqlException) {
							}
						}
					}
				}
		
		return accountId;
	}
	
	public int insertForAccountName(String accountName) {
		/*INSERT INTO `account_master` VALUES (1,'Ford'),(2,'Ford Direct'),(3,'Mazda'),(4,'TRW');
		   
		INSERT INTO `projects` VALUES (1,'OWS',1);*/
		StringBuffer sql = new StringBuffer("INSERT INTO account_master(ACCOUNT_NAME) VALUES(?) ");
		//StringBuffer sql1 = new StringBuffer("INSERT INTO projects(PROJECT_NAME, ACC_ID) VALUES(?, ?)");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int generatedKey = 0;
		try {
			conn = dataSource.getConnection();
			
			ps = conn.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, accountName);
		    ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			
			if (rs.next()) {
			    generatedKey = rs.getInt(1);
			}
			
			
 
		} catch (SQLException sqlException) {
			sqlException.getMessage();
		} finally {
			if (conn != null) {
				try {
					rs.close();
					ps.close();
					conn.close();
				} catch (SQLException sqlException) {
				}
			}
		}
		return generatedKey;
	}
	
	public int getProjectNameByAccountId(String projectName, int accountId) {
		int projectId = 0;
		if(StringUtils.isNotBlank(projectName)) {
			projectId = getProjectIdByName(projectName, accountId);
		}
		
		
		return projectId;
	}
	public int getProjectIdByName(String projectName , int accountId) {
		
		int projectId = 0;
		
		StringBuffer sql = new StringBuffer("SELECT * FROM tmp.projects WHERE LOWER(PROJECT_NAME) like ? ");
		
		Connection conn = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			 ps = conn.prepareStatement(sql.toString());
			ps.setString(1, projectName.toLowerCase());
			rs = ps.executeQuery();
			
			if (rs != null) {
				if (rs.next()) {
					projectId = rs.getInt("ID");			
					}
			}
			
			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			if (conn != null) {
				try {
					rs.close();
					ps.close();
					conn.close();
				} catch (SQLException sqlException) {}
			}
		}
			if(projectId == 0) {
			projectId = insertProject(accountId, projectName);	
			}
		return projectId;
		
	}
	public int insertProject(int accountId, String projectName) {
		
		StringBuffer sql = new StringBuffer("INSERT INTO projects(PROJECT_NAME,ACC_ID) VALUES(?,?) ");
		//StringBuffer sql1 = new StringBuffer("INSERT INTO projects(PROJECT_NAME, ACC_ID) VALUES(?, ?)");

		Connection conn = null;
		int generatedKey = 0;
		try {
			conn = dataSource.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, projectName);
			ps.setInt(2, accountId);
		    ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			
			if (rs.next()) {
			    generatedKey = rs.getInt(1);
			}
			rs.close();
			ps.close();
			
 
		} catch (SQLException sqlException) {
			sqlException.getMessage();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlException) {
				}
			}
		}
		return generatedKey;
		
	}
	
}
