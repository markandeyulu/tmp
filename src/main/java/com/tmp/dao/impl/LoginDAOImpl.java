package com.tmp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tmp.dao.ConfigDAO;
import com.tmp.dao.LoginDAO;
import com.tmp.entity.Role;
import com.tmp.entity.User;
import com.tmp.entity.UserRoleMapping;

@Repository
@Qualifier("loginDAO")
public class LoginDAOImpl implements LoginDAO{

	@Autowired(required = true)
	@Qualifier("configDAO")
	ConfigDAO configDAO;
	
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public User getAuthenticateUser(String username, String password) {

		StringBuffer sql = new StringBuffer("SELECT NAME, ID, DISPLAY_NAME, PASSWORD, ROLE FROM USER WHERE NAME=? AND PASSWORD=? ");

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs=null;
		
		User userInfo = new User();

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if (rs.next()) {
				userInfo.setId(rs.getInt("ID"));
				userInfo.setName(rs.getString("NAME"));
				userInfo.setDisplayName(rs.getString("DISPLAY_NAME"));
				userInfo.setRole(Role.fromInt(rs.getInt("ROLE")));
			}

		}catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			if (conn != null) {
				try {
					conn.close();
					ps.close();
					rs.close();
				} catch (SQLException sqlException) {
				}
			}
		}

		return userInfo;
	}
	
	public UserRoleMapping getUserRole(int userId){
		StringBuffer sql = new StringBuffer("SELECT ROLE FROM USER_ROLE_MAPPING WHERE USER_ID=?");

		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs=null;
		
		UserRoleMapping userRole = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			if (rs != null) {
				userRole = new UserRoleMapping();
				while (rs.next()) {
					userRole.setRole(configDAO.getAdminInfoKeyValueMapping(rs.getInt("ROLE")));
				}
			}
			rs.close();
			ps.close();
		}catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			if (conn != null) {
				try {
					conn.close();
					ps.close();
					rs.close();
				} catch (SQLException sqlException) {
				}
			}
		}

		return userRole;
	}
}