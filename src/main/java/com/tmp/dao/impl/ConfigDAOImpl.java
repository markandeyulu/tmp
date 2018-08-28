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
public class ConfigDAOImpl implements ConfigDAO {
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public AdminInfoKeyValueMapping getAdminInfoKeyValueMapping(int id) {
		StringBuffer sql = new StringBuffer(
				"SELECT AKVM.ID, AKVM.ADMIN_INFO_KEY_ID, AKVM.ADMIN_INFO_VALUE_ID, AK.KEY, AV.VALUE \r\n")
		.append("FROM ADMIN_INFO_KEY_VALUE_MAPPING AKVM\r\n")
		.append("INNER JOIN ADMIN_INFO_KEY AK ON AKVM.ADMIN_INFO_KEY_ID = AK.ID\r\n")
		.append("INNER JOIN ADMIN_INFO_VALUE AV ON AKVM.ADMIN_INFO_VALUE_ID = AV.ID\r\n"
				+ "WHERE AKVM.ID = ?");

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, id);
			AdminInfoKeyValueMapping adminInfoKeyValueMapping = null;
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				adminInfoKeyValueMapping = new AdminInfoKeyValueMapping();
				if (rs.next()) {
					adminInfoKeyValueMapping = populateAdminInfoKeyValueMapping(rs);
				}
			}
			rs.close();
			ps.close();
			return adminInfoKeyValueMapping;
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
	
	public AdminInfoKeyValueMapping getAdminInfoKeyValueMapping(String strValue) {
		StringBuffer sql = new StringBuffer(
				"SELECT AKVM.ID, AKVM.ADMIN_INFO_KEY_ID, AKVM.ADMIN_INFO_VALUE_ID, AK.KEY, AV.VALUE \r\n")
		.append("FROM ADMIN_INFO_KEY_VALUE_MAPPING AKVM\r\n")
		.append("INNER JOIN ADMIN_INFO_KEY AK ON AKVM.ADMIN_INFO_KEY_ID = AK.ID\r\n")
		.append("INNER JOIN ADMIN_INFO_VALUE AV ON AKVM.ADMIN_INFO_VALUE_ID = AV.ID\r\n"
				+ "WHERE AV.VALUE = ?");

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, strValue);
			AdminInfoKeyValueMapping adminInfoKeyValueMapping = null;
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				adminInfoKeyValueMapping = new AdminInfoKeyValueMapping();
				if (rs.next()) {
					adminInfoKeyValueMapping = populateAdminInfoKeyValueMapping(rs);
				}
			}
			rs.close();
			ps.close();
			return adminInfoKeyValueMapping;
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

	public ConfigKeyValueMapping getConfigKeyValueMapping(int id) {
		StringBuffer sql = new StringBuffer(
				"SELECT CKVM.ID, CKVM.CONFIG_KEY_ID, CKVM.CONFIG_VALUE_ID, CK.KEY, CV.VALUE \r\n")
		.append("FROM CONFIG_KEY_VALUE_MAPPING CKVM\r\n")
		.append("INNER JOIN CONFIG_KEY CK ON CKVM.CONFIG_KEY_ID = CK.ID\r\n")
		.append("INNER JOIN CONFIG_VALUE CV ON CKVM.CONFIG_VALUE_ID = CV.ID\r\n" + "WHERE CKVM.ID = ?");

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, id);
			ConfigKeyValueMapping configKeyValueMapping = null;
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				configKeyValueMapping = new ConfigKeyValueMapping();
				if (rs.next()) {
					configKeyValueMapping = populateConfigKeyValueMapping(rs);
				}
			}
			rs.close();
			ps.close();
			return configKeyValueMapping;
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
	
	public ConfigKeyValueMapping getConfigKeyValueMapping(String strValue) {
		StringBuffer sql = new StringBuffer(
				"SELECT CKVM.ID, CKVM.CONFIG_KEY_ID, CKVM.CONFIG_VALUE_ID, CK.KEY, CV.VALUE \r\n")
		.append("FROM CONFIG_KEY_VALUE_MAPPING CKVM\r\n")
		.append("INNER JOIN CONFIG_KEY CK ON CKVM.CONFIG_KEY_ID = CK.ID\r\n")
		.append("INNER JOIN CONFIG_VALUE CV ON CKVM.CONFIG_VALUE_ID = CV.ID\r\n" + "WHERE CV.VALUE = ?");

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, strValue);
			ConfigKeyValueMapping configKeyValueMapping = null;
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				configKeyValueMapping = new ConfigKeyValueMapping();
				if (rs.next()) {
					configKeyValueMapping = populateConfigKeyValueMapping(rs);
				}
			}
			rs.close();
			ps.close();
			return configKeyValueMapping;
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

	private ConfigKeyValueMapping populateConfigKeyValueMapping(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}

		ConfigKeyValueMapping configKeyValueMapping = new ConfigKeyValueMapping();
		configKeyValueMapping.setId(rs.getInt("ID"));
		//configKeyValueMapping.setConfigKey(populateConfigKey(rs));
		configKeyValueMapping.setConfigValue(populateConfigValue(rs));
		return configKeyValueMapping;
	}

	private Configvalue populateConfigValue(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}

		Configvalue configValue = new Configvalue();
		//configValue.setId(rs.getInt("CONFIG_VALUE_ID"));
		String value = rs.getString("VALUE");
		if(value!=null && !value.isEmpty()){
		    configValue.setValue(value);
		}else{
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

	public List<ConfigKeyValueMapping> getConfigKeyValues(int id) {
		StringBuffer sql = new StringBuffer(
				"SELECT CKVM.ID, CV.VALUE \r\n")
		.append("FROM CONFIG_KEY_VALUE_MAPPING CKVM\r\n")
		.append("INNER JOIN CONFIG_KEY CK ON CKVM.CONFIG_KEY_ID = CK.ID\r\n")
		.append("INNER JOIN CONFIG_VALUE CV ON CKVM.CONFIG_VALUE_ID = CV.ID\r\n" + "WHERE CK.ID = ?");

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, id);
			List<ConfigKeyValueMapping> configKeyValueMappings = null;
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				configKeyValueMappings = new ArrayList<ConfigKeyValueMapping>();
				while (rs.next()) {
					configKeyValueMappings.add(populateConfigKeyValueMapping(rs));
				}
			}
			rs.close();
			ps.close();
			return configKeyValueMappings;
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

	public List<AdminInfoKeyValueMapping> getAdminInfoKeyValues(int id) {
		StringBuffer sql = new StringBuffer(
				"SELECT AKVM.ID, AKVM.ADMIN_INFO_KEY_ID, AKVM.ADMIN_INFO_VALUE_ID, AK.KEY, AV.VALUE \r\n")
		.append("FROM ADMIN_INFO_KEY_VALUE_MAPPING AKVM\r\n")
		.append("INNER JOIN ADMIN_INFO_KEY AK ON AKVM.ADMIN_INFO_KEY_ID = AK.ID\r\n")
		.append("INNER JOIN ADMIN_INFO_VALUE AV ON AKVM.ADMIN_INFO_VALUE_ID = AV.ID\r\n"
				+ "WHERE AK.ID = ?");

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, id);
			List<AdminInfoKeyValueMapping> adminInfoKeyValueMappings = null;
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				adminInfoKeyValueMappings = new ArrayList<AdminInfoKeyValueMapping>();
				while (rs.next()) {
					adminInfoKeyValueMappings.add(populateAdminInfoKeyValueMapping(rs));
				}
			}
			rs.close();
			ps.close();
			return adminInfoKeyValueMappings;
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

	public List<AccountMapping> getAccounts(String ibu) {
		// TODO Auto-generated method stub
		return null;
	}

	public AccountMapping getAccountMapping(int accountId) {
		StringBuffer sql = new StringBuffer("SELECT AM.ACCOUNT FROM ACCOUNT_MAPPING AM WHERE AM.ID=? ");
		
		AccountMapping accountMapping= null;
		
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, accountId);
			ResultSet rs = ps.executeQuery();
			accountMapping= new AccountMapping();
			if (rs != null) {
					if (rs.next()) {
						int accId=rs.getInt("ACCOUNT");
						accountMapping.setAccount(getAdminInfoKeyValueMapping(accId));
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

		return accountMapping;
	}
	
	public AccountMapping getAccountMappingId(int accountId) {
		
		StringBuffer sql = new StringBuffer("SELECT AM.ID FROM ACCOUNT_MAPPING AM WHERE AM.ACCOUNT=? ");
		
		AccountMapping accountMapping= null;
		
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, accountId);
			ResultSet rs = ps.executeQuery();
			accountMapping = new AccountMapping();
			if (rs != null) {
					if (rs.next()) {
						accountMapping.setAccountId(rs.getInt("ID"));
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

		return accountMapping;
	}
	
	public List<ProjectMapping> getProjects(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProjectMapping getProjectMapping(int projectId) {
		StringBuffer sql = new StringBuffer("SELECT PM.PROJECT FROM PROJECT_MAPPING PM WHERE PM.ID=? ");

		Connection conn = null;
		ProjectMapping mapping = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, projectId);
			ResultSet rs = ps.executeQuery();
			mapping = new ProjectMapping();
			if (rs != null) {
				if (rs.next()) {
					int proId=rs.getInt("PROJECT");
					mapping.setProject(getAdminInfoKeyValueMapping(proId));
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

		return mapping;
	}
	
	public ProjectMapping getProjectMappingId(int accountId, int projectId) {
		
		StringBuffer sql = new StringBuffer("SELECT PM.ID FROM PROJECT_MAPPING PM WHERE PM.ACCOUNT_MAPPING_ID=? AND PM.PROJECT=?");
		
		ProjectMapping projectMapping= null;
		
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, accountId);
			ps.setInt(2, projectId);
			ResultSet rs = ps.executeQuery();
			projectMapping = new ProjectMapping();
			if (rs != null) {
					if (rs.next()) {
						projectMapping.setId(rs.getInt("ID"));
					}
			}
			rs.close();
			ps.close();
			
		} catch (SQLException sqlException) {
			sqlException.getMessage();
			projectMapping.setMessage("Either account or project is not mapped");
			return projectMapping;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlException) {
				}
			}
		}

		return projectMapping;
	}

	@Override
	public User getUserId(int userId) {
		User userInfo = new User();

			StringBuffer sql = new StringBuffer("SELECT * FROM USER WHERE ID = ?");

			Connection conn = null;

			try {
				conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ps.setInt(1, userId);
				ResultSet rs = ps.executeQuery();
				if (rs != null) {
					if (rs.next()) {
						userInfo.setName(rs.getString("NAME"));
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

			return userInfo;
	}
	public Profile getProfileName(int profileId) {
		Profile profile = new Profile();
		profile.setId(profileId);

			StringBuffer sql = new StringBuffer("SELECT NAME FROM PROFILE WHERE ID=?");

			Connection conn = null;

			try {
				conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ps.setInt(1, profileId);
				ResultSet rs = ps.executeQuery();
				if (rs != null) {
					if (rs.next()) {
						profile.setName(rs.getString("NAME"));
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

			return profile;
	}
	
}
