package com.tmp.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import com.tmp.dao.ResumeUploadDAO;
import com.tmp.entity.ProfileResume;

/**
 * This class used to upload resume  into table
 * 
 * @author AJ00561494
 *
 */

public class ResumeUploadDAOImpl extends BaseDAO implements ResumeUploadDAO {
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public int uploadResume(ProfileResume resume) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(resume.getFileName()));
			System.out.println("Filr Name : "+ resume.getFileName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuffer sql = new StringBuffer(
				"INSERT INTO Resume_Upload ( EMP_ID, BLOB, CREATED_ON, CREATED_BY) VALUES ( ?, ?, ?, ?)");
		Connection conn = null;
		PreparedStatement ps = null;
		int generatedKey = 0;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, resume.getProfileId());
			ps.setBinaryStream(2, fis);
			ps.setDate(3, new java.sql.Date(resume.getCreatedOn().getTime()));
			ps.setInt(4, resume.getCreatedBy());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				generatedKey = rs.getInt(1);
			}

		} catch (SQLException sqlException) {
			System.out.println("SQLException occurred " + sqlException.getMessage());

		}
		catch(Exception e){
			e.printStackTrace();
		}
			finally {
		
			closeDBObjects(conn, null, ps);
		}
		System.out.println("Generated keys : "+generatedKey);
		return generatedKey;
	}

}
