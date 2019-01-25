package com.tmp.scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tmp.dao.ConfigDAO;
import com.tmp.dao.impl.BaseDAO;
import com.tmp.entity.Profile;
import com.tmp.entity.Profiles;
import com.tmp.entity.Requirement;
import com.tmp.entity.Requirements;
import com.tmp.entity.User;
import com.tmp.util.TMPDAOUtil;


public class SchedulerDAO extends BaseDAO{
	
	@Autowired(required = true)
	@Qualifier("configDAO")
	ConfigDAO configDAO;
	
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
	
	
	public TMPDAOUtil getTmpDAOUtil() {
		return tmpDAOUtil;
	}
	public void setTmpDAOUtil(TMPDAOUtil tmpDAOUtil) {
		this.tmpDAOUtil = tmpDAOUtil;
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	public Requirements getRequirements() {
		StringBuffer sql = new StringBuffer("SELECT * FROM REQUIREMENT");

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			conn = dataSource.getConnection();
			 ps = conn.prepareStatement(sql.toString());
			List<Requirement> requirementsList = null;
			Requirements requirements = null;
			 rs = ps.executeQuery();
			if (rs != null) {
				requirements = new Requirements();
				requirementsList = new ArrayList<Requirement>();
				while (rs.next()) {
					requirementsList.add(populateRequirement(rs));
				}
				requirements.setRequirements(requirementsList);
			}
			
			return requirements;
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}
	} 
	
	private Requirement populateRequirement(ResultSet rs) throws SQLException {
		
		Requirement requirement = new Requirement();
		requirement.setId(rs.getString("ID"));
		requirement.setActivityOwner(rs.getString("ACTIVITY_OWNER"));
		requirement.setActivityOwnerEmail(rs.getString("ACTIVITY_OWNER_EMAIL"));
		requirement.setActualClosureDate(rs.getDate("ACTUAL_CLOSURE_DATE"));
		requirement.setActualOwner(rs.getString("ACTUAL_OWNER"));
		requirement.setActualOwnerEmail(rs.getString("ACTUAL_OWNER_EMAIL"));
		requirement.setBillingRate(rs.getDouble("BILLING_RATE"));
		requirement.setCity(rs.getString("CITY"));
		requirement.setCreatedOn(rs.getDate("CREATED_ON"));
		requirement.setExpectedDOJ(rs.getDate("EXPECTED_DOJ"));
		requirement.setIntimatedBy(rs.getString("INTIMATED_BY"));
		requirement.setIntimationDate(rs.getDate("INTIMATION_DATE"));
		requirement.setIntimatorEmail(rs.getString("INTIMATOR_EMAIL"));
		requirement.setJo(rs.getInt("JO"));
		requirement.setJobDescription(rs.getString("JOB_DESCRIPTION"));
		requirement.setRemarks(rs.getString("REMARKS"));
		requirement.setSo(rs.getInt("SO"));
		requirement.setUpdatedOn(rs.getTimestamp("UPDATED_ON"));
		requirement.setCriticality(configDAO.getConfigKeyValueMapping(rs.getInt("CRITICALITY")));
		requirement.setIntimationMode(configDAO.getConfigKeyValueMapping(rs.getInt("INTIMATION_MODE")));
		requirement.setLocation(configDAO.getConfigKeyValueMapping(rs.getInt("LOCATION")));
		requirement.setPrimarySkill(configDAO.getConfigKeyValueMapping(rs.getInt("PRIMARY_SKILL")).getConfigValue().getValue());
		requirement.setRequirementType(configDAO.getConfigKeyValueMapping(rs.getInt("REQUIREMENT_TYPE")));
		requirement.setSkillCategory(configDAO.getConfigKeyValueMapping(rs.getInt("SKILL_CATEGORY")).getConfigValue().getValue());
		requirement.setStatus(configDAO.getConfigKeyValueMapping(rs.getInt("STATUS")));
		requirement.setAccount(tmpDAOUtil.getAccount(rs.getInt("ACCOUNT")));
		requirement.setProject(tmpDAOUtil.getProject(rs.getInt("PROJECT")));
		requirement.setBand(rs.getString("BAND"));
		requirement.setYearExperience(rs.getString("YEAR_EXPERIENCE"));
		requirement.setQuantity(rs.getString("QUANTITY"));
		requirement.setProjectDuration(rs.getString("PROJECT_DURATION"));
		requirement.setIbg_cdg(configDAO.getAdminInfoKeyValueMapping(rs.getInt("IBG_CDG")).getAdminInfoValue().getValue());
		requirement.setIbu_cdu(configDAO.getAdminInfoKeyValueMapping(rs.getInt("IBU_CDU")).getAdminInfoValue().getValue());
		requirement.setPid_crmid_so(rs.getString("PID_CRMID_SO"));
		requirement.setUpdatedBy(configDAO.getUserId(rs.getInt("UPDATED_BY")));
		requirement.setCreatedBy(configDAO.getUserId(rs.getInt("CREATED_BY")));
		requirement.setUserId(rs.getInt("CREATED_BY"));
		requirement.setOppurtunityStatus(configDAO.getConfigKeyValueMapping(rs.getInt("OPPORTUNITY_STATUS")));
		requirement.setShortlistedProfile_id(configDAO.getProfileName(rs.getInt("SHORTLISTED_PROFILE_ID")));
		return requirement;
	}
	public String getEmailId(int userId) {

		StringBuffer sql = new StringBuffer("SELECT Email FROM tmp.user where ID=?");

		Connection conn = null;
		String eMail = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					 eMail= rs.getString("Email");
				}
			}
			rs.close();
			ps.close();
			return eMail;
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
	
	public String getIBUHeadEmailId(int userId) {

		StringBuffer sql = new StringBuffer("SELECT u.email from user_account_mapping ac\r\n" + 
				"INNER JOIN User u ON u.ID = ac.user_id\r\n" + 
				"where u.ROLE = 2 AND ac.ACCOUNT_ID = ?");

		Connection conn = null;
		String eMail = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					 eMail= rs.getString("email");
				}
			}
			rs.close();
			ps.close();
			return eMail;
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
	
}
