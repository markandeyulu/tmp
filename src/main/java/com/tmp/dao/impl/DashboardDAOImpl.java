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
		StringBuilder queryString = new StringBuilder("SELECT COUNT(STATUS) AS COUNT, m.ACCOUNT_NAME AS CUSTOMER_NAME, STATUS, "+
				"(SELECT VALUE FROM CONFIG_VALUE WHERE ID=C.CONFIG_VALUE_ID) AS VALUE \r\n" + 
				"FROM REQUIREMENT R \r\n" + 
				"INNER JOIN TMP.account_master M on M.ACCOUNT_ID = R.ACCOUNT\r\n" + 
				"INNER JOIN TMP.ADMIN_INFO_KEY_VALUE_MAPPING E ON E.ID = R.ACCOUNT \r\n" + 
				"INNER JOIN TMP.ADMIN_INFO_KEY F ON F.ID = E.ADMIN_INFO_KEY_ID \r\n" + 
				"INNER JOIN TMP.ADMIN_INFO_VALUE G ON G.ID =E.ADMIN_INFO_VALUE_ID \r\n" + 
				"INNER JOIN USER_ACCOUNT_MAPPING A ON A.ACCOUNT_ID = R.ACCOUNT \r\n" + 
				"INNER JOIN USER U ON U.ID = A.USER_ID \r\n" + 
				"JOIN CONFIG_KEY_VALUE_MAPPING C \r\n" + 
				"WHERE R.STATUS = C.ID and U.ID = ? \r\n" + 
				"GROUP BY R.ACCOUNT,R.STATUS ");
		
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
		StringBuffer sql = new StringBuffer("SELECT count(distinct R.ID) AS COUNT, CV.VALUE FROM REQUIREMENT R \r\n" + 
				"INNER JOIN CONFIG_KEY_VALUE_MAPPING C ON R.STATUS = C.ID \r\n" + 
				"INNER JOIN CONFIG_VALUE CV ON C.CONFIG_VALUE_ID = CV.ID \r\n" + 
				"INNER JOIN USER_ACCOUNT_MAPPING E ON E.ACCOUNT_ID = R.ACCOUNT \r\n" + 
				"INNER JOIN USER A ON A.ID = E.USER_ID WHERE A.ID = ? GROUP BY STATUS");
		
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

	public List<Account> getAccountDetails(String userId){
		StringBuffer sql = new StringBuffer
				("SELECT C.ACCOUNT_ID, C.ACCOUNT_NAME \r\n" + 
				"FROM USER A \r\n" + 
				"INNER JOIN USER_ACCOUNT_MAPPING B ON B.USER_ID = A.ID \r\n" + 
				"INNER JOIN ACCOUNT_MASTER C ON C.ACCOUNT_ID = B.ACCOUNT_ID \r\n" + 
				"WHERE A.ID = ?");

		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			List<Account> accounts = new ArrayList<Account>();
			if (rs != null) {
				while (rs.next()) {
					Account account = new Account();
					account.setAccountId(rs.getInt("ACCOUNT_ID"));
					account.setAccountName(rs.getString("ACCOUNT_NAME"));
					accounts.add(account);
				}
				
			}
			rs.close();
			ps.close();
			return accounts;
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
	public List<Project> getProjectDetails(String userId){
		StringBuffer sql = new StringBuffer
				("SELECT A.PROJECT_ID, A.PROJECT_NAME, A.ACC_ID FROM PROJECTS A \r\n" + 
						"INNER JOIN USER_ACCOUNT_MAPPING B ON B.ACCOUNT_ID = A.ACC_ID \r\n" + 
						"INNER JOIN USER C ON B.USER_ID = C.ID \r\n" + 
						"WHERE C.ID = ?");


		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			List<Project> projects = new ArrayList<Project>();
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					
					Project project = new Project();
					project.setAccountId(rs.getInt("ACC_ID"));
					project.setProjectId(rs.getInt("PROJECT_ID"));
					project.setProjectName(rs.getString("PROJECT_NAME"));
					
					projects.add(project);
				}
			}
			rs.close();
			ps.close();
			return projects;
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
