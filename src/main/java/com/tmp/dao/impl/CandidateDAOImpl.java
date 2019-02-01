package com.tmp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import com.mysql.jdbc.Statement;
import com.tmp.dao.CandidateDAO;
import com.tmp.entity.Associate;
import com.tmp.entity.Departments;

/**
 * This class used to insert candidate details into table
 * 
 * @author AJ00561494
 *
 */
public class CandidateDAOImpl extends BaseDAO implements CandidateDAO {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	
	public void insertCandidateList(ArrayList<Associate> candidateList, String userId, String userName) {
		int successCount = 0;
		int failedCount = 0;
		int candidateListSize = candidateList.size();
		for (Associate candidate : candidateList) {
			int ibuId = getIbuByName(candidate.getEmpIBU());
			int primarySkillId = getSkillByName(candidate.getPrimarySkill());
			int secondarySkillId = getSkillByName(candidate.getSecondarySkill());
			int projId = getProjectAccountDetails(candidate.getProjectId());
			candidate.setIbuId(ibuId);
			candidate.setPrimarySkillId(primarySkillId);
			candidate.setSecondarySkillId(secondarySkillId);
			candidate.setProjId(projId);
			int empId = isResourceExist(candidate, userId, userName);
			if (empId != 0) {
				insertAssociateProjectDetails(empId, projId, candidate);
				successCount++;
			}
			
		}
		System.out.println("Total Records : "+candidateList);
		System.out.println("Records inserted : "+successCount);
		failedCount = candidateListSize - successCount;
		System.out.println("Records failed to insert : "+failedCount);
	}

	/**
	 * This method used to check whether associate details exits already To avoid
	 * duplicate entry
	 * 
	 */
	public int isResourceExist(Associate associate, String userId, String userName) {
		StringBuffer sql = new StringBuffer("SELECT ID FROM ASSOCIATE WHERE EMP_ID=?");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int id = 0;
		int recordCount = 0;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, associate.getEmpId());
			rs = ps.executeQuery();

			if (rs != null) {

				while (rs.next()) {

					recordCount = 1;
					break;

				}

			}

			if (recordCount == 0)
				id = insertCandidateDetails(associate, userId, userName);

		} catch (SQLException e) {
			System.out.println("SQLException Occurred " + e.getMessage());
		} finally {
			closeDBObjects(conn, rs, ps);
		}
		return id;
	}

	/**
	 * This method used to insert data into Associate table
	 * 
	 * @param candidate
	 * @param userId
	 * @param userName
	 * @return generatedKey
	 */

	public int insertCandidateDetails(Associate candidate, String userId, String userName) {
		StringBuffer sql = new StringBuffer(
				"INSERT INTO tmp.associate (EMP_ID, EMP_NAME, BAND, GENDER, HTR_FLAG, EMP_IBU_ID, TOTAL_EXPERIENCE, \r\n")
						.append("TECHM_EXPERIENCE, CURRENT_COUNTRY, CURRENT_LOCATION_CITY, ONSITE_OFFSHORE, BILLABILITY_STATUS, \r\n")
						.append("BILLABILITY_START_DATE, BILLABILITY_END_DATE, SUPERVISOR_NAME, PROJECT_MANAGER_NAME, PROGRAM_MANAGER_NAME, \r\n")
						.append("PRIMARY_SKILL_ID, SECONDARY_SKILL_ID, CERTIFICATIONS_DONE, PROJECT_ID )\r\n")
						.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int generatedKey = 0;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			populateCandidateDetailsForInsert(ps, candidate, userId);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				generatedKey = rs.getInt(1);
			}

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				closeDBObjects(conn, null, ps);
			}
		}
		return generatedKey;
	}

	public ArrayList<Departments> getSkillChartData(String location) {
		String whereClause = "";

		if (!location.equalsIgnoreCase("na")) {
			whereClause = " ASSOCIATE.ONSITE_OFFSHORE=? AND ";
		}
		StringBuffer sql = new StringBuffer("SELECT SKILL.SKILL_NAME AS SKILL, COUNT(ASSOCIATE.PRIMARY_SKILL_ID) AS COUNT FROM \r\n")
				.append("ASSOCIATE, SKILL WHERE "
						+ whereClause
						+ "ASSOCIATE.PRIMARY_SKILL_ID = SKILL.ID \r\n")
				.append(" GROUP BY ASSOCIATE.PRIMARY_SKILL_ID");
		
		System.out.println("sql query is "+sql.toString());
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String primarySkillName = "";
		int skillCount = 0;
		int recordCount = 0;
		ArrayList<Departments> departmentList = new ArrayList<Departments>();
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			
			if(!location.equalsIgnoreCase("na"))
				ps.setString(1, location);
			
			rs = ps.executeQuery();
			
			Departments department = null;
			
			while(rs.next()) {
				skillCount = rs.getInt("COUNT");
				primarySkillName = rs.getString("SKILL");
				recordCount++;
				department = new Departments(recordCount+"", skillCount+"", primarySkillName);
				departmentList.add(department);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (conn != null) {
				closeDBObjects(conn, null, ps);
			}
		}
		return departmentList;
	}
	
	public ArrayList<Departments> getAccountChartData(String location){
		
		String whereClause = "";
		if(!location.equalsIgnoreCase("na"))
			whereClause = " associate.ONSITE_OFFSHORE=? AND ";
		
		StringBuffer sql = new StringBuffer("select project_account_master.CUSTOMER_NAME as Account, count(associate.PROJECT_ID) ")
				.append("as count from associate, project_account_master where "
						+ whereClause
						+ "associate.PROJECT_ID = project_account_master.ID\r\n")
				.append("group by associate.PROJECT_ID;");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String accountName = "";
		int accountCount = 0;
		int recordCount = 0;
		ArrayList<Departments> departmentList = new ArrayList<Departments>();
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			
			if(!location.equalsIgnoreCase("na"))
				ps.setString(1, location);
			
			rs = ps.executeQuery();
			
			Departments department = null;
			
			while(rs.next()) {
				accountCount = rs.getInt("COUNT");
				accountName = rs.getString("Account");
				recordCount++;
				department = new Departments(recordCount+"", accountCount+"", accountName);
				departmentList.add(department);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (conn != null) {
				closeDBObjects(conn, null, ps);
			}
		}
		return departmentList;
	}
	
	public void populateCandidateDetailsForInsert(PreparedStatement ps, Associate candidate, String userId) {

		if (ps != null) {
			try {
				ps.setString(1, candidate.getEmpId());
				ps.setString(2, candidate.getEmpName());
				ps.setString(3, candidate.getBand());
				ps.setString(4, candidate.getGender());
				ps.setString(5, candidate.getHtrFlag());
				ps.setInt(6, candidate.getIbuId());
				ps.setDouble(7, candidate.getTotalExperience());
				ps.setDouble(8, candidate.getTechmExperience());
				ps.setString(9, candidate.getCurrentCoutnry());
				ps.setString(10, candidate.getCurrentLocationCity());
				ps.setString(11, candidate.getOnsiteOffshore());
				ps.setString(12, candidate.getBillablityStatus());
				ps.setDate(13, formatDate(candidate.getStartDate()));
				ps.setDate(14, formatDate(candidate.getEndDate()));
				ps.setString(15, candidate.getSupervisorName());
				ps.setString(16, candidate.getProjectManagerName());
				ps.setString(17, candidate.getProgramManagerName());
				ps.setInt(18, candidate.getPrimarySkillId());
				ps.setInt(19, candidate.getSecondarySkillId());
				ps.setString(20, candidate.getCertifications());
				ps.setInt(21, candidate.getProjId());

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int getIbuByName(String ibuName) {

		StringBuffer sql = new StringBuffer("SELECT IBU_ID FROM tmp.ibu WHERE LOWER(IBU_NAME) like ? ");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int ibuId = 0;
		String ibuDescription = "";

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, ibuName.toLowerCase());
			rs = ps.executeQuery();

			if (rs != null) {
				if (rs.next()) {
					ibuId = rs.getInt("IBU_ID");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			closeDBObjects(conn, rs, ps);

		}
		if (ibuId == 0) {
			ibuId = insertIbuName(ibuName, ibuDescription);
		}
		return ibuId;
	}

	public int getProjectAccountDetails(String projectId) {

		StringBuffer sql = new StringBuffer(
				"SELECT ID FROM tmp.project_account_master WHERE LOWER(PROJECT_ID) like ? ");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int pId = 0;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, projectId.toLowerCase());
			rs = ps.executeQuery();

			if (rs != null) {
				if (rs.next()) {
					pId = rs.getInt("ID");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			closeDBObjects(conn, rs, ps);

		}
		return pId;
	}

	public int getSkillByName(String skillName) {

		StringBuffer sql = new StringBuffer("SELECT ID FROM tmp.skill WHERE LOWER(SKILL_NAME) like ? ");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int skillId = 0;
		String skillDescription = "";

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, skillName.toLowerCase());
			rs = ps.executeQuery();

			if (rs != null) {
				if (rs.next()) {
					skillId = rs.getInt("ID");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			closeDBObjects(conn, rs, ps);

		}
		if (skillId == 0) {
			skillId = insertSkillName(skillName, skillDescription);
		}
		return skillId;
	}

	/**
	 * This method used to insert data into ibu table
	 * 
	 * @param ibuName
	 * @param ibuDescription
	 * @return generatedKey
	 */

	public int insertIbuName(String ibuName, String ibuDescription) {

		StringBuffer sql = new StringBuffer("INSERT INTO tmp.ibu(IBU_NAME, IBU_DESCRIPTION) VALUES(?, ?) ");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int generatedKey = 0;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, ibuName);
			ps.setString(2, ibuDescription);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				generatedKey = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(conn, rs, ps);
		}

		return generatedKey;
	}

	/**
	 * This method used to insert data into associate_project_mapping table
	 * 
	 * @param associateId
	 * @param projectId
	 * @param candidate
	 * @return generatedKey
	 */

	public int insertAssociateProjectDetails(int associateId, int projectId, Associate candidate) {

		StringBuffer sql = new StringBuffer("INSERT INTO tmp.associate_project_mapping(ASSOCIATE_ID, PROJECT_ID, \r\n")
				.append("PROJECT_CONTRACT_TYPE, PROJECT_END_DATE, PROJECT_IBU_ID) \r\n")
				.append("VALUES(?, ?, ?, ?, ?)");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int generatedKey = 0;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, associateId);
			ps.setInt(2, projectId);
			ps.setString(3, candidate.getProjectContractType());
			ps.setDate(4, candidate.getProjectEndDate() != null ? formatDate(candidate.getProjectEndDate()) : null);
			ps.setInt(5, candidate.getIbuId());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				generatedKey = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(conn, rs, ps);
		}

		return generatedKey;
	}

	/**
	 * This method used to insert data into skill table
	 * 
	 * @param skillName
	 * @param skillDescription
	 * @return generatedKey
	 */

	public int insertSkillName(String skillName, String skillDescription) {

		StringBuffer sql = new StringBuffer("INSERT INTO tmp.skill(SKILL_NAME, SKILL_DESCRIPTION) VALUES(?, ?) ");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int generatedKey = 0;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, skillName);
			ps.setString(2, skillDescription);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				generatedKey = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(conn, rs, ps);
		}

		return generatedKey;
	}

	/**
	 * This method used to convert String object to java.util.Date
	 * 
	 * @param dateStr
	 * @return java.sql.Date(toDate.getTime())
	 */

	public static java.sql.Date formatDate(String dateStr) {
		DateFormat toFormatter = new SimpleDateFormat("dd-MM-yyyy");
		Date toDate = null;
		try {
			toDate = toFormatter.parse(dateStr);
			System.out.println("Date is " + toDate);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}

		return new java.sql.Date(toDate.getTime());
	}

}
