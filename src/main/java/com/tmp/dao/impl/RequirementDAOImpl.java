package com.tmp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tmp.dao.AdminDAO;
import com.tmp.dao.ConfigDAO;
import com.tmp.dao.RequirementDAO;
import com.tmp.entity.Account;
import com.tmp.entity.Project;
import com.tmp.entity.Requirement;
import com.tmp.entity.RequirementProfileMapping;
import com.tmp.entity.Requirements;
import com.tmp.util.TMPDAOUtil;
import com.tmp.util.TMPUtil;

@Repository
@Qualifier("requirementDAO")
public class RequirementDAOImpl extends BaseDAO implements RequirementDAO {

	@Autowired(required = true)
	@Qualifier("configDAO")
	ConfigDAO configDAO;
	
	public AdminDAO getAdminDAO() {
		return adminDAO;
	}
	public void setAdminDAO(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}
	@Autowired(required = true)
	@Qualifier("adminDAO")
	AdminDAO adminDAO;

	@Autowired(required = true)
	@Qualifier("tmpDAOUtil")
	TMPDAOUtil tmpDAOUtil;

	@Autowired(required = true)
	@Qualifier("tmpUtil")
	TMPUtil tmpUtil;
	
	public TMPUtil getTmpUtil() {
		return tmpUtil;
	}
	public void setTmpUtil(TMPUtil tmpUtil) {
		this.tmpUtil = tmpUtil;
	}

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * This method provides all the requirements based on the status, location and account
	 */
	public Requirements getRequirements(String location, String account, String status) {
		StringBuffer sql = new StringBuffer("SELECT * FROM REQUIREMENT WHERE STATUS IN (?) ");

		if (!StringUtils.isEmpty(location)) {
			sql.append("AND LOCATION IN (?) ");
		}

		if (!StringUtils.isEmpty(account)) {
			sql.append("AND ACCOUNT IN (?) ");
		}

		sql.append("ORDERBY CREATED_ON DESC");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Requirement> requirementsList = null;
		Requirements requirements = null;
		
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, status);
			ps.setString(2, location);
			ps.setString(3, account);
			
			rs = ps.executeQuery();
			if (rs != null) {
				requirements = new Requirements();
				requirementsList = new ArrayList<Requirement>();
				while (rs.next()) {
					requirementsList.add(populateRequirement(rs));
				}
				requirements.setRequirements(requirementsList);
			}
			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}return requirements;
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

	private Requirement populateRequirement(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}

		Requirement requirement = new Requirement();
		requirement.setId(rs.getString("ID"));
		requirement.setActivityOwner(rs.getString("ACTIVITY_OWNER"));
		requirement.setActivityOwnerEmail(rs.getString("ACTIVITY_OWNER_EMAIL"));
		requirement.setActualClosureDate(rs.getDate("ACTUAL_CLOSURE_DATE"));
		requirement.setPlannedClosureDate(rs.getDate("PLANNED_CLOSURE_DATE"));
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
		requirement.setUpdatedOn(rs.getDate("UPDATED_ON"));
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
		requirement.setOppurtunityStatus(configDAO.getConfigKeyValueMapping(rs.getInt("OPPORTUNITY_STATUS")));
		requirement.setShortlistedProfile_id(configDAO.getProfileName(rs.getInt("SHORTLISTED_PROFILE_ID")));
		return requirement;
	}

	/**
	 * This method displays the details of the particular requirement by id.
	 */
	public Requirement getRequirement(String requirementId) {
		StringBuffer sql = new StringBuffer("SELECT * FROM REQUIREMENT WHERE ID = ?");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Requirement requirement = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, requirementId);
			
			rs = ps.executeQuery();
			if (rs != null) {
				requirement = new Requirement();
				while (rs.next()) {
					requirement = populateRequirement(rs);
				}
			}
			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}return requirement;
	}

	public Requirements createRequirements(List<Requirement> requirements) {
		return null;
	}

	/**
	 * This method creates new requirement
	 */
	@Override
	public int createRequirement(Requirement requirement, String userId, String userName) {
		StringBuffer sql = new StringBuffer(
				"INSERT INTO REQUIREMENT (ID, CRITICALITY, SKILL_CATEGORY, PRIMARY_SKILL, JOB_DESCRIPTION, \r\n")
				.append("LOCATION, CITY, BILLING_RATE, INTIMATION_DATE, INTIMATED_BY, INTIMATOR_EMAIL, INTIMATION_MODE, \r\n")
				.append("REQUIREMENT_TYPE, EXPECTED_DOJ, SO, JO, \r\n")
				.append("REMARKS, IBG_CDG, IBU_CDU, ACCOUNT, PROJECT, CREATED_ON, CREATED_BY, BAND ,QUANTITY, PROJECT_DURATION, PID_CRMID_SO, YEAR_EXPERIENCE, \r\n")
				.append("SKILL_CATEGORY2, SKILL_CATEGORY3, SKILL_CATEGORY4, STATUS, OPPORTUNITY_STATUS) \r\n")
				.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		Connection conn = null;
		PreparedStatement ps = null;
		
		int value = 0;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			requirement.setId(getRequirementId(requirement, userName, userId));
			populateRequirementForInsert(ps, requirement, userId);
			value = ps.executeUpdate();
			System.out.println("Successfully the requirement has been created!! "+requirement.getId());
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println("Failure!! the requirement has not been created!! "+requirement.getId());
		} finally {
			closeDBObjects(conn, null, ps);
		}
		return value;
	}
	
	
	private void populateRequirementForInsert(PreparedStatement ps, Requirement requirement, String userId) throws SQLException {

		if (ps == null) {
			return;
		}
		
		ps.setString(1, requirement.getId());
		ps.setString(2, requirement.getCriticality1());
		//ps.setInt(3, Integer.parseInt(requirement.getSkillCategoryAdd1()));
		//reqBulk.setPositionStatus(getKeyByValue("positionstatus", cell.getStringCellValue()));
		ps.setInt(3, tmpUtil.getKeyByValue("skillcategory", requirement.getSkillCategoryAdd1()));
		//ps.setInt(4, Integer.parseInt(requirement.getPrimarySkill1()));
		ps.setInt(4, tmpUtil.getKeyByValue("primaryskill", requirement.getPrimarySkill1()));
		ps.setString(5, requirement.getJobDescription());
		ps.setString(6, requirement.getLocation1());
		ps.setString(7, requirement.getCity());
		ps.setDouble(8, requirement.getBillingRate());
		ps.setDate(9, tmpDAOUtil.convertUtilDatetoSQLDate(requirement.getIntimationDate()));
		ps.setString(10, requirement.getIntimatedBy());
		ps.setString(11, requirement.getIntimatorEmail());
		ps.setString(12,requirement.getIntimationModeAdd());
		ps.setString(13, requirement.getRequirementTypeAdd());
		ps.setDate(14, tmpDAOUtil.convertUtilDatetoSQLDate(requirement.getExpectedDOJ()));
		//ps.setDate(15, tmpDAOUtil.convertUtilDatetoSQLDate(requirement.getActualClosureDate()));
		ps.setInt(15, requirement.getSo());
		ps.setInt(16, requirement.getJo());
		ps.setString(17, requirement.getRemarks());
		adminDAO.getRequirementAdminMappingValue(requirement.getIbg_cdg(), 4, userId);
		ps.setInt(18, configDAO.getAdminInfoKeyValueMapping(requirement.getIbg_cdg()).getId());
		adminDAO.getRequirementAdminMappingValue(requirement.getIbu_cdu(), 5, userId);
		ps.setInt(19, configDAO.getAdminInfoKeyValueMapping(requirement.getIbu_cdu()).getId());
		ps.setInt(20, tmpUtil.getAccountIdByName(requirement.getAccount1(), userId));
		ps.setInt(21, tmpUtil.getProjectNameByAccountId(requirement.getProjectAdd(), tmpUtil.getAccountIdByName(requirement.getAccount1(), userId)));
		ps.setTimestamp(22, tmpDAOUtil.getCurrentTimestamp());
		ps.setString(23, userId);
		ps.setString(24, requirement.getBand());
		ps.setString(25, requirement.getQuantity());
		ps.setString(26, requirement.getProjectDuration());
		ps.setString(27, requirement.getPid_crmid_so());
		ps.setString(28, requirement.getYearExperience());
		ps.setString(29, requirement.getSkillCategoryAdd2());
		ps.setString(30, requirement.getSkillCategoryAdd3());
		ps.setString(31, requirement.getSkillCategoryAdd4());
		ps.setInt(32, configDAO.getConfigKeyValueMapping("Profile Sourcing").getId());
		ps.setInt(33, configDAO.getConfigKeyValueMapping("Open").getId());

	}

	public Requirements updateRequirements(List<Requirement> requirements) {
		return null;
	}

	/**
	 * This method updates the fields of the requirement on click of id
	 */
	public int updateRequirement(Requirement requirement, String userId) {
		StringBuffer sql = new StringBuffer(
				"UPDATE REQUIREMENT SET CRITICALITY=?, SKILL_CATEGORY =?, PRIMARY_SKILL=?, JOB_DESCRIPTION=?, \r\n")
		.append("LOCATION=?, CITY=?, BILLING_RATE=?, INTIMATION_DATE=?, INTIMATED_BY=?, INTIMATOR_EMAIL=?, INTIMATION_MODE=?, \r\n")
		.append("REQUIREMENT_TYPE=?, EXPECTED_DOJ=?, ACTUAL_CLOSURE_DATE=?, SO=?, JO=?, STATUS=?, ACTIVITY_OWNER=?, ACTIVITY_OWNER_EMAIL=?,\r\n")
		.append("ACTUAL_OWNER=?, ACTUAL_OWNER_EMAIL=?, REMARKS=?, UPDATED_ON=?, BAND=?, YEAR_EXPERIENCE=?, QUANTITY=?, PROJECT_DURATION=?, IBG_CDG=?, IBU_CDU=?, PID_CRMID_SO=?, UPDATED_BY=?,OPPORTUNITY_STATUS=?, PLANNED_CLOSURE_DATE=?  WHERE ID=?");

		Connection conn = null;
		PreparedStatement ps = null;
		int result=0;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			populateRequirementForUpdate(ps, requirement, userId);
			result=ps.executeUpdate();
			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
			
		} finally {
			closeDBObjects(conn, null, ps);
		}return result;
	}

	private void populateRequirementForUpdate(PreparedStatement ps, Requirement requirement, String userId ) throws SQLException {

		if (ps == null) {
			return;
		}
		ps.setInt(1, requirement.getCriticality().getId());
		ps.setInt(2, tmpUtil.getKeyByValue("skillcategory", requirement.getSkillCategory()));
		ps.setInt(3, tmpUtil.getKeyByValue("primaryskill", requirement.getPrimarySkill()));
		ps.setString(4, requirement.getJobDescription());
		ps.setInt(5, requirement.getLocation().getId());
		ps.setString(6, requirement.getCity());
		ps.setDouble(7, requirement.getBillingRate());
		ps.setDate(8, tmpDAOUtil.convertUtilDatetoSQLDate(requirement.getIntimationDate()));
		ps.setString(9, requirement.getIntimatedBy());
		ps.setString(10, requirement.getIntimatorEmail());
		ps.setInt(11, requirement.getIntimationMode().getId());
		ps.setInt(12, requirement.getRequirementType().getId());
		ps.setDate(13, tmpDAOUtil.convertUtilDatetoSQLDate(requirement.getExpectedDOJ()));
		ps.setDate(14, tmpDAOUtil.convertUtilDatetoSQLDate(requirement.getActualClosureDate()));
		ps.setInt(15, requirement.getSo());
		ps.setInt(16, requirement.getJo());
		ps.setInt(17, requirement.getStatus().getId());
		ps.setString(18, requirement.getActivityOwner());
		ps.setString(19, requirement.getActivityOwnerEmail());
		ps.setString(20, requirement.getActualOwner());
		ps.setString(21, requirement.getActualOwnerEmail());
		ps.setString(22, requirement.getRemarks());
		ps.setTimestamp(23, tmpDAOUtil.getCurrentTimestamp());
		ps.setString(24,requirement.getBand());
		ps.setString(25, requirement.getYearExperience());
		ps.setString(26,requirement.getQuantity());
		ps.setString(27,requirement.getProjectDuration());
		adminDAO.getRequirementAdminMappingValue(requirement.getIbg_cdg(), 4, userId);
		ps.setInt(28,configDAO.getAdminInfoKeyValueMapping(requirement.getIbg_cdg()).getId());
		adminDAO.getRequirementAdminMappingValue(requirement.getIbu_cdu(), 5, userId);
		ps.setInt(29,configDAO.getAdminInfoKeyValueMapping(requirement.getIbu_cdu()).getId());
		ps.setString(30,requirement.getPid_crmid_so());
		ps.setString(31, userId);
		ps.setInt(32, requirement.getOppurtunityStatus().getId());
		ps.setDate(33, tmpDAOUtil.convertUtilDatetoSQLDate(requirement.getPlannedClosureDate()));
		ps.setString(34, requirement.getId());
	}

	/**
	 * This method deletes the selected requirement
	 */
	public int deleteRequirement(ArrayList<String> requirementId) {

		StringBuffer sql = new StringBuffer("DELETE FROM REQUIREMENT_PROFILE_MAPPING WHERE REQUIREMENT_ID=?");
		StringBuffer sql1 = new StringBuffer("DELETE FROM PROFILE WHERE REQUIREMENT_ID=?");
		StringBuffer sql2 = new StringBuffer("DELETE FROM REQUIREMENT WHERE ID=?");
		ArrayList<String> reqIdList = requirementId;
		 for (String num : reqIdList) { 		      
	           System.out.println(num); 		
	      }
		int index = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		int result = 0;
		try {
			for (String reqId : reqIdList) { 
		
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps1 = conn.prepareStatement(sql1.toString());
			ps2 = conn.prepareStatement(sql2.toString());
			
			if(null!=reqId){
				ps.setString(1, reqId);
				ps1.setString(1, reqId);
				ps2.setString(1, reqId);
				ps.executeUpdate();
				ps1.executeUpdate();
				result = ps2.executeUpdate();
				++index;
			}
		} 
		}catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps1 != null) {
				try {
					ps1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			closeDBObjects(conn, null, ps2);
		}
		return index;
	}

	/**
	 * Generates requirement ID
	 * Format : YYYY_MM_AccountName_NameOfUser_incrementor
	 * Eg : 2018_08_FD_SS_01
	 * @param projectName 
	 * @param accountName 
	 * @param userName 
	 * @return
	 */
	private String getRequirementId(Requirement requirement, String userName, String userId) { 
		String requirementId = null;
		String accountName = null, projectName = null;
		int accountId = 0;
		int projectId = 0;
		String incrementor=null;
		String dbDate="";
		try {
			
			
			accountId = tmpUtil.getAccountIdByName(requirement.getAccount1(), userId);
			accountName = tmpDAOUtil.getAccount(accountId).getAccountName();
			projectId = tmpUtil.getProjectNameByAccountId(requirement.getProjectAdd(), tmpUtil.getAccountIdByName(requirement.getAccount1(), userId));
			
			projectName = tmpDAOUtil.getProject(projectId).getProjectName();
			
			requirementId = getLatestRequirementId();
			
			incrementor = getLatestIdForAccountAndProject(accountId, projectId);
			
			System.out.println("accountName "+accountName+" projectName "+projectName +" requirementId "+requirementId);
			System.out.println("INcrementor "+incrementor);
			requirementId = getRandomString(dbDate, incrementor, accountName, projectId+"", userName);
			System.out.println(" New Req ID : "+requirementId);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return requirementId;
	}

	private static String getRandomString(String dbDate, String incrementor, String accountName, String projectName, String userName) {

		DateFormat df = new SimpleDateFormat("yyyy_MM");
		Date today = Calendar.getInstance().getTime(); 
		String currDate = df.format(today);
		String defaultIncrementor="01";
		String reqRandomNo =null;
		String shortNames = "_"+getShortName(accountName)+"_"+projectName+"_"+getShortName(userName)+"_";
		try {
			
			if(dbDate == null){
				reqRandomNo = currDate+shortNames+defaultIncrementor;
			}else{
				int reqDateIncr= Integer.parseInt(incrementor)+1;
			
			if(reqDateIncr<=9){
				reqRandomNo = currDate+shortNames+0+reqDateIncr;
			}else{
				reqRandomNo = currDate+shortNames+reqDateIncr;
			}
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return reqRandomNo;
	}
	
	private static String getShortName(String name) {
		
		String shortName = "";
		String[] words = name.split(" ");
		if(words.length == 1) { // ONE WORD ONLY
			shortName = words[0].substring(0, 2);
		}else{
			shortName = words[0].substring(0, 1)+words[1].substring(0, 1);
		}
		return shortName.toUpperCase();
	}
	
	private String getLatestRequirementId() {
		StringBuffer sql = new StringBuffer("SELECT ID FROM REQUIREMENT ORDER BY CREATED_ON DESC LIMIT 1");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String id=null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			
			rs = ps.executeQuery();
			if (rs.next()) {
				id= rs.getString("ID");
			}
			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}return id;
	}
	private String getLatestIdForAccountAndProject(int accountId, int projectId) {
		
		System.out.println(" account *"+accountId +" project *"+projectId);
		StringBuffer sql = new StringBuffer("select count(*) as ID from requirement where account=? and project=?");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String id="0";
		
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			
			ps.setInt(1, accountId);
			ps.setInt(2, projectId);
			rs = ps.executeQuery();
				if (rs.next()) {
					id = rs.getString("ID");
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeDBObjects(conn, rs, ps);
		}
		return id;
	}
	
	private boolean isRequirementExist(String requirementId) {
		StringBuffer sql = new StringBuffer("SELECT * FROM REQUIREMENT WHERE ID = ?");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist = false;
		try {
			
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, requirementId);
			rs = ps.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					isExist = true;
				}
			}
			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}return isExist;
	}

	public Requirement populateBlock(ResultSet rs1) throws SQLException{


		if (rs1 == null) {
			return null;
		}

	Requirement temp1 =new Requirement();

		while(rs1.next())
		{

			String status=rs1.getString(2);

			int count=rs1.getInt(1);

			if(status.equals("Lead Generation")){
				temp1.setLeadcount(count);
			}else if(status.equals("Profile Sourcing")){
				temp1.setProfilecount(count);;
			}else if(status.equals("Technical Evaluation")){
				temp1.setTechevalcount(count);
			}else if(status.equals("Customer Evaluation")){
				temp1.setCustevalcount(count);;
			}else if(status.equals("Offer Processing")){
				temp1.setOffercount(count);;
			}else if(status.equals("Onboarding")){
				temp1.setBoardingcount(count);
			}else if(status.equals("Hold Opportunity")){
				temp1.setHoldOpporCount(count);;
			}else if(status.equals("Abandoned Oppurtunity")){
				temp1.setAbandonedOpporCount(count);;
			}else if(status.equals("Lost Opportunity")){
				temp1.setLostOppurCount(count);;
			}
		}

		temp1.setTotalcount(temp1.getLeadcount()+temp1.getProfilecount()+temp1.getTechevalcount()+temp1.getCustevalcount()+temp1.getOffercount()+temp1.getBoardingcount()
		+temp1.getHoldOpporCount()+temp1.getAbandonedOpporCount()+temp1.getLostOppurCount());

		return temp1;

	}
	/**
	 * Get the requirement values
	 */
	public Requirement getRequirementValues(String userId) {
		StringBuffer sql = new StringBuffer("SELECT COUNT(distinct R.ID) AS COUNT, CV.VALUE FROM REQUIREMENT R "
				+ "INNER JOIN CONFIG_KEY_VALUE_MAPPING C ON R.STATUS = C.ID "
				+ "INNER JOIN CONFIG_VALUE CV ON C.CONFIG_VALUE_ID = CV.ID "
				+ "INNER JOIN USER_ACCOUNT_MAPPING Y ON Y.ACCOUNT_ID = R.ACCOUNT "
				+ "INNER JOIN USER Z ON Z.ID = Y.USER_ID "
				+ "WHERE Z.ID = ? GROUP BY R.STATUS");

		Connection conn = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		Requirement requirement = null;
		try {
			conn = dataSource.getConnection();
			ps1 = conn.prepareStatement(sql.toString());
			ps1.setString(1, userId);
			
			rs1 = ps1.executeQuery();

			requirement=populateBlock(rs1);

			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs1, ps1);
		}return requirement;
	}
	public Requirement populateBlock1(ResultSet rs1) throws SQLException{


		if (rs1 == null) {
			return null;
		}

		Requirement temp1 =new Requirement();

		while(rs1.next())
		{

			String status=rs1.getString(2);

			int count=rs1.getInt(1);

			if(status.equals("Lead Generation")){
				temp1.setLeadcount(count);
			}else if(status.equals("Profile Sourcing")){
				temp1.setProfilecount(count);;
			}else if(status.equals("Technical Evaluation")){
				temp1.setTechevalcount(count);
			}else if(status.equals("Customer Evaluation")){
				temp1.setCustevalcount(count);;
			}else if(status.equals("Offer Processing")){
				temp1.setOffercount(count);;
			}else if(status.equals("Onboarding")){
				temp1.setBoardingcount(count);
			}else if(status.equals("Lost Opportunity")) {
				temp1.setLostOppurCount(count);
			}else if(status.equals("Abandoned Opportunity")) {
				temp1.setAbandonedOpporCount(count);
			}else if(status.equals("Hold Opportunity")) {
				temp1.setHoldOpporCount(count);
			}
		}

		temp1.setTotalcount(temp1.getLeadcount()+temp1.getProfilecount()+temp1.getTechevalcount()
				+temp1.getCustevalcount()+temp1.getOffercount()+temp1.getBoardingcount()+temp1.getAbandonedOpporCount()+
				temp1.getLostOppurCount()+temp1.getHoldOpporCount());

		return temp1;

	}
	
	/**
	 * Get the requirement based on location and account
	 */
	@Override
	public Requirement getRequirementValuesSubmit(Requirement requirement, String userId) {

		Connection conn = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		Requirement requirement1=null;

		try {
		
				StringBuffer queryString1 = new StringBuffer("SELECT COUNT(STATUS) AS COUNT, CV.VALUE FROM REQUIREMENT R "
						+ "INNER JOIN CONFIG_KEY_VALUE_MAPPING C ON R.STATUS = C.ID "
						+ "INNER JOIN CONFIG_VALUE CV ON C.CONFIG_VALUE_ID = CV.ID "
						+ "INNER JOIN USER_ACCOUNT_MAPPING Y ON Y.ACCOUNT_ID = R.ACCOUNT "
						+ "INNER JOIN USER Z ON Z.ID = Y.USER_ID "
						+ "WHERE Z.ID = ?");
			
			if(null != requirement.getLocation1() && !requirement.getLocation1().isEmpty()){
				String[] location = requirement.getLocation1().split(",");
				queryString1.append("AND ");
				queryString1.append("R.LOCATION IN (");

				for (int i = 0; i < location.length; i++) {
					queryString1.append(location[i]);
					if (location.length > i + 1) {
						queryString1.append(",");
					}
				}
				queryString1.append(") ");
				
			}
			if(null != requirement.getAccount1() && !requirement.getAccount1().isEmpty()){
				String[] account = requirement.getAccount1().split(",");
				queryString1.append("AND ");
				queryString1.append("R.ACCOUNT IN");
				queryString1.append(" (");
				
				for (int j = 0; j < account.length; j++) {
					queryString1.append(account[j]);
					if (account.length > j + 1) {
						queryString1.append(",");
					}
				}
				queryString1.append(") ");
				
			}
			
			queryString1.append("GROUP BY R.STATUS ");
			
			conn = dataSource.getConnection();
			ps1 = conn.prepareStatement(queryString1.toString());
			ps1.setString(1, userId);
			rs1 = ps1.executeQuery();

			requirement1=populateBlock1(rs1);

			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs1, ps1);
		}return requirement1;
	}
	
	private ArrayList<Requirement> populateTable(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}

		 ArrayList<Requirement>  tmp=new ArrayList<Requirement>();

		while(rs.next()){
			Requirement rq = new Requirement();
			rq.setId(rs.getString(1));
			rq.setCriticality1(rs.getString(2));
			rq.setPrimarySkill1(rs.getString(3));
			rq.setLocation2(rs.getString(4));
			rq.setIntimationDate(tmpDAOUtil.convertUtilDatetoSQLDate(rs.getDate(5)));
			rq.setExpectedDOJ(tmpDAOUtil.convertUtilDatetoSQLDate(rs.getDate(6)));
			rq.setStatus1(rs.getString(7));
			rq.setOppurtunitystatus(rs.getString(8));
			rq.setProfileshared(rs.getInt(9));
			if(rs.getString(10)!=null){
			String shortlist=rs.getString(10);			 			
			String[] shortlistId = shortlist.split(",");
			int count=shortlistId.length;
			rq.setCustomershortlisted(String.valueOf(count));
			}else{
			rq.setCustomershortlisted("0");
			}
			int internalEvalInProg = rq.getProfileshared()-Integer.parseInt(rq.getCustomershortlisted());
			
			rq.setInternalEvaluation1(internalEvalInProg);
			
			tmp.add(rq);
		}
		return tmp ;
	}
	/**
	 * Provides the requirement details for table 
	 */
	public ArrayList<Requirement> getRequirementTable(String userId){
		StringBuffer sql = new StringBuffer("SELECT R.ID, C1.VALUE, C2.VALUE, C3.VALUE, INTIMATION_DATE,EXPECTED_DOJ, C4.VALUE, C6.VALUE, COUNT(DISTINCT R1.PROFILE_ID), \r\n")
        .append("R.SHORTLISTED_PROFILE_ID, COUNT(R1.INTERNAL_EVALUATION_RESULT) FROM REQUIREMENT R \r\n ") 
        .append("LEFT JOIN CONFIG_VALUE C1 ON C1.ID=(SELECT CONFIG_VALUE_ID FROM CONFIG_KEY_VALUE_MAPPING CKVM WHERE CKVM.ID=R.CRITICALITY ) \r\n")
        .append("LEFT JOIN CONFIG_VALUE C2 ON C2.ID = (SELECT CONFIG_VALUE_ID FROM CONFIG_KEY_VALUE_MAPPING CKVM WHERE CKVM.ID= R.PRIMARY_SKILL) \r\n")
        .append("LEFT JOIN CONFIG_VALUE C3 ON C3.ID = (SELECT CONFIG_VALUE_ID FROM CONFIG_KEY_VALUE_MAPPING CKVM WHERE CKVM.ID= R.LOCATION) \r\n")
        .append("LEFT JOIN CONFIG_VALUE C4 ON C4.ID = (SELECT CONFIG_VALUE_ID FROM CONFIG_KEY_VALUE_MAPPING CKVM WHERE CKVM.ID= R.STATUS ) \r\n")
        .append("LEFT JOIN CONFIG_VALUE C6 ON C6.ID =(SELECT CONFIG_VALUE_ID FROM CONFIG_KEY_VALUE_MAPPING CKVM WHERE CKVM.ID= R.OPPORTUNITY_STATUS ) \r\n")
        .append("LEFT JOIN REQUIREMENT_PROFILE_MAPPING R1 ON R1.REQUIREMENT_ID = R.ID LEFT JOIN PROFILE P ON P.ID = R1.PROFILE_ID \r\n")
        .append("INNER JOIN USER_ACCOUNT_MAPPING Y ON Y.ACCOUNT_ID = R.ACCOUNT ")
        .append("INNER JOIN USER Z ON Z.ID = Y.USER_ID ")
        .append("WHERE Z.ID = ? GROUP BY R.ID ORDER BY R.CREATED_ON DESC");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Requirement> requirement = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1,  userId);
			
			
			rs = ps.executeQuery();

			requirement=populateTable(rs);

			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}return requirement;
		
		
	}
	/**
	 * Provides account list
	 */
	public ArrayList<Account> getAccountList(){
		StringBuffer sql = new StringBuffer("SELECT * FROM account_master;");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Account>  tmp=null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			
			rs = ps.executeQuery();

			if (rs == null) {
				return null;
			}

			 tmp=new ArrayList<Account>();

			while(rs.next()){
				Account rq = new Account();
				rq.setAccountId((rs.getInt(1)));
				rq.setAccountName((rs.getString(2)));
				
				tmp.add(rq);
			}
			
			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}return tmp ;
	}
	/**
	 * Provides project list
	 */
	public ArrayList<Project> getProjectList(){
		StringBuffer sql = new StringBuffer("SELECT * FROM Projects;");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Project>  tmp=null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			
			rs = ps.executeQuery();

			if (rs == null) {
				return null;
			}

			 tmp = new ArrayList<Project>();

			while(rs.next()){
				Project rq = new Project();
				rq.setId((rs.getInt(1)));
				rq.setProjectName((rs.getString(3)));
				rq.setAccountId(rs.getInt(7));
				
				tmp.add(rq);
			}
			
			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}return tmp ;
	}
	
	private ArrayList<Requirement> populateTableSubmit(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}

		 ArrayList<Requirement>  tmp=new ArrayList<Requirement>();

		while(rs.next()){
			Requirement rq = new Requirement();
			rq.setId(rs.getString(1));
			rq.setCriticality1(rs.getString(2));
			rq.setPrimarySkill1(rs.getString(3));
			rq.setLocation2(rs.getString(4));
			rq.setIntimationDate(tmpDAOUtil.convertUtilDatetoSQLDate(rs.getDate(5)));
			rq.setExpectedDOJ(tmpDAOUtil.convertUtilDatetoSQLDate(rs.getDate(6)));
			rq.setStatus1(rs.getString(7));
			rq.setOppurtunitystatus(rs.getString(8));
			rq.setProfileshared(rs.getInt(9));
			if(rs.getString(10)!=null){
				String shortlist=rs.getString(10);			 			
				String[] shortlistId = shortlist.split(",");
				int count=shortlistId.length;
				rq.setCustomershortlisted(String.valueOf(count));
				}else{
				rq.setCustomershortlisted("0");
				}
				rq.setInternalEvaluation1(rs.getInt(11));
			
			tmp.add(rq);
		}

		

		
		return tmp ;
	}
	/**
	 * Provides requirement details for table
	 */
	public ArrayList<Requirement> getRequirementTable(Requirement requirement, String userId){
		
		Connection conn = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		String location = null;
		String account = null;
		ArrayList<Requirement> requirement1=null;
		try {
			
			StringBuilder queryString = new StringBuilder("SELECT R.ID, C1.VALUE, C2.VALUE, C3.VALUE, INTIMATION_DATE,EXPECTED_DOJ, "
					+ "C4.VALUE, C6.VALUE, COUNT(DISTINCT R1.REQUIREMENT_ID), R.SHORTLISTED_PROFILE_ID, COUNT(DISTINCT R1.INTERNAL_EVALUATION_RESULT),COUNT(DISTINCT R1.CUSTOMER_INTERVIEW_STATUS) FROM REQUIREMENT R "
					+ "LEFT JOIN CONFIG_VALUE C1 ON C1.ID=(SELECT CONFIG_VALUE_ID FROM CONFIG_KEY_VALUE_MAPPING CKVM WHERE CKVM.ID=R.CRITICALITY ) "
					+ "LEFT JOIN CONFIG_VALUE C2 ON C2.ID = (SELECT CONFIG_VALUE_ID FROM CONFIG_KEY_VALUE_MAPPING CKVM WHERE CKVM.ID= R.PRIMARY_SKILL) "
					+ "LEFT JOIN CONFIG_VALUE C3 ON C3.ID = (SELECT CONFIG_VALUE_ID FROM CONFIG_KEY_VALUE_MAPPING CKVM WHERE CKVM.ID= R.LOCATION) "
					+ "LEFT JOIN CONFIG_VALUE C4 ON C4.ID = (SELECT CONFIG_VALUE_ID FROM CONFIG_KEY_VALUE_MAPPING CKVM WHERE CKVM.ID= R.STATUS ) "
					+ "LEFT JOIN CONFIG_VALUE C6 ON C6.ID =(SELECT CONFIG_VALUE_ID FROM CONFIG_KEY_VALUE_MAPPING CKVM WHERE CKVM.ID= R.OPPORTUNITY_STATUS ) "
					+ "LEFT JOIN REQUIREMENT_PROFILE_MAPPING R1 ON R1.REQUIREMENT_ID = R.ID LEFT JOIN PROFILE P ON P.ID = R1.PROFILE_ID "
					+ "INNER JOIN USER_ACCOUNT_MAPPING Y ON Y.ACCOUNT_ID = R.ACCOUNT "
					+ "INNER JOIN USER Z ON Z.ID = Y.USER_ID "
					+ "WHERE Z.ID = ?");
			
			location = requirement.getLocation1();
			account = requirement.getAccount1();
			
			if(StringUtils.isNotBlank(location) && StringUtils.isNotBlank(account)){
				queryString = queryString.append(" AND R.LOCATION IN (");
				queryString = queryString.append(location+") AND R.ACCOUNT IN (");
				queryString = queryString.append(account+")");
			}else if(StringUtils.isNotBlank(location)){
				queryString = queryString.append(" AND R.LOCATION IN (");
				queryString = queryString.append(location+") ");
			}else if(StringUtils.isNotBlank(account)){
				queryString = queryString.append(" AND R.ACCOUNT IN (");
				queryString = queryString.append(account+") ");
			}
			
			queryString = queryString.append(" GROUP BY R.ID"); 
			
			conn = dataSource.getConnection();
			ps1 = conn.prepareStatement(queryString.toString());
			
			ps1.setString(1,  userId);
			rs1 = ps1.executeQuery();

		requirement1=populateTableSubmit(rs1);

			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs1, ps1);
		}return requirement1;
		
		
	}
	
	private ArrayList<Requirement> populateTableCustomername(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}

		 ArrayList<Requirement>  tmp=new ArrayList<Requirement>();

		while(rs.next()){
			Requirement rq = new Requirement();
			rq.setId(rs.getString(1));
			rq.setCriticality1(rs.getString(2));
			rq.setPrimarySkill1(rs.getString(3));
			rq.setLocation2(rs.getString(4));
			rq.setIntimationDate(tmpDAOUtil.convertUtilDatetoSQLDate(rs.getDate(5)));
			rq.setExpectedDOJ(tmpDAOUtil.convertUtilDatetoSQLDate(rs.getDate(6)));
			rq.setStatus1(rs.getString(7));
			rq.setOppurtunitystatus(rs.getString(8));
			rq.setProfileshared(rs.getInt(9));
			if(rs.getString(10)!=null){
				String shortlist=rs.getString(10);			 			
				String[] shortlistId = shortlist.split(",");
				int count=shortlistId.length;
				rq.setCustomershortlisted(String.valueOf(count));
				}else{
				rq.setCustomershortlisted("0");
				}
				rq.setInternalEvaluation1(rs.getInt(11));
			tmp.add(rq);
		}

		

		
		return tmp ;
	}
	/**
	 * Provides requirement details for table based on account name
	 */
	public ArrayList<Requirement> getRequirementCustomerTable(String accountName){

		StringBuffer sql = new StringBuffer("SELECT R.ID, C1.VALUE, C2.VALUE, C3.VALUE, INTIMATION_DATE, EXPECTED_DOJ, "
				+ "C4.VALUE, C6.VALUE, COUNT(R1.REQUIREMENT_ID),R.SHORTLISTED_PROFILE_ID, COUNT(R1.INTERNAL_EVALUATION_RESULT) "
				+ "FROM REQUIREMENT R LEFT JOIN CONFIG_VALUE C1 ON C1.ID=(SELECT CONFIG_VALUE_ID FROM CONFIG_KEY_VALUE_MAPPING CKVM WHERE CKVM.ID=R.CRITICALITY) "
				+ "LEFT JOIN CONFIG_VALUE C2 ON C2.ID = (SELECT CONFIG_VALUE_ID FROM CONFIG_KEY_VALUE_MAPPING CKVM WHERE CKVM.ID = R.PRIMARY_SKILL) "
				+ "LEFT JOIN CONFIG_VALUE C3 ON C3.ID = (SELECT CONFIG_VALUE_ID FROM CONFIG_KEY_VALUE_MAPPING CKVM WHERE CKVM.ID= R.LOCATION) "
				+ "LEFT JOIN CONFIG_VALUE C4 ON C4.ID = (SELECT CONFIG_VALUE_ID FROM CONFIG_KEY_VALUE_MAPPING CKVM WHERE CKVM.ID= R.STATUS) "
				+ "LEFT JOIN CONFIG_VALUE C6 ON C6.ID =(SELECT CONFIG_VALUE_ID FROM CONFIG_KEY_VALUE_MAPPING CKVM WHERE CKVM.ID= R.OPPORTUNITY_STATUS) "
				+ "LEFT JOIN REQUIREMENT_PROFILE_MAPPING R1 ON R1.REQUIREMENT_ID = R.ID LEFT JOIN PROFILE P ON P.ID= R1.PROFILE_ID "
				+ "WHERE R.ACCOUNT= ? GROUP BY R.ID ");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Requirement> requirement = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, tmpDAOUtil.getAccountFromName(accountName).getAccountId());
			
			
			rs = ps.executeQuery();

			requirement=populateTableCustomername(rs);

			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}return requirement;
		
		
	}
	
	/**
	 * Provides requirement details based on account id and name
	 */
	public Requirement getRequirementValuesCustomerName(String name) {
		StringBuffer sql = new StringBuffer("SELECT COUNT(STATUS) AS COUNT, CV.VALUE FROM REQUIREMENT R "
				+ "INNER JOIN CONFIG_KEY_VALUE_MAPPING C ON R.STATUS = C.ID INNER JOIN CONFIG_VALUE CV ON C.CONFIG_VALUE_ID = CV.ID "
				+ "WHERE R.ACCOUNT=? GROUP BY R.STATUS");

		Connection conn = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		int accId = 0;
		int acctnameId = 0;
		Requirement requirement = null;
		try {
			conn = dataSource.getConnection();
			ps1 = conn.prepareStatement(sql.toString());
			accId = configDAO.getAdminInfoKeyValueMapping(name).getId();
			acctnameId = tmpDAOUtil.getAccountFromName(name).getAccountId();
			ps1.setInt(1, acctnameId);
			
			rs1 = ps1.executeQuery();

			requirement=populateBlock(rs1);

			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs1, ps1);
	}return requirement;
	}
	
	/**
	 * Get details from mapping table for requirement and profile
	 */
	public ArrayList<RequirementProfileMapping> getRequirementProfile(String requirementId){
		
		StringBuffer sql = new StringBuffer("SELECT PROFILE_ID,INTERNAL_EVALUATION_RESULT,CUSTOMER_INTERVIEW_STATUS,PROFILE_SHARED_CUSTOMER,OFFER_PROCESSING_STATUS,"
				+ "REMARKS FROM REQUIREMENT_PROFILE_MAPPING WHERE REQUIREMENT_ID = ?");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RequirementProfileMapping> requirement = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, requirementId);
			
			rs = ps.executeQuery();
			
		requirement = populateRequirementProfile(rs);
			
			
			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}return requirement;
		
	}
	private ArrayList<RequirementProfileMapping> populateRequirementProfile(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}
		ArrayList<RequirementProfileMapping> mapping=new ArrayList<RequirementProfileMapping>();
		if (rs != null) {
			
			while (rs.next()) {
				RequirementProfileMapping requirement =new RequirementProfileMapping() ;
		requirement.setProfileId(configDAO.getProfileName(rs.getInt("PROFILE_ID")));
		requirement.setInternalEvaluationResult(configDAO.getConfigKeyValueMapping(rs.getInt("INTERNAL_EVALUATION_RESULT")));
		requirement.setCustomerInterviewStatus(configDAO.getConfigKeyValueMapping(rs.getInt("CUSTOMER_INTERVIEW_STATUS")));
		requirement.setProfileSharedCustomer(rs.getString("PROFILE_SHARED_CUSTOMER"));
		requirement.setOfferStatus(configDAO.getConfigKeyValueMapping(rs.getString("OFFER_PROCESSING_STATUS")));
		requirement.setRemarks(rs.getString("REMARKS"));
		mapping.add(requirement);
	   }
		}
		return mapping;
		}
	
	/**
	 * Provides requirement id and profile id from mapping table
	 */
public ArrayList<RequirementProfileMapping> getRequirementProfileName(String requirementId){
		
		StringBuffer sql = new StringBuffer("SELECT REQUIREMENT_ID, PROFILE_ID FROM REQUIREMENT_PROFILE_MAPPING WHERE REQUIREMENT_ID = ?");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RequirementProfileMapping> requirement = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());			
			ps.setString(1, requirementId);			
						
			rs = ps.executeQuery();			
		requirement = populateRequirementProfileName(rs);			
			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}return requirement;
		
	}
private ArrayList<RequirementProfileMapping> populateRequirementProfileName(ResultSet rs) throws SQLException {
	if (rs == null) {
		return null;
	}
	ArrayList<RequirementProfileMapping> mapping=new ArrayList<RequirementProfileMapping>();
	if (rs != null) {
		
		while (rs.next()) {
	RequirementProfileMapping requirement =new RequirementProfileMapping() ;
	requirement.setRequirementId1(rs.getString("REQUIREMENT_ID"));
	requirement.setProfileId(configDAO.getProfileName(rs.getInt("PROFILE_ID")));	
	mapping.add(requirement);
   }
	}
	
	return mapping;
	}
	
/**
 * While updating provides data for shortlistedProfileId, updatedOn, updatedBy based on id
 */
public int updateShortlistedProfile(Requirement requirement, String userId) {
	StringBuffer sql = new StringBuffer(
			"UPDATE REQUIREMENT SET SHORTLISTED_PROFILE_ID=?, UPDATED_ON=?, UPDATED_BY=?  WHERE ID=?");
	Connection conn = null;
	PreparedStatement ps = null;
	int result=0;
	try {
		conn = dataSource.getConnection();
		ps = conn.prepareStatement(sql.toString());		
		populateshortlistedProfileForUpdate(ps, requirement, userId);		
		result=ps.executeUpdate();
		
	} catch (SQLException sqlException) {
		throw new RuntimeException(sqlException);
		
	} finally {
		closeDBObjects(conn, null, ps);
	}return result;
}
private void populateshortlistedProfileForUpdate(PreparedStatement ps, Requirement requirement, String userId ) throws SQLException {

	if (ps == null) {
		return;
	}
	ps.setString(1, requirement.getShortlistedProfiles());	
	ps.setTimestamp(2, tmpDAOUtil.getCurrentTimestamp());	
	ps.setString(3, userId);	
	ps.setString(4, requirement.getId());
}


/**
 * Displays ShortlistedProfileId from requirement
 */

public  ArrayList<Requirement> getShortlistedProfiles(String requirementId) {
	StringBuffer queryString = new StringBuffer("SELECT SHORTLISTED_PROFILE_ID FROM REQUIREMENT WHERE ID =?");

	Connection conn = null;
	PreparedStatement ps = null;
	PreparedStatement ps1 = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	ArrayList<Requirement> requirement=null;
	String id=null;
	try {
		conn = dataSource.getConnection();
		
		ps = conn.prepareStatement(queryString.toString());
		ps.setString(1, requirementId);
		rs = ps.executeQuery();
		if (rs != null) {
			while (rs.next()) {
				id=rs.getString("SHORTLISTED_PROFILE_ID");
			}
			}
		StringBuffer queryString1=  new StringBuffer();
	   if(StringUtils.isNotBlank(id)){
			queryString1 = queryString1.append(" SELECT NAME, ID FROM PROFILE WHERE ID IN(");
			queryString1 = queryString1.append(id+") ");
		}
		ps1 = conn.prepareStatement(queryString1.toString());
	
		rs1 = ps1.executeQuery();
		requirement=populateShortlistedProfiles(rs1);

		
	} catch (SQLException sqlException) {
		throw new RuntimeException(sqlException);
	} finally {
		if (rs1 != null) {
			try {
				rs1.close();
			} catch (SQLException sqlException) {
			}
		}
		if (ps1 != null) {
			try {
				ps1.close();
			} catch (SQLException sqlException) {
			}
		}
		closeDBObjects(conn, rs, ps);
	}return requirement;
}

public ArrayList<Requirement>  populateShortlistedProfiles(ResultSet rs) throws SQLException{

	if (rs == null) {
		return null;
	}
	ArrayList<Requirement> mapping=new ArrayList<Requirement>();
	
	

	while(rs.next())
	{
		Requirement requirement=new Requirement();
		requirement.setShortlistedProfiles(rs.getString("NAME"));
		requirement.setShortlistProfileId(rs.getInt("ID"));
		mapping.add(requirement);
	}

	return mapping;

}

}
	

