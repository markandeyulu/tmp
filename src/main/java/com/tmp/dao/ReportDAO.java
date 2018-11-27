package com.tmp.dao;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import com.tmp.entity.Report;
import com.tmp.vo.ReportVO;

public interface ReportDAO {
	public void getReport(Report report, HttpServletResponse httpServletResponse ) throws ParseException,IOException ;
	public ArrayList<ReportVO> getReportForExcel(Report report) ;
}
