package com.tmp.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tmp.entity.Report;
import com.tmp.service.ReportService;
import com.tmp.vo.ReportVO;
@Controller
public class ReportController  {

	@Autowired(required = true)
	@Qualifier("reportService")
	ReportService reportService;


	/**
	 * @return the reportService
	 */
	public ReportService getReportService() {
		return reportService;
	}

	/**
	 * @param reportService the reportService to set
	 */
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	@RequestMapping(value = "/reports", method = RequestMethod.GET)
	public ModelAndView report() {
		Report reportmodel=new Report();
		return new ModelAndView("reports", "reportModel",reportmodel);
	}

	@RequestMapping(value="/report", method = RequestMethod.POST, produces = {"text/html" })
	public void reportGeneration(ModelMap model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("reportModel") Report reportBean)  
			throws ServletException, IOException {
		response.setContentType("text/html");  
		try {
			
			ArrayList<ReportVO> reportList = reportService.getReportList(reportBean, response);
			writeDataIntoExcel(reportBean, response, reportList);
			
		} catch (Exception e) {
			System.out.println("Exception  in Report Generation "+e.getMessage());
		}

	}
	
	/**
	 * This method used to write the data into excel
	 * @param report
	 * @param response
	 * @param reportList
	 */
		
	private void writeDataIntoExcel(Report report,HttpServletResponse response, ArrayList<ReportVO> reportList) {
		
		XSSFWorkbook workBook = new XSSFWorkbook();
		XSSFSheet spreadSheet = workBook.createSheet("Requirements Report");
		
		
		setHeaders(report, workBook, spreadSheet, reportList, response);
		
	}


	/**
	 * This method used to set header and cell styles
	 * @param report Report Object
	 * @param workbook Workbook Object
	 * @param spreadsheet Spread Sheet Object
	 * @param reportList List of ReportVO
	 * @param response HttpServletResponse object
	 */

	private void setHeaders(Report report, XSSFWorkbook workbook,XSSFSheet spreadsheet, ArrayList<ReportVO> reportList, 
		HttpServletResponse response) {
	
	String startDate = report.getStartdate();
	String endDate = report.getEnddate();
	
	Font headerFont = workbook.createFont();
	headerFont.setBold(true);
	headerFont.setFontHeightInPoints((short) 11);
	headerFont.setColor(IndexedColors.DARK_BLUE.getIndex());

	Font hFont = workbook.createFont();
	hFont.setBold(true);
	hFont.setFontHeightInPoints((short) 12);
	hFont.setColor(IndexedColors.DARK_RED.getIndex());

	// Create a CellStyle with the font
	CellStyle headerCellStyle = workbook.createCellStyle();
	headerCellStyle.setFillBackgroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
	headerCellStyle.setFont(headerFont);
	headerCellStyle.setBorderBottom(BorderStyle.THIN);
	headerCellStyle.setBorderLeft(BorderStyle.THIN);
	headerCellStyle.setBorderRight(BorderStyle.THIN);
	headerCellStyle.setBorderTop(BorderStyle.THIN);
	headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
	headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

	CellStyle hstyle = workbook.createCellStyle();
	hstyle.setFont(hFont);
	hstyle.setBorderBottom(BorderStyle.THICK);
	hstyle.setBorderLeft(BorderStyle.THIN);
	hstyle.setBorderRight(BorderStyle.THIN);
	hstyle.setBorderTop(BorderStyle.THIN);
	hstyle.setAlignment(HorizontalAlignment.CENTER);
	hstyle.setVerticalAlignment(VerticalAlignment.CENTER);

	// Create a Row
	// Row headerRow = sheet.createRow(0);
	CellRangeAddress cellrange = new CellRangeAddress(0, 0, 1, 3);
	CellStyle cellstyle1 = workbook.createCellStyle();
	cellstyle1.setBorderBottom(BorderStyle.THIN);
	cellstyle1.setBorderLeft(BorderStyle.THIN);
	cellstyle1.setBorderRight(BorderStyle.THIN);
	cellstyle1.setBorderTop(BorderStyle.THIN);
	cellstyle1.setAlignment(HorizontalAlignment.CENTER);
	cellstyle1.setVerticalAlignment(VerticalAlignment.CENTER);

	Row hrow = spreadsheet.createRow(0);
	Cell hcell;
	hcell = hrow.createCell(1);
	spreadsheet.addMergedRegion(cellrange);
	hcell.setCellStyle(hstyle);
	hcell.setCellValue("Requirements Report");
	hcell = hrow.createCell(2);
	hcell.setCellStyle(hstyle);
	hcell = hrow.createCell(3);
	hcell.setCellStyle(hstyle);
	hcell = hrow.createCell(5);
	hcell.setCellValue("Start Date : " + startDate + " ");
	hcell = hrow.createCell(7);
	hcell.setCellValue("End Date : " + endDate + " ");
	
	XSSFRow row = spreadsheet.createRow(2);
	XSSFCell cell;
	cell = row.createCell(0);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("REF NO");
	cell = row.createCell(1);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("CRITICALITY");
	cell = row.createCell(2);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("SKILL CATEGORY");
	cell = row.createCell(3);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("PRIMARY SKILL");
	cell = row.createCell(4);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("JOB DESCRIPTION");
	cell = row.createCell(5);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("LOCATION");
	cell = row.createCell(6);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("CITY");
	cell = row.createCell(7);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("BILLING RATE");
	cell = row.createCell(8);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("INTIMATION DATE");
	cell = row.createCell(9);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("INTIMATED BY");
	cell = row.createCell(10);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("EMAIL ID OF INTIMATOR");
	cell = row.createCell(11);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("MODE OF INTIMATION");
	cell = row.createCell(12);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("TYPE OF REQUIREMENT");
	cell = row.createCell(13);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("CUSTOMER EXPECTED DOJ");
	cell = row.createCell(14);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("ACTUAL/PROJECTED CLOSURE DATE");
	cell = row.createCell(15);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("SO");
	cell = row.createCell(16);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("JO");
	cell = row.createCell(17);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("SHORTLISTED PROFILE ID");
	cell = row.createCell(18);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("STATUS");
	cell = row.createCell(19);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("ACTIVITY OWNER");
	cell = row.createCell(20);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("ACTIVITY OWNER EMAIL");
	cell = row.createCell(21);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("ACTUAL OWNER");
	cell = row.createCell(22);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("ACTUAL OWNER EMAIL");
	cell = row.createCell(23);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("REMARKS");
	cell = row.createCell(24);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("ACCOUNT");
	cell = row.createCell(25);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("PROJECT");
	cell = row.createCell(26);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("CREATED ON");
	cell = row.createCell(27);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("CREATED BY");
	cell = row.createCell(28);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("UPDATED ON");
	cell = row.createCell(29);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("UPDATED BY");
	cell = row.createCell(30);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("BAND");
	cell = row.createCell(31);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("QUANTITY");
	cell = row.createCell(32);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("IBG/CDG");
	cell = row.createCell(33);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("IBU/CDU");
	cell = row.createCell(34);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("PROJECT DURATION");
	cell = row.createCell(35);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("PID/CRMID TO RAISE SO");
	cell = row.createCell(36);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("YEAR OF EXPERIENCE");
	cell = row.createCell(37);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("OPPORTUNITY STATUS");
	cell = row.createCell(38);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("SKILL CATEGORY2");
	cell = row.createCell(39);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("SKILL CATEGORY3");
	cell = row.createCell(40);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue("SKILL CATEGORY4");
	
	
	fillData(row, cell, headerCellStyle, cellstyle1, report, workbook,spreadsheet,reportList, response);
		
}
	
/**
 * This method used to fill the data cells
 * @param row Row Object
 * @param cell Cell Object
 * @param headerCellStyle Header Cell Style Object
 * @param cellstyle1 Cell Style for Data
 * @param report Report Object
 * @param workbook WorkBook object
 * @param spreadsheet Spread Sheet Object
 * @param reportList List of ReportV
 * @param response HttpServletResponse object
 */

private void fillData(XSSFRow row,XSSFCell cell,CellStyle headerCellStyle, CellStyle cellstyle1,Report report,
		XSSFWorkbook workbook,XSSFSheet spreadsheet, 
		ArrayList<ReportVO> reportList, HttpServletResponse response) {
	
	if(reportList != null && reportList.size() > 0) {
		row = spreadsheet.createRow(3);
		cell = row.createCell(5);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue("No data found for report.");
	}
	int i = 3; // data should start from 3rd row
	for(ReportVO obj:reportList) {
		
		row = spreadsheet.createRow(i);
		cell = row.createCell(0);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getId());
		cell = row.createCell(1);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getCriticality());
		cell = row.createCell(2);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getSkillCategory());
		cell = row.createCell(3);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getPrimarySkill());
		cell = row.createCell(4);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getJobDescription());
		cell = row.createCell(5);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getLocation());
		cell = row.createCell(6);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getCity());
		cell = row.createCell(7);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getBillingRate());
		cell = row.createCell(8);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getIntimationDate());
		cell = row.createCell(9);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getIntimatedBy());
		cell = row.createCell(10);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getIntimatorEmail());
		cell = row.createCell(11);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getIntimationMode());
		cell = row.createCell(12);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getRequirementType());
		cell = row.createCell(13);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getExpectedDOJ());
		cell = row.createCell(14);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getActualClosureDate());
		cell = row.createCell(15);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getSo());
		cell = row.createCell(16);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getJo());
		cell = row.createCell(17);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getShortlistedProfileId());
		cell = row.createCell(18);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getStatus());
		cell = row.createCell(19);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getActivityOwner());
		cell = row.createCell(20);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getActivityOwnerEmail());
		cell = row.createCell(21);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getActualOwner());
		cell = row.createCell(22);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getActualOwnerEmail());
		cell = row.createCell(23);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getRemarks());
		cell = row.createCell(24);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getAccountName());
		cell = row.createCell(25);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getProjectName());
		cell = row.createCell(26);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getCreatedOn());
		cell = row.createCell(27);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getName());
		cell = row.createCell(28);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getUpdatedOn());
		cell = row.createCell(29);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getUpdatedBy());
		cell = row.createCell(30);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getBand());
		cell = row.createCell(31);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getQuantity());
		cell = row.createCell(32);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getIbgCdg());
		cell = row.createCell(33);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getIbuCdu());
		cell = row.createCell(34);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getProjectDuration());
		cell = row.createCell(35);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getPidCrmidSo());
		cell = row.createCell(36);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getYearOfExperience());
		cell = row.createCell(37);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getOpportunityStatus());
		cell = row.createCell(38);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getSkillCategory2());
		cell = row.createCell(39);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getSkillCategory3());
		cell = row.createCell(40);
		cell.setCellStyle(cellstyle1);
		cell.setCellValue(obj.getSkillCategory4());
		
		i++;
	}
	
	try {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline; filename=\"Requirement_Report.xls\"");
		OutputStream out = response.getOutputStream();
		workbook.write(out);
		out.close();
		workbook.close();
	} catch (IOException ie) {
		System.out.println("IOException occured " + ie.getMessage());
	}

}

	

}
