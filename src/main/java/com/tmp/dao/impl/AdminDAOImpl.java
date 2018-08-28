package com.tmp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tmp.dao.AdminDAO;
import com.tmp.dao.ConfigDAO;
import com.tmp.util.TMPDAOUtil;

@Repository
@Qualifier("adminDAO")
public class AdminDAOImpl implements AdminDAO{

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
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public int getRequirementAdminMappingValue(String adminRequirementValue, int keyId, String userId) {
		int result = 0;
		while (!isAdminDataExist(adminRequirementValue)) {
		 result = adminDataInserttoMap(adminRequirementValue, keyId, userId);
		}
		return result;
	}
	public int adminDataInserttoMap(String adminRequirementData, int keyId, String userId) {
		StringBuffer sql = new StringBuffer("INSERT INTO ADMIN_INFO_VALUE(ID, VALUE, CREATED_ON, CREATED_BY) VALUES(?, ?, ?, ?) ");
		StringBuffer sql1 = new StringBuffer("INSERT INTO ADMIN_INFO_KEY_VALUE_MAPPING(ID, ADMIN_INFO_KEY_ID, ADMIN_INFO_VALUE_ID, CREATED_ON, CREATED_BY) VALUES(?, ?, ?, ?, ?)");

		Connection conn = null;

		int value;
		try {
			conn = dataSource.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, 0);
			ps.setString(2, adminRequirementData);
			ps.setTimestamp(3, tmpDAOUtil.getCurrentTimestamp());
			ps.setString(4, userId);
		    value = ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			int generatedKey = 0;
			if (rs.next()) {
			    generatedKey = rs.getInt(1);
			}
			//system.out.println("Inserted ADMIN_INFO_VALUE record's ID: " + generatedKey);
			
			ps.close();
			rs.close();
			
			PreparedStatement ps1 = conn.prepareStatement(sql1.toString());
			ps1.setInt(1, 0);
			ps1.setInt(2, keyId);
			ps1.setInt(3, generatedKey);
			ps1.setTimestamp(4, tmpDAOUtil.getCurrentTimestamp());
			ps1.setString(5, userId);
		    value = ps1.executeUpdate();
			ps1.close();
 
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
		return value;
	}
	private boolean isAdminDataExist(String adminRequirementData) {
		StringBuffer sql = new StringBuffer("SELECT * FROM ADMIN_INFO_VALUE AIV WHERE AIV.VALUE=?");

		Connection conn = null;

		try {
			boolean isExist = false;
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, adminRequirementData);
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					isExist = true;
				}
			}
			rs.close();
			ps.close();
			return isExist;
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
	public int getRequirementAdminProjectMapping(String requirementProjectValue, int keyId, String userId,String requirementAccountValue, int accNewMappingId, int accountId) {
		int result = 0;
		while (!isAdminDataExist(requirementProjectValue)) {
		 result = adminDataInserttoProjMapping(requirementProjectValue, keyId, userId, accNewMappingId, accountId);
		}
		if(isAdminDataExist(requirementProjectValue) == true && accNewMappingId>0){
		 result = insertToProjectMapping(requirementProjectValue, keyId, userId, accNewMappingId, accountId);
		}else if(!isAdminDataExist(requirementAccountValue) && !isAdminDataExist(requirementProjectValue)) {
		 result = insertToProjectMapping(requirementProjectValue, keyId, userId, accNewMappingId, accountId);
		}
		return result;
	}
	public int insertToProjectMapping(String requirementProjectValue, int keyId, String userId, int accNewMappingId, int accountId) {
		StringBuffer sql = new StringBuffer("INSERT INTO PROJECT_MAPPING(ID, ACCOUNT_MAPPING_ID, PROJECT, CREATED_ON, CREATED_BY) VALUES(?, ?, ?, ?, ?)");

		Connection conn = null;

		int result = 0;
		try {
			conn = dataSource.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, 0);
			if(accNewMappingId>0){
			ps.setInt(2, accNewMappingId);
			}else{
			ps.setInt(2, accountId);
			}
			ps.setInt(3, configDAO.getAdminInfoKeyValueMapping(requirementProjectValue).getId());	
			ps.setTimestamp(4, tmpDAOUtil.getCurrentTimestamp());
			ps.setString(5, userId);
			result = ps.executeUpdate();
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
		return result;
	}
	public int adminDataInserttoProjMapping(String requirementProjectValue, int keyId, String userId, int accNewMappingId, int accountId) {
		StringBuffer sql = new StringBuffer("INSERT INTO ADMIN_INFO_VALUE(ID, VALUE, CREATED_ON, CREATED_BY) VALUES(?, ?, ?, ?) ");
		StringBuffer sql1 = new StringBuffer("INSERT INTO ADMIN_INFO_KEY_VALUE_MAPPING(ID, ADMIN_INFO_KEY_ID, ADMIN_INFO_VALUE_ID, CREATED_ON, CREATED_BY) VALUES(?, ?, ?, ?, ?)");
		StringBuffer sql2 = new StringBuffer("INSERT INTO PROJECT_MAPPING(ID, ACCOUNT_MAPPING_ID, PROJECT, CREATED_ON, CREATED_BY) VALUES(?, ?, ?, ?, ?)");

		Connection conn = null;

		int result = 0;
		try {
			conn = dataSource.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, 0);
			ps.setString(2, requirementProjectValue);
			ps.setTimestamp(3, tmpDAOUtil.getCurrentTimestamp());
			ps.setString(4, userId);
		    ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			int generatedKey = 0;
			if(rs!=null){
			if (rs.next()) {
			    generatedKey = rs.getInt(1);
			}
			}
			//system.out.println("Inserted Projects==> ADMIN_INFO_VALUE record's ID: " + generatedKey);
			
			ps.close();
			rs.close();
			
			PreparedStatement ps1 = conn.prepareStatement(sql1.toString(), Statement.RETURN_GENERATED_KEYS);
			ps1.setInt(1, 0);
			ps1.setInt(2, keyId);
			ps1.setInt(3, generatedKey);
			ps1.setTimestamp(4, tmpDAOUtil.getCurrentTimestamp());
			ps1.setString(5, userId);
		    ps1.executeUpdate();
		    
		    ResultSet rs1 = ps1.getGeneratedKeys();
			int generatedKeyMapping = 0;
			if(rs1!=null){
			if (rs1.next()) {
				generatedKeyMapping = rs1.getInt(1);
			}
			}
			//system.out.println("Inserted Projects==> ADMIN_INFO_KEY_VALUE_MAPPING record's ID: " + generatedKeyMapping);
			
			ps1.close();
			rs1.close();
			
			PreparedStatement ps2 = conn.prepareStatement(sql2.toString());
			ps2.setInt(1, 0);
			if(accNewMappingId>0){
			ps2.setInt(2, accNewMappingId);
			}else{
			ps2.setInt(2, accountId);
			}
			if(generatedKeyMapping>0){
			ps2.setInt(3, generatedKeyMapping);
			}else{
			ps2.setInt(3, configDAO.getAdminInfoKeyValueMapping(requirementProjectValue).getId());	
			}
			ps2.setTimestamp(4, tmpDAOUtil.getCurrentTimestamp());
			ps2.setString(5, userId);
			result = ps2.executeUpdate();
			ps2.close();
			
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
		return result;
	}
	
	public int getRequirementAdminAccountMapping(String requirementAccountValue, int keyId, String userId, int ibg, int ibu) {
		int result = 0;
		while (!isAdminDataExist(requirementAccountValue)) {
		 result = adminDataInserttoMapping(requirementAccountValue, keyId, userId, ibg, ibu);
		}
		return result;
	}
	public int adminDataInserttoMapping(String requirementAccountValue, int keyId, String userId, int ibg, int ibu) {
		StringBuffer sql = new StringBuffer("INSERT INTO ADMIN_INFO_VALUE(ID, VALUE, CREATED_ON, CREATED_BY) VALUES(?, ?, ?, ?) ");
		StringBuffer sql1 = new StringBuffer("INSERT INTO ADMIN_INFO_KEY_VALUE_MAPPING(ID, ADMIN_INFO_KEY_ID, ADMIN_INFO_VALUE_ID, CREATED_ON, CREATED_BY) VALUES(?, ?, ?, ?, ?)");
		StringBuffer sql2 = new StringBuffer("INSERT INTO ACCOUNT_MAPPING(ID, ORGANIZATION, IBG, IBU, ACCOUNT, CREATED_ON, CREATED_BY) VALUES(?, ?, ?, ?, ?, ?, ?)");

		Connection conn = null;

		int generatedKeyMapping1 = 0;
		try {
			conn = dataSource.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, 0);
			ps.setString(2, requirementAccountValue);
			ps.setTimestamp(3, tmpDAOUtil.getCurrentTimestamp());
			ps.setString(4, userId);
		    ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			int generatedKey = 0;
			if(rs!=null){
			if (rs.next()) {
			    generatedKey = rs.getInt(1);
			}
			}
			//system.out.println("Inserted Accounts==> ADMIN_INFO_VALUE record's ID: " + generatedKey);
			
			ps.close();
			rs.close();
			
			PreparedStatement ps1 = conn.prepareStatement(sql1.toString(), Statement.RETURN_GENERATED_KEYS);
			ps1.setInt(1, 0);
			ps1.setInt(2, keyId);
			ps1.setInt(3, generatedKey);
			ps1.setTimestamp(4, tmpDAOUtil.getCurrentTimestamp());
			ps1.setString(5, userId);
		    ps1.executeUpdate();
		    
		    ResultSet rs1 = ps1.getGeneratedKeys();
			int generatedKeyMapping = 0;
			if(rs1!=null){
			if (rs1.next()) {
				generatedKeyMapping = rs1.getInt(1);
			}
			}
			//system.out.println("Inserted Accounts==> ADMIN_INFO_KEY_VALUE_MAPPING record's ID: " + generatedKeyMapping);
			
			ps1.close();
			rs1.close();
			
			PreparedStatement ps2 = conn.prepareStatement(sql2.toString(), Statement.RETURN_GENERATED_KEYS);
			ps2.setInt(1, 0);
			ps2.setInt(2, 7);
			ps2.setInt(3, ibg);
			ps2.setInt(4, ibu);
			if(generatedKeyMapping>0){
				ps2.setInt(5, generatedKeyMapping);
				}else{
				ps2.setInt(5, configDAO.getAdminInfoKeyValueMapping(requirementAccountValue).getId());	
				}
			ps2.setTimestamp(6, tmpDAOUtil.getCurrentTimestamp());
			ps2.setString(7, userId);
		    ps2.executeUpdate();
		    
		    ResultSet rs2 = ps2.getGeneratedKeys();
			if(rs2!=null){
			if (rs2.next()) {
				generatedKeyMapping1 = rs2.getInt(1);
			}
			}
			//system.out.println("Inserted ACCOUNT_MAPPING record's ID: " + generatedKeyMapping1);
			
			ps2.close();
			rs2.close();
			
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
		return generatedKeyMapping1;
	}
	
	public int getRequirementMappingValue(String requirementValue, int keyId, String userId) {
		int result = 0;
		while (!isDataExist(requirementValue)) {
		 result = dataInserttoConfig(requirementValue, keyId, userId);
		}
		return result;
	}
	public int dataInserttoConfig(String requirementData, int keyId, String userId) {
		StringBuffer sql = new StringBuffer("INSERT INTO CONFIG_VALUE(ID, VALUE, CREATED_ON, CREATED_BY) VALUES(?, ?, ?, ?) ");
		StringBuffer sql1 = new StringBuffer("INSERT INTO CONFIG_KEY_VALUE_MAPPING(ID, CONFIG_KEY_ID, CONFIG_VALUE_ID, CREATED_ON, CREATED_BY) VALUES(?, ?, ?, ?, ?)");

		Connection conn = null;

		int value;
		try {
			conn = dataSource.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, 0);
			ps.setString(2, requirementData);
			ps.setTimestamp(3, tmpDAOUtil.getCurrentTimestamp());
			ps.setString(4, userId);
		    value = ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			int generatedKey = 0;
			if (rs.next()) {
			    generatedKey = rs.getInt(1);
			}
			//system.out.println("Inserted CONFIG_VALUE record's ID: " + generatedKey);
			
			ps.close();
			rs.close();
			
			PreparedStatement ps1 = conn.prepareStatement(sql1.toString());
			ps1.setInt(1, 0);
			ps1.setInt(2, keyId);
			ps1.setInt(3, generatedKey);
			ps1.setTimestamp(4, tmpDAOUtil.getCurrentTimestamp());
			ps1.setString(5, userId);
		    value = ps1.executeUpdate();
			ps1.close();
 
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
		return value;
	}
	private boolean isDataExist(String requirementData) {
		StringBuffer sql = new StringBuffer("SELECT * FROM CONFIG_VALUE CV WHERE CV.VALUE=?");

		Connection conn = null;

		try {
			boolean isExist = false;
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, requirementData);
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					isExist = true;
				}
			}
			rs.close();
			ps.close();
			return isExist;
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
