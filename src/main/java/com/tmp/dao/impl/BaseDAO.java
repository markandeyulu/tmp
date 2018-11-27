package com.tmp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDAO {
	
	
	public void closeDBObjects(Connection con, ResultSet rs, PreparedStatement ps) {
		
		try {
			if(null != rs)
				rs.close();
			
			if(null != ps)
				ps.close();
			
			if(null != con)
				con.close();
			
		}catch(SQLException se) {
			System.out.println("Exception Occurred in Close "+se.getMessage());
		}
		
	}

}
