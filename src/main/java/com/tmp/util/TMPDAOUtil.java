package com.tmp.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;

import com.tmp.entity.Account;
import com.tmp.entity.Project;

@Qualifier("tmpDAOUtil")
public class TMPDAOUtil {
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public Account getAccount(int accountId) {
		StringBuffer sql = new StringBuffer("SELECT * FROM tmp.account_master WHERE ACCOUNT_ID=? ");
			
		Connection conn = null;
		Account account = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, accountId);
			ResultSet rs = ps.executeQuery();
			
			if (rs != null) {
				if (rs.next()) {
					account = new Account();
					account.setAccountId(rs.getInt("ACCOUNT_ID"));
					account.setAccountName(rs.getString("ACCOUNT_NAME"));
				}
			}
			rs.close();
			ps.close();
			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlException) {}
			}
		}
		return account;
	}
	public Project getProject(int projectId) {
		StringBuffer sql = new StringBuffer("SELECT * FROM tmp.projects WHERE ID=? ");
			
		Connection conn = null;
		Project project =null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, projectId);
			ResultSet rs = ps.executeQuery();
			
			if (rs != null) {
				if (rs.next()) {
					project = new Project();
					project.setAccountId(rs.getInt("ACC_ID"));
					project.setProjectId(rs.getInt("ID"));
					project.setProjectName(rs.getString("PROJECT_NAME"));
				}
			}
			rs.close();
			ps.close();
			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlException) {}
			}
		}
		return project;
	}
	
	public java.sql.Date convertUtilDatetoSQLDate(Date utilDate) {
		java.sql.Date sqlDate = null;
		if (utilDate != null) {
			sqlDate = new java.sql.Date(utilDate.getTime());
		}

		return sqlDate;
	}

	public String convertDateFormat(Date sqlDate) {
	
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String DateToStr = simpleDateFormat.format(sqlDate);
		
		return DateToStr;
	}
	public java.util.Date convertSQLDateToUtilDate(java.sql.Date sqlDate) {
		Date utilDate = null;
		if (sqlDate != null) {
			utilDate = new Date(sqlDate.getTime());
		}
		return utilDate;
	}

	public Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public Account getAccountFromName(String accountName) {
		StringBuffer sql = new StringBuffer("SELECT * FROM tmp.account_master WHERE ACCOUNT_NAME=? ");
			
		Connection conn = null;
		Account account = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, accountName);
			ResultSet rs = ps.executeQuery();
			
			if (rs != null) {
				if (rs.next()) {
					account = new Account();
					account.setAccountId(rs.getInt("ACCOUNT_ID"));
					account.setAccountName(rs.getString("ACCOUNT_NAME"));
				}
			}
			rs.close();
			ps.close();
			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlException) {}
			}
		}
		return account;
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
	
	
	public int getAccountByName(String accountName) {
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
		}
		return accountId;
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
					projectId = rs.getInt("PROJECT_ID");			
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

	public Project getProjectName(String projectName) {
		StringBuffer sql = new StringBuffer("SELECT * FROM tmp.projects WHERE PROJECT_NAME like ? ");
			
		Connection conn = null;
		Project project = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, "%"+projectName+"%");
			ResultSet rs = ps.executeQuery();
			
			if (rs != null) {
				if (rs.next()) {
					project = new Project();
					project.setAccountId(rs.getInt("ACC_ID"));
					project.setProjectId(rs.getInt("PROJECT_ID"));
					project.setProjectName(rs.getString("PROJECT_NAME"));
				}
				/*if (rs.next()) {
					account = new Account();
					account.setAccountId(rs.getInt("ACCOUNT_ID"));
					account.setAccountName(rs.getString("ACCOUNT_NAME"));
				}*/
			}
			rs.close();
			ps.close();
			
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlException) {}
			}
		}
		return project;
	}
	
}
