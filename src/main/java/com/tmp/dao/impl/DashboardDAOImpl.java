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
import com.tmp.entity.DashboardRequirement;
import com.tmp.entity.LoginRoleAccounts;
import com.tmp.entity.Requirement;

@Qualifier("dashboardDAO")
public class DashboardDAOImpl implements DashboardDAO{
	
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

		Map<String, DashboardRequirement> mapDashboard=new HashMap<String, DashboardRequirement>(); 

		while(rs.next())
		{
			String customerName=rs.getString(2);
			String status=rs.getString(4);

			int count=rs.getInt(1);

			DashboardRequirement temp;

			if(!mapDashboard.containsKey(customerName)){
				temp=new DashboardRequirement();
				temp.setCustomerName(customerName);
				mapDashboard.put(customerName, temp);
			}else{				
				temp=mapDashboard.get(customerName);				
			}


			if(status.equals("Lead Generation")){
				temp.setLeadGeneration(count);
			}else if(status.equals("Profile Sourcing")){
				temp.setProfileSourcing(count);
			}else if(status.equals("Technical Evaluation")){
				temp.setInternalEvaluation(count);
			}else if(status.equals("Customer Evaluation")){
				temp.setCustomerEvaluation(count);
			}else if(status.equals("Offer Processing")){
				temp.setOfferProcessing(count);
			}else if(status.equals("Onboarding")){
				temp.setOnboarded(count);
			}else if(status.equals("Lost")){
				temp.setPositionsLost(count);
			}
		}

		ArrayList<DashboardRequirement> listDashboardRequirement = new ArrayList<DashboardRequirement>(mapDashboard.values());

		for(DashboardRequirement obj:listDashboardRequirement)
		{
			
			obj.setTotalPositions(obj.getLeadGeneration()+obj.getProfileSourcing()+obj.getInternalEvaluation()+obj.getCustomerEvaluation()+obj.getOfferProcessing());
			obj.setTotalActive(obj.getLeadGeneration()+obj.getProfileSourcing()+obj.getInternalEvaluation()+obj.getCustomerEvaluation()+obj.getOfferProcessing()+obj.getOnboarded());
		}	

		return listDashboardRequirement;
	}




	public ArrayList<DashboardRequirement> getRequirementJson(String userId) {
		StringBuilder queryString = new StringBuilder("SELECT COUNT(STATUS) AS COUNT, G.VALUE AS CUSTOMER_NAME, STATUS, "
				+ "(SELECT VALUE FROM CONFIG_VALUE WHERE ID=C.CONFIG_VALUE_ID) AS VALUE FROM REQUIREMENT R "
				+ "INNER JOIN TMP.ACCOUNT_MAPPING D ON D.ID = R.ACCOUNT INNER JOIN TMP.ADMIN_INFO_KEY_VALUE_MAPPING E ON E.ID = D.ACCOUNT "
				+ "INNER JOIN TMP.ADMIN_INFO_KEY F ON F.ID = E.ADMIN_INFO_KEY_ID INNER JOIN TMP.ADMIN_INFO_VALUE G ON G.ID =E.ADMIN_INFO_VALUE_ID "
				+ "LEFT JOIN USER_ROLE_ACCOUNT_MAPPING A ON A.ACCOUNT_ID = R.ACCOUNT LEFT JOIN ACCOUNT_MAPPING AM ON AM.ID = A.ACCOUNT_ID "
				+ "LEFT JOIN USER_ROLE_MAPPING B ON B.ID = A.USER_ROLE_ID LEFT JOIN USER U ON U.ID = B.USER_ID "
				+ "JOIN CONFIG_KEY_VALUE_MAPPING C WHERE R.STATUS = C.ID and U.ID = ? GROUP BY R.ACCOUNT ");
		
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(queryString.toString());
			ps.setString(1,  userId);
			ArrayList<DashboardRequirement> dashRequirement = null;
			ResultSet rs = ps.executeQuery();

			dashRequirement=populateDashboard(rs);

			rs.close();
			ps.close();
			return dashRequirement;
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

	public DashboardRequirement populateBlock(ResultSet rs1) throws SQLException{


		if (rs1 == null) {
			return null;
		}

		DashboardRequirement temp1 =new DashboardRequirement();

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
			}
		}

		temp1.setTotalcount(temp1.getLeadcount()+temp1.getProfilecount()+temp1.getTechevalcount()+temp1.getCustevalcount()+temp1.getOffercount()+temp1.getBoardingcount());

		return temp1;

	}

	@Override
	public DashboardRequirement getDashboardRequirement(String userId) {
		StringBuffer sql = new StringBuffer("SELECT COUNT(STATUS) AS COUNT, CV.VALUE FROM REQUIREMENT R "
				+ "INNER JOIN CONFIG_KEY_VALUE_MAPPING C ON R.STATUS = C.ID "
				+ "INNER JOIN CONFIG_VALUE CV ON C.CONFIG_VALUE_ID = CV.ID "
				+ "LEFT JOIN USER_ROLE_ACCOUNT_MAPPING E ON E.ACCOUNT_ID = R.ACCOUNT "
				+ "LEFT JOIN ACCOUNT_MAPPING D ON D.ID = E.ACCOUNT_ID "
				+ "LEFT JOIN USER_ROLE_MAPPING B ON B.ID = E.USER_ROLE_ID "
				+ "LEFT JOIN USER A ON A.ID = B.USER_ID WHERE A.ID = ? GROUP BY STATUS");
		
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps1 = conn.prepareStatement(sql.toString());
			ps1.setString(1, userId);
			DashboardRequirement dashRequirement1 = null;
			ResultSet rs1 = ps1.executeQuery();

			dashRequirement1=populateBlock(rs1);

			rs1.close();
			ps1.close();
			return dashRequirement1;
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
	private ArrayList<DashboardRequirement> populateDashboard1(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}

		Map<String, DashboardRequirement> mapDashboard=new HashMap<String, DashboardRequirement>(); 

		while(rs.next())
		{
			String customerName=rs.getString(3);
			String status=rs.getString(5);

			int count=rs.getInt(1);

			DashboardRequirement temp;

			if(!mapDashboard.containsKey(customerName)){
				temp=new DashboardRequirement();
				temp.setCustomerName(customerName);
				mapDashboard.put(customerName, temp);
			}else{				
				temp=mapDashboard.get(customerName);				
			}


			if(status.equals("Lead Generation")){
				temp.setLeadGeneration(count);
			}else if(status.equals("Profile Sourcing")){
				temp.setProfileSourcing(count);
			}else if(status.equals("Technical Evaluation")){
				temp.setInternalEvaluation(count);
			}else if(status.equals("Customer Evaluation")){
				temp.setCustomerEvaluation(count);
			}else if(status.equals("Offer Processing")){
				temp.setOfferProcessing(count);
			}else if(status.equals("Onboarding")){
				temp.setOnboarded(count);
			}else if(status.equals("Lost")){
				temp.setPositionsLost(count);
			}
		}

		ArrayList<DashboardRequirement> listDashboardRequirement = new ArrayList<DashboardRequirement>(mapDashboard.values());

		for(DashboardRequirement obj:listDashboardRequirement)
		{
			
			obj.setTotalPositions(obj.getLeadGeneration()+obj.getProfileSourcing()+obj.getInternalEvaluation()+obj.getCustomerEvaluation()+obj.getOfferProcessing());
			obj.setTotalActive(obj.getLeadGeneration()+obj.getProfileSourcing()+obj.getInternalEvaluation()+obj.getCustomerEvaluation()+obj.getOfferProcessing()+obj.getOnboarded());
		}	

		return listDashboardRequirement;
	}

	@Override
	public ArrayList<DashboardRequirement> getRequirementJson(DashboardRequirement dashboardRequirement, String userId){

		Connection conn = null;

		try {
			StringBuffer queryString1 = new StringBuffer(
					"SELECT COUNT(STATUS) AS COUNT, R.ACCOUNT, G.VALUE AS CUSTOMER_NAME, STATUS, "
					+ "(SELECT VALUE FROM CONFIG_VALUE WHERE ID=C.CONFIG_VALUE_ID) AS VALUE FROM REQUIREMENT R "
					+ "INNER JOIN TMP.ACCOUNT_MAPPING D ON D.ID = R.ACCOUNT INNER JOIN TMP.ADMIN_INFO_KEY_VALUE_MAPPING E ON E.ID = D.ACCOUNT "
					+ "INNER JOIN TMP.ADMIN_INFO_KEY F ON F.ID = E.ADMIN_INFO_KEY_ID INNER JOIN TMP.ADMIN_INFO_VALUE G ON G.ID =E.ADMIN_INFO_VALUE_ID "
					+ "LEFT JOIN USER_ROLE_ACCOUNT_MAPPING A ON A.ACCOUNT_ID = R.ACCOUNT LEFT JOIN ACCOUNT_MAPPING AM ON AM.ID = A.ACCOUNT_ID "
					+ "LEFT JOIN USER_ROLE_MAPPING B ON B.ID = A.USER_ROLE_ID LEFT JOIN USER U ON U.ID = B.USER_ID "
					+ "JOIN CONFIG_KEY_VALUE_MAPPING C WHERE R.STATUS = C.ID AND U.ID = ? ");

			if (null != dashboardRequirement.getLocation()
					&& !dashboardRequirement.getLocation().isEmpty()) {
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
			if (null != dashboardRequirement.getAccount()
					&& !dashboardRequirement.getAccount().isEmpty()) {
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
			PreparedStatement ps1 = conn.prepareStatement(queryString1.toString());
			ps1.setString(1, userId);
			ResultSet rs1 = ps1.executeQuery();

			ArrayList<DashboardRequirement> dashRequirement = populateDashboard1(rs1);

			rs1.close();
			ps1.close();
			return dashRequirement;
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
	
	public DashboardRequirement populateBlock1(ResultSet rs1) throws SQLException{


		if (rs1 == null) {
			return null;
		}

		DashboardRequirement temp1 =new DashboardRequirement();

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
			}
		}

		temp1.setTotalcount(temp1.getLeadcount()+temp1.getProfilecount()+temp1.getTechevalcount()+temp1.getCustevalcount()+temp1.getOffercount()+temp1.getBoardingcount());

		return temp1;

	}
	
	
	@Override
	public DashboardRequirement getDashboardRequirement(DashboardRequirement dashboardRequirement, String userId) {

		Connection conn = null;

		try {

			StringBuffer queryString1 = new StringBuffer(
					"SELECT COUNT(STATUS) AS COUNT, CV.VALUE FROM REQUIREMENT R "
							+ "INNER JOIN CONFIG_KEY_VALUE_MAPPING C ON R.STATUS = C.ID "
							+ "INNER JOIN CONFIG_VALUE CV ON C.CONFIG_VALUE_ID = CV.ID "
							+ "LEFT JOIN USER_ROLE_ACCOUNT_MAPPING E ON E.ACCOUNT_ID = R.ACCOUNT "
							+ "LEFT JOIN ACCOUNT_MAPPING D ON D.ID = E.ACCOUNT_ID "
							+ "LEFT JOIN USER_ROLE_MAPPING B ON B.ID = E.USER_ROLE_ID "
							+ "LEFT JOIN USER A ON A.ID = B.USER_ID WHERE A.ID = ? ");

			// StringBuilder parameterBuilder1 = new StringBuilder();
			if (null != dashboardRequirement.getLocation()
					&& !dashboardRequirement.getLocation().isEmpty()) {
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
			if (null != dashboardRequirement.getAccount()
					&& !dashboardRequirement.getAccount().isEmpty()) {
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
			PreparedStatement ps1 = conn.prepareStatement(queryString1
					.toString());
			ps1.setString(1, userId);
			ResultSet rs1 = ps1.executeQuery();

			DashboardRequirement dashboardRequirement1 = populateBlock1(rs1);

			rs1.close();
			ps1.close();
			return dashboardRequirement1;
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

	public LoginRoleAccounts getAccountDetails(String userId){
		StringBuffer sql = new StringBuffer("SELECT A.NAME, B.ROLE, F.KEY, G.VALUE, D.ACCOUNT, D.ID FROM USER A INNER JOIN USER_ROLE_MAPPING B ON A.ID = B.USER_ID \r\n")
							.append("INNER JOIN USER_ROLE_ACCOUNT_MAPPING C ON C.USER_ROLE_ID = B.ID INNER JOIN ACCOUNT_MAPPING D ON D.ID = C.ACCOUNT_ID  \r\n")
							.append("INNER JOIN ADMIN_INFO_KEY_VALUE_MAPPING E ON E.ID = D.ACCOUNT INNER JOIN ADMIN_INFO_KEY F ON F.ID = E.ADMIN_INFO_KEY_ID  \r\n")
							.append("INNER JOIN ADMIN_INFO_VALUE G ON G.ID =E.ADMIN_INFO_VALUE_ID WHERE A.ID = ?");


		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			List<DashboardRequirement> loginAccountsList = null;
			LoginRoleAccounts requirementsAcc = null;
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				requirementsAcc = new LoginRoleAccounts();
				loginAccountsList = new ArrayList<DashboardRequirement>();
				while (rs.next()) {
					int accountId = rs.getInt(5);
					loginAccountsList.add(populateAccounts(accountId));
				}
				requirementsAcc.setListAccount(loginAccountsList);
				
			}
			rs.close();
			ps.close();
			return requirementsAcc;
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
	public LoginRoleAccounts getProjectDetails(String userId){
		StringBuffer sql = new StringBuffer("SELECT A.NAME, B.ROLE, F.KEY, G.VALUE, D.ACCOUNT, D.ID, H.PROJECT FROM USER A INNER JOIN USER_ROLE_MAPPING B ON A.ID = B.USER_ID \r\n")
							.append("INNER JOIN USER_ROLE_ACCOUNT_MAPPING C ON C.USER_ROLE_ID = B.ID INNER JOIN ACCOUNT_MAPPING D ON D.ID = C.ACCOUNT_ID  \r\n")
							.append("INNER JOIN PROJECT_MAPPING H ON H.ACCOUNT_MAPPING_ID = D.ID \r\n")
							.append("INNER JOIN ADMIN_INFO_KEY_VALUE_MAPPING E ON E.ID = D.ACCOUNT INNER JOIN ADMIN_INFO_KEY F ON F.ID = E.ADMIN_INFO_KEY_ID  \r\n")
							.append("INNER JOIN ADMIN_INFO_VALUE G ON G.ID =E.ADMIN_INFO_VALUE_ID WHERE A.ID = ?");


		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			List<DashboardRequirement> loginProjectsList = null;
			LoginRoleAccounts projsList = null;

			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				projsList = new LoginRoleAccounts();
				loginProjectsList = new ArrayList<DashboardRequirement>();
				while (rs.next()) {
					int accountId = rs.getInt(6);
					int projectId = rs.getInt(7);
					loginProjectsList.add(populateProjects(accountId,projectId));
				}
				projsList.setListProject(loginProjectsList);
			}
			rs.close();
			ps.close();
			return projsList;
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
