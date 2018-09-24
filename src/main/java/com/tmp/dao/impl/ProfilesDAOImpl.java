package com.tmp.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tmp.dao.AdminDAO;
import com.tmp.dao.ConfigDAO;
import com.tmp.dao.ProfilesDAO;
import com.tmp.entity.Profile;
import com.tmp.entity.Profiles;
import com.tmp.util.TMPDAOUtil;

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

		StringBuffer sql = new StringBuffer("SELECT * FROM PROFILE ORDER BY ID DESC");

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

		StringBuffer sql = new StringBuffer("SELECT D.ACCOUNT, R.ID FROM USER A INNER JOIN USER_ROLE_MAPPING B ON A.ID = B.USER_ID \r\n") 
				.append("INNER JOIN USER_ROLE_ACCOUNT_MAPPING C ON C.USER_ROLE_ID = B.ID INNER JOIN ACCOUNT_MAPPING D ON D.ID = C.ACCOUNT_ID \r\n")
				.append("INNER JOIN REQUIREMENT R ON R.ACCOUNT =D.ID WHERE A.ID = ?");

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
		StringBuffer sql = new StringBuffer("SELECT p.ID, p.NAME, p.EMAIL_ID, p.CONTACT_NO, p.CURRENT_COMPANY," + 
				" p.LOCATION, p.PRIMARY_SKILL, p.PROFILE_SHARED_DATE, p.PROFILE_SHARED_BY, " + 
				" p.YEARS_OF_EXPERIENCE, p.RELEVANT_EXPERIENCE, p.NOTICE_PERIOD, " + 
				" p.CURRENT_CTC, p.EXPECTED_CTC, p.IS_ALLOCATED," + 
				" p.ALLOCATION_START_DATE, p.ALLOCATION_END_DATE, p.CREATED_ON, " + 
				" p.CREATED_BY, p.UPDATED_ON, p.UPDATED_BY, p.PROFILE_SOURCE, " + 
				" p.INTERNAL_EVALUATION_RESULT_DATE, p.INITIAL_EVALUATION_RESULT, " + 
				" p.PROFILE_SHARED_CUSTOMER, p.PROFILE_SHARED_CUSTOMER_DATE, p.REMARKS, " + 
				" p.CUSTOMER_INTERVIEW_STATUS, p.REQUIREMENT_ID,r.ACCOUNT,r.PROJECT " + 
				"FROM PROFILE p inner join REQUIREMENT r ON p.REQUIREMENT_ID = r.ID WHERE p.ID = ?");

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
		.append("INTERNAL_EVALUATION_RESULT_DATE, INITIAL_EVALUATION_RESULT, PROFILE_SHARED_CUSTOMER, \r\n")
		.append("PROFILE_SHARED_CUSTOMER_DATE, CUSTOMER_INTERVIEW_STATUS, REMARKS, CREATED_ON,\r\n")
		.append("CREATED_BY, EMAIL_ID, REQUIREMENT_ID) \r\n")
		.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			
			int newProfileId = 0;
			int result = 0;
			
			int profileId = isProfilesExist(profile,strUserId);
			
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
			} else {
				profile.setId(profileId);
				if (profile.getId() > 0) {
					result = insertProfileMapping(profile, "", strUserId);
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

	public int updateProfile(Profile profile,String userId) {
		
		StringBuffer sql = new StringBuffer(
				"UPDATE PROFILE SET NAME=?, EMAIL_ID =?, CONTACT_NO=?, CURRENT_COMPANY=?, \r\n")
		.append("LOCATION=?, PRIMARY_SKILL=?, PROFILE_SHARED_DATE=?, PROFILE_SHARED_BY=?, YEARS_OF_EXPERIENCE=?, RELEVANT_EXPERIENCE=?, NOTICE_PERIOD=?, \r\n")
		.append("CURRENT_CTC=?, EXPECTED_CTC=?, IS_ALLOCATED=?, ALLOCATION_START_DATE=?, ALLOCATION_END_DATE=?, CREATED_ON=?, \r\n")
		.append(" UPDATED_ON=?, PROFILE_SOURCE=?, INTERNAL_EVALUATION_RESULT_DATE=?, INITIAL_EVALUATION_RESULT=?,  \r\n")
		.append("PROFILE_SHARED_CUSTOMER=?, PROFILE_SHARED_CUSTOMER_DATE=?, CUSTOMER_INTERVIEW_STATUS=?, REMARKS=?,UPDATED_BY=?") ;
		
		StringBuffer sql1 = new StringBuffer("UPDATE REQUIREMENT_PROFILE_MAPPING SET INTERNAL_EVALUATION_RESULT=?, CUSTOMER_INTERVIEW_STATUS=?, REMARKS=?, REQUIREMENT_ID=? WHERE PROFILE_ID=?");
		StringBuffer sql2=new StringBuffer();
		if(profile.getInitialEvaluationResult().getId()==26) { //26-->Did not process
			sql2.append("UPDATE REQUIREMENT SET STATUS=15 WHERE ID=?"); //15-->Profile Sourcing
		}else if(profile.getInitialEvaluationResult().getId()==60) { //60-->In progress
			sql2.append("UPDATE REQUIREMENT SET STATUS=16 WHERE ID=?"); //16-->Technical Evaluation
		}else if(profile.getInitialEvaluationResult().getId()==25) { //25-->Hold
			sql2.append("UPDATE REQUIREMENT SET STATUS=16 WHERE ID=?");
		}
		else if(profile.getInitialEvaluationResult().getId()==23 && profile.getCustomerInterviewStatus().getId()==61) {//23-->Shortlisted 61-->Yet to process
			sql2.append("UPDATE REQUIREMENT SET STATUS=17 WHERE ID=?"); //17-->Customer Evaluation
		}else if(profile.getInitialEvaluationResult().getId()==23 && profile.getCustomerInterviewStatus().getId()==27) { //27-->Shortlisted
			sql2.append("UPDATE REQUIREMENT SET STATUS=18 WHERE ID=?"); //18-->Offer Processing
		}else if(profile.getInitialEvaluationResult().getId()==23 && profile.getCustomerInterviewStatus().getId()==29) { //29-->Hold
			sql2.append("UPDATE REQUIREMENT SET STATUS=17 WHERE ID=?");
		}
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
			PreparedStatement ps2 = conn.prepareStatement(sql2.toString());
			populateProfileForUpdate(ps, profile, userId);
			populateProfileForUpdateForMapping(ps1, profile, userId);
			populateProfileForUpdateReq(ps2,profile);
			ps.executeUpdate();
			ps2.executeUpdate();
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
		profile.setIsAllocated(configDAO.getConfigKeyValueMapping(rs.getInt("IS_ALLOCATED")));
		profile.setAccount(configDAO.getAccountMapping(rs.getInt("ACCOUNT")));
		profile.setProject(configDAO.getProjectMapping(rs.getInt("PROJECT")));
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
		int iniEvalResult = rs.getInt("INITIAL_EVALUATION_RESULT");
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
		profile.setAccount(configDAO.getAccountMapping(rs.getInt("ACCOUNT")));
		profile.setProject(configDAO.getProjectMapping(rs.getInt("PROJECT")));
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
		int iniEvalResult = rs.getInt("INITIAL_EVALUATION_RESULT");
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
		ps.setInt(1, profile.getId());
		ps.setString(2, profile.getName());
		ps.setString(3, profile.getContactNo());
		ps.setString(4, profile.getCurrentCompany());
		ps.setString(5, profile.getLocation());
		adminDAO.getRequirementMappingValue(profile.getPrimarySkillAdd(), 11, strUserId);
		ps.setInt(6, configDAO.getConfigKeyValueMapping(profile.getPrimarySkillAdd()).getId());
		ps.setDate(7, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getProfileSharedDate()));
		ps.setString(8, profile.getProfileSourceAdd());
		ps.setString(9, profile.getProfileSharedBy());
		ps.setInt(10, profile.getYearsOfExperience());
		ps.setInt(11, profile.getRelevantExperience());
		ps.setInt(12, profile.getCurrentCTC());
		ps.setInt(13, profile.getExpectedCTC());
		ps.setInt(14, profile.getNoticePeriod());
		ps.setDate(15, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getInternalEvaluationResultDate()));
		
		if(StringUtils.isBlank(profile.getInitialEvaluationResultAdd()))
		{
			ps.setString(16, null);
		}else {
			ps.setString(16, profile.getInitialEvaluationResultAdd());
		}
		
		ps.setString(17, profile.getProfileSharedCustomer());
		
		ps.setDate(18, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getProfileSharedCustomerDate()));
		//ps.setString(19, profile.getCustomerInterviewStatusAdd());
		
		if(null!=profile.getCustomerInterviewStatusAdd())
			ps.setString(19, profile.getCustomerInterviewStatusAdd());
		else
			ps.setString(19,"61");
		
		ps.setString(20, profile.getRemarks());
		ps.setTimestamp(21,tmpDAOUtil.getCurrentTimestamp());
		ps.setString(22,strUserId);
		ps.setString(23,profile.getEmail());
		ps.setString(24,profile.getReqRefNo());
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
		ps.setDate(i++, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getInternalEvaluationResultDate()));
		ps.setInt(i++, profile.getInitialEvaluationResult().getId());
		ps.setString(i++, profile.getProfileSharedCustomer());
		ps.setDate(i++, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getProfileSharedCustomerDate()));
		if(null!=profile.getCustomerInterviewStatus())
			ps.setInt(i++, profile.getCustomerInterviewStatus().getId());
		else
			ps.setInt(i++,61);
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
		ps1.setString(i++, profile.getReqRefNo());
		ps1.setInt(i++, profile.getId());
	}
	
	private void populateProfileForUpdateReq(PreparedStatement ps2, Profile profile) throws SQLException {
		if(ps2 == null) {
			return;
		}
		ps2.setString(1, profile.getReqRefNo());
	}
	public int deleteProfile(ArrayList<String> profileId) {
		StringBuffer sql = new StringBuffer("DELETE FROM REQUIREMENT_PROFILE_MAPPING WHERE PROFILE_ID=?");
		StringBuffer sql1 = new StringBuffer("DELETE FROM PROFILE WHERE ID=?");
		ArrayList<String> profIdList = profileId;
		 for (String num : profIdList) { 		      
	           System.out.println(num); 		
	      }
		 int index = 0;
		Connection conn = null;
		try {
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
			}
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
		.append("INTERNAL_EVALUATION_RESULT_DATE, INITIAL_EVALUATION_RESULT, PROFILE_SHARED_CUSTOMER, \r\n")
		.append("PROFILE_SHARED_CUSTOMER_DATE, CUSTOMER_INTERVIEW_STATUS, REMARKS, CREATED_ON,\r\n")
		.append("CREATED_BY, EMAIL_ID, ALLOCATION_START_DATE, ALLOCATION_END_DATE, IS_ALLOCATED, ACCOUNT, PROJECT, REQUIREMENT_ID ) \r\n")
		.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
		
		if(profile.getInternalEvaluationResultDate()!=null){
			ps.setDate(14, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getInternalEvaluationResultDate()));
			}else{
			ps.setString(14, "0000-00-00");
		}
		
		if((profile.getInitialEvaluationResultAdd()!=null) && !(profile.getInitialEvaluationResultAdd().isEmpty())){
			ps.setInt(15, configDAO.getConfigKeyValueMapping(profile.getInitialEvaluationResultAdd()).getId());
			}else{
			ps.setInt(15, configDAO.getConfigKeyValueMapping("0").getId());
		}
		
		if(profile.getProfileSharedCustomer()!=null){
			ps.setString(16, profile.getProfileSharedCustomer());
			}else{
			ps.setString(16, "");
		}
		
		if(profile.getProfileSharedCustomerDate()!=null){
			ps.setDate(17, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getProfileSharedCustomerDate()));
			}else{
			ps.setString(17, "0000-00-00");
		}
		
		if((profile.getCustomerInterviewStatusAdd()!=null) && !(profile.getCustomerInterviewStatusAdd().isEmpty())){
			ps.setInt(18, configDAO.getConfigKeyValueMapping(profile.getCustomerInterviewStatusAdd()).getId());
			}else{
			ps.setInt(18, configDAO.getConfigKeyValueMapping("0").getId());
		}
		
		if(profile.getRemarks()!=null){
			ps.setString(19, profile.getRemarks());
			}else{
			ps.setString(19, "");
		}
		ps.setTimestamp(20,tmpDAOUtil.getCurrentTimestamp());
		ps.setString(21,userId);
		ps.setString(22,profile.getEmail());
		
		if(profile.getAllocationStartDate()!=null){
			ps.setDate(23, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getAllocationStartDate()));
			}else{
			ps.setString(23, "0000-00-00");
		}
		
		if(profile.getAllocationEndDate()!=null){
			ps.setDate(24, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getAllocationEndDate()));
			}else{
			ps.setString(24, "0000-00-00");
		}
		
		if((profile.getIsAllocated1()!=null) && !(profile.getIsAllocated1().isEmpty())){
			ps.setInt(25, configDAO.getConfigKeyValueMapping(profile.getIsAllocated1()).getId());
			}else{
			ps.setInt(25, 0);
		}
		int accNewMappingId = adminDAO.getRequirementAdminAccountMapping(profile.getAccount1(), 6, userId, 8,  9);
		int accId = configDAO.getAdminInfoKeyValueMapping(profile.getAccount1()).getId();
		int accountId = configDAO.getAccountMappingId(accId).getAccountId();
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
		}
		ps.setString(28, profile.getReqRefNo());
	}
	
	public int insertProfileMapping(Profile profile, String refNo, String userId){
		StringBuffer sql = new StringBuffer("INSERT INTO REQUIREMENT_PROFILE_MAPPING(REQUIREMENT_ID,PROFILE_ID,INTERNAL_EVALUATION_RESULT,CUSTOMER_INTERVIEW_STATUS,REMARKS,CREATED_ON,CREATED_BY)VALUES(?,?,?,?,?,?,?)");
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
	}

}
