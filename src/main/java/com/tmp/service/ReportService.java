package com.tmp.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tmp.dao.ReportDAO;
import com.tmp.entity.Report;
import com.tmp.vo.ReportVO;


@Service
@Qualifier("reportService")
public class ReportService extends BaseService {
	
	@Autowired(required = true)
	@Qualifier("reportDAO")
	ReportDAO reportDAO;
	
	/**
	 * This method used to get report list from db
	 * @param reportBean input parameters
	 * @param response HttpServletResponse Object
	 */
	public ArrayList<ReportVO> getReportList(Report reportBean, HttpServletResponse response) {
		
		ArrayList<ReportVO> reportList = reportDAO.getReportForExcel(reportBean);
		return reportList;
	}
	
	
	
	

}
