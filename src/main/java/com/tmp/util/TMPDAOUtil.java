package com.tmp.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;

@Qualifier("tmpDAOUtil")
public class TMPDAOUtil {

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
