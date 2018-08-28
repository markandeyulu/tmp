package com.tmp.dao;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;

import com.tmp.entity.Report;

public interface ReportDAO {
	public void getReport(Report report, HttpServletResponse httpServletResponse ) throws ParseException,IOException ;
}
