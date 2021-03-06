package com.tmp.dao.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

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

import com.tmp.dao.ConfigDAO;
import com.tmp.dao.ReportDAO;
import com.tmp.entity.Report;
import com.tmp.util.TMPDAOUtil;
import com.tmp.vo.ReportVO;

public class ReportDAOImpl extends BaseDAO implements ReportDAO {
	@Autowired(required = true)
	@Qualifier("configDAO")
	ConfigDAO configDAO;

	@Autowired(required = true)
	@Qualifier("tmpDAOUtil")
	TMPDAOUtil tmpDAOUtil;

	private DataSource dataSource;

	public TMPDAOUtil getTmpDAOUtil() {
		return tmpDAOUtil;
	}

	public void setTmpDAOUtil(TMPDAOUtil tmpDAOUtil) {
		this.tmpDAOUtil = tmpDAOUtil;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * This method used to populate the report list from given resultset
	 * @param rs ResultSet Object
	 * @return List of ReportVO
	 */
	private ArrayList<ReportVO> populateReportList(ResultSet rs) {
		
		ArrayList<ReportVO> reportList = new ArrayList<ReportVO>();
		
		try {
			if(null != rs) {
			while (rs.next()) {
				
				ReportVO obj = new ReportVO();
				
				obj.setId(rs.getString("ID"));
				if(rs.getInt("CRITICALITY") > 0)
					obj.setCriticality(configDAO.getConfigKeyValueMapping(rs.getInt("CRITICALITY")).getConfigValue().getValue());
				else
					obj.setCriticality("");
				
				if(rs.getInt("SKILL_CATEGORY") > 0)
					obj.setSkillCategory(configDAO.getConfigKeyValueMapping(rs.getInt("SKILL_CATEGORY")).getConfigValue().getValue());
				else
					obj.setSkillCategory("");
				
				if(rs.getInt("PRIMARY_SKILL") > 0)
					obj.setPrimarySkill(configDAO.getConfigKeyValueMapping(rs.getInt("PRIMARY_SKILL")).getConfigValue().getValue());
				else
					obj.setPrimarySkill("");
				
				obj.setJobDescription(rs.getString("JOB_DESCRIPTION"));
				
				if(rs.getInt("LOCATION") > 0)
					obj.setLocation(configDAO.getConfigKeyValueMapping(rs.getInt("LOCATION")).getConfigValue().getValue());
				else
					obj.setLocation("");
				
				obj.setCity(rs.getString("CITY"));
				obj.setBillingRate(rs.getString("BILLING_RATE"));
				obj.setIntimationDate(rs.getString("INTIMATION_DATE"));
				obj.setIntimatedBy(rs.getString("INTIMATED_BY"));
				obj.setIntimatorEmail(rs.getString("INTIMATOR_EMAIL"));
				
				if(rs.getInt("INTIMATION_MODE") > 0)
					obj.setIntimationMode(configDAO.getConfigKeyValueMapping(rs.getInt("INTIMATION_MODE")).getConfigValue().getValue());
				else
					obj.setIntimationMode("");
				
				if(rs.getInt("REQUIREMENT_TYPE") > 0)
					obj.setRequirementType(configDAO.getConfigKeyValueMapping(rs.getInt("REQUIREMENT_TYPE")).getConfigValue().getValue());
				else
					obj.setRequirementType("");
				
				obj.setExpectedDOJ(rs.getString("EXPECTED_DOJ"));
				obj.setActualClosureDate(rs.getString("ACTUAL_CLOSURE_DATE"));
				obj.setSo(rs.getString("SO"));
				obj.setJo(rs.getString("JO"));
				obj.setShortlistedProfileId(rs.getString("SHORTLISTED_PROFILE_ID"));
				
				if(rs.getInt("STATUS") > 0)
					obj.setStatus(configDAO.getConfigKeyValueMapping(rs.getInt("STATUS")).getConfigValue().getValue());
				else
					obj.setStatus("");
				
				obj.setActivityOwner(rs.getString("ACTIVITY_OWNER"));
				obj.setActivityOwnerEmail(rs.getString("ACTIVITY_OWNER_EMAIL"));
				obj.setActualOwner(rs.getString("ACTUAL_OWNER"));
				obj.setActualOwnerEmail(rs.getString("ACTUAL_OWNER_EMAIL"));
				obj.setRemarks(rs.getString("REMARKS"));
				obj.setAccountName(rs.getString("ACCOUNT_NAME"));
				obj.setProjectName(rs.getString("PROJECT_NAME"));
				obj.setCreatedOn(rs.getString("CREATED_ON"));
				obj.setName(rs.getString("NAME"));
				obj.setUpdatedOn(rs.getString("UPDATED_ON"));
				obj.setUpdatedBy(rs.getString("UPDATED_BY"));
				obj.setBand(rs.getString("BAND"));
				obj.setQuantity(rs.getString("QUANTITY"));
				obj.setProjectDuration(rs.getString("PROJECT_DURATION"));
				obj.setPidCrmidSo(rs.getString("PID_CRMID_SO"));
				obj.setYearOfExperience(rs.getString("YEAR_EXPERIENCE"));
				obj.setSkillCategory2(rs.getString("SKILL_CATEGORY2"));
				obj.setSkillCategory3(rs.getString("SKILL_CATEGORY3"));
				obj.setSkillCategory4(rs.getString("SKILL_CATEGORY4"));
				obj.setIbgCdg(configDAO.getAdminInfoKeyValueMapping(rs.getInt("IBG_CDG")).getAdminInfoValue().getValue());
				if(configDAO.getAdminInfoKeyValueMapping(rs.getInt("IBU_CDU")).getAdminInfoValue() != null)
					obj.setIbuCdu(configDAO.getAdminInfoKeyValueMapping(rs.getInt("IBU_CDU")).getAdminInfoValue().getValue());
				else
					obj.setIbuCdu("");
				
				if(rs.getInt("OPPORTUNITY_STATUS") > 0)
					obj.setOpportunityStatus(configDAO.getConfigKeyValueMapping(rs.getInt("OPPORTUNITY_STATUS")).getConfigValue().getValue());
				else
					obj.setOpportunityStatus("");
				
				
				reportList.add(obj);

			}
			}
		} catch (SQLException se) {
			System.out.println("SQL Exception in populateReportList "+se.getMessage());
		} catch (Exception e) {
			System.out.println("Exception in populateReportList "+e.getMessage());
		}
		return reportList;
	}
	
	
	public ArrayList<ReportVO> getReportForExcel(Report report) {
		
		String startDate = report.getStartdate();
		String endDate =report.getEnddate();
		ArrayList<ReportVO> reportList = null;

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date date1 = null;
		java.util.Date date2 = null;
		
		try {
			date1 = sdf.parse(startDate);
			date2 = sdf.parse(endDate);
		} catch (ParseException pe) {
			System.out.println("Exception - date parsing " + pe.getMessage());
		}

		java.sql.Date ssqlDate = new Date(date1.getTime());
		java.sql.Date esqlDate = new Date(date2.getTime());
		//system.out.println("String converted to java.sql.Date :ssqlDate--> " +ssqlDate+"esqlDate-->"+esqlDate);

		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			StringBuffer sql = new StringBuffer("SELECT a.ACCOUNT_NAME,p.PROJECT_NAME,u.NAME,r.* FROM tmp.requirement r"
					+ " INNER JOIN tmp.projects p ON p.ID = r.PROJECT"
					+ " INNER JOIN tmp.account_master a ON a.ACCOUNT_ID = r.ACCOUNT"
					+ " INNER JOIN tmp.user u ON u.ID = r.CREATED_BY"
					+ " where r.CREATED_ON >= ? AND r.CREATED_ON <= ? ORDER BY r.CREATED_ON DESC");
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setDate(1, ssqlDate);
			ps.setDate(2, esqlDate);
			//system.out.println("ps.executeQuery()===> "+ps.executeQuery());
			rs = ps.executeQuery();
			
			reportList = populateReportList(rs);
		} catch(SQLException se) { 
			System.out.println("SQL Exception Occured "+se.getMessage());
		}
		catch (Exception e) {
			System.out.println("Exception Occured "+e.getMessage());
		}finally {
				closeDBObjects(conn, rs, ps);
		}
		return reportList;
		
	}

	public void getReport(Report report, HttpServletResponse response) throws ParseException, IOException {
		String Startdate = report.getStartdate();
		String Enddate =report.getEnddate();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date date1 = sdf.parse(Startdate);
		java.util.Date date2 = sdf.parse(Enddate);

		java.sql.Date ssqlDate = new Date(date1.getTime());
		java.sql.Date esqlDate = new Date(date2.getTime());
		//system.out.println("String converted to java.sql.Date :ssqlDate--> " +ssqlDate+"esqlDate-->"+esqlDate);

		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			StringBuffer sql = new StringBuffer("SELECT a.ACCOUNT_NAME,p.PROJECT_NAME,u.NAME,r.* FROM tmp.requirement r"
					+ " INNER JOIN tmp.projects p ON p.PROJECT_ID = r.PROJECT"
					+ " INNER JOIN tmp.account_master a ON a.ACCOUNT_ID = r.ACCOUNT"
					+ " INNER JOIN tmp.user u ON u.ID = r.CREATED_BY"
					+ " where r.CREATED_ON >= ? AND r.CREATED_ON <= ? ORDER BY r.CREATED_ON DESC");
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setDate(1, ssqlDate);
			ps.setDate(2, esqlDate);
			//system.out.println("ps.executeQuery()===> "+ps.executeQuery());
			rs = ps.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline; filename=\"Requirement_Report.xls\"");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet("Requirements Report");
		// Create a Font for styling header cells

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
		hcell.setCellValue("Start Date : " + Startdate + " ");
		hcell = hrow.createCell(7);
		hcell.setCellValue("End Date : " + Enddate + " ");

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
		
		try {
			int i = 3;
			if (rs.getRow()==0 || rs == null) {
				row = spreadsheet.createRow(3);
				cell = row.createCell(5);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue("No data found for report.");
			}
			if(rs!=null){
			while(rs.next()) {

				row = spreadsheet.createRow(i);
				cell = row.createCell(0);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("ID"));
				cell = row.createCell(1);
				cell.setCellStyle(cellstyle1);
				if((rs.getInt("CRITICALITY"))>0){
				cell.setCellValue(configDAO.getConfigKeyValueMapping(rs.getInt("CRITICALITY")).getConfigValue().getValue());
				}else{
					cell.setCellValue("");
				}
				cell = row.createCell(2);
				cell.setCellStyle(cellstyle1);
				if((rs.getInt("SKILL_CATEGORY"))>0){
				cell.setCellValue(configDAO.getConfigKeyValueMapping(rs.getInt("SKILL_CATEGORY")).getConfigValue().getValue());
				}else{
					cell.setCellValue("");
				}
				cell = row.createCell(3);
				cell.setCellStyle(cellstyle1);
				if((rs.getInt("PRIMARY_SKILL"))>0){
				cell.setCellValue(configDAO.getConfigKeyValueMapping(rs.getInt("PRIMARY_SKILL")).getConfigValue().getValue());
				}else{
					cell.setCellValue("");
				}
				cell = row.createCell(4);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("JOB_DESCRIPTION"));
				cell = row.createCell(5);
				cell.setCellStyle(cellstyle1);
				if((rs.getInt("LOCATION"))>0){
				cell.setCellValue(configDAO.getConfigKeyValueMapping(rs.getInt("LOCATION")).getConfigValue().getValue());
				}else{
					cell.setCellValue("");
				}
				cell = row.createCell(6);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("CITY"));
				cell = row.createCell(7);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("BILLING_RATE"));
				cell = row.createCell(8);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("INTIMATION_DATE"));
				cell = row.createCell(9);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("INTIMATED_BY"));
				cell = row.createCell(10);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("INTIMATOR_EMAIL"));
				cell = row.createCell(11);
				cell.setCellStyle(cellstyle1);
				if((rs.getInt("INTIMATION_MODE"))>0){
				cell.setCellValue(configDAO.getConfigKeyValueMapping(rs.getInt("INTIMATION_MODE")).getConfigValue().getValue());
				}else{
					cell.setCellValue("");
				}
				cell = row.createCell(12);
				cell.setCellStyle(cellstyle1);
				if((rs.getInt("REQUIREMENT_TYPE"))>0){
				cell.setCellValue(configDAO.getConfigKeyValueMapping(rs.getInt("REQUIREMENT_TYPE")).getConfigValue().getValue());
				}else{
					cell.setCellValue("");
				}
				cell = row.createCell(13);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("EXPECTED_DOJ"));
				cell = row.createCell(14);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("ACTUAL_CLOSURE_DATE"));
				cell = row.createCell(15);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("SO"));
				cell = row.createCell(16);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("JO"));
				cell = row.createCell(17);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("SHORTLISTED_PROFILE_ID"));
				cell = row.createCell(18);
				cell.setCellStyle(cellstyle1);
				if((rs.getInt("STATUS"))>0){
				cell.setCellValue(configDAO.getConfigKeyValueMapping(rs.getInt("STATUS")).getConfigValue().getValue());
				}else{
					cell.setCellValue("");
				}
				cell = row.createCell(19);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("ACTIVITY_OWNER"));
				cell = row.createCell(20);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("ACTIVITY_OWNER_EMAIL"));
				cell = row.createCell(21);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("ACTUAL_OWNER"));
				cell = row.createCell(22);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("ACTUAL_OWNER_EMAIL"));
				cell = row.createCell(23);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("REMARKS"));
				cell = row.createCell(24);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("ACCOUNT_NAME"));
				cell = row.createCell(25);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("PROJECT_NAME"));
				cell = row.createCell(26);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("CREATED_ON"));
				cell = row.createCell(27);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("NAME"));
				cell = row.createCell(28);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("UPDATED_ON"));
				cell = row.createCell(29);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("UPDATED_BY"));
				cell = row.createCell(30);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("BAND"));
				cell = row.createCell(31);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("QUANTITY"));
				cell = row.createCell(32);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(configDAO.getAdminInfoKeyValueMapping(rs.getInt("IBG_CDG")).getAdminInfoValue().getValue());
				cell = row.createCell(33);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(configDAO.getAdminInfoKeyValueMapping(rs.getInt("IBU_CDU")).getAdminInfoValue().getValue());
				cell = row.createCell(34);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("PROJECT_DURATION"));
				cell = row.createCell(35);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("PID_CRMID_SO"));
				cell = row.createCell(36);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("YEAR_EXPERIENCE"));
				cell = row.createCell(37);
				cell.setCellStyle(cellstyle1);
				if((rs.getInt("OPPORTUNITY_STATUS"))>0){
					cell.setCellValue(configDAO.getConfigKeyValueMapping(rs.getInt("OPPORTUNITY_STATUS")).getConfigValue().getValue());
					}else{
						cell.setCellValue("");
					}
				cell = row.createCell(38);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("SKILL_CATEGORY2"));
				cell = row.createCell(39);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("SKILL_CATEGORY3"));
				cell = row.createCell(40);
				cell.setCellStyle(cellstyle1);
				cell.setCellValue(rs.getString("SKILL_CATEGORY4"));
				
				i++;
			}
			}
			
			/*// Resize all columns to fit the content size
			for (int j = 0; j < 11; j++) {
				spreadsheet.autoSizeColumn(j);
				//system.out.println("spreadsheet.autoSizeColumn(j)");
			}*/
		}
		catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		OutputStream out = response.getOutputStream();
		workbook.write(out);
		out.close();
		workbook.close();
		//system.out.println("exceldatabase.xlsx written successfully");

	}

}
