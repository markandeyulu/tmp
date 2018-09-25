package com.tmp.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;

@Qualifier("tmpDAOUtil")
public class TMPDAOUtil {
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public String getAccount(int accountId) {
		StringBuffer sql = new StringBuffer("SELECT ACCOUNT_NAME FROM tmp.account_master WHERE ACCOUNT_ID=? ");
		
			
		Connection conn = null;
		String accName=null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, accountId);
			ResultSet rs = ps.executeQuery();
			
			
			if (rs != null) {
					if (rs.next()) {
						 accName=rs.getString("ACCOUNT_NAME");
						
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
				} catch (SQLException sqlException) {
				}
			}
		}
		return accName;

		
		
	}
	public String getProject(int projectId) {
		StringBuffer sql = new StringBuffer("SELECT PROJECT_NAME FROM tmp.projects WHERE PROJECT_ID=? ");
		
			
		Connection conn = null;
		String prjtName=null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, projectId);
			ResultSet rs = ps.executeQuery();
			
			
			if (rs != null) {
					if (rs.next()) {
						prjtName=rs.getString("PROJECT_NAME");
						
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
				} catch (SQLException sqlException) {
				}
			}
		}
		return prjtName;

		
		
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
		////system.out.println("DateToStr==> "+DateToStr);
		
		return DateToStr;
	}
	public java.util.Date convertSQLDateToUtilDate(java.sql.Date sqlDate) {
		Date utilDate = null;
		if (sqlDate != null) {
			utilDate = new Date(sqlDate.getTime());
			////system.out.println("utilDate==> "+utilDate);
		}
		return utilDate;
	}

	public Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	
}
