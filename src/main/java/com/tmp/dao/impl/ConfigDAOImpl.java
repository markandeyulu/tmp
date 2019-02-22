package com.tmp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tmp.dao.ConfigDAO;
import com.tmp.entity.AccountMapping;
import com.tmp.entity.AdminInfoKey;
import com.tmp.entity.AdminInfoKeyValueMapping;
import com.tmp.entity.AdminInfoValue;
import com.tmp.entity.ConfigKeyValueMapping;
import com.tmp.entity.Configkey;
import com.tmp.entity.Configvalue;
import com.tmp.entity.Profile;
import com.tmp.entity.ProjectMapping;
import com.tmp.entity.User;

@Repository
@Qualifier("configDAO")
public class ConfigDAOImpl extends BaseDAO implements ConfigDAO {
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * This method gives the id for mapping admin key and admin value
	 */
	public AdminInfoKeyValueMapping getAdminInfoKeyValueMapping(int id) {
		StringBuffer sql = new StringBuffer(
				"SELECT AKVM.ID, AKVM.ADMIN_INFO_KEY_ID, AKVM.ADMIN_INFO_VALUE_ID, AK.KEY, AV.VALUE \r\n")
						.append("FROM ADMIN_INFO_KEY_VALUE_MAPPING AKVM\r\n")
						.append("INNER JOIN ADMIN_INFO_KEY AK ON AKVM.ADMIN_INFO_KEY_ID = AK.ID\r\n")
						.append("INNER JOIN ADMIN_INFO_VALUE AV ON AKVM.ADMIN_INFO_VALUE_ID = AV.ID\r\n"
								+ "WHERE AKVM.ID = ?");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, id);
			AdminInfoKeyValueMapping adminInfoKeyValueMapping = null;
			rs = ps.executeQuery();
			if (rs != null) {
				adminInfoKeyValueMapping = new AdminInfoKeyValueMapping();
				if (rs.next()) {
					adminInfoKeyValueMapping = populateAdminInfoKeyValueMapping(rs);
				}
			}

			return adminInfoKeyValueMapping;
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}
	}

	/**
	 * This method gives the value for mapping admin key and admin value
	 */
	public AdminInfoKeyValueMapping getAdminInfoKeyValueMapping(String strValue) {
		StringBuffer sql = new StringBuffer(
				"SELECT AKVM.ID, AKVM.ADMIN_INFO_KEY_ID, AKVM.ADMIN_INFO_VALUE_ID, AK.KEY, AV.VALUE \r\n")
						.append("FROM ADMIN_INFO_KEY_VALUE_MAPPING AKVM\r\n")
						.append("INNER JOIN ADMIN_INFO_KEY AK ON AKVM.ADMIN_INFO_KEY_ID = AK.ID\r\n")
						.append("INNER JOIN ADMIN_INFO_VALUE AV ON AKVM.ADMIN_INFO_VALUE_ID = AV.ID\r\n"
								+ "WHERE AV.VALUE = ?");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, strValue);
			AdminInfoKeyValueMapping adminInfoKeyValueMapping = null;
			rs = ps.executeQuery();
			if (rs != null) {
				adminInfoKeyValueMapping = new AdminInfoKeyValueMapping();
				if (rs.next()) {
					adminInfoKeyValueMapping = populateAdminInfoKeyValueMapping(rs);
				}
			}

			return adminInfoKeyValueMapping;
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}
	}

	private AdminInfoKeyValueMapping populateAdminInfoKeyValueMapping(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}

		AdminInfoKeyValueMapping adminInfoKeyValueMapping = new AdminInfoKeyValueMapping();
		adminInfoKeyValueMapping.setId(rs.getInt("ID"));
		adminInfoKeyValueMapping.setAdminInfoKey(populateAdminInfoKey(rs));
		adminInfoKeyValueMapping.setAdminInfoValue(populateAdminInfoValue(rs));
		return adminInfoKeyValueMapping;
	}

	private AdminInfoValue populateAdminInfoValue(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}

		AdminInfoValue adminInfoValue = new AdminInfoValue();
		adminInfoValue.setId(rs.getInt("ADMIN_INFO_VALUE_ID"));
		adminInfoValue.setValue(rs.getString("VALUE"));
		return adminInfoValue;
	}

	private AdminInfoKey populateAdminInfoKey(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}

		AdminInfoKey adminInfoKey = new AdminInfoKey();
		adminInfoKey.setId(rs.getInt("ADMIN_INFO_KEY_ID"));
		adminInfoKey.setKey(rs.getString("Key"));
		return adminInfoKey;
	}

	/**
	 * This method provides the id for mapping config key and config value.
	 */
	public ConfigKeyValueMapping getConfigKeyValueMapping(int id) {
		StringBuffer sql = new StringBuffer(
				"SELECT CKVM.ID, CKVM.CONFIG_KEY_ID, CKVM.CONFIG_VALUE_ID, CK.KEY, CV.VALUE \r\n")
						.append("FROM CONFIG_KEY_VALUE_MAPPING CKVM\r\n")
						.append("INNER JOIN CONFIG_KEY CK ON CKVM.CONFIG_KEY_ID = CK.ID\r\n")
						.append("INNER JOIN CONFIG_VALUE CV ON CKVM.CONFIG_VALUE_ID = CV.ID\r\n" + "WHERE CKVM.ID = ?");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, id);
			ConfigKeyValueMapping configKeyValueMapping = null;
			rs = ps.executeQuery();
			if (rs != null) {
				configKeyValueMapping = new ConfigKeyValueMapping();
				if (rs.next()) {
					configKeyValueMapping = populateConfigKeyValueMapping(rs);
				}
			}

			return configKeyValueMapping;
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}
	}

	/**
	 * This method provides the value for mapping the config key and config value.
	 */
	public ConfigKeyValueMapping getConfigKeyValueMapping(String strValue) {
		StringBuffer sql = new StringBuffer(
				"SELECT CKVM.ID, CKVM.CONFIG_KEY_ID, CKVM.CONFIG_VALUE_ID, CK.KEY, CV.VALUE \r\n")
						.append("FROM CONFIG_KEY_VALUE_MAPPING CKVM\r\n")
						.append("INNER JOIN CONFIG_KEY CK ON CKVM.CONFIG_KEY_ID = CK.ID\r\n")
						.append("INNER JOIN CONFIG_VALUE CV ON CKVM.CONFIG_VALUE_ID = CV.ID\r\n"
								+ "WHERE CV.VALUE = ?");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, strValue);
			ConfigKeyValueMapping configKeyValueMapping = null;
			rs = ps.executeQuery();
			if (rs != null) {
				configKeyValueMapping = new ConfigKeyValueMapping();
				if (rs.next()) {
					configKeyValueMapping = populateConfigKeyValueMapping(rs);
				}
			}

			return configKeyValueMapping;
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}
	}

	private ConfigKeyValueMapping populateConfigKeyValueMapping(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}

		ConfigKeyValueMapping configKeyValueMapping = new ConfigKeyValueMapping();
		configKeyValueMapping.setId(rs.getInt("ID"));
		// configKeyValueMapping.setConfigKey(populateConfigKey(rs));
		configKeyValueMapping.setConfigValue(populateConfigValue(rs));
		return configKeyValueMapping;
	}

	private Configvalue populateConfigValue(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}

		Configvalue configValue = new Configvalue();
		// configValue.setId(rs.getInt("CONFIG_VALUE_ID"));
		String value = rs.getString("VALUE");
		if (value != null && !value.isEmpty()) {
			configValue.setValue(value);
		} else {
			configValue.setValue("");
		}
		return configValue;
	}

	private Configkey populateConfigKey(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}

		Configkey configKey = new Configkey();
		configKey.setId(rs.getInt("CONFIG_KEY_ID"));
		configKey.setKey(rs.getString("Key"));
		return configKey;
	}

	/**
	 * This method provides the id for config table
	 */
	public List<ConfigKeyValueMapping> getConfigKeyValues(int id) {
		StringBuffer sql = new StringBuffer("SELECT CKVM.ID, CV.VALUE \r\n")
				.append("FROM CONFIG_KEY_VALUE_MAPPING CKVM\r\n")
				.append("INNER JOIN CONFIG_KEY CK ON CKVM.CONFIG_KEY_ID = CK.ID\r\n")
				.append("INNER JOIN CONFIG_VALUE CV ON CKVM.CONFIG_VALUE_ID = CV.ID\r\n" + "WHERE CK.ID = ?");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, id);
			List<ConfigKeyValueMapping> configKeyValueMappings = null;
			rs = ps.executeQuery();
			if (rs != null) {
				configKeyValueMappings = new ArrayList<ConfigKeyValueMapping>();
				while (rs.next()) {
					configKeyValueMappings.add(populateConfigKeyValueMapping(rs));
				}
			}
			return configKeyValueMappings;
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}
	}

	/**
	 * This method provides the id and value for config table
	 */
	public int getConfigKeyValues(int id, String value) {
		StringBuffer sql = new StringBuffer("SELECT CKVM.ID as ID ").append("FROM CONFIG_KEY_VALUE_MAPPING CKVM ")
				.append("INNER JOIN CONFIG_KEY CK ON CKVM.CONFIG_KEY_ID = CK.ID ")
				.append("INNER JOIN CONFIG_VALUE CV ON CKVM.CONFIG_VALUE_ID = CV.ID WHERE CK.ID = ? and CV.VALUE = ?");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int CKVMID = 0;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, id);
			ps.setString(2, value);

			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					CKVMID = rs.getInt("ID");
				}
			}

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}
		return CKVMID;
	}

	/**
	 * This method provides the id for admin table
	 */
	public List<AdminInfoKeyValueMapping> getAdminInfoKeyValues(int id) {
		StringBuffer sql = new StringBuffer(
				"SELECT AKVM.ID, AKVM.ADMIN_INFO_KEY_ID, AKVM.ADMIN_INFO_VALUE_ID, AK.KEY, AV.VALUE \r\n")
						.append("FROM ADMIN_INFO_KEY_VALUE_MAPPING AKVM\r\n")
						.append("INNER JOIN ADMIN_INFO_KEY AK ON AKVM.ADMIN_INFO_KEY_ID = AK.ID\r\n")
						.append("INNER JOIN ADMIN_INFO_VALUE AV ON AKVM.ADMIN_INFO_VALUE_ID = AV.ID\r\n"
								+ "WHERE AK.ID = ?");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, id);
			List<AdminInfoKeyValueMapping> adminInfoKeyValueMappings = null;
			rs = ps.executeQuery();
			if (rs != null) {
				adminInfoKeyValueMappings = new ArrayList<AdminInfoKeyValueMapping>();
				while (rs.next()) {
					adminInfoKeyValueMappings.add(populateAdminInfoKeyValueMapping(rs));
				}
			}

			return adminInfoKeyValueMappings;
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}
	}

	public List<AccountMapping> getAccounts(String ibu) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method provides the account name from account_mapping table based on the
	 * given account id
	 */
	public AccountMapping getAccountMapping(int accountId) {
		StringBuffer sql = new StringBuffer("SELECT AM.ACCOUNT FROM ACCOUNT_MAPPING AM WHERE AM.ID=? ");

		AccountMapping accountMapping = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, accountId);
			rs = ps.executeQuery();
			accountMapping = new AccountMapping();
			if (rs != null) {
				if (rs.next()) {
					int accId = rs.getInt("ACCOUNT");
					accountMapping.setAccount(getAdminInfoKeyValueMapping(accId));
				}
			}

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}

		return accountMapping;
	}

	/**
	 * This method provides the account id from account_mapping table based on the
	 * given account name.
	 */
	public AccountMapping getAccountMappingId(int accountId) {

		StringBuffer sql = new StringBuffer("SELECT AM.ID FROM ACCOUNT_MAPPING AM WHERE AM.ACCOUNT=? ");

		AccountMapping accountMapping = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, accountId);
			rs = ps.executeQuery();
			accountMapping = new AccountMapping();
			if (rs != null) {
				if (rs.next()) {
					accountMapping.setAccountId(rs.getInt("ID"));
				}
			}

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}

		return accountMapping;
	}

	public List<ProjectMapping> getProjects(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method provides the project name from project_mapping table based on the
	 * given project id.
	 */
	public ProjectMapping getProjectMapping(int projectId) {
		StringBuffer sql = new StringBuffer("SELECT PM.PROJECT FROM PROJECT_MAPPING PM WHERE PM.ID=? ");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProjectMapping mapping = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, projectId);
			rs = ps.executeQuery();
			mapping = new ProjectMapping();
			if (rs != null) {
				if (rs.next()) {
					int proId = rs.getInt("PROJECT");
					mapping.setProject(getAdminInfoKeyValueMapping(proId));
				}
			}

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}

		return mapping;
	}

	/**
	 * This method provides the project id from project_mapping table based on the
	 * given project name.
	 */
	public ProjectMapping getProjectMappingId(int accountId, int projectId) {

		StringBuffer sql = new StringBuffer(
				"SELECT PM.ID FROM PROJECT_MAPPING PM WHERE PM.ACCOUNT_MAPPING_ID=? AND PM.PROJECT=?");

		ProjectMapping projectMapping = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, accountId);
			ps.setInt(2, projectId);
			rs = ps.executeQuery();
			projectMapping = new ProjectMapping();
			if (rs != null) {
				if (rs.next()) {
					projectMapping.setId(rs.getInt("ID"));
				}
			}

		} catch (SQLException sqlException) {
			sqlException.getMessage();
			projectMapping.setMessage("Either account or project is not mapped");

		} finally {
			closeDBObjects(conn, rs, ps);
		}

		return projectMapping;
	}

	/**
	 * This method provides the id of the particular user
	 */
	@Override
	public User getUserId(int userId) {
		User userInfo = new User();

		StringBuffer sql = new StringBuffer("SELECT * FROM USER WHERE ID = ?");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					userInfo.setName(rs.getString("NAME"));
				}
			}

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}

		return userInfo;
	}

	/**
	 * This method provides the name of profile from profile table based on the
	 * given profile id
	 */
	public Profile getProfileName(int profileId) {
		Profile profile = new Profile();
		profile.setId(profileId);

		StringBuffer sql = new StringBuffer("SELECT NAME FROM PROFILE WHERE ID=?");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, profileId);
			rs = ps.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					profile.setName(rs.getString("NAME"));
				}
			}

		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		} finally {
			closeDBObjects(conn, rs, ps);
		}

		return profile;
	}

}
