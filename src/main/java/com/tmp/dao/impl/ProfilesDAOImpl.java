package com.tmp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tmp.dao.AdminDAO;
import com.tmp.dao.ConfigDAO;
import com.tmp.dao.ProfilesDAO;
import com.tmp.dao.RequirementDAO;
import com.tmp.entity.Profile;
import com.tmp.entity.Profiles;
import com.tmp.entity.RequirementProfileMapping;
import com.tmp.util.ProfileRequirementStatusMappingUtil;
import com.tmp.util.TMPDAOUtil;
import com.tmp.util.TMPUtil;

@Repository
@Qualifier("profilesDAO")
public class ProfilesDAOImpl implements ProfilesDAO {

	@Autowired(required = true)
	@Qualifier("configDAO")
	ConfigDAO configDAO;

	@Autowired(required = true)
	@Qualifier("adminDAO")
	AdminDAO adminDAO;
	
	@Autowired(required = true)
	@Qualifier("tmpDAOUtil")
	TMPDAOUtil tmpDAOUtil;

	@Autowired(required = true)
	@Qualifier("tmpUtil")
	TMPUtil tmpUtil;
	
	@Autowired(required = true)
	@Qualifier("requirementDAO")
	RequirementDAO requirementDAO;
	
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public ConfigDAO getConfigDAO() {
		return configDAO;
	}

	public void setConfigDAO(ConfigDAO configDAO) {
		this.configDAO = configDAO;
	}

	public AdminDAO getAdminDAO() {
		return adminDAO;
	}

	public void setAdminDAO(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}

	public TMPDAOUtil getTmpDAOUtil() {
		return tmpDAOUtil;
	}

	public void setTmpDAOUtil(TMPDAOUtil tmpDAOUtil) {
		this.tmpDAOUtil = tmpDAOUtil;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public Profiles getProfiles() {

		StringBuffer sql = new StringBuffer("SELECT p.*, m.INTERNAL_EVALUATION_RESULT_DATE, m.INTERNAL_EVALUATION_RESULT,m.PROFILE_SHARED_CUSTOMER,\r\n" + 
				"m.PROFILE_SHARED_CUSTOMER_DATE,m.CUSTOMER_INTERVIEW_STATUS FROM PROFILE p\r\n" + 
				"inner join REQUIREMENT_PROFILE_MAPPING m ON p.ID= m.PROFILE_ID\r\n" + 
				"ORDER BY ID DESC");

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());

			List<Profile> profilesList = null;
			Profiles profiles = null;
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				profiles = new Profiles();
				profilesList = new ArrayList<Profile>();
				while (rs.next()) {
					profilesList.add(populateProfile(rs));
				}
				profiles.setProfiles(profilesList);
			}
			rs.close();
			ps.close();
			return profiles;
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlException) {
				}
			}
		}
	}
	
	public Profiles getRequirementRefNum(String userId) {

		StringBuffer sql = new StringBuffer("SELECT R.ACCOUNT, R.ID FROM USER A\r\n" + 
				"INNER JOIN USER_ACCOUNT_MAPPING C ON C.USER_ID = A.ID \r\n" + 
				"INNER JOIN REQUIREMENT R ON R.ACCOUNT =C.ACCOUNT_ID WHERE A.ID = ? \r\n" + 
				"GROUP BY R.ACCOUNT");

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());

			List<Profile> profilesList = null;
			Profiles profiles = null;
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				profiles = new Profiles();
				profilesList = new ArrayList<Profile>();
				while (rs.next()) {
					profilesList.add(populateRefNum(rs));
				}
				profiles.setProfiles(profilesList);
			}
			rs.close();
			ps.close();
			return profiles;
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlException) {
				}
			}
		}
	}
	
	public int getRefId(String RefId) {
		StringBuffer sql = new StringBuffer("SELECT ID FROM requirement WHERE ID = ?");

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, RefId);
			int retid = 0;
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				
				while (rs.next()) {
					retid = 1;
				}
			}
			rs.close();
			ps.close();
			return retid;
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlException) {
				}
			}
		}
	}
	
	public Profile getProfile(String profileId) {
		//StringBuffer sql = new StringBuffer("SELECT * FROM PROFILE WHERE ID = ?");
		StringBuffer sql = new StringBuffer("SELECT p.ID,r.ID AS REQUIREMENT_ID, p.NAME, p.EMAIL_ID, p.CONTACT_NO, p.CURRENT_COMPANY, p.LOCATION, \r\n" + 
				"p.PRIMARY_SKILL,p.PROFILE_SOURCE, p.PROFILE_SHARED_BY,  p.YEARS_OF_EXPERIENCE, \r\n" + 
				"p.RELEVANT_EXPERIENCE, p.NOTICE_PERIOD,  p.CURRENT_CTC, p.EXPECTED_CTC,\r\n" + 
				" p.IS_ALLOCATED, r.ACCOUNT,r.PROJECT,\r\n" + 
				"p.ALLOCATION_START_DATE, p.ALLOCATION_END_DATE,\r\n" + 
				" p.CREATED_BY, p.UPDATED_BY,\r\n" + 
				" m.INTERNAL_EVALUATION_RESULT_DATE,m.PROFILE_SHARED_CUSTOMER,\r\n" + 
				" m.PROFILE_SHARED_CUSTOMER_DATE,m.REMARKS,\r\n" + 
				" p.CREATED_ON,  p.UPDATED_ON, \r\n" + 
				" m.INTERNAL_EVALUATION_RESULT,m.CUSTOMER_INTERVIEW_STATUS,\r\n" + 
				" p.PROFILE_SHARED_DATE FROM PROFILE p \r\n" + 
				"inner join REQUIREMENT r ON p.REQUIREMENT_ID = r.ID \r\n" + 
				"inner join REQUIREMENT_PROFILE_MAPPING m ON p.ID= m.PROFILE_ID\r\n" + 
				"WHERE p.ID = ?");

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, profileId);
			Profile profile = null;
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				profile = new Profile();
				while (rs.next()) {
					profile = populateProfileUpdate(rs);
				}
			}
			rs.close();
			ps.close();
			return profile;
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlException) {
				}
			}
		}
	}

	public Profiles createProfiles(List<Profile> profiles) {
		// TODO Auto-generated method stub
		return null;
	}

	public int createProfile(Profile profile, String strUserId) {
		StringBuffer sql = new StringBuffer(
				"INSERT INTO PROFILE (ID, NAME, CONTACT_NO, CURRENT_COMPANY, LOCATION, PRIMARY_SKILL, PROFILE_SHARED_DATE, \r\n")
		.append("PROFILE_SOURCE, PROFILE_SHARED_BY, YEARS_OF_EXPERIENCE, RELEVANT_EXPERIENCE, CURRENT_CTC, EXPECTED_CTC, NOTICE_PERIOD, \r\n")
		.append("REMARKS, CREATED_ON,\r\n")
		.append("CREATED_BY, EMAIL_ID, REQUIREMENT_ID) \r\n")
		.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			
			int newProfileId = 0;
			int result = 0;
			
			int profileId = isProfilesExist(profile,strUserId);
			int initialEvalRes,customerInterviewStatus;
			if(StringUtils.isNotBlank(profile.getInitialEvaluationResultAdd()) && !profile.getInitialEvaluationResultAdd().equals("0")){
				initialEvalRes = new Integer(profile.getInitialEvaluationResultAdd());
				}else{
				initialEvalRes = configDAO.getConfigKeyValueMapping("In Progress").getId();
			}
			
			if((profile.getCustomerInterviewStatusAdd()!=null) && !(profile.getCustomerInterviewStatusAdd().isEmpty())){
					customerInterviewStatus = new Integer(profile.getCustomerInterviewStatusAdd());
				}else{
					customerInterviewStatus=0;
			}
			if(profileId == 0){
			PreparedStatement ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			populateProfileForInsert(ps, profile, strUserId);
			
			result = ps.executeUpdate();
			
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				 newProfileId=generatedKeys.getInt(1);
				 //system.out.println("newProfileId==> "+newProfileId);
			} else {
				throw new SQLException(
						"Creating profile failed, no ID obtained for profile.");
			}
			ps.close();

			profile.setId(newProfileId);
			
				if (profile.getId() > 0) {
					result = insertProfileMapping(profile, "", strUserId);
				}
						
				
				int status= ProfileRequirementStatusMappingUtil.findDashboardStatus(initialEvalRes,profile.getProfileSharedCustomer(),customerInterviewStatus);
				
				int reqStatus = requirementDAO.getRequirement(profile.getReqRefNo()).getStatus().getId();
				String sql2=null;
				if(status> reqStatus){
					sql2="UPDATE REQUIREMENT SET STATUS=? WHERE ID=?";
					if(StringUtils.isNotBlank(sql2)) {
						System.out.println("sql2-->"+sql2);
						PreparedStatement ps2 = conn.prepareStatement(sql2.toString());
						ps2.setInt(1, status);
						ps2.setString(2, profile.getReqRefNo());
						ps2.executeUpdate();
					}
				}
			} else {
				profile.setId(profileId);
				if (profile.getId() > 0) {
					result = insertProfileMapping(profile, "", strUserId);
				}
								
				int status= ProfileRequirementStatusMappingUtil.findDashboardStatus(initialEvalRes,profile.getProfileSharedCustomer(),customerInterviewStatus);
				
				int reqStatus = requirementDAO.getRequirement(profile.getReqRefNo()).getStatus().getId();
				String sql2=null;
				if(status> reqStatus){
					sql2="UPDATE REQUIREMENT SET STATUS=15 WHERE ID=?";
					if(StringUtils.isNotBlank(sql2)) {
						System.out.println("sql2-->"+sql2);
						PreparedStatement ps2 = conn.prepareStatement(sql2.toString());
						ps2.setString(1, profile.getReqRefNo());
						ps2.executeUpdate();
					}
				}
			}
			
			return result;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw new RuntimeException(sqlException);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlException) {
				}
			}
		}
	}

	public Profiles updateProfiles(List<Profile> profiles) {
		// TODO Auto-generated method stub
		return null;
	}
	private int getActualRequirementStatus(ArrayList<RequirementProfileMapping> profiles, int reqStatus, int proposedReqStatus, int profileId) {
		int propReqStatus = 0;
		if(reqStatus < proposedReqStatus || profiles.size()==1){
			return proposedReqStatus;
		}
		ArrayList<Integer> sortReqStatus = new ArrayList<Integer>();
		Set<Integer> reqStatusList = new TreeSet<Integer>();
		
		
		Integer sortStatus = 0;
		for (RequirementProfileMapping requirementProfileMapping : profiles) {
			if(requirementProfileMapping.getProfileId().getId() != profileId ) {
				int initialEvalRes,customerInterviewStatus;
				String profileSharedCusomer;
				if(!requirementProfileMapping.getInternalEvaluationResult().equals("0")){
					initialEvalRes = requirementProfileMapping.getInternalEvaluationResult().getId();
					}else{
					initialEvalRes = configDAO.getConfigKeyValueMapping("In Progress").getId();
				}
				
				if((requirementProfileMapping.getCustomerInterviewStatus()!=null)){
						customerInterviewStatus = new Integer(requirementProfileMapping.getCustomerInterviewStatus().getId());
						profileSharedCusomer="yes";
					}else{
						customerInterviewStatus=0;
						profileSharedCusomer="no";
				}
				propReqStatus = ProfileRequirementStatusMappingUtil.findDashboardStatus(initialEvalRes,profileSharedCusomer,customerInterviewStatus);
				reqStatusList.add(propReqStatus);
				
			}
			sortReqStatus.addAll(reqStatusList);
			Collections.reverse(sortReqStatus);
			  sortStatus = sortReqStatus.get(0);
		}
		return sortStatus;
	}

	public int updateProfile(Profile profile,String userId, int reqStatus, ArrayList<RequirementProfileMapping> profiles) {
		
		StringBuffer sql = new StringBuffer(
				"UPDATE PROFILE SET NAME=?, EMAIL_ID =?, CONTACT_NO=?, CURRENT_COMPANY=?, \r\n")
		.append("LOCATION=?, PRIMARY_SKILL=?, PROFILE_SHARED_DATE=?, PROFILE_SHARED_BY=?, YEARS_OF_EXPERIENCE=?, RELEVANT_EXPERIENCE=?, NOTICE_PERIOD=?, \r\n")
		.append("CURRENT_CTC=?, EXPECTED_CTC=?, IS_ALLOCATED=?, ALLOCATION_START_DATE=?, ALLOCATION_END_DATE=?, CREATED_ON=?, \r\n")
		.append(" UPDATED_ON=?, PROFILE_SOURCE=?,REMARKS=?,UPDATED_BY=?") ;
		
		StringBuffer sql1 = new StringBuffer("UPDATE REQUIREMENT_PROFILE_MAPPING SET INTERNAL_EVALUATION_RESULT=?, CUSTOMER_INTERVIEW_STATUS=?, REMARKS=?, INTERNAL_EVALUATION_RESULT_DATE=?,PROFILE_SHARED_CUSTOMER=?, PROFILE_SHARED_CUSTOMER_DATE=? WHERE PROFILE_ID=?");
		
		boolean foundProfile = false;
		
		
		
		/*
	switch(profile.getInitialEvaluationResult().getId()) {
			case 26:
<<<<<<< HEAD
				if(reqStatus>=15){
					for (RequirementProfileMapping requirementProfileMapping : profiles) {
						if(requirementProfileMapping.getProfileId().getId() != profile.getId() ) {
							if(requirementProfileMapping.getCustomerInterviewStatus().getId()==29|| requirementProfileMapping.getCustomerInterviewStatus().getId()==28) {
								//CIS : 61-InProgress, 29-Hold
								sql2 = null;
								foundProfile = true;
								break;
							}
						}
					}

					if(!foundProfile) {
						System.out.println("No other Profile forund for the CES status : 17");
						sql2="UPDATE REQUIREMENT SET STATUS=16 WHERE ID=?";
					}
=======
				for (RequirementProfileMapping requirementProfileMapping : profiles) {
					if(requirementProfileMapping.getProfileId().getId() != profile.getId() ) {
						if(requirementProfileMapping.getInternalEvaluationResult().getId()==60 || requirementProfileMapping.getInternalEvaluationResult().getId()==25
								|| requirementProfileMapping.getInternalEvaluationResult().getId()==23) {
							//IES : 60-InProgress, 25-Hold, 23-Shortlisted
							sql2 = null;
							foundProfile = true;
							break;
						}
					}
				}
				if(!foundProfile) {
					System.out.println("No other Profile forund for the IES status : 16,17,18");
					sql2="UPDATE REQUIREMENT SET STATUS=15 WHERE ID=?";
>>>>>>> branch 'master' of https://github.com/markandeyulu/tmp.git
				}
				break;
			case 60:
<<<<<<< HEAD
				if(reqStatus >=15){
					for (RequirementProfileMapping requirementProfileMapping : profiles) {
						if(requirementProfileMapping.getProfileId().getId() != profile.getId() ) {
							if(requirementProfileMapping.getCustomerInterviewStatus().getId()==29|| requirementProfileMapping.getCustomerInterviewStatus().getId()==28) {
								//CIS : 61-InProgress, 29-Hold
								sql2 = null;
								foundProfile = true;
								break;
							}
						}
					}

					if(!foundProfile) {
						System.out.println("No other Profile forund for the CES status : 17");
						sql2="UPDATE REQUIREMENT SET STATUS=16 WHERE ID=?";
					}
=======
				for (RequirementProfileMapping requirementProfileMapping : profiles) {
					if(requirementProfileMapping.getProfileId().getId() != profile.getId() ) {
						if(requirementProfileMapping.getInternalEvaluationResult().getId()==23) {
							sql2 = null;
							foundProfile = true;
							break;
						}
					}
				}
				if(!foundProfile) {
					System.out.println("No other Profile forund for the IES status : 16,17,18");
					sql2="UPDATE REQUIREMENT SET STATUS=16 WHERE ID=?";
>>>>>>> branch 'master' of https://github.com/markandeyulu/tmp.git
				}
				break;
			case 25:
<<<<<<< HEAD
				if(reqStatus>=15){
					for (RequirementProfileMapping requirementProfileMapping : profiles) {
						if(requirementProfileMapping.getProfileId().getId() != profile.getId() ) {
							if(requirementProfileMapping.getCustomerInterviewStatus().getId()==29|| requirementProfileMapping.getCustomerInterviewStatus().getId()==28) {
								//CIS : 61-InProgress, 29-Hold
								sql2 = null;
								foundProfile = true;
								break;
							}
						}
					}

					if(!foundProfile) {
						System.out.println("No other Profile forund for the CES status : 17");
						sql2="UPDATE REQUIREMENT SET STATUS=16 WHERE ID=?";
					}
=======
				for (RequirementProfileMapping requirementProfileMapping : profiles) {
					if(requirementProfileMapping.getProfileId().getId() != profile.getId() ) {
						if(requirementProfileMapping.getInternalEvaluationResult().getId()==23) {
							sql2 = null;
							foundProfile = true;
							break;
						}
					}
				}
				if(!foundProfile) {
					System.out.println("No other Profile forund for the IES status : 16,17,18");
					sql2="UPDATE REQUIREMENT SET STATUS=16 WHERE ID=?";
>>>>>>> branch 'master' of https://github.com/markandeyulu/tmp.git
				}
				break;
			case 23:
				if(null == profile.getCustomerInterviewStatus()) { //IES Short-listed & CES is disabled or Not Needed
					if(reqStatus != 18 )// Marking req status as Offer Processing 
						sql2="UPDATE REQUIREMENT SET STATUS=18 WHERE ID=?";
					break;
				}
				switch(profile.getCustomerInterviewStatus().getId()) {
				case 61:
					sql2="UPDATE REQUIREMENT SET STATUS=17 WHERE ID=?";
					break;
				case 27:
					sql2="UPDATE REQUIREMENT SET STATUS=18 WHERE ID=?";
					break;
				case 29:
					sql2="UPDATE REQUIREMENT SET STATUS=17 WHERE ID=?";
					break;
				case 28:
					if(reqStatus>=16){
						for (RequirementProfileMapping requirementProfileMapping : profiles) {
							if(requirementProfileMapping.getProfileId().getId() != profile.getId() ) {
<<<<<<< HEAD
								if(requirementProfileMapping.getCustomerInterviewStatus().getId()==29|| requirementProfileMapping.getCustomerInterviewStatus().getId()==28) {
=======
								if(requirementProfileMapping.getCustomerInterviewStatus().getId()==61 || requirementProfileMapping.getCustomerInterviewStatus().getId()==29) {
>>>>>>> branch 'master' of https://github.com/markandeyulu/tmp.git
									//CIS : 61-InProgress, 29-Hold
									sql2 = null;
									foundProfile = true;
									break;
								}
							}
						}

						if(!foundProfile) {
							System.out.println("No other Profile forund for the CES status : 17");
							sql2="UPDATE REQUIREMENT SET STATUS=16 WHERE ID=?";
						}
					}
					break;
				}

				break;
			case 24: //IES Rejected
				if(reqStatus>15) {
					for (RequirementProfileMapping requirementProfileMapping : profiles) {
						if(requirementProfileMapping.getProfileId().getId() != profile.getId() ) {
							if(requirementProfileMapping.getInternalEvaluationResult().getId()==60 || requirementProfileMapping.getInternalEvaluationResult().getId()==25
									|| requirementProfileMapping.getInternalEvaluationResult().getId()==26) {
								//IES : 26-DidNotProcess, 60-InProgress, 25-Hold
								sql2 = null;
								foundProfile = true;
								break;
							}
						}
					}
					if(!foundProfile) {
						System.out.println("No other Profile forund for the IES status : 16");
						sql2="UPDATE REQUIREMENT SET STATUS=15 WHERE ID=?";
					}
				} 
				break;
			}*/
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			if(null != profile.getAccount() && profile.getAccount().getId()!=0){
				sql.append(", ACCOUNT=?");
			}
			if(null != profile.getProject() &&  profile.getProject().getId()!=0){
				sql.append(", PROJECT=?"); 
			}
			
			sql.append(" WHERE ID=? ");
					
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			PreparedStatement ps1 = conn.prepareStatement(sql1.toString());
			populateProfileForUpdate(ps, profile, userId);
			populateProfileForUpdateForMapping(ps1, profile, userId);
			
			//Updating the Requirement Status
			String sql2 ="UPDATE REQUIREMENT SET STATUS=? WHERE ID=?";
				System.out.println("sql2-->"+sql2);
				PreparedStatement ps2 = conn.prepareStatement(sql2.toString());
				int initialEvalRes,customerInterviewStatus;
 				if(!profile.getInitialEvaluationResult().equals("0")){
					initialEvalRes = profile.getInitialEvaluationResult().getId();
					}else{
					initialEvalRes = configDAO.getConfigKeyValueMapping("In Progress").getId();
				}
				
				if((profile.getCustomerInterviewStatus()!=null)){
						customerInterviewStatus = new Integer(profile.getCustomerInterviewStatus().getId());
					}else{
						customerInterviewStatus=0;
				}
				int proposedReqStatus = ProfileRequirementStatusMappingUtil.findDashboardStatus(initialEvalRes,profile.getProfileSharedCustomer(),customerInterviewStatus);
				
				
				ps2.setInt(1, getActualRequirementStatus(profiles, reqStatus, proposedReqStatus, profile.getId()));
				ps2.setString(2, profile.getReqRefNo());
				ps2.executeUpdate();
			
			ps.executeUpdate();
			int result=ps1.executeUpdate();
			ps.close();
			
			return result;
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlException) {
				}
			}
		}
	}
	private Profile populateRefNum(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}
		Profile profile = new Profile();
		profile.setReqRefNo(rs.getString(2));
		
		return profile;
	}
	private Profile populateProfile(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}
		
		Profile profile = new Profile();
		profile.setId(rs.getInt("ID"));
		profile.setReqRefNo(rs.getString("REQUIREMENT_ID"));
		profile.setName(rs.getString("NAME"));
		profile.setEmail(rs.getString("EMAIL_ID"));
		profile.setContactNo(rs.getString("CONTACT_NO"));
		profile.setCurrentCompany(rs.getString("CURRENT_COMPANY"));
		profile.setLocation(rs.getString("LOCATION"));
		profile.setPrimarySkill(configDAO.getConfigKeyValueMapping(rs.getInt("PRIMARY_SKILL")));
		profile.setProfileSource(configDAO.getConfigKeyValueMapping(rs.getInt("PROFILE_SOURCE")));
		/*profile.setProfileSharedDate(rs.getDate("PROFILE_SHARED_DATE"));*/
		profile.setProfileSharedBy(rs.getString("PROFILE_SHARED_BY"));
		profile.setYearsOfExperience(rs.getInt("YEARS_OF_EXPERIENCE"));
		profile.setRelevantExperience(rs.getInt("RELEVANT_EXPERIENCE"));
		profile.setNoticePeriod(rs.getInt("NOTICE_PERIOD"));
		profile.setCurrentCTC(rs.getInt("CURRENT_CTC"));
		profile.setExpectedCTC(rs.getInt("EXPECTED_CTC"));
		profile.setIsAllocated(configDAO.getConfigKeyValueMapping(rs.getInt("IS_ALLOCATED")));/*
		profile.setAccount(configDAO.getAccountMapping(rs.getInt("ACCOUNT")));
		profile.setProject(configDAO.getProjectMapping(rs.getInt("PROJECT")));*/
		profile.setAllocationStartDate(tmpDAOUtil.convertUtilDatetoSQLDate(rs.getDate("ALLOCATION_START_DATE")));
		profile.setAllocationEndDate(tmpDAOUtil.convertUtilDatetoSQLDate(rs.getDate("ALLOCATION_END_DATE")));		
		profile.setCreatedBy(configDAO.getUserId(rs.getInt("CREATED_BY")));
		profile.setUpdatedBy(configDAO.getUserId(rs.getInt("UPDATED_BY")));
		profile.setInternalEvaluationResultDate(rs.getDate("INTERNAL_EVALUATION_RESULT_DATE"));
		//profile.setInitialEvaluationResult(rs.getString("INITIAL_EVALUATION_RESULT"));
		profile.setProfileSharedCustomer(rs.getString("PROFILE_SHARED_CUSTOMER"));
		profile.setProfileSharedCustomerDate(rs.getDate("PROFILE_SHARED_CUSTOMER_DATE"));
		//profile.setCustomerInterviewStatus(rs.getString("CUSTOMER_INTERVIEW_STATUS"));
		profile.setRemarks(rs.getString("REMARKS"));
		profile.setCreatedOn(rs.getDate("CREATED_ON"));
		profile.setUpdatedOn(rs.getDate("UPDATED_ON"));
		int iniEvalResult = rs.getInt("INTERNAL_EVALUATION_RESULT");
		if(iniEvalResult>0){
			profile.setInitialEvaluationResult(configDAO.getConfigKeyValueMapping(iniEvalResult));
		}else{
			profile.setInitialEvaluationResult(configDAO.getConfigKeyValueMapping("In Progress"));
		}
		
		int customerInterStatus = rs.getInt("CUSTOMER_INTERVIEW_STATUS");
		if(customerInterStatus>0){
			profile.setCustomerInterviewStatus(configDAO.getConfigKeyValueMapping(customerInterStatus));
		}else{
			profile.setCustomerInterviewStatus(configDAO.getConfigKeyValueMapping("Yet to Process"));
		}
		Date sqldate=rs.getDate("PROFILE_SHARED_DATE");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		String DateToStr = simpleDateFormat.format(sqldate);
		profile.setProfileSharedDatestr(DateToStr);
		return profile;
	}
	
	private Profile populateProfileUpdate(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}
		
		Profile profile = new Profile();
		profile.setId(rs.getInt("ID"));
		profile.setReqRefNo(rs.getString("REQUIREMENT_ID"));
		profile.setName(rs.getString("NAME"));
		profile.setEmail(rs.getString("EMAIL_ID"));
		profile.setContactNo(rs.getString("CONTACT_NO"));
		profile.setCurrentCompany(rs.getString("CURRENT_COMPANY"));
		profile.setLocation(rs.getString("LOCATION"));
		profile.setPrimarySkill(configDAO.getConfigKeyValueMapping(rs.getInt("PRIMARY_SKILL")));
		profile.setProfileSource(configDAO.getConfigKeyValueMapping(rs.getInt("PROFILE_SOURCE")));
		/*profile.setProfileSharedDate(rs.getDate("PROFILE_SHARED_DATE"));*/
		profile.setProfileSharedBy(rs.getString("PROFILE_SHARED_BY"));
		profile.setYearsOfExperience(rs.getInt("YEARS_OF_EXPERIENCE"));
		profile.setRelevantExperience(rs.getInt("RELEVANT_EXPERIENCE"));
		profile.setNoticePeriod(rs.getInt("NOTICE_PERIOD"));
		profile.setCurrentCTC(rs.getInt("CURRENT_CTC"));
		profile.setExpectedCTC(rs.getInt("EXPECTED_CTC"));
		profile.setIsAllocated(configDAO.getConfigKeyValueMapping(rs.getInt("IS_ALLOCATED")));
		profile.setAccount2(tmpDAOUtil.getAccount(rs.getInt("ACCOUNT")));
		profile.setProject2(tmpDAOUtil.getProject(rs.getInt("PROJECT")));
		profile.setAllocationStartDate(tmpDAOUtil.convertUtilDatetoSQLDate(rs.getDate("ALLOCATION_START_DATE")));
		profile.setAllocationEndDate(tmpDAOUtil.convertUtilDatetoSQLDate(rs.getDate("ALLOCATION_END_DATE")));		
		profile.setCreatedBy(configDAO.getUserId(rs.getInt("CREATED_BY")));
		profile.setUpdatedBy(configDAO.getUserId(rs.getInt("UPDATED_BY")));
		profile.setInternalEvaluationResultDate(rs.getDate("INTERNAL_EVALUATION_RESULT_DATE"));
		//profile.setInitialEvaluationResult(rs.getString("INITIAL_EVALUATION_RESULT"));
		profile.setProfileSharedCustomer(rs.getString("PROFILE_SHARED_CUSTOMER"));
		profile.setProfileSharedCustomerDate(rs.getDate("PROFILE_SHARED_CUSTOMER_DATE"));
		//profile.setCustomerInterviewStatus(rs.getString("CUSTOMER_INTERVIEW_STATUS"));
		profile.setRemarks(rs.getString("REMARKS"));
		profile.setCreatedOn(rs.getDate("CREATED_ON"));
		profile.setUpdatedOn(rs.getDate("UPDATED_ON"));
		int iniEvalResult = rs.getInt("INTERNAL_EVALUATION_RESULT");
		if(iniEvalResult>0){
			profile.setInitialEvaluationResult(configDAO.getConfigKeyValueMapping(iniEvalResult));
		}else{
			profile.setInitialEvaluationResult(configDAO.getConfigKeyValueMapping("In Progress"));
		}
		
		int customerInterStatus = rs.getInt("CUSTOMER_INTERVIEW_STATUS");
		if(customerInterStatus>0){
			profile.setCustomerInterviewStatus(configDAO.getConfigKeyValueMapping(customerInterStatus));
		}else{
			profile.setCustomerInterviewStatus(configDAO.getConfigKeyValueMapping("Yet to Process"));
		}
		profile.setProfileSharedDate(rs.getDate("PROFILE_SHARED_DATE"));
	/*	Date sqldate=rs.getDate("PROFILE_SHARED_DATE");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		String DateToStr = simpleDateFormat.format(sqldate);
		profile.setProfileSharedDatestr(DateToStr);*/
		return profile;
	}

	
	private void populateProfileForInsert(PreparedStatement ps, Profile profile, String strUserId) throws SQLException {

		if (ps == null) {
			return;
		}
		int i=1;
		ps.setInt(i++, profile.getId());
		ps.setString(i++, profile.getName());
		ps.setString(i++, profile.getContactNo());
		ps.setString(i++, profile.getCurrentCompany());
		ps.setString(i++, profile.getLocation());
		adminDAO.getRequirementMappingValue(profile.getPrimarySkillAdd(), 11, strUserId);
		ps.setInt(i++, configDAO.getConfigKeyValueMapping(profile.getPrimarySkillAdd()).getId());
		ps.setDate(i++, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getProfileSharedDate()));
		ps.setString(i++, profile.getProfileSourceAdd());
		ps.setString(i++, profile.getProfileSharedBy());
		ps.setInt(i++, profile.getYearsOfExperience());
		ps.setInt(i++, profile.getRelevantExperience());
		ps.setInt(i++, profile.getCurrentCTC());
		ps.setInt(i++, profile.getExpectedCTC());
		ps.setInt(i++, profile.getNoticePeriod());
		ps.setString(i++, profile.getRemarks());
		ps.setTimestamp(i++,tmpDAOUtil.getCurrentTimestamp());
		ps.setString(i++,strUserId);
		ps.setString(i++,profile.getEmail());
		ps.setString(i++,profile.getReqRefNo());
	}

	private void populateProfileForUpdate(PreparedStatement ps, Profile profile, String userId) throws SQLException {

		if (ps == null) {
			return;
		}
		int i=1;
		ps.setString(i++, profile.getName());
		ps.setString(i++, profile.getEmail());
		ps.setString(i++, profile.getContactNo());
		ps.setString(i++, profile.getCurrentCompany());
		ps.setString(i++, profile.getLocation());
		ps.setInt(i++, profile.getPrimarySkill().getId());
		ps.setDate(i++, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getProfileSharedDate()));
		ps.setString(i++, profile.getProfileSharedBy());
		ps.setInt(i++, profile.getYearsOfExperience());
		ps.setInt(i++, profile.getRelevantExperience());
		ps.setInt(i++, profile.getNoticePeriod());
		ps.setInt(i++, profile.getCurrentCTC());
		ps.setInt(i++, profile.getExpectedCTC());
		ps.setInt(i++, configDAO.getConfigKeyValueMapping(profile.getIsAllocated1()).getId());
		ps.setDate(i++, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getAllocationStartDate()));
		ps.setDate(i++, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getAllocationEndDate()));
		ps.setDate(i++,tmpDAOUtil.convertUtilDatetoSQLDate(profile.getCreatedOn()));
		ps.setTimestamp(i++,tmpDAOUtil.getCurrentTimestamp());
		ps.setInt(i++,profile.getProfileSource().getId());
		ps.setString(i++, profile.getRemarks());
		ps.setString(i++, userId);
		if(null != profile.getAccount() && profile.getAccount().getId()!=0){
			ps.setInt(i++, profile.getAccount().getId());
		}else{
			//ps.setInt(i++, configDAO.getConfigKeyValueMapping("0").getId());
		}
		if(null != profile.getProject() && profile.getProject().getId()!=0){
			ps.setInt(i++, profile.getProject().getId());
		}
		else{
			//ps.setInt(i++, configDAO.getConfigKeyValueMapping("0").getId());
		}
		//ps.setString(29, profile.getReqRefNo());
		ps.setInt(i++, profile.getId());
		
	}

	
	private void populateProfileForUpdateForMapping(PreparedStatement ps1, Profile profile, String userId) throws SQLException {
		if (ps1 == null) {
			return;
		}
		int i=1;
		ps1.setInt(i++, profile.getInitialEvaluationResult().getId());
		if(null!=profile.getCustomerInterviewStatus())
			ps1.setInt(i++, profile.getCustomerInterviewStatus().getId());
		else
			ps1.setInt(i++,61);
		//ps1.setInt(i++, profile.getCustomerInterviewStatus().getId());
		ps1.setString(i++, profile.getRemarks());
		ps1.setDate(i++, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getInternalEvaluationResultDate()));
		ps1.setString(i++, profile.getProfileSharedCustomer());
		ps1.setDate(i++, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getProfileSharedCustomerDate()));
		
		ps1.setInt(i++, profile.getId());
		
	}
	
	/*private void populateProfileForUpdateReq(PreparedStatement ps2, Profile profile) throws SQLException {
		if(ps2 == null) {
			return;
		}
		ps2.setString(1, profile.getReqRefNo());
	}*/
	public int deleteProfile(ArrayList<String> profIdList) {
		StringBuffer sql2 = new StringBuffer("SELECT DISTINCT REQUIREMENT_ID FROM REQUIREMENT_PROFILE_MAPPING WHERE PROFILE_ID IN(");
		StringBuffer sb= new StringBuffer();
		String reqRefNum = null;
		//ArrayList<String> profIdList = profileId;
		for (String profId : profIdList) { 		      
			System.out.println(profId); 
			if(profId!= ""){
				sb.append(profId+","); 
			}
		}
		String withoutLastComma = sb.substring( 0, sb.length()-1);

		StringBuffer sql = new StringBuffer("DELETE FROM REQUIREMENT_PROFILE_MAPPING WHERE PROFILE_ID=?");
		StringBuffer sql1 = new StringBuffer("DELETE FROM PROFILE WHERE ID=?");
		//ArrayList<String> profIdList = profileId;
		for (String num : profIdList) { 		      
			System.out.println(num); 		
		}
		int index = 0;
		Connection conn = null;
		try {
			sql2.append(withoutLastComma);
			sql2.append(")");
			conn = dataSource.getConnection();
			PreparedStatement ps2 = conn.prepareStatement(sql2.toString());
			ResultSet rs = ps2.executeQuery();
			ArrayList<String> reqList = new ArrayList<String>();
			if (rs != null ) {

				while (rs.next()) {
					reqRefNum = rs.getString("REQUIREMENT_ID");
					reqList.add(reqRefNum);
				}
			}

			for (String profId : profIdList) { 
				System.out.println(profId); 
				conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				PreparedStatement ps1 = conn.prepareStatement(sql1.toString());
				if(null!=profId){
					ps.setString(1, profId);
					ps1.setString(1, profId);
					int result = ps.executeUpdate();
					int result1 = ps1.executeUpdate();
					System.out.println("result==> "+result+"result1==> "+result1);
					++index;

					ps.close();
					ps1.close();
					ps2.close();


				}
			}
			for(String reqId : reqList) {
				StringBuffer sql4 = new StringBuffer("select count(PROFILE_ID) as PROFILE_COUNT from tmp.requirement_profile_mapping where REQUIREMENT_ID=?");
				PreparedStatement ps3 = conn.prepareStatement(sql4.toString());
				ps3.setString(1, reqId);
				ResultSet rs1 = ps3.executeQuery();
				int profileCount = 0; 
				if (rs1 != null ) {

					while (rs1.next()) {
						profileCount = rs1.getInt("PROFILE_COUNT");
					}
					if(profileCount == 0){
						String sql5 = "UPDATE REQUIREMENT SET STATUS=15 WHERE ID=?";
						if(StringUtils.isNotBlank(sql5)) {
							System.out.println("sql2-->"+sql5);
							PreparedStatement ps4 = conn.prepareStatement(sql5.toString());
							ps4.setString(1, reqRefNum);
							ps4.executeUpdate();
							ps4.close();
						}
					}
				}
				ps3.close();

			}

		} catch (SQLException sqlException) {
			//system.out.println("sqlException.getMessage()"+sqlException.getMessage());
			throw new RuntimeException(sqlException);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlException) {
				}
			}
		}
		return index;
	}

		public int isProfilesExist(Profile profile, String userId){
			StringBuffer sql = new StringBuffer("SELECT ID, EMAIL_ID, CONTACT_NO, REQUIREMENT_ID FROM PROFILE WHERE EMAIL_ID=? AND REQUIREMENT_ID = ?");
			Connection conn = null;
			int id = 0;
			try {
				conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ps.setString(1, profile.getEmail());
				ps.setString(2, profile.getReqRefNo());
				ResultSet rs = ps.executeQuery();
				 
				if (rs != null ) {
					
					while (rs.next()) {
						
						String contactNum = rs.getString("CONTACT_NO");
						String emailId = rs.getString("EMAIL_ID");
						String reqRefNum = rs.getString("REQUIREMENT_ID");
						
						if((contactNum.equals(profile.getContactNo()) && reqRefNum.equals(profile.getReqRefNo())) || 
								(emailId.equals(profile.getEmail()) && reqRefNum.equals(profile.getReqRefNo()))){
							id = rs.getInt("ID");
							//system.out.println("Profile ID==> "+id);
						}else{
							insertProfile(profile, userId);
						}
						
					}
				} 
				rs.close();
				ps.close();
		     return id;
			} catch (SQLException sqlException) {
				throw new RuntimeException(sqlException);
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException sqlException) {
					}
				}
			}
		} 
		
		public int isProfileMapingExist(int profileId, String refNo){
			StringBuffer sql = new StringBuffer("SELECT * FROM REQUIREMENT_PROFILE_MAPPING WHERE REQUIREMENT_ID=? AND PROFILE_ID=?");
			Connection conn = null;
			int id = 0;
			try {
				conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ps.setString(1, refNo);
				ps.setInt(2, profileId);
				ResultSet rs = ps.executeQuery();
				 
				if (rs != null ) {
					
					while (rs.next()) {
						id = rs.getInt("ID");
						//system.out.println("Profile ID==> "+id);
					}
				} 
				rs.close();
				ps.close();
		     return id;
			} catch (SQLException sqlException) {
				throw new RuntimeException(sqlException);
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException sqlException) {
					}
				}
			}
		} 
	
	public int insertProfile(Profile profile, String userId) {
		StringBuffer sql = new StringBuffer(
				"INSERT INTO PROFILE ( NAME, CONTACT_NO, CURRENT_COMPANY, LOCATION, PRIMARY_SKILL, PROFILE_SHARED_DATE, \r\n")
		.append("PROFILE_SOURCE, PROFILE_SHARED_BY, YEARS_OF_EXPERIENCE, RELEVANT_EXPERIENCE, CURRENT_CTC, EXPECTED_CTC, NOTICE_PERIOD, \r\n")
		.append(" REMARKS, CREATED_ON,\r\n")
		.append("CREATED_BY, EMAIL_ID, ALLOCATION_START_DATE, ALLOCATION_END_DATE, IS_ALLOCATED, REQUIREMENT_ID ) \r\n")
		.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		Connection conn = null;
		int value;
		int profileId;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			populateProfileForInsertExcel(ps, profile, userId);
			 value = ps.executeUpdate();
			
				ResultSet generatedKeys = ps.getGeneratedKeys();
				if (generatedKeys.next()) {
					 profileId=generatedKeys.getInt(1);
					 //system.out.println("profileId==> "+profileId);
				} else {
					throw new SQLException(
							"Creating user failed, no ID obtained.");
				}
			
			 //system.out.println("valueee...."+value);
			ps.close();
		} catch (SQLException sqlException) {
			sqlException.getMessage();
			return 0;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlException) {
				}
			}
		}
		return profileId;
	}
	private void populateProfileForInsertExcel(PreparedStatement ps, Profile profile, String userId) throws SQLException {

		if (ps == null) {
			return;
		}

		ps.setString(1, profile.getName());
		ps.setString(2, profile.getContactNo());
		if(profile.getCurrentCompany()!=null){
			ps.setString(3, profile.getCurrentCompany());
			}else{
			ps.setString(3, "");
		}
		
		if(profile.getLocation()!=null){
			ps.setString(4, profile.getLocation());
			}else{
			ps.setInt(4, configDAO.getConfigKeyValueMapping("0").getId());
		}

		if((profile.getPrimarySkillAdd()!=null) && !(profile.getPrimarySkillAdd().isEmpty())){
			ps.setInt(5, configDAO.getConfigKeyValueMapping(profile.getPrimarySkillAdd()).getId());
			}else{
			ps.setInt(5, configDAO.getConfigKeyValueMapping("0").getId());
		}
		
		if(profile.getProfileSharedDate()!=null){
			ps.setDate(6, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getProfileSharedDate()));
			}else{
			ps.setString(6, "0000-00-00");
		}
		
		if((profile.getProfileSourceAdd()!=null) && !(profile.getProfileSourceAdd().isEmpty())){
			ps.setInt(7, configDAO.getConfigKeyValueMapping(profile.getProfileSourceAdd()).getId());
			}else{
			ps.setInt(7, configDAO.getConfigKeyValueMapping("0").getId());
		}
		
		if(profile.getProfileSharedBy()!=null){
			ps.setString(8, profile.getProfileSharedBy());
			}else{
			ps.setString(8, "");
		}

		if(profile.getYearsOfExperience()>0){
			ps.setInt(9, profile.getYearsOfExperience());
			}else{
			ps.setString(9, "");
		}
		
		if(profile.getRelevantExperience()>0){
			ps.setInt(10, profile.getRelevantExperience());
			}else{
			ps.setString(10, "");
		}
		
		if(profile.getCurrentCTC()>0){
			ps.setInt(11, profile.getCurrentCTC());
			}else{
			ps.setString(11, "");
		}
		
		if(profile.getExpectedCTC()>0){
			ps.setInt(12, profile.getExpectedCTC());
			}else{
			ps.setString(12, "");
		}
		
		if(profile.getNoticePeriod()>0){
			ps.setInt(13, profile.getNoticePeriod());
			}else{
			ps.setString(13, "");
		}
		
		
		if(profile.getRemarks()!=null){
			ps.setString(14, profile.getRemarks());
			}else{
			ps.setString(14, "");
		}
		ps.setTimestamp(15,tmpDAOUtil.getCurrentTimestamp());
		ps.setString(16,userId);
		ps.setString(17,profile.getEmail());
		
		if(profile.getAllocationStartDate()!=null){
			ps.setDate(18, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getAllocationStartDate()));
			}else{
			ps.setString(18, "0000-00-00");
		}
		
		if(profile.getAllocationEndDate()!=null){
			ps.setDate(19, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getAllocationEndDate()));
			}else{
			ps.setString(19, "0000-00-00");
		}
		
		if((profile.getIsAllocated1()!=null) && !(profile.getIsAllocated1().isEmpty())){
			ps.setInt(20, configDAO.getConfigKeyValueMapping(profile.getIsAllocated1()).getId());
			}else{
			ps.setInt(20, 0);
		}
		/*int accNewMappingId = adminDAO.getRequirementAdminAccountMapping(profile.getAccount1(), 6, userId, 8,  9);
		//int accId = configDAO.getAdminInfoKeyValueMapping(profile.getAccount1()).getId();
			/	int accountId = configDAO.getAccountMappingId(accId).getAccountId();
		int accountId = tmpDAOUtil.getAccount(accID);
		if(profile.getAccount1()!=null){
			ps.setInt(26, accountId);
			}else{
			ps.setInt(26, configDAO.getConfigKeyValueMapping("0").getId());
		}
		
		adminDAO.getRequirementAdminProjectMapping(profile.getProject1(), 7, userId, profile.getAccount1(), accNewMappingId, accountId);
		int projectId = configDAO.getAdminInfoKeyValueMapping(profile.getProject1()).getId();
		if(profile.getProject1()!=null){
			ps.setInt(27, configDAO.getProjectMappingId(accountId,projectId).getId());
			}else{
			ps.setInt(27, configDAO.getConfigKeyValueMapping("0").getId());
		}*/
		ps.setString(21, profile.getReqRefNo());
		}
	
	public int insertProfileMapping(Profile profile, String refNo, String userId){
		StringBuffer sql = new StringBuffer("INSERT INTO REQUIREMENT_PROFILE_MAPPING(REQUIREMENT_ID,PROFILE_ID,INTERNAL_EVALUATION_RESULT,CUSTOMER_INTERVIEW_STATUS,"
				+ "REMARKS,CREATED_ON,CREATED_BY, INTERNAL_EVALUATION_RESULT_DATE,PROFILE_SHARED_CUSTOMER,PROFILE_SHARED_CUSTOMER_DATE)VALUES(?,?,?,?,?,?,?,?,?,?)");
		Connection conn = null;
		int value;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			populateProfileMappingForInsertExcel(ps, profile,refNo, userId);
			 value = ps.executeUpdate();
			 //system.out.println("INSERT INTO REQUIREMENT_PROFILE_MAPPING valueee...."+value);
			ps.close();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			return 0;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlException) {
				}
			}
		}
		return value;
	}
	private void populateProfileMappingForInsertExcel(PreparedStatement ps, Profile profile,String refNo, String userId) throws SQLException {

		if (ps == null) {
			return;
		}
		if (refNo == null || refNo == "") {
			ps.setString(1,profile.getReqRefNo());
		}else{
		ps.setString(1,refNo);
		}
		ps.setInt(2,profile.getId());
		
		if(StringUtils.isNotBlank(profile.getInitialEvaluationResultAdd()) && !profile.getInitialEvaluationResultAdd().equals("0")){
			ps.setInt(3, new Integer(profile.getInitialEvaluationResultAdd()));
			}else{
				ps.setInt(3,configDAO.getConfigKeyValueMapping("In Progress").getId());
		}
		
		if((profile.getCustomerInterviewStatusAdd()!=null) && !(profile.getCustomerInterviewStatusAdd().isEmpty())){
			ps.setInt(4, new Integer(profile.getCustomerInterviewStatusAdd()));
			}else{
				ps.setInt(4,configDAO.getConfigKeyValueMapping("Yet to Process").getId());
		}
		
		if(profile.getRemarks()!=null){
			ps.setString(5,profile.getRemarks());
			}else{
			ps.setString(5, "");
		}
		
		ps.setTimestamp(6,tmpDAOUtil.getCurrentTimestamp());
		ps.setString(7,userId);
		ps.setDate(8, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getInternalEvaluationResultDate()));
				
		ps.setString(9, profile.getProfileSharedCustomer());
		
		ps.setDate(10, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getProfileSharedCustomerDate()));
	}

}
