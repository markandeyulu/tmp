package com.tmp.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
public class ProfilesDAOImpl extends BaseDAO implements ProfilesDAO {

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

	/**
	 * This method provides all the available profiles
	 */
	public Profiles getProfiles() {

		StringBuffer sql = new StringBuffer(
				"SELECT p.*, m.INTERNAL_EVALUATION_RESULT_DATE, m.INTERNAL_EVALUATION_RESULT,m.PROFILE_SHARED_CUSTOMER,\r\n"
						+ "m.PROFILE_SHARED_CUSTOMER_DATE,m.CUSTOMER_INTERVIEW_STATUS FROM PROFILE p\r\n"
						+ "inner join REQUIREMENT_PROFILE_MAPPING m ON p.ID= m.PROFILE_ID\r\n" + "ORDER BY ID DESC");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Profile> profilesList = null;
		Profiles profiles = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());

			rs = ps.executeQuery();
			if (rs != null) {
				profiles = new Profiles();
				profilesList = new ArrayList<Profile>();
				while (rs.next()) {
					profilesList.add(populateProfile(rs));
				}
				profiles.setProfiles(profilesList);
			}

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}
		return profiles;
	}

	/**
	 * This method provides the requirement reference number based on user and
	 * account details.
	 */
	public Profiles getRequirementRefNum(String userId) {

		StringBuffer sql = new StringBuffer("SELECT R.ACCOUNT, R.ID FROM USER A\r\n"
				+ "INNER JOIN USER_ACCOUNT_MAPPING C ON C.USER_ID = A.ID \r\n"
				+ "INNER JOIN REQUIREMENT R ON R.ACCOUNT =C.ACCOUNT_ID WHERE A.ID = ? \r\n" + "GROUP BY R.ACCOUNT");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Profile> profilesList = null;
		Profiles profiles = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, userId);
			rs = ps.executeQuery();
			if (rs != null) {
				profiles = new Profiles();
				profilesList = new ArrayList<Profile>();
				while (rs.next()) {
					profilesList.add(populateRefNum(rs));
				}
				profiles.setProfiles(profilesList);
			}

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}
		return profiles;
	}

	/**
	 * This method provides the id of the particular requirement
	 */
	public int getRefId(String RefId) {
		StringBuffer sql = new StringBuffer("SELECT ID FROM requirement WHERE ID = ?");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int retid = 0;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, RefId);

			rs = ps.executeQuery();
			if (rs != null) {

				while (rs.next()) {
					retid = 1;
				}
			}

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}
		return retid;
	}

	/**
	 * This method provides all the field values of the profile for updating
	 */
	public Profile getProfile(String profileId) {
		StringBuffer sql = new StringBuffer(
				"SELECT p.ID,r.ID AS REQUIREMENT_ID, p.NAME, p.EMAIL_ID, p.CONTACT_NO, p.CURRENT_COMPANY, p.LOCATION, \r\n"
						+ "p.PRIMARY_SKILL,p.PROFILE_SOURCE, p.PROFILE_SHARED_BY,  p.YEARS_OF_EXPERIENCE, \r\n"
						+ "p.RELEVANT_EXPERIENCE, p.NOTICE_PERIOD,  p.CURRENT_CTC, p.EXPECTED_CTC,\r\n"
						+ " p.IS_ALLOCATED, r.ACCOUNT,r.PROJECT,\r\n"
						+ "p.ALLOCATION_START_DATE, p.ALLOCATION_END_DATE,\r\n" + " p.CREATED_BY, p.UPDATED_BY,\r\n"
						+ " m.INTERNAL_EVALUATION_RESULT_DATE,m.PROFILE_SHARED_CUSTOMER,\r\n"
						+ " m.PROFILE_SHARED_CUSTOMER_DATE,m.REMARKS,\r\n" + " p.CREATED_ON,  p.UPDATED_ON, \r\n"
						+ " m.INTERNAL_EVALUATION_RESULT,m.CUSTOMER_INTERVIEW_STATUS,m.OFFER_PROCESSING_STATUS, \r\n"
						+ " p.PROFILE_SHARED_DATE FROM PROFILE p \r\n"
						+ "inner join REQUIREMENT r ON p.REQUIREMENT_ID = r.ID \r\n"
						+ "inner join REQUIREMENT_PROFILE_MAPPING m ON p.ID= m.PROFILE_ID\r\n" + "WHERE p.ID = ?");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Profile profile = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, profileId);

			rs = ps.executeQuery();
			if (rs != null) {
				profile = new Profile();
				while (rs.next()) {
					profile = populateProfileUpdate(rs);
				}
			}

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}
		return profile;
	}

	public Profiles createProfiles(List<Profile> profiles) {
		return null;
	}

	public int createProfile(Profile profile, String strUserId) {
		StringBuffer sql = new StringBuffer(
				"INSERT INTO PROFILE (ID, NAME, CONTACT_NO, CURRENT_COMPANY, LOCATION, PRIMARY_SKILL, PROFILE_SHARED_DATE, \r\n")
						.append("PROFILE_SOURCE, PROFILE_SHARED_BY, YEARS_OF_EXPERIENCE, RELEVANT_EXPERIENCE, CURRENT_CTC, EXPECTED_CTC, NOTICE_PERIOD, \r\n")
						.append("REMARKS, CREATED_ON,\r\n").append("CREATED_BY, EMAIL_ID, REQUIREMENT_ID) \r\n")
						.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet generatedKeys = null;
		int newProfileId = 0;
		int result = 0;
		int initialEvalRes, customerInterviewStatus;
		String profileSharedCustomer = "No";
		int profileId = 0;
		int status = 0;
		int reqStatus = 0;

		try {
			conn = dataSource.getConnection();

			profileId = isProfilesExist(profile, strUserId);

			if (StringUtils.isNotBlank(profile.getInitialEvaluationResultAdd())
					&& !profile.getInitialEvaluationResultAdd().equals("0")) {
				initialEvalRes = new Integer(profile.getInitialEvaluationResultAdd());
			} else {
				initialEvalRes = configDAO.getConfigKeyValueMapping("In Progress").getId();
			}

			if ((profile.getCustomerInterviewStatusAdd() != null)
					&& !(profile.getCustomerInterviewStatusAdd().isEmpty())) {
				customerInterviewStatus = new Integer(profile.getCustomerInterviewStatusAdd());
			} else {
				customerInterviewStatus = 0;
			}

			if ((profile.getProfileSharedCustomer() != null)) {
				profileSharedCustomer = profile.getProfileSharedCustomer();
			}
			if (profileId == 0) {
				ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				populateProfileForInsert(ps, profile, strUserId);

				result = ps.executeUpdate();

				generatedKeys = ps.getGeneratedKeys();
				if (generatedKeys.next()) {
					newProfileId = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating profile failed, no ID obtained for profile.");
				}

				profile.setId(newProfileId);

				if (profile.getId() > 0) {
					result = insertProfileMapping(profile, "", strUserId);
				}
				
				System.out.println("initialEvalRes "+ initialEvalRes + " profileSharedCustomer "+ profileSharedCustomer
						+" customerInterviewStatus "+ customerInterviewStatus);

				status = ProfileRequirementStatusMappingUtil.findDashboardStatus(initialEvalRes, profileSharedCustomer,
						customerInterviewStatus, 0);

				reqStatus = requirementDAO.getRequirement(profile.getReqRefNo()).getStatus().getId();
				String sql2 = null;
				if (status > reqStatus) {
					sql2 = "UPDATE REQUIREMENT SET STATUS=? WHERE ID=?";
					if (StringUtils.isNotBlank(sql2)) {
						System.out.println("sql2-->" + sql2);
						ps2 = conn.prepareStatement(sql2.toString());
						
						System.out.println("Status Id "+status +" id "+profile.getReqRefNo());
						
						ps2.setInt(1, status);
						ps2.setString(2, profile.getReqRefNo());
						ps2.executeUpdate();
					}
				}
			} else {
				profile.setId(profileId);

				status = ProfileRequirementStatusMappingUtil.findDashboardStatus(initialEvalRes,
						profile.getProfileSharedCustomer(), customerInterviewStatus, 0);

				reqStatus = requirementDAO.getRequirement(profile.getReqRefNo()).getStatus().getId();
				String sql2 = null;
				if (status > reqStatus) {
					sql2 = "UPDATE REQUIREMENT SET STATUS=15 WHERE ID=?";
					if (StringUtils.isNotBlank(sql2)) {
						System.out.println("sql2-->" + sql2);
						ps2 = conn.prepareStatement(sql2.toString());
						ps2.setString(1, profile.getReqRefNo());
						ps2.executeUpdate();
					}
				}
			}

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw new RuntimeException(sqlException);
		} finally {
			if (ps2 != null) {
				try {
					ps2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			closeDBObjects(conn, generatedKeys, ps);
		}
		return result;
	}

	/**
	 * This method updates the requirement status based on the
	 * initialEvaluationResult, profileSharedCustomer and customerStatus
	 */
	@Override
	public void updateRequirementStatus(int profileId, String reqId, int initialEvaluationResult,
			String profileSharedCustomer, int customerInterviewStatus, String userId) {

		int proposedReqStatus = ProfileRequirementStatusMappingUtil.findDashboardStatus(initialEvaluationResult,
				profileSharedCustomer, customerInterviewStatus, 0);

		int reqStatus = requirementDAO.getRequirement(reqId).getStatus().getId();
		String sql2 = null;

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps3 = null;

		try {
			conn = dataSource.getConnection();

			if (proposedReqStatus == 18) { // Offer Processing
				// append this profile id to SHORTLISTED_PROFILE_ID column value in
				// tmp.requirement table
				String sql3 = "UPDATE REQUIREMENT SET STATUS=18, SHORTLISTED_PROFILE_ID = IF(SHORTLISTED_PROFILE_ID IS NULL, '"
						+ profileId + "', CONCAT(SHORTLISTED_PROFILE_ID, '," + profileId + "')) WHERE ID=?";
				ps3 = conn.prepareStatement(sql3);
				ps3.setString(1, reqId);
				ps3.executeUpdate();
			} else if (proposedReqStatus > reqStatus) {
				sql2 = "UPDATE REQUIREMENT SET STATUS=? WHERE ID=?";
				if (StringUtils.isNotBlank(sql2)) {
					ps = conn.prepareStatement(sql2.toString());
					ps.setInt(1, proposedReqStatus);
					ps.setString(2, reqId);
					
					System.out.println("Status Id "+proposedReqStatus +" id "+reqId);
					
					ps.executeUpdate();

				}
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, null, ps);
		}

	}

	public Profiles updateProfiles(List<Profile> profiles) {
		return null;
	}

	private int getActualRequirementStatus(ArrayList<RequirementProfileMapping> profiles, int reqStatus,
			int proposedReqStatus, int profileId) {
		int propReqStatus = 0;
		Integer sortStatus = 0;
		Set<Integer> reqStatusList = new TreeSet<Integer>();

		System.out.println("profile size " + profiles.size());
		/*
		 * If existing status value is lesser then the proposed status
		 * if profile size is 1 and actual status is in LeadGeneration/ProfileSourcing
		 * if profile size is 1 and updated that profile
		 *
		 */
		
		if (reqStatus <= proposedReqStatus) {
			return proposedReqStatus;
		} else if (profiles.size() == 1 && reqStatus < 16) {
			return proposedReqStatus;
		} else if (profiles.size() == 1 && 
				(((RequirementProfileMapping)profiles.get(0)).getProfileId().getId() == profileId)) {
			return proposedReqStatus;
			
		}
		
	/*	else if (profiles.size() == 1) {
			return proposedReqStatus;
			
		}*/
		
		ArrayList<Integer> sortReqStatus = new ArrayList<Integer>();
		

		
		for (RequirementProfileMapping requirementProfileMapping : profiles) {
			if (requirementProfileMapping.getProfileId().getId() != profileId) {
				int initialEvalRes = 0, customerInterviewStatus = 0, offerStatus = 0;
				String profileSharedCusomer = "no";
				if ((requirementProfileMapping.getInternalEvaluationResult()) != null
						&& (requirementProfileMapping.getInternalEvaluationResult().getId() != 0)) {
					initialEvalRes = requirementProfileMapping.getInternalEvaluationResult().getId();
				} else {
					initialEvalRes = configDAO.getConfigKeyValueMapping("In Progress").getId();
				}

				System.out.println(" requirementProfileMapping.getProfileSharedCustomer() "
						+ requirementProfileMapping.getProfileSharedCustomer());

				if (StringUtils.isNotBlank(requirementProfileMapping.getProfileSharedCustomer())
						&& ("yes".equalsIgnoreCase(requirementProfileMapping.getProfileSharedCustomer()))) {
					customerInterviewStatus = new Integer(
							requirementProfileMapping.getCustomerInterviewStatus().getId());
					profileSharedCusomer = "yes";
				}

				if (null != requirementProfileMapping.getOfferStatus()
						&& !"0".equals(requirementProfileMapping.getOfferStatus())) {
					offerStatus = requirementProfileMapping.getOfferStatus().getId();
				} else {
					offerStatus = 0;// configDAO.getConfigKeyValueMapping("A").getId();
				}

				System.out.println("initialEvalRes" + initialEvalRes + " profileSharedCusomer " + profileSharedCusomer
						+ ",customerInterviewStatus" + customerInterviewStatus + " ,Offer Processing Status "+offerStatus);
				propReqStatus = ProfileRequirementStatusMappingUtil.findDashboardStatus(initialEvalRes,
						profileSharedCusomer, customerInterviewStatus, offerStatus);
				System.out.println("propReqStatus " + propReqStatus);
				reqStatusList.add(propReqStatus);

			}
			if (!reqStatusList.isEmpty()) {

				sortReqStatus.addAll(reqStatusList);
				Collections.reverse(sortReqStatus);
				sortStatus = sortReqStatus.get(0);
			}

		}
		System.out.println(" sortStatus " + sortStatus);
		return sortStatus;
	}

	/**
	 * This method updates the profile for any given field
	 */
	public int updateProfile(Profile profile, String userId, int reqStatus,
			ArrayList<RequirementProfileMapping> profiles) {

		StringBuffer sql = new StringBuffer(
				"UPDATE PROFILE SET NAME=?, EMAIL_ID =?, CONTACT_NO=?, CURRENT_COMPANY=?, \r\n").append(
						"LOCATION=?, PRIMARY_SKILL=?, PROFILE_SHARED_DATE=?, PROFILE_SHARED_BY=?, YEARS_OF_EXPERIENCE=?, RELEVANT_EXPERIENCE=?, NOTICE_PERIOD=?, \r\n")
						.append("CURRENT_CTC=?, EXPECTED_CTC=?, IS_ALLOCATED=?, ALLOCATION_START_DATE=?, ALLOCATION_END_DATE=?, CREATED_ON=?, \r\n")
						.append(" UPDATED_ON=?, PROFILE_SOURCE=?,REMARKS=?,UPDATED_BY=?");

		StringBuffer sql1 = new StringBuffer(
				"UPDATE REQUIREMENT_PROFILE_MAPPING SET INTERNAL_EVALUATION_RESULT=?, CUSTOMER_INTERVIEW_STATUS=?, REMARKS=?, INTERNAL_EVALUATION_RESULT_DATE=?,PROFILE_SHARED_CUSTOMER=?, PROFILE_SHARED_CUSTOMER_DATE=?, OFFER_PROCESSING_STATUS=? WHERE PROFILE_ID=?");

		boolean foundProfile = false;

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		int result = 0;

		try {
			conn = dataSource.getConnection();
			if (null != profile.getAccount() && profile.getAccount().getId() != 0) {
				sql.append(", ACCOUNT=?");
			}
			if (null != profile.getProject() && profile.getProject().getId() != 0) {
				sql.append(", PROJECT=?");
			}

			sql.append(" WHERE ID=? ");

			ps = conn.prepareStatement(sql.toString());
			ps1 = conn.prepareStatement(sql1.toString());
			populateProfileForUpdate(ps, profile, userId);
			populateProfileForUpdateForMapping(ps1, profile, userId);

			// Updating the Requirement Status
			updateRequirementStatus(profile, reqStatus, profiles);

			ps.executeUpdate();
			result = ps1.executeUpdate();

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			if (ps1 != null) {
				try {
					ps1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			closeDBObjects(conn, null, ps);
		}
		return result;
	}

	private void updateRequirementStatus(Profile profile, int reqStatus,
			ArrayList<RequirementProfileMapping> profiles) {

		Connection conn = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps5 = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			String sql2 = "UPDATE REQUIREMENT SET STATUS=? WHERE ID=?";
			System.out.println("sql2-->" + sql2);
			ps2 = conn.prepareStatement(sql2.toString());
			int initialEvalRes, customerInterviewStatus, offerStatus;
			if (!profile.getInitialEvaluationResult().equals("0")) {
				initialEvalRes = profile.getInitialEvaluationResult().getId();
			} else {
				initialEvalRes = configDAO.getConfigKeyValueMapping("In Progress").getId();
			}

			if ((profile.getCustomerInterviewStatus() != null)) {
				customerInterviewStatus = new Integer(profile.getCustomerInterviewStatus().getId());
			} else {
				customerInterviewStatus = 0;
			}

			if (profile.getOfferProcessingStatus() != null) {

				offerStatus = profile.getOfferProcessingStatus().getId();
			} else {
				offerStatus = 0;/* configDAO.getConfigKeyValueMapping("A").getId(); */
			}

			/*
			 * if(profile.getOfferProcessingStatus() != null) { offerStatus = new
			 * Integer(profile.getOfferProcessingStatus().getId()); }else { offerStatus = 0;
			 * }
			 */

			int proposedReqStatus = ProfileRequirementStatusMappingUtil.findDashboardStatus(initialEvalRes,
					profile.getProfileSharedCustomer(), customerInterviewStatus, offerStatus);

			int actualReqStatus = getActualRequirementStatus(profiles, reqStatus, proposedReqStatus, profile.getId());

			ps2.setInt(1, actualReqStatus);
			ps2.setString(2, profile.getReqRefNo());
			
			System.out.println("Status Id "+actualReqStatus +" id "+profile.getReqRefNo());
			ps2.executeUpdate();

			if (actualReqStatus == 18) {// append this profile id to SHORTLISTED_PROFILE_ID column value in
										// tmp.requirement table
				String sql3 = "UPDATE REQUIREMENT SET SHORTLISTED_PROFILE_ID = IF(SHORTLISTED_PROFILE_ID IS NULL, '"
						+ profile.getId() + "', CONCAT(SHORTLISTED_PROFILE_ID, '," + profile.getId() + "')) WHERE ID=?";
				ps3 = conn.prepareStatement(sql3);
				ps3.setString(1, profile.getReqRefNo());
				ps3.executeUpdate();

			} else if (reqStatus == 18) {
				String sql5 = "SELECT SHORTLISTED_PROFILE_ID from REQUIREMENT WHERE ID=?";
				ps5 = conn.prepareStatement(sql5);
				ps5.setString(1, profile.getReqRefNo());
				rs = ps5.executeQuery();
				String shortlistedProfileId = null;
				if (rs != null) {
					while (rs.next()) {
						shortlistedProfileId = rs.getString("SHORTLISTED_PROFILE_ID");
					}
				}
				String[] shortlistProfIds = shortlistedProfileId.split(",");
				StringBuffer shortlistedProfileIds = new StringBuffer();

				for (String str : shortlistProfIds) {
					if (Integer.parseInt(str) != profile.getId()) {
						shortlistedProfileIds.append(str);
						shortlistedProfileIds.append(",");
					}
				}
				String withoutLastComma1 = null;
				if (StringUtils.isNotBlank(shortlistedProfileIds)) {
					withoutLastComma1 = shortlistedProfileIds.substring(0, shortlistedProfileIds.length() - 1);
				}
				String sql3 = "UPDATE REQUIREMENT SET SHORTLISTED_PROFILE_ID = ? WHERE ID=?";
				ps3 = conn.prepareStatement(sql3);
				ps3.setString(1, withoutLastComma1);
				ps3.setString(2, profile.getReqRefNo());
				ps3.executeUpdate();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps3 != null) {
				try {
					ps3.close();
				} catch (SQLException sqlException) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlException) {
				}
			}
			if (ps5 != null) {
				try {
					ps5.close();
				} catch (SQLException sqlException) {
				}
			}
			closeDBObjects(conn, null, ps2);
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
		profile.setPrimarySkill(
				configDAO.getConfigKeyValueMapping(rs.getInt("PRIMARY_SKILL")).getConfigValue().getValue());
		profile.setProfileSource(configDAO.getConfigKeyValueMapping(rs.getInt("PROFILE_SOURCE")));
		/* profile.setProfileSharedDate(rs.getDate("PROFILE_SHARED_DATE")); */
		profile.setProfileSharedBy(rs.getString("PROFILE_SHARED_BY"));
		profile.setYearsOfExperience(rs.getDouble("YEARS_OF_EXPERIENCE"));
		profile.setRelevantExperience(rs.getDouble("RELEVANT_EXPERIENCE"));
		profile.setNoticePeriod(rs.getInt("NOTICE_PERIOD"));
		profile.setCurrentCTC(rs.getInt("CURRENT_CTC"));
		profile.setExpectedCTC(rs.getInt("EXPECTED_CTC"));
		profile.setIsAllocated(configDAO.getConfigKeyValueMapping(
				rs.getInt("IS_ALLOCATED")));/*
											 * profile.setAccount(configDAO.getAccountMapping(rs.getInt("ACCOUNT")));
											 * profile.setProject(configDAO.getProjectMapping(rs.getInt("PROJECT")));
											 */
		profile.setAllocationStartDate(tmpDAOUtil.convertUtilDatetoSQLDate(rs.getDate("ALLOCATION_START_DATE")));
		profile.setAllocationEndDate(tmpDAOUtil.convertUtilDatetoSQLDate(rs.getDate("ALLOCATION_END_DATE")));
		profile.setCreatedBy(configDAO.getUserId(rs.getInt("CREATED_BY")));
		profile.setUpdatedBy(configDAO.getUserId(rs.getInt("UPDATED_BY")));
		profile.setInternalEvaluationResultDate(rs.getDate("INTERNAL_EVALUATION_RESULT_DATE"));
		// profile.setInitialEvaluationResult(rs.getString("INITIAL_EVALUATION_RESULT"));
		profile.setProfileSharedCustomer(rs.getString("PROFILE_SHARED_CUSTOMER"));
		profile.setProfileSharedCustomerDate(rs.getDate("PROFILE_SHARED_CUSTOMER_DATE"));
		// profile.setCustomerInterviewStatus(rs.getString("CUSTOMER_INTERVIEW_STATUS"));
		profile.setRemarks(rs.getString("REMARKS"));
		profile.setCreatedOn(rs.getDate("CREATED_ON"));
		profile.setUpdatedOn(rs.getDate("UPDATED_ON"));
		int iniEvalResult = rs.getInt("INTERNAL_EVALUATION_RESULT");
		if (iniEvalResult > 0) {
			profile.setInitialEvaluationResult(configDAO.getConfigKeyValueMapping(iniEvalResult));
		} else {
			profile.setInitialEvaluationResult(configDAO.getConfigKeyValueMapping("In Progress"));
		}

		int customerInterStatus = rs.getInt("CUSTOMER_INTERVIEW_STATUS");
		if (customerInterStatus > 0) {
			profile.setCustomerInterviewStatus(configDAO.getConfigKeyValueMapping(customerInterStatus));
		} else {
			profile.setCustomerInterviewStatus(configDAO.getConfigKeyValueMapping("Yet to Process"));
		}
		Date sqldate = rs.getDate("PROFILE_SHARED_DATE");
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
		profile.setPrimarySkill(
				configDAO.getConfigKeyValueMapping(rs.getInt("PRIMARY_SKILL")).getConfigValue().getValue());
		profile.setProfileSource(configDAO.getConfigKeyValueMapping(rs.getInt("PROFILE_SOURCE")));
		/* profile.setProfileSharedDate(rs.getDate("PROFILE_SHARED_DATE")); */
		profile.setProfileSharedBy(rs.getString("PROFILE_SHARED_BY"));
		profile.setYearsOfExperience(rs.getDouble("YEARS_OF_EXPERIENCE"));
		profile.setRelevantExperience(rs.getDouble("RELEVANT_EXPERIENCE"));
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
		// profile.setInitialEvaluationResult(rs.getString("INITIAL_EVALUATION_RESULT"));
		profile.setProfileSharedCustomer(rs.getString("PROFILE_SHARED_CUSTOMER"));
		profile.setProfileSharedCustomerDate(rs.getDate("PROFILE_SHARED_CUSTOMER_DATE"));
		// profile.setCustomerInterviewStatus(rs.getString("CUSTOMER_INTERVIEW_STATUS"));
		profile.setRemarks(rs.getString("REMARKS"));
		profile.setCreatedOn(rs.getDate("CREATED_ON"));
		profile.setUpdatedOn(rs.getDate("UPDATED_ON"));
		int iniEvalResult = rs.getInt("INTERNAL_EVALUATION_RESULT");
		if (iniEvalResult > 0) {
			profile.setInitialEvaluationResult(configDAO.getConfigKeyValueMapping(iniEvalResult));
		} else {
			profile.setInitialEvaluationResult(configDAO.getConfigKeyValueMapping("In Progress"));
		}

		int customerInterStatus = rs.getInt("CUSTOMER_INTERVIEW_STATUS");

		if (customerInterStatus > 0) {
			profile.setCustomerInterviewStatus(configDAO.getConfigKeyValueMapping(customerInterStatus));
		} else {
			profile.setCustomerInterviewStatus(configDAO.getConfigKeyValueMapping("Yet to Process"));
		}

		int offerProcessStatus = rs.getInt("OFFER_PROCESSING_STATUS");

		System.out.println("OfferProcessingStatus " + offerProcessStatus);

		if (offerProcessStatus > 0) {
			profile.setOfferProcessingStatus(configDAO.getConfigKeyValueMapping(offerProcessStatus));

			System.out.println(" Inside If-> offer processing status from configkeyvalue mapping "
					+ profile.getOfferProcessingStatus());

		} else {
			profile.setOfferProcessingStatus(configDAO.getConfigKeyValueMapping("A"));
			System.out.println(" Inside Else -> offer processing status from configkeyvalue mapping "
					+ profile.getOfferProcessingStatus());
		}
		profile.setProfileSharedDate(rs.getDate("PROFILE_SHARED_DATE"));
		/*
		 * Date sqldate=rs.getDate("PROFILE_SHARED_DATE"); SimpleDateFormat
		 * simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy"); String DateToStr =
		 * simpleDateFormat.format(sqldate); profile.setProfileSharedDatestr(DateToStr);
		 */
		return profile;
	}

	private void populateProfileForInsert(PreparedStatement ps, Profile profile, String strUserId) throws SQLException {

		if (ps == null) {
			return;
		}
		int i = 1;
		ps.setInt(i++, profile.getId());
		ps.setString(i++, profile.getName());
		ps.setString(i++, profile.getContactNo());
		ps.setString(i++, profile.getCurrentCompany());
		ps.setString(i++, profile.getLocation());
		// adminDAO.getRequirementMappingValue(profile.getPrimarySkillAdd(), 11,
		// strUserId);
		// ps.setInt(i++,
		// configDAO.getConfigKeyValueMapping(profile.getPrimarySkillAdd()).getId());
		// ps.setInt(i++,Integer.parseInt(profile.getPrimarySkillAdd()));
		ps.setInt(i++, tmpUtil.getKeyByValue("primaryskill", profile.getPrimarySkillAdd()));
		ps.setDate(i++, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getProfileSharedDate()));
		ps.setString(i++, profile.getProfileSourceAdd());
		ps.setString(i++, profile.getProfileSharedBy());
		ps.setDouble(i++, profile.getYearsOfExperience());
		ps.setDouble(i++, profile.getRelevantExperience());
		ps.setInt(i++, profile.getCurrentCTC());
		ps.setInt(i++, profile.getExpectedCTC());
		ps.setInt(i++, profile.getNoticePeriod());
		ps.setString(i++, profile.getRemarks());
		ps.setTimestamp(i++, tmpDAOUtil.getCurrentTimestamp());
		ps.setString(i++, strUserId);
		ps.setString(i++, profile.getEmail());
		ps.setString(i++, profile.getReqRefNo());
	}

	private void populateProfileForUpdate(PreparedStatement ps, Profile profile, String userId) throws SQLException {

		if (ps == null) {
			return;
		}
		int i = 1;
		ps.setString(i++, profile.getName());
		ps.setString(i++, profile.getEmail());
		ps.setString(i++, profile.getContactNo());
		ps.setString(i++, profile.getCurrentCompany());
		ps.setString(i++, profile.getLocation());
		// ps.setInt(i++, profile.getPrimarySkill().getId());
		ps.setInt(i++, tmpUtil.getKeyByValue("primaryskill", profile.getPrimarySkill()));
		ps.setDate(i++, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getProfileSharedDate()));
		ps.setString(i++, profile.getProfileSharedBy());
		ps.setDouble(i++, profile.getYearsOfExperience());
		ps.setDouble(i++, profile.getRelevantExperience());
		ps.setInt(i++, profile.getNoticePeriod());
		ps.setInt(i++, profile.getCurrentCTC());
		ps.setInt(i++, profile.getExpectedCTC());
		ps.setInt(i++, configDAO.getConfigKeyValueMapping(profile.getIsAllocated1()).getId());
		ps.setDate(i++, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getAllocationStartDate()));
		ps.setDate(i++, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getAllocationEndDate()));
		ps.setDate(i++, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getCreatedOn()));
		ps.setTimestamp(i++, tmpDAOUtil.getCurrentTimestamp());
		ps.setInt(i++, profile.getProfileSource().getId());
		ps.setString(i++, profile.getRemarks());
		ps.setString(i++, userId);
		if (null != profile.getAccount() && profile.getAccount().getId() != 0) {
			ps.setInt(i++, profile.getAccount().getId());
		} else {
			// ps.setInt(i++, configDAO.getConfigKeyValueMapping("0").getId());
		}
		if (null != profile.getProject() && profile.getProject().getId() != 0) {
			ps.setInt(i++, profile.getProject().getId());
		} else {
			// ps.setInt(i++, configDAO.getConfigKeyValueMapping("0").getId());
		}
		// ps.setString(29, profile.getReqRefNo());
		ps.setInt(i++, profile.getId());

	}

	private void populateProfileForUpdateForMapping(PreparedStatement ps1, Profile profile, String userId)
			throws SQLException {
		if (ps1 == null) {
			return;
		}
		int i = 1;
		ps1.setInt(i++, profile.getInitialEvaluationResult().getId());
		if (null != profile.getCustomerInterviewStatus())
			ps1.setInt(i++, profile.getCustomerInterviewStatus().getId());
		else
			ps1.setInt(i++, 61);
		// ps1.setInt(i++, profile.getCustomerInterviewStatus().getId());
		ps1.setString(i++, profile.getRemarks());
		ps1.setDate(i++, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getInternalEvaluationResultDate()));
		ps1.setString(i++, profile.getProfileSharedCustomer());
		ps1.setDate(i++, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getProfileSharedCustomerDate()));
		if (null != profile.getOfferProcessingStatus()) {
			ps1.setInt(i++, profile.getOfferProcessingStatus().getId());
		} else {
			ps1.setInt(i++, 0);
		}
		ps1.setInt(i++, profile.getId());

	}

	/**
	 * This method deletes the selected profiles
	 */
	public int deleteProfile(ArrayList<String> profIdList) {
		StringBuffer sql2 = new StringBuffer(
				"SELECT DISTINCT REQUIREMENT_ID FROM REQUIREMENT_PROFILE_MAPPING WHERE PROFILE_ID IN(");
		StringBuffer sb = new StringBuffer();
		String reqRefNum = null;
		// ArrayList<String> profIdList = profileId;
		for (String profId : profIdList) {
			System.out.println("profI"+profId);
			if (profId != "") {
				sb.append(profId + ",");
			}
		}
		String withoutLastComma = sb.substring(0, sb.length() - 1);

		StringBuffer sql = new StringBuffer("DELETE FROM REQUIREMENT_PROFILE_MAPPING WHERE PROFILE_ID=?");
		StringBuffer sql1 = new StringBuffer("DELETE FROM PROFILE WHERE ID=?");
		// ArrayList<String> profIdList = profileId;
		for (String num : profIdList) {
			System.out.println("num1"+num);
		}
		int index = 0;

		int profileId = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		PreparedStatement ps7 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;

		try {
			sql2.append(withoutLastComma);
			sql2.append(")");
			conn = dataSource.getConnection();
			ps2 = conn.prepareStatement(sql2.toString());
			rs = ps2.executeQuery();
			ArrayList<String> reqList = new ArrayList<String>();
			if (rs != null) {

				while (rs.next()) {
					reqRefNum = rs.getString("REQUIREMENT_ID");
					reqList.add(reqRefNum);
				}
			}

			for (String profId : profIdList) {
				System.out.println(profId);
				conn = dataSource.getConnection();
				ps = conn.prepareStatement(sql.toString());
				ps1 = conn.prepareStatement(sql1.toString());
				if (null != profId && !profId.isEmpty()) {
					ps.setString(1, profId);
					ps1.setString(1, profId);
					int result = ps.executeUpdate();
					int result1 = ps1.executeUpdate();
					System.out.println("result==> " + result + "result1==> " + result1);
					++index;

				}
				if (null != profId && !profId.isEmpty()) {
					profileId = new Integer(profId);
				}
				for (String reqId : reqList) {
					StringBuffer sql4 = new StringBuffer(
							"select count(PROFILE_ID) as PROFILE_COUNT from tmp.requirement_profile_mapping where REQUIREMENT_ID=?");
					ps3 = conn.prepareStatement(sql4.toString());
					ps3.setString(1, reqId);
					rs1 = ps3.executeQuery();
					int profileCount = 0;
					if (rs1 != null) {

						while (rs1.next()) {
							profileCount = rs1.getInt("PROFILE_COUNT");
						}
						if (profileCount == 0) {
							String sql5 = "UPDATE REQUIREMENT SET STATUS=15,SHORTLISTED_PROFILE_ID=NULL WHERE ID=?";
							if (StringUtils.isNotBlank(sql5)) {
								System.out.println("sql2-->" + sql5);
								ps4 = conn.prepareStatement(sql5.toString());
								ps4.setString(1, reqRefNum);
								ps4.executeUpdate();
							}
						} else {
							ArrayList<RequirementProfileMapping> requirement = new ArrayList<RequirementProfileMapping>();
							sql4 = new StringBuffer(
									"select PROFILE_ID,INTERNAL_EVALUATION_RESULT,CUSTOMER_INTERVIEW_STATUS,PROFILE_SHARED_CUSTOMER,OFFER_PROCESSING_STATUS from tmp.requirement_profile_mapping where REQUIREMENT_ID=?");
							ps3 = conn.prepareStatement(sql4.toString());
							ps3.setString(1, reqId);
							rs1 = ps3.executeQuery();
							int profileIds = 0, proposedReqStatus = 15;
							if (rs1 != null) {
								while (rs1.next()) {
									RequirementProfileMapping requirement1 = new RequirementProfileMapping();
									requirement1.setProfileId(configDAO.getProfileName(rs1.getInt("PROFILE_ID")));
									requirement1.setInternalEvaluationResult(configDAO
											.getConfigKeyValueMapping(rs1.getInt("INTERNAL_EVALUATION_RESULT")));
									requirement1.setCustomerInterviewStatus(configDAO
											.getConfigKeyValueMapping(rs1.getInt("CUSTOMER_INTERVIEW_STATUS")));
									requirement1.setProfileSharedCustomer(rs1.getString("PROFILE_SHARED_CUSTOMER"));
									// added offer processing status as it is required in getActualRequirementStatus method
									requirement1.setOfferStatus(configDAO
											.getConfigKeyValueMapping(rs1.getInt("OFFER_PROCESSING_STATUS")));
									requirement.add(requirement1);
								}

							}

							String sql6 = "SELECT SHORTLISTED_PROFILE_ID FROM REQUIREMENT WHERE ID=?";
							String shortListedProfId = null;
							StringBuffer profId1 = new StringBuffer();
							ps5 = conn.prepareStatement(sql6.toString());
							ps5.setString(1, reqId);
							rs1 = ps5.executeQuery();
							if (rs1 != null) {
								while (rs1.next()) {
									shortListedProfId = rs1.getString("SHORTLISTED_PROFILE_ID");
								}
							}
							if (StringUtils.isNotBlank(shortListedProfId)) {
								String sql7 = "SELECT ID FROM PROFILE WHERE ID IN( " + shortListedProfId + ")";
								ps7 = conn.prepareStatement(sql7.toString());
								rs1 = ps7.executeQuery();
								if (rs1 != null) {
									while (rs1.next()) {
										profId1.append(rs1.getString("ID"));
										profId1.append(',');
									}
								}

								if (StringUtils.isNotBlank(profId1.toString())) {
									String withoutLastComma1 = profId1.substring(0, profId1.length() - 1);

									String sql5 = "UPDATE REQUIREMENT SET STATUS=?,SHORTLISTED_PROFILE_ID=? WHERE ID=?";
									ps4 = conn.prepareStatement(sql5.toString());
									int reqStatus = requirementDAO.getRequirement(reqId).getStatus().getId();
									ps4.setInt(1, getActualRequirementStatus(requirement, reqStatus, proposedReqStatus,
											profileId));
									ps4.setString(2, withoutLastComma1);
									ps4.setString(3, reqId);
									ps4.executeUpdate();
								} else {
									String sql5 = "UPDATE REQUIREMENT SET STATUS=?,SHORTLISTED_PROFILE_ID=? WHERE ID=?";
									ps4 = conn.prepareStatement(sql5.toString());
									int reqStatus = requirementDAO.getRequirement(reqId).getStatus().getId();
									int test = getActualRequirementStatus(requirement, reqStatus, proposedReqStatus,
											profileId);
									ps4.setInt(1, test);
									ps4.setString(2, null);
									ps4.setString(3, reqId);
									ps4.executeUpdate();
								}

							} else {
								String sql5 = "UPDATE REQUIREMENT SET STATUS=?,SHORTLISTED_PROFILE_ID=NULL WHERE ID=?";
								ps4 = conn.prepareStatement(sql5.toString());
								int reqStatus = requirementDAO.getRequirement(reqId).getStatus().getId();
								ps4.setInt(1, getActualRequirementStatus(requirement, reqStatus, proposedReqStatus,
										profileId));
								ps4.setString(2, reqId);
								ps4.executeUpdate();

							}

						}
					}
				}
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw new RuntimeException(sqlException);
		} finally {
			if (rs1 != null) {
				try {
					rs1.close();
				} catch (SQLException sqlException) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}
			}
			if (ps1 != null) {
				try {
					ps1.close();
				} catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}
			}
			if (ps3 != null) {
				try {
					ps3.close();
				} catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}
			}
			if (ps4 != null) {
				try {
					ps4.close();
				} catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}
			}
			if (ps5 != null) {
				try {
					ps5.close();
				} catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}
			}
			if (ps7 != null) {
				try {
					ps7.close();
				} catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}
			}
			closeDBObjects(conn, rs, ps2);
		}
		return index;
	}

	/**
	 * This method checks if the profile already exists for the requirement id
	 */
	public int isProfilesExist(Profile profile, String userId) {
		StringBuffer sql = new StringBuffer(
				"SELECT ID, EMAIL_ID, CONTACT_NO, REQUIREMENT_ID FROM PROFILE WHERE EMAIL_ID=? AND REQUIREMENT_ID = ?");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int id = 0;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, profile.getEmail());
			ps.setString(2, profile.getReqRefNo());
			rs = ps.executeQuery();

			if (rs != null) {

				while (rs.next()) {

					String contactNum = rs.getString("CONTACT_NO");
					String emailId = rs.getString("EMAIL_ID");
					String reqRefNum = rs.getString("REQUIREMENT_ID");

					if ((contactNum.equals(profile.getContactNo()) && reqRefNum.equals(profile.getReqRefNo()))
							|| (emailId.equals(profile.getEmail()) && reqRefNum.equals(profile.getReqRefNo()))) {
						id = rs.getInt("ID");
					} else {
						insertProfile(profile, userId);
					}

				}
			}

		} catch (SQLException e) {
			System.out.println("SQLException Occurred " + e.getMessage());
		} finally {
			closeDBObjects(conn, rs, ps);
		}
		return id;
	}

	/**
	 * This method checks if mapping exists for the particular profile
	 */
	public int isProfileMapingExist(int profileId, String refNo) {
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM REQUIREMENT_PROFILE_MAPPING WHERE REQUIREMENT_ID=? AND PROFILE_ID=?");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int id = 0;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, refNo);
			ps.setInt(2, profileId);
			rs = ps.executeQuery();

			if (rs != null) {

				while (rs.next()) {
					id = rs.getInt("ID");
				}
			}

		} catch (SQLException sqlException) {
			System.out.println("Exception Occurred " + sqlException.getMessage());
		} finally {
			closeDBObjects(conn, rs, ps);
		}
		return id;
	}

	/**
	 * This method inserts profile details from excel sheet
	 */
	public int insertProfile(Profile profile, String userId) {
		StringBuffer sql = new StringBuffer(
				"INSERT INTO PROFILE ( NAME, CONTACT_NO, CURRENT_COMPANY, LOCATION, PRIMARY_SKILL, PROFILE_SHARED_DATE, \r\n")
						.append("PROFILE_SOURCE, PROFILE_SHARED_BY, YEARS_OF_EXPERIENCE, RELEVANT_EXPERIENCE, CURRENT_CTC, EXPECTED_CTC, NOTICE_PERIOD, \r\n")
						.append(" REMARKS, CREATED_ON,\r\n")
						.append("CREATED_BY, EMAIL_ID, ALLOCATION_START_DATE, ALLOCATION_END_DATE, IS_ALLOCATED, REQUIREMENT_ID ) \r\n")
						.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet generatedKeys = null;

		int value = 0;
		int profileId = 0;
		int newProfileId = 0;
		int result = 0;
		int status = 0;
		int reqStatus = 0;
		try {
			conn = dataSource.getConnection();

			profileId = isProfilesExist(profile, userId);
			int initialEvalRes, customerInterviewStatus;
			if (StringUtils.isNotBlank(profile.getInitialEvaluationResultAdd())
					&& !profile.getInitialEvaluationResultAdd().equals("0")) {
				initialEvalRes = new Integer(profile.getInitialEvaluationResultAdd());
			} else {
				initialEvalRes = configDAO.getConfigKeyValueMapping("In Progress").getId();
			}

			if ((profile.getCustomerInterviewStatusAdd() != null)
					&& !(profile.getCustomerInterviewStatusAdd().isEmpty())) {
				customerInterviewStatus = new Integer(profile.getCustomerInterviewStatusAdd());
			} else {
				customerInterviewStatus = 0;
			}
			if (profileId == 0) {

				ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				populateProfileForInsertExcel(ps, profile, userId);
				value = ps.executeUpdate();

				generatedKeys = ps.getGeneratedKeys();
				if (generatedKeys.next()) {
					profileId = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}

				profile.setId(profileId);

				status = ProfileRequirementStatusMappingUtil.findDashboardStatus(initialEvalRes, "No",
						customerInterviewStatus, 0);

				reqStatus = requirementDAO.getRequirement(profile.getReqRefNo()).getStatus().getId();
				String sql2 = null;
				if (status > reqStatus) {
					sql2 = "UPDATE REQUIREMENT SET STATUS=? WHERE ID=?";
					if (StringUtils.isNotBlank(sql2)) {
						System.out.println("sql2-->" + sql2);
						ps2 = conn.prepareStatement(sql2.toString());
						ps2.setInt(1, status);
						ps2.setString(2, profile.getReqRefNo());
						ps2.executeUpdate();
					}
				}
			} else {
				profile.setId(profileId);

				status = ProfileRequirementStatusMappingUtil.findDashboardStatus(initialEvalRes,
						profile.getProfileSharedCustomer(), customerInterviewStatus, 0);

				reqStatus = requirementDAO.getRequirement(profile.getReqRefNo()).getStatus().getId();
				String sql2 = null;
				if (status > reqStatus) {
					sql2 = "UPDATE REQUIREMENT SET STATUS=15 WHERE ID=?";
					if (StringUtils.isNotBlank(sql2)) {
						System.out.println("sql2-->" + sql2);
						ps2 = conn.prepareStatement(sql2.toString());
						ps2.setString(1, profile.getReqRefNo());
						ps2.executeUpdate();
					}
				}
			}

		} catch (SQLException sqlException) {
			sqlException.getMessage();
		} finally {
			if (ps2 != null) {
				try {
					ps2.close();
				} catch (SQLException sqlException) {
				}
			}
			closeDBObjects(conn, generatedKeys, ps);
		}
		return profileId;
	}

	private void populateProfileForInsertExcel(PreparedStatement ps, Profile profile, String userId)
			throws SQLException {

		if (ps == null) {
			return;
		}

		ps.setString(1, profile.getName());
		ps.setString(2, profile.getContactNo());
		if (profile.getCurrentCompany() != null) {
			ps.setString(3, profile.getCurrentCompany());
		} else {
			ps.setString(3, "");
		}

		if (profile.getLocation() != null) {
			ps.setString(4, profile.getLocation());
		} else {
			ps.setInt(4, configDAO.getConfigKeyValueMapping("0").getId());
		}

		if ((profile.getPrimarySkillAdd() != null) && !(profile.getPrimarySkillAdd().isEmpty())) {
			ps.setInt(5, configDAO.getConfigKeyValueMapping(profile.getPrimarySkillAdd()).getId());
		} else {
			ps.setInt(5, configDAO.getConfigKeyValueMapping("0").getId());
		}

		if (profile.getProfileSharedDate() != null) {
			ps.setDate(6, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getProfileSharedDate()));
		} else {
			ps.setString(6, "0000-00-00");
		}

		if ((profile.getProfileSourceAdd() != null) && !(profile.getProfileSourceAdd().isEmpty())) {
			ps.setInt(7, configDAO.getConfigKeyValueMapping(profile.getProfileSourceAdd()).getId());
		} else {
			ps.setInt(7, configDAO.getConfigKeyValueMapping("0").getId());
		}

		if (profile.getProfileSharedBy() != null) {
			ps.setString(8, profile.getProfileSharedBy());
		} else {
			ps.setString(8, "");
		}

		if (profile.getYearsOfExperience() > 0) {
			ps.setDouble(9, profile.getYearsOfExperience());
		} else {
			ps.setString(9, "");
		}

		if (profile.getRelevantExperience() > 0) {
			ps.setDouble(10, profile.getRelevantExperience());
		} else {
			ps.setString(10, "");
		}

		if (profile.getCurrentCTC() > 0) {
			ps.setInt(11, profile.getCurrentCTC());
		} else {
			ps.setString(11, "");
		}

		if (profile.getExpectedCTC() > 0) {
			ps.setInt(12, profile.getExpectedCTC());
		} else {
			ps.setString(12, "");
		}

		if (profile.getNoticePeriod() > 0) {
			ps.setInt(13, profile.getNoticePeriod());
		} else {
			ps.setString(13, "");
		}

		if (profile.getRemarks() != null) {
			ps.setString(14, profile.getRemarks());
		} else {
			ps.setString(14, "");
		}
		ps.setTimestamp(15, tmpDAOUtil.getCurrentTimestamp());
		ps.setString(16, userId);
		ps.setString(17, profile.getEmail());

		if (profile.getAllocationStartDate() != null) {
			ps.setDate(18, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getAllocationStartDate()));
		} else {
			ps.setString(18, "0000-00-00");
		}

		if (profile.getAllocationEndDate() != null) {
			ps.setDate(19, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getAllocationEndDate()));
		} else {
			ps.setString(19, "0000-00-00");
		}

		if ((profile.getIsAllocated1() != null) && !(profile.getIsAllocated1().isEmpty())) {
			ps.setInt(20, configDAO.getConfigKeyValueMapping(profile.getIsAllocated1()).getId());
		} else {
			ps.setInt(20, 0);
		}

		ps.setString(21, profile.getReqRefNo());
	}

	/**
	 * This method inserts into the requirement_pofile_mapping the common details of
	 * both profile and requirement
	 */
	public int insertProfileMapping(Profile profile, String refNo, String userId) {
		StringBuffer sql = new StringBuffer(
				"INSERT INTO REQUIREMENT_PROFILE_MAPPING(REQUIREMENT_ID,PROFILE_ID,INTERNAL_EVALUATION_RESULT,CUSTOMER_INTERVIEW_STATUS,"
						+ "REMARKS,CREATED_ON,CREATED_BY, INTERNAL_EVALUATION_RESULT_DATE,PROFILE_SHARED_CUSTOMER,PROFILE_SHARED_CUSTOMER_DATE)VALUES(?,?,?,?,?,?,?,?,?,?)");
		Connection conn = null;
		PreparedStatement ps = null;

		int value = 0;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			populateProfileMappingForInsertExcel(ps, profile, refNo, userId);
			value = ps.executeUpdate();

		} catch (SQLException sqlException) {
			System.out.println("SQLException Occurred " + sqlException.getMessage());
		} finally {
			closeDBObjects(conn, null, ps);
		}
		return value;
	}

	private void populateProfileMappingForInsertExcel(PreparedStatement ps, Profile profile, String refNo,
			String userId) throws SQLException {

		if (null != ps) {

			if (StringUtils.isNotBlank(refNo)) {
				ps.setString(1, refNo);
			} else {
				ps.setString(1, profile.getReqRefNo());
			}

			ps.setInt(2, profile.getId());

			if (StringUtils.isNotBlank(profile.getInitialEvaluationResultAdd())
					&& !profile.getInitialEvaluationResultAdd().equals("0")) {
				ps.setInt(3, new Integer(profile.getInitialEvaluationResultAdd()));
			} else {
				ps.setInt(3, configDAO.getConfigKeyValueMapping("In Progress").getId());
			}

			if ((profile.getCustomerInterviewStatusAdd() != null)
					&& !(profile.getCustomerInterviewStatusAdd().isEmpty())) {
				ps.setInt(4, new Integer(profile.getCustomerInterviewStatusAdd()));
			} else {
				ps.setInt(4, configDAO.getConfigKeyValueMapping("Yet to Process").getId());
			}

			if (profile.getRemarks() != null) {
				ps.setString(5, profile.getRemarks());
			} else {
				ps.setString(5, "");
			}

			ps.setTimestamp(6, tmpDAOUtil.getCurrentTimestamp());
			ps.setString(7, userId);
			ps.setDate(8, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getInternalEvaluationResultDate()));

			ps.setString(9, profile.getProfileSharedCustomer());

			ps.setDate(10, tmpDAOUtil.convertUtilDatetoSQLDate(profile.getProfileSharedCustomerDate()));
		}
	}

	/**
	 * This method used to get Excel File
	 * 
	 * @param workingDirectory input parameters
	 * @param fileName         File Object
	 * @param request          HttpServletRequest Object
	 * @param userId           input parameters
	 */

	public int getExcelFile(String workingDirectory, File fileName, String file1, HttpServletRequest request,
			String userId) {

		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;

		int result = 0;
		try {
			fis = new FileInputStream(file1);
			// Create an excel workbook from the file system.
			workbook = new XSSFWorkbook(fis);
			// Get the first sheet on the workbook.
			sheet = workbook.getSheetAt(0);
			Iterator<?> rows = sheet.rowIterator();

			while (rows.hasNext()) {

				XSSFRow row = (XSSFRow) rows.next();
				XSSFCell cell;
				for (int i = 0; i < row.getLastCellNum(); i++) {
					cell = row.getCell(i, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					System.out.print("cell==> " + cell.toString() + " ");
				}
				// system.out.println ("Row No.: " + row.getRowNum ());
				if (row.getRowNum() == 0) {
					continue; // just skip the rows if row number is 0 or 1
				} else {
					Iterator<?> cells = row.cellIterator();
					Profile profile = new Profile();
					DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
					while (cells.hasNext()) {
						String refNo = ((XSSFCell) cells.next()).toString().trim();
						profile.setReqRefNo(refNo);
						profile.setName(((XSSFCell) cells.next()).toString().trim());
						profile.setEmail(((XSSFCell) cells.next()).toString().trim());
						profile.setContactNo(
								new BigDecimal(((XSSFCell) cells.next()).getNumericCellValue()).toString().trim());

						String currentCompany = ((XSSFCell) cells.next()).toString().trim();
						if ((currentCompany != null) && (!currentCompany.isEmpty())) {
							profile.setCurrentCompany(currentCompany);
						} else {
							profile.setCurrentCompany("");
						}

						String location = ((XSSFCell) cells.next()).toString().trim();
						if ((location != null) && (!location.isEmpty())) {
							profile.setLocation(location);
						} else {
							profile.setLocation("");
						}

						String primarySkill = ((XSSFCell) cells.next()).toString().trim();
						if ((primarySkill != null) && (!primarySkill.isEmpty())) {
							profile.setPrimarySkillAdd(primarySkill);
						} else {
							profile.setPrimarySkillAdd("");
						}

						String profSharedDate = ((XSSFCell) cells.next()).toString().trim();
						if ((profSharedDate != null) && (!profSharedDate.equals(""))) {
							Date profileSharedDate = formatter.parse(profSharedDate);
							profile.setProfileSharedDate(profileSharedDate);
						} else {
							Date profileSharedDate = null;
							profile.setProfileSharedDate(profileSharedDate);
						}

						String profileSharedBy = (((XSSFCell) cells.next()).toString().trim());
						if ((profileSharedBy != null) && (!profileSharedBy.isEmpty())) {
							profile.setProfileSharedBy(profileSharedBy);
						} else {
							profile.setProfileSharedBy("");
						}

						String yearsofExp = ((XSSFCell) cells.next()).toString().trim();
						if ((yearsofExp != null) && (!yearsofExp.isEmpty())) {
							int yearsOfExperience = (int) Double.parseDouble(yearsofExp);
							profile.setYearsOfExperience(yearsOfExperience);
						} else {
							profile.setYearsOfExperience(0);
						}

						String relevantExp = ((XSSFCell) cells.next()).toString().trim();
						if ((relevantExp != null) && (!relevantExp.isEmpty())) {
							int relevantExperience = (int) Double.parseDouble(relevantExp);
							profile.setRelevantExperience(relevantExperience);
						} else {
							profile.setRelevantExperience(0);
						}

						String noticePerd = ((XSSFCell) cells.next()).toString().trim();
						if ((noticePerd != null) && (!noticePerd.isEmpty())) {
							int noticePeriod = (int) Double.parseDouble(noticePerd);
							profile.setNoticePeriod(noticePeriod);
						} else {
							profile.setNoticePeriod(0);
						}

						String currCTC = ((XSSFCell) cells.next()).toString().trim();
						if ((currCTC != null) && (!currCTC.isEmpty())) {
							int currentCTC = (int) Double.parseDouble(currCTC);
							profile.setCurrentCTC(currentCTC);
						} else {
							profile.setCurrentCTC(0);
						}

						String expectCTC = ((XSSFCell) cells.next()).toString().trim();
						if ((expectCTC != null) && (!expectCTC.isEmpty())) {
							int expectedCTC = (int) Double.parseDouble(expectCTC);
							profile.setExpectedCTC(expectedCTC);
						} else {
							profile.setExpectedCTC(0);
						}

						String allocation = ((XSSFCell) cells.next()).toString().trim();
						if ((allocation != null) && (!allocation.isEmpty())) {
							// int isAllocated=(int)Integer.parseInt(allocation);
							profile.setIsAllocated1(allocation);
						} else {
							profile.setIsAllocated1("No");
						}

						String allocStartDate = ((XSSFCell) cells.next()).toString().trim();
						if ((allocStartDate != null) && (!allocStartDate.equals(""))) {
							Date allocationStartDate = formatter.parse(allocStartDate);
							profile.setAllocationStartDate(allocationStartDate);
						} else {
							Date allocationStartDate = null;
							profile.setAllocationStartDate(allocationStartDate);
						}
						String allocEndDate = ((XSSFCell) cells.next()).toString().trim();
						if ((allocEndDate != null) && (!allocEndDate.equals(""))) {
							Date allocationEndDate = formatter.parse(allocEndDate);
							profile.setAllocationEndDate(allocationEndDate);
						} else {
							Date allocationEndDate = null;
							profile.setAllocationEndDate(allocationEndDate);
						}

						String profileSourceAdd = ((XSSFCell) cells.next()).toString().trim();
						if ((profileSourceAdd != null) && (!profileSourceAdd.isEmpty())) {
							profile.setProfileSourceAdd(profileSourceAdd);
						} else {
							profile.setProfileSourceAdd("");
						}

						String remarks = ((XSSFCell) cells.next()).toString();
						if ((remarks != null) && (!remarks.isEmpty())) {
							profile.setRemarks(remarks);
						} else {
							profile.setRemarks("");
						}

						int profileId = tmpUtil.isProfilesExist(profile, userId);
						if (profileId == 0) {
							int id = tmpUtil.insertProfile(profile, userId);
							profile.setId(id);
							int profId = profile.getId();
							if (profId > 0) {
								int profileMapingId = tmpUtil.isProfileMapingExist(profId, refNo);
								if (profileMapingId == 0) {
									profile.setId(profId);
									if (profile.getId() > 0) {
										result = tmpUtil.createProfileRequirementMapping(profile, refNo, userId);
									}
								}
							}
						} else {
							int profileMapingId = tmpUtil.isProfileMapingExist(profileId, refNo);
							if (profileMapingId == 0) {
								profile.setId(profileId);
								if (profile.getId() > 0) {
									result = tmpUtil.createProfileRequirementMapping(profile, refNo, userId);
								}
							}
							workbook.close();
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception occured" + e.getMessage());
		}

		finally {

			if (fis != null) {

				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

		return result;
	}

	/**
	 * This method checks if the requirement id already exists for the profile id
	 * 
	 * @param profile
	 * @return
	 */
	public String isReqExist(int profile) {
		StringBuffer sql = new StringBuffer("Select rp.REQUIREMENT_ID FROM tmp.requirement_profile_mapping rp\r\n"
				+ "INNER JOIN profile p ON rp.PROFILE_ID = p.ID where p.ID=?");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String reqId = null;
		int id = 0;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, profile);
			rs = ps.executeQuery();

			if (rs != null) {

				while (rs.next()) {

					reqId = rs.getString("REQUIREMENT_ID");

				}
			}

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}
		return reqId;
	}

}
