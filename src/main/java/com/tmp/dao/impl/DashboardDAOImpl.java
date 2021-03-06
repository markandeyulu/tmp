package com.tmp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tmp.dao.ConfigDAO;
import com.tmp.dao.DashboardDAO;
import com.tmp.entity.Account;
import com.tmp.entity.DashboardRequirement;
import com.tmp.entity.Project;

@Qualifier("dashboardDAO")
public class DashboardDAOImpl extends BaseDAO implements DashboardDAO {

	@Autowired(required = true)
	@Qualifier("configDAO")
	ConfigDAO configDAO;

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private ArrayList<DashboardRequirement> populateDashboard(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}

		Map<String, DashboardRequirement> mapDashboard = new HashMap<String, DashboardRequirement>();

		while (rs.next()) {
			String customerName = rs.getString(2);
			String status = rs.getString(4);

			int count = rs.getInt(1);

			DashboardRequirement temp;

			if (!mapDashboard.containsKey(customerName)) {
				temp = new DashboardRequirement();
				temp.setCustomerName(customerName);
				mapDashboard.put(customerName, temp);
			} else {
				temp = mapDashboard.get(customerName);
			}

			if (status.equals("Lead Generation")) {
				temp.setLeadGeneration(count);
			} else if (status.equals("Profile Sourcing")) {
				temp.setProfileSourcing(count);
			} else if (status.equals("Technical Evaluation")) {
				temp.setInternalEvaluation(count);
			} else if (status.equals("Customer Evaluation")) {
				temp.setCustomerEvaluation(count);
			} else if (status.equals("Offer Processing")) {
				temp.setOfferProcessing(count);
			} else if (status.equals("Onboarding")) {
				temp.setOnboarded(count);
			} else if (status.equals("Lost")) {
				temp.setPositionsLost(count);
			} else if (status.equals("Lost Opportunity")) {
				temp.setLostOpportunity(count);
			} else if (status.equals("Abandoned Opportunity")) {
				temp.setAbandonedOpportunity(count);
			} else if (status.equals("Hold Opportunity")) {
				temp.setHoldOpportunity(count);
			}
		}

		ArrayList<DashboardRequirement> listDashboardRequirement = new ArrayList<DashboardRequirement>(
				mapDashboard.values());

		for (DashboardRequirement obj : listDashboardRequirement) {

			obj.setTotalPositions(obj.getLeadGeneration() + obj.getProfileSourcing() + obj.getInternalEvaluation()
					+ obj.getCustomerEvaluation() + obj.getOfferProcessing());
			obj.setTotalActive(obj.getLeadGeneration() + obj.getProfileSourcing() + obj.getInternalEvaluation()
					+ obj.getCustomerEvaluation() + obj.getOfferProcessing() + obj.getOnboarded());
		}

		return listDashboardRequirement;
	}

	/**
	 * This method provides the data needed for json population
	 */
	public ArrayList<DashboardRequirement> getRequirementJson(String userId) {
		StringBuilder queryString = new StringBuilder(
				"SELECT COUNT(STATUS) AS COUNT, m.ACCOUNT_NAME AS CUSTOMER_NAME, STATUS, "
						+ "(SELECT VALUE FROM CONFIG_VALUE WHERE ID=C.CONFIG_VALUE_ID) AS VALUE \r\n"
						+ "FROM REQUIREMENT R \r\n" + "INNER JOIN TMP.account_master M on M.ACCOUNT_ID = R.ACCOUNT\r\n"
						+ "INNER JOIN TMP.ADMIN_INFO_KEY_VALUE_MAPPING E ON E.ID = R.ACCOUNT \r\n"
						+ "INNER JOIN TMP.ADMIN_INFO_KEY F ON F.ID = E.ADMIN_INFO_KEY_ID \r\n"
						+ "INNER JOIN TMP.ADMIN_INFO_VALUE G ON G.ID =E.ADMIN_INFO_VALUE_ID \r\n"
						+ "INNER JOIN USER_ACCOUNT_MAPPING A ON A.ACCOUNT_ID = R.ACCOUNT \r\n"
						+ "INNER JOIN USER U ON U.ID = A.USER_ID \r\n" + "JOIN CONFIG_KEY_VALUE_MAPPING C \r\n"
						+ "WHERE R.STATUS = C.ID and U.ID = ? \r\n" + "GROUP BY R.ACCOUNT,R.STATUS ");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList<DashboardRequirement> dashRequirement = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(queryString.toString());
			ps.setString(1, userId);

			rs = ps.executeQuery();

			dashRequirement = populateDashboard(rs);

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}
		return dashRequirement;
	}

	public DashboardRequirement populateBlock(ResultSet rs1) throws SQLException {

		if (rs1 == null) {
			return null;
		}

		DashboardRequirement temp1 = new DashboardRequirement();

		while (rs1.next()) {

			String status = rs1.getString(2);

			int count = rs1.getInt(1);

			if (status.equals("Lead Generation")) {
				temp1.setLeadcount(count);
			} else if (status.equals("Profile Sourcing")) {
				temp1.setProfilecount(count);
				;
			} else if (status.equals("Technical Evaluation")) {
				temp1.setTechevalcount(count);
			} else if (status.equals("Customer Evaluation")) {
				temp1.setCustevalcount(count);
				;
			} else if (status.equals("Offer Processing")) {
				temp1.setOffercount(count);
				;
			} else if (status.equals("Onboarding")) {
				temp1.setBoardingcount(count);
			} else if (status.equals("Hold Opportunity")) {
				temp1.setHoldOpportunityCount(count);
			} else if (status.equals("Abandoned Opportunity")) {
				temp1.setAbandonedOpportunityCount(count);
			} else if (status.equals("Lost Opportunity")) {
				temp1.setLostOpportunityCount(count);
			}
		}

		temp1.setTotalcount(
				temp1.getLeadcount() + temp1.getProfilecount() + temp1.getTechevalcount() + temp1.getCustevalcount()
						+ temp1.getOffercount() + temp1.getBoardingcount() + temp1.getHoldOpportunityCount()
						+ temp1.getAbandonedOpportunityCount() + temp1.getLostOpportunityCount());

		return temp1;

	}

	/**
	 * This method provides data for dashboard redirection
	 */
	@Override
	public DashboardRequirement getDashboardRequirement(String userId) {
		StringBuffer sql = new StringBuffer("SELECT count(distinct R.ID) AS COUNT, CV.VALUE FROM REQUIREMENT R \r\n"
				+ "INNER JOIN CONFIG_KEY_VALUE_MAPPING C ON R.STATUS = C.ID \r\n"
				+ "INNER JOIN CONFIG_VALUE CV ON C.CONFIG_VALUE_ID = CV.ID \r\n"
				+ "INNER JOIN USER_ACCOUNT_MAPPING E ON E.ACCOUNT_ID = R.ACCOUNT \r\n"
				+ "INNER JOIN USER A ON A.ID = E.USER_ID WHERE A.ID = ? GROUP BY STATUS");

		Connection conn = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		DashboardRequirement dashRequirement1 = null;

		try {
			conn = dataSource.getConnection();
			ps1 = conn.prepareStatement(sql.toString());
			ps1.setString(1, userId);

			rs1 = ps1.executeQuery();

			dashRequirement1 = populateBlock(rs1);

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs1, ps1);
		}
		return dashRequirement1;
	}

	private ArrayList<DashboardRequirement> populateDashboard1(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}

		Map<String, DashboardRequirement> mapDashboard = new HashMap<String, DashboardRequirement>();

		while (rs.next()) {
			String customerName = rs.getString(3);
			String status = rs.getString(5);

			int count = rs.getInt(1);

			DashboardRequirement temp;

			if (!mapDashboard.containsKey(customerName)) {
				temp = new DashboardRequirement();
				temp.setCustomerName(customerName);
				mapDashboard.put(customerName, temp);
			} else {
				temp = mapDashboard.get(customerName);
			}

			if (status.equals("Lead Generation")) {
				temp.setLeadGeneration(count);
			} else if (status.equals("Profile Sourcing")) {
				temp.setProfileSourcing(count);
			} else if (status.equals("Technical Evaluation")) {
				temp.setInternalEvaluation(count);
			} else if (status.equals("Customer Evaluation")) {
				temp.setCustomerEvaluation(count);
			} else if (status.equals("Offer Processing")) {
				temp.setOfferProcessing(count);
			} else if (status.equals("Onboarding")) {
				temp.setOnboarded(count);
			} else if (status.equals("Lost")) {
				temp.setPositionsLost(count);
			} else if (status.equals("Lost Opportunity")) {
				temp.setLostOpportunity(count);
			} else if (status.equals("Abandoned Opportunity")) {
				temp.setAbandonedOpportunity(count);
			} else if (status.equals("Hold Opportunity")) {
				temp.setHoldOpportunity(count);
			}
		}

		ArrayList<DashboardRequirement> listDashboardRequirement = new ArrayList<DashboardRequirement>(
				mapDashboard.values());

		for (DashboardRequirement obj : listDashboardRequirement) {

			obj.setTotalPositions(obj.getLeadGeneration() + obj.getProfileSourcing() + obj.getInternalEvaluation()
					+ obj.getCustomerEvaluation() + obj.getOfferProcessing() + obj.getAbandonedOpportunityCount()
					+ obj.getHoldOpportunityCount() + obj.getLostOpportunityCount());
			obj.setTotalActive(obj.getLeadGeneration() + obj.getProfileSourcing() + obj.getInternalEvaluation()
					+ obj.getCustomerEvaluation() + obj.getOfferProcessing() + obj.getOnboarded());
		}

		return listDashboardRequirement;
	}

	/**
	 * This method provides the data on selection of particular requirement
	 */
	@Override
	public ArrayList<DashboardRequirement> getRequirementJson(DashboardRequirement dashboardRequirement,
			String userId) {

		Connection conn = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;

		ArrayList<DashboardRequirement> dashRequirement = null;

		try {
			StringBuffer queryString1 = new StringBuffer(
					"SELECT COUNT(distinct STATUS) AS COUNT, R.ACCOUNT,m.ACCOUNT_NAME AS CUSTOMER_NAME, STATUS, \r\n"
							+ "(SELECT VALUE FROM CONFIG_VALUE WHERE ID=C.CONFIG_VALUE_ID) AS VALUE FROM REQUIREMENT R \r\n"
							+ "INNER JOIN TMP.account_master M on M.ACCOUNT_ID = R.ACCOUNT \r\n"
							+ "INNER JOIN TMP.ADMIN_INFO_KEY_VALUE_MAPPING E ON E.ID = R.ACCOUNT \r\n"
							+ "INNER JOIN TMP.ADMIN_INFO_KEY F ON F.ID = E.ADMIN_INFO_KEY_ID \r\n"
							+ "INNER JOIN TMP.ADMIN_INFO_VALUE G ON G.ID =E.ADMIN_INFO_VALUE_ID \r\n"
							+ "INNER JOIN TMP.USER_ACCOUNT_MAPPING Y ON Y.ACCOUNT_ID =R.ACCOUNT\r\n"
							+ "INNER JOIN TMP.USER Z ON Z.ID = Y.USER_ID \r\n" + "JOIN CONFIG_KEY_VALUE_MAPPING C \r\n"
							+ "WHERE R.STATUS = C.ID AND Z.ID = ?");

			if (null != dashboardRequirement.getLocation() && !dashboardRequirement.getLocation().isEmpty()) {
				String[] location = dashboardRequirement.getLocation().split(",");
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
			if (null != dashboardRequirement.getAccount() && !dashboardRequirement.getAccount().isEmpty()) {
				String[] account = dashboardRequirement.getAccount().split(",");
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

			queryString1.append("GROUP BY R.ACCOUNT ");

			conn = dataSource.getConnection();
			ps1 = conn.prepareStatement(queryString1.toString());
			ps1.setString(1, userId);
			rs1 = ps1.executeQuery();

			dashRequirement = populateDashboard1(rs1);

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs1, ps1);
		}
		return dashRequirement;
	}

	public DashboardRequirement populateBlock1(ResultSet rs1) throws SQLException {

		if (rs1 == null) {
			return null;
		}

		DashboardRequirement temp1 = new DashboardRequirement();

		while (rs1.next()) {

			String status = rs1.getString(5);

			int count = rs1.getInt(1);

			if (status.equals("Lead Generation")) {
				temp1.setLeadcount(count);
			} else if (status.equals("Profile Sourcing")) {
				temp1.setProfilecount(count);
				;
			} else if (status.equals("Technical Evaluation")) {
				temp1.setTechevalcount(count);
			} else if (status.equals("Customer Evaluation")) {
				temp1.setCustevalcount(count);
				;
			} else if (status.equals("Offer Processing")) {
				temp1.setOffercount(count);
				;
			} else if (status.equals("Onboarding")) {
				temp1.setBoardingcount(count);
			} else if (status.equals("Hold Opportunity")) {
				temp1.setHoldOpportunityCount(count);
			} else if (status.equals("Abandoned Opportunity")) {
				temp1.setAbandonedOpportunityCount(count);
			} else if (status.equals("Lost Opportunity")) {
				temp1.setLostOpportunityCount(count);
			}
		}

		temp1.setTotalcount(
				temp1.getLeadcount() + temp1.getProfilecount() + temp1.getTechevalcount() + temp1.getCustevalcount()
						+ temp1.getOffercount() + temp1.getBoardingcount() + temp1.getHoldOpportunityCount()
						+ temp1.getAbandonedOpportunityCount() + temp1.getLostOpportunityCount());

		return temp1;

	}

	@Override
	public DashboardRequirement getDashboardRequirement(DashboardRequirement dashboardRequirement, String userId) {

		Connection conn = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;

		DashboardRequirement dashboardRequirement1 = null;

		try {

			StringBuffer queryString1 = new StringBuffer(
					"SELECT COUNT(distinct STATUS) AS COUNT, R.ACCOUNT, G.VALUE AS CUSTOMER_NAME, STATUS, \r\n"
							+ "(SELECT VALUE FROM CONFIG_VALUE WHERE ID=C.CONFIG_VALUE_ID) AS VALUE FROM REQUIREMENT R  \r\n"
							+ "INNER JOIN TMP.ADMIN_INFO_KEY_VALUE_MAPPING E ON E.ID = R.ACCOUNT \r\n"
							+ "INNER JOIN TMP.ADMIN_INFO_KEY F ON F.ID = E.ADMIN_INFO_KEY_ID \r\n"
							+ "INNER JOIN TMP.ADMIN_INFO_VALUE G ON G.ID =E.ADMIN_INFO_VALUE_ID \r\n"
							+ "INNER JOIN TMP.USER_ACCOUNT_MAPPING Y ON Y.ACCOUNT_ID =R.ACCOUNT\r\n"
							+ "INNER JOIN TMP.USER Z ON Z.ID = Y.USER_ID \r\n" + "JOIN CONFIG_KEY_VALUE_MAPPING C \r\n"
							+ "WHERE R.STATUS = C.ID AND Z.ID = ? ");

			// StringBuilder parameterBuilder1 = new StringBuilder();
			if (null != dashboardRequirement.getLocation() && !dashboardRequirement.getLocation().isEmpty()) {
				String[] location = dashboardRequirement.getLocation().split(",");
				queryString1.append("AND ");
				queryString1.append("R.LOCATION IN (");

				for (int i = 0; i < location.length; i++) {
					queryString1.append(location[i]);
					if (location.length > i + 1) {
						queryString1.append(",");
					}
				}
				queryString1.append(") ");

				// queryString1=queryString1.replace("and ",
				// parameterBuilder1.toString());
			}
			if (null != dashboardRequirement.getAccount() && !dashboardRequirement.getAccount().isEmpty()) {
				String[] account = dashboardRequirement.getAccount().split(",");
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

				// queryString1 = queryString1.replace("and",
				// parameterBuilder1.toString());
			}

			queryString1.append("GROUP BY R.STATUS ");

			conn = dataSource.getConnection();
			ps1 = conn.prepareStatement(queryString1.toString());
			ps1.setString(1, userId);
			rs1 = ps1.executeQuery();

			dashboardRequirement1 = populateBlock1(rs1);

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs1, ps1);
		}
		return dashboardRequirement1;
	}

	/**
	 * This method provides the account details
	 */
	public List<Account> getAccountDetails(String userId) {
		StringBuffer sql = new StringBuffer("SELECT C.ACCOUNT_ID, C.ACCOUNT_NAME \r\n" + "FROM USER A \r\n"
				+ "INNER JOIN USER_ACCOUNT_MAPPING B ON B.USER_ID = A.ID \r\n"
				+ "INNER JOIN ACCOUNT_MASTER C ON C.ACCOUNT_ID = B.ACCOUNT_ID \r\n" + "WHERE A.ID = ?");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Account> accounts = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());

			ps.setString(1, userId);
			rs = ps.executeQuery();
			accounts = new ArrayList<Account>();
			if (rs != null) {
				while (rs.next()) {
					Account account = new Account();
					account.setAccountId(rs.getInt("ACCOUNT_ID"));
					account.setAccountName(rs.getString("ACCOUNT_NAME"));
					accounts.add(account);
				}

			}

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}
		return accounts;
	}

	/**
	 * This method provides the project details
	 */
	public List<Project> getProjectDetails(String userId) {
		StringBuffer sql = new StringBuffer(
				"SELECT DISTINCT A.PROJECT_ID, A.PROJECT_NAME, A.ACC_ID FROM PROJECTS A \r\n"
						+ "INNER JOIN USER_ACCOUNT_MAPPING B ON B.ACCOUNT_ID = A.ACC_ID \r\n"
						+ "INNER JOIN USER C ON B.USER_ID = C.ID \r\n" + "WHERE C.ID = ?");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Project> projects = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			projects = new ArrayList<Project>();
			ps.setString(1, userId);
			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {

					Project project = new Project();
					project.setAccountId(rs.getInt("ACC_ID"));
					project.setProjectId(rs.getInt("PROJECT_ID"));
					project.setProjectName(rs.getString("PROJECT_NAME"));

					projects.add(project);
				}
			}

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}
		return projects;
	}

	private DashboardRequirement populateAccounts(int accountId) {
		DashboardRequirement requirementAccounts = new DashboardRequirement();
		requirementAccounts.setUserAccountName(configDAO.getAdminInfoKeyValueMapping(accountId));
		requirementAccounts.setUserAccount(configDAO.getAccountMappingId(accountId));
		return requirementAccounts;
	}

	private DashboardRequirement populateProjects(int accountId, int projectId) {
		DashboardRequirement requirementAccounts = new DashboardRequirement();
		requirementAccounts.setUserProjectName(configDAO.getAdminInfoKeyValueMapping(projectId));
		requirementAccounts.setUserProject(configDAO.getProjectMappingId(accountId, projectId));
		return requirementAccounts;
	}
}
