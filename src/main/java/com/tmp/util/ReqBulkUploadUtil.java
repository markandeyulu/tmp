package com.tmp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tmp.dao.AdminDAO;
import com.tmp.dao.ConfigDAO;
import com.tmp.dao.RequirementDAO;
import com.tmp.entity.ReqBulk;
import com.tmp.entity.Requirement;

/**
 * This class used to read the data from excelsheet and insert into table
 * 
 * @author AJ00561494
 *
 */
public class ReqBulkUploadUtil {
	
	
	@Autowired(required = true)
	@Qualifier("configDAO")
	ConfigDAO configDAO;
	
	@Autowired(required = true)
	@Qualifier("adminDAO")
	AdminDAO adminDAO;

	@Autowired(required = true)
	@Qualifier("tmpUtil")
	TMPUtil tmpUtil;
	
	public TMPUtil getTmpUtil() {
		return tmpUtil;
	}
	public void setTmpUtil(TMPUtil tmpUtil) {
		this.tmpUtil = tmpUtil;
	}

	@Autowired(required = true)
	@Qualifier("tmpDAOUtil")
	TMPDAOUtil tmpDAOUtil;
	
	@Autowired(required = true)
	@Qualifier("requirementDAO")
	RequirementDAO requirementDAO;
	
	public RequirementDAO getRequirementDAO() {
		return requirementDAO;
	}
	public void setRequirementDAO(RequirementDAO requirementDAO) {
		this.requirementDAO = requirementDAO;
	}
	/*public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}*/
	public ConfigDAO getConfigDAO() {
		return configDAO;
	}
	public void setConfigDAO(ConfigDAO configDAO) {
		this.configDAO = configDAO;
	}
	public AdminDAO getAdminDAO() {
		return adminDAO;
	}
	public void setAdminDAO(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}
	public TMPDAOUtil getTmpDAOUtil() {
		return tmpDAOUtil;
	}
	public void setTmpDAOUtil(TMPDAOUtil tmpDAOUtil) {
		this.tmpDAOUtil = tmpDAOUtil;
	}
	
	Connection connect = null;
	PreparedStatement ps = null;
	ResultSet rt = null;
	static int i = 0;

	//private static final String FILE_PATH = "C:/Users/AJ00561494/Downloads/";
	
	private static final String FILE_PATH = "D://excel//";
	
	//D:\excel
	// C:\Users\AJ00561494\Desktop

	static String setStringValue(String input) {
		if (StringUtils.isNotEmpty(input))
			return input;
		else
			return "";
	}
	static Integer setNumericValue(Integer input){
		if (input!=null)
			return input;
		else
			return 0;
	}
	
	static Long convertLongObject(Object value) {
		// Long result = new Long(0);

		// Double d = new Double(15.25);

		// System.out.println("value is "+d.longValue());

		if (StringUtils.isNotBlank(value+"")) {
			// result = (Long) value;
			if (value instanceof Double)
				return ((Double) value).longValue();
			else {
				String strValue = ((String)value).substring(1,((String)value).length());
				try {
				return new Double(strValue).longValue();
				}catch(Exception e)
				{
					return new Long(0);
				}
				
			}
		} else
			// return result;
			return new Long(0);
	}
	
	public int getAccountIdByName(String accountName) {
		
		int accountId = 0;
 		if(StringUtils.isNotBlank(accountName)) {
 			System.out.println("Account Name : "+accountName);
			accountId = tmpDAOUtil.getAccountByName(accountName);
		}
 		
 		
 		
		return accountId;
	}
	
	public int getProjectName(String projectName) {
		
		int projectId = 0;
		if(StringUtils.isNotBlank(projectName)) {
			projectId = tmpDAOUtil.getProjectName(projectName).getProjectId();
		}
		return projectId;
	}
	
	public int getProjectNameByAccountId(String projectName, int accountId) {
		int projectId = 0;
		if(StringUtils.isNotBlank(projectName)) {
			projectId = tmpDAOUtil.getProjectIdByName(projectName, accountId);
		}
		
		
		return projectId;
	}
	
	
	
	public int getKeyByValue(String searchFor, String searchValue) {
		
		int searchKey = 0;
		
		if(StringUtils.isNotBlank(searchValue)) {
			System.out.println("SearchValue "+ searchValue);
			searchKey = tmpUtil.getConfigKeyIdByName(getSearchIndex(searchFor), searchValue);
		}
		
		return searchKey;
	}
	
	static int getSearchIndex(String searchFor) {
		
		int searchIndex = 0;
		if(StringUtils.isNotBlank(searchFor)) {
		if(searchFor.equalsIgnoreCase("location"))
			searchIndex = 2;
		if(searchFor.equalsIgnoreCase("criticality"))
			searchIndex = 1;
		if(searchFor.equalsIgnoreCase("positionstatus"))
			searchIndex = 5;
		if(searchFor.equalsIgnoreCase("opportunitystatus"))
			searchIndex = 6;
		if(searchFor.equalsIgnoreCase("primaryskill"))
			searchIndex = 11;
		if(searchFor.equalsIgnoreCase("skillcategory"))
			searchIndex = 10;
		if(searchFor.equalsIgnoreCase("intimationmode"))
			searchIndex = 3;
		if(searchFor.equalsIgnoreCase("reqtype"))
			searchIndex = 4;
		}
		return searchIndex;
	}
	
	
	
	static Integer convertStringObject(String value) {
		
		if (value != null && !(value.trim().equals(""))) {
		    Integer a=0;
			if (value instanceof String) {
				try {
				a =Integer.parseInt(value);
				return a;
				}catch(Exception e) {
					
				}
			}
		} 
			return new Integer(0);
	}

	/**
	 * This method used to read the values from cell and convert into User object
	 * 
	 * @return List of User Object
	 * @throws IOException
	 */
	public ArrayList<ReqBulk> getReqListFromExcel(HttpServletRequest request) throws IOException {
		ReqBulk reqBulk = null;
		ArrayList<ReqBulk> reqList = new ArrayList<ReqBulk>();
		HttpSession session = request.getSession();
		String userId=session.getAttribute("user").toString();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(FILE_PATH + "requirement.xlsx"));
			Workbook workbook = new XSSFWorkbook(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			// to iterate through sheets
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				
				System.out.println("Sheet Name "+sheet.getSheetName());

				Iterator<Row> rowIterator = sheet.iterator();
				// static rowIterator
				// to iterate through rows
				while (rowIterator.hasNext()) {

					reqBulk = new ReqBulk();
					Row row = (Row) rowIterator.next();
					// System.out.println ("Row No.: " + row.getRowNum ());
					if (row.getRowNum() == 0) {
						continue; // just skip the rows if row number is 0 or 1
					} else {

						Iterator<Cell> cellIterator = row.cellIterator();
						DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
						// to iterate through cells
						while (cellIterator.hasNext()) {
							Cell cell = (Cell) cellIterator.next();

							if (cell.getColumnIndex() == 34) {
								reqBulk.setBand(setStringValue(cell.getStringCellValue()));
								
								//reqBulk.setBand(cell.getStringCellValue());
								System.out.println("index 34" + reqBulk.getBand());
							}
							if (cell.getColumnIndex() == 37) {
								reqBulk.setCustomerName(cell.getStringCellValue());
								//reqBulk.setCustomerName(getKeyByValue("reqtype", cell.getStringCellValue()));
								System.out.println("index 37 customer name" + reqBulk.getCustomerName());
							}
							if (cell.getColumnIndex() == 38) {

								//reqBulk.setProjectName(getProjectName(cell.getStringCellValue()));
								if(reqBulk.getCustomerName() != null)
									reqBulk.setProjectName(cell.getStringCellValue()); 
								//reqBulk.setProjectName(getKeyByValue("reqtype", cell.getStringCellValue()));

								System.out.println("index 38 project name" + reqBulk.getProjectName());
							}
							if (cell.getColumnIndex() == 14) {
								//reqBulk.setExpectedDOJ(setStringValue(cell.getStringCellValue()));
								System.out.println("****Date is "+cell.getDateCellValue() +" ***"+cell.getDateCellValue());
								Date expectedDOJ = null;
								if(StringUtils.isNotBlank(cell.getDateCellValue()+"" )) {
									//expectedDOJ = formatter.parse(cell.getStringCellValue());
									expectedDOJ = formatDate(cell.getDateCellValue()+"");
									reqBulk.setExpectedDOJ(expectedDOJ);
								}
								//System.out.println("index 14" + reqBulk.getExpectedDOJ());
							}
							if (cell.getColumnIndex() == 13) {
								//reqBulk.setReqType(convertStringObject(cell.getStringCellValue()));
								reqBulk.setReqType(getKeyByValue("reqtype", cell.getStringCellValue()));
								System.out.println("index 13" + reqBulk.getReqType());
							}
							if (cell.getColumnIndex() == 18) {
								reqBulk.setSo(convertStringObject(cell.getNumericCellValue()+""));
								System.out.println("index 18" + reqBulk.getSo());
							}
							if (cell.getColumnIndex() == 19) {
								reqBulk.setJo(convertStringObject(cell.getNumericCellValue()+""));
								System.out.println("index 19" + reqBulk.getJo());
							}
							if (cell.getColumnIndex() == 6) {
								reqBulk.setLocation(getKeyByValue("location", cell.getStringCellValue()));
								System.out.println("index 6" + reqBulk.getLocation());
							}
							if (cell.getColumnIndex() == 26) {
								reqBulk.setPositionStatus(getKeyByValue("positionstatus", cell.getStringCellValue()));
								System.out.println("index 26" + reqBulk.getPositionStatus());
							}
							if (cell.getColumnIndex() == 16) {
								
								Date actualDate = null;
								if(StringUtils.isNotBlank(cell.getDateCellValue()+"" )) {
									//actualDate = formatter.parse(cell.getStringCellValue());
									actualDate = formatDate(cell.getDateCellValue()+"");
									reqBulk.setActualClosureDate(actualDate);
								
								System.out.println("index 16" + reqBulk.getActualClosureDate());
								}
								
							}
							if (cell.getColumnIndex() == 15) {
								Date planDate = null;
								if(StringUtils.isNotBlank(cell.getDateCellValue()+"" )) {
									//planDate = formatter.parse(cell.getStringCellValue());
									planDate = formatDate(cell.getDateCellValue()+"");
									reqBulk.setPlannedClosureDate(planDate);
									System.out.println("index 15" + reqBulk.getPlannedClosureDate());
								}
								System.out.println("index 15 can be null");
								
							}
							if (cell.getColumnIndex() == 32) {
								reqBulk.setRemarks(setStringValue(cell.getStringCellValue() + ""));
								System.out.println("index 32" + reqBulk.getRemarks());
							}
							if (cell.getColumnIndex() == 7) {
								reqBulk.setCity(setStringValue(cell.getStringCellValue()));
								System.out.println("index 7" + reqBulk.getCity());
							}
							if (cell.getColumnIndex() == 4) {
								reqBulk.setpSkill(getKeyByValue("primaryskill", cell.getStringCellValue()));
								//reqBulk.setProjectName(getProjectName(cell.getStringCellValue()));
								System.out.println("index 4" + reqBulk.getpSkill());
							}
							if (cell.getColumnIndex() == 3) {
								reqBulk.setSkillCategory(getKeyByValue("skillcategory", cell.getStringCellValue()));
								System.out.println("index 3" + reqBulk.getSkillCategory());
							}
							if (cell.getColumnIndex() == 5) {
								reqBulk.setJobDescription(setStringValue(cell.getStringCellValue() + ""));
								if(reqBulk.getJobDescription().length() > 255) {
									reqBulk.setJobDescription(reqBulk.getJobDescription().substring(0, 254));
								}
								System.out.println("index 5" + reqBulk.getJobDescription());
							}
							if (cell.getColumnIndex() == 8) {
								//reqBulk.setBillingRate(convertLongObject(cell.getNumericCellValue() + ""));
								String billingRate = "";
								
								try {
									billingRate = cell.getStringCellValue() + "";
								}catch(Exception e) {
									billingRate = cell.getNumericCellValue() + "";
								}
								
								reqBulk.setBillingRate(convertLongObject(billingRate));
								System.out.println("index 8" + reqBulk.getBillingRate());
							}
							if (cell.getColumnIndex() == 38) {
								reqBulk.setPid(setStringValue(cell.getStringCellValue() + ""));
								System.out.println("index 38" + reqBulk.getPid());
							}
							if (cell.getColumnIndex() == 1) {

								System.out.println("crita****"+cell.getStringCellValue());
								//reqBulk.setCriticality(convertStringObject(cell.getStringCellValue()));
								reqBulk.setCriticality(getKeyByValue("criticality", setStringValue(cell.getStringCellValue())));
								System.out.println("index 1" + reqBulk.getCriticality());
							}
							if (cell.getColumnIndex() == 11) {
								reqBulk.setIntimatedBy(setStringValue(cell.getStringCellValue()));
								System.out.println("index 11" + reqBulk.getIntimatedBy());
							}
							if (cell.getColumnIndex() == 30) {
								reqBulk.setActualOwner(setStringValue(cell.getStringCellValue() + ""));
								System.out.println("index 30" + reqBulk.getActualOwner());
							}
							if (cell.getColumnIndex() == 31) {
								reqBulk.setActivityOwner(setStringValue(cell.getStringCellValue() + ""));
								System.out.println("index 31" + reqBulk.getActivityOwner());
							}
							if (cell.getColumnIndex() == 12) {
								if(StringUtils.isNotBlank(cell.getStringCellValue())) {
								reqBulk.setIntimationMode(getKeyByValue("intimationmode", cell.getStringCellValue()));
								}else {
									reqBulk.setIntimationMode(7);
								}
								System.out.println("index 12" + reqBulk.getIntimationMode());
							}
							if (cell.getColumnIndex() == 27) {
								reqBulk.setOpportunityStatus(getKeyByValue("opportunitystatus", cell.getStringCellValue()));
								System.out.println("index 27" + reqBulk.getOpportunityStatus());
							}
							if (cell.getColumnIndex() == 9) {
								Date intiDate = null;
								System.out.println(" cell value is "+cell.getDateCellValue());
								if(StringUtils.isNotBlank(cell.getDateCellValue()+"" )) {
									//intiDate = formatter.parse(cell.getDateCellValue()+"");
									intiDate = formatDate(cell.getDateCellValue()+"");
									reqBulk.setIntiDate(intiDate);
									System.out.println("index 9" + reqBulk.getIntiDate());
								}
								System.out.println("index 9 can be null");
								/*Date intiDate = formatter.parse(cell.getStringCellValue());
								reqBulk.setIntiDate(intiDate);
								System.out.println("index 9" + reqBulk.getIntiDate());*/
							}
						}
					}
					setDummyValues(reqBulk);
					reqList.add(reqBulk);
					
				}
			
			
		}workbook.close(); }catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reqList;
	}
	
	public static Date formatDate(String dateStr) {
		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		DateFormat toFormatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		Date toDate = null;
		String formatedDate = "";
		try {
			date = (Date)formatter.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			formatedDate = cal.get(Calendar.DATE) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +cal.get(Calendar.YEAR);
			System.out.println("Formatted Date "+formatedDate);
			toDate = toFormatter.parse(formatedDate);
			System.out.println("toDate object"+toDate);
		}catch(ParseException pe) {
			
		}
		return toDate;
	}

	private void populateRequirementForInsert(PreparedStatement ps, Requirement requirement, String userId) throws SQLException {

		if (ps == null) {
			return;
		}
		
		ps.setString(1, requirement.getId());
		ps.setString(2, requirement.getCriticality1());
		adminDAO.getRequirementMappingValue(requirement.getSkillCategoryAdd1(), 10, userId);
		ps.setInt(3, configDAO.getConfigKeyValueMapping(requirement.getSkillCategoryAdd1()).getId());
		adminDAO.getRequirementMappingValue(requirement.getPrimarySkill1(), 11, userId);
		ps.setInt(4, configDAO.getConfigKeyValueMapping(requirement.getPrimarySkill1()).getId());
		ps.setString(5, requirement.getJobDescription());
		ps.setString(6, requirement.getLocation1());
		ps.setString(7, requirement.getCity());
		ps.setDouble(8, requirement.getBillingRate());
		ps.setDate(9, tmpDAOUtil.convertUtilDatetoSQLDate(requirement.getIntimationDate()));
		ps.setString(10, requirement.getIntimatedBy());
		ps.setString(11, requirement.getIntimatorEmail());
		ps.setString(12,requirement.getIntimationModeAdd());
		ps.setString(13, requirement.getRequirementTypeAdd());
		ps.setDate(14, tmpDAOUtil.convertUtilDatetoSQLDate(requirement.getExpectedDOJ()));
		ps.setDate(15, tmpDAOUtil.convertUtilDatetoSQLDate(requirement.getActualClosureDate()));
		ps.setInt(16, requirement.getSo());
		ps.setInt(17, requirement.getJo());
		ps.setString(18, requirement.getRemarks());
		adminDAO.getRequirementAdminMappingValue(requirement.getIbg_cdg(), 4, userId);
		ps.setInt(19, configDAO.getAdminInfoKeyValueMapping(requirement.getIbg_cdg()).getId());
		adminDAO.getRequirementAdminMappingValue(requirement.getIbu_cdu(), 5, userId);
		ps.setInt(20, configDAO.getAdminInfoKeyValueMapping(requirement.getIbu_cdu()).getId());
		/*int accNewMappingId = adminDAO.getRequirementAdminAccountMapping(requirement.getAccount1(), 6, userId, configDAO.getAdminInfoKeyValueMapping(requirement.getIbg_cdg()).getId(),
				configDAO.getAdminInfoKeyValueMapping(requirement.getIbu_cdu()).getId());
		int accId = configDAO.getAdminInfoKeyValueMapping(requirement.getAccount1()).getId();
		int accountId = configDAO.getAccountMappingId(accId).getAccountId();
		ps.setInt(21, accountId);
		adminDAO.getRequirementAdminProjectMapping(requirement.getProjectAdd(), 7, userId, requirement.getAccount1(), accNewMappingId, accountId);
		int projectId = configDAO.getAdminInfoKeyValueMapping(requirement.getProjectAdd()).getId();
		ps.setInt(22, configDAO.getProjectMappingId(accountId,projectId).getId());*/
		
		ps.setInt(21, Integer.parseInt(requirement.getAccount1()));
		ps.setInt(22, Integer.parseInt(requirement.getProjectAdd()));
		ps.setTimestamp(23, new TMPDAOUtil().getCurrentTimestamp());
		ps.setString(24, userId);
		ps.setString(25, requirement.getBand());
		ps.setString(26, requirement.getQuantity());
		ps.setString(27, requirement.getProjectDuration());
		ps.setString(28, requirement.getPid_crmid_so());
		ps.setString(29, requirement.getYearExperience());
		ps.setString(30, requirement.getSkillCategoryAdd2());
		ps.setString(31, requirement.getSkillCategoryAdd3());
		ps.setString(32, requirement.getSkillCategoryAdd4());
		ps.setInt(33, configDAO.getConfigKeyValueMapping("Profile Sourcing").getId());
		ps.setInt(34, configDAO.getConfigKeyValueMapping("Open").getId());

	}
	
	/**
	 * This method used to set default values for mandatory fields as they are missing in excel sheet
	 * @param reqBulk ReqBulk Object
	 */
	private static void setDummyValues(ReqBulk reqBulk) {
		
		reqBulk.setIntimatorEmail("Siva.Subramanian@TechMahindra.com");
		reqBulk.setPosition(1);
		reqBulk.setProjectDuration(6);
		reqBulk.setIbg(8);
		reqBulk.setIbu(9);
		reqBulk.setExperience(4);
		
	}

	// check the result set size, if it is greater than 0, then have to return true
	// else return false
	
	
	
private static String getShortName(String name) {
		
		String shortName = "";
		String[] words = name.split(" ");
		if(words.length == 1) { // ONE WORD ONLY
			shortName = words[0].substring(0, 2);
		}else{
			shortName = words[0].substring(0, 1)+words[1].substring(0, 1);
		}
		return shortName.toUpperCase();
	}
	
	private static String getRandomString(String dbDate, String incrementor, String accountName, String projectName, String userName) {

		DateFormat df = new SimpleDateFormat("yyyy_MM");
		Date today = Calendar.getInstance().getTime(); 
		String currDate = df.format(today);
		String defaultIncrementor="01";
		String reqRandomNo =null;
		String shortNames = "_"+getShortName(accountName)+"_"+getShortName(projectName)+"_"+getShortName(userName)+"_";
		try {
			
			if(dbDate == null){
				reqRandomNo = currDate+shortNames+defaultIncrementor;
			}else if(dbDate.equals(currDate)){
				int reqDateIncr= Integer.parseInt(incrementor)+1;
				if(reqDateIncr<=9){
					reqRandomNo = currDate+shortNames+0+reqDateIncr;
				}else{
					reqRandomNo = currDate+shortNames+reqDateIncr;
				}
			}else{
				reqRandomNo = currDate+shortNames+defaultIncrementor;
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return reqRandomNo;
	}
	
	
	/*private String getRequirementId(Requirement requirement, String userName, String userId) { 
		String requirementId = null;
		try {
			
			String accountName = null, projectName = null;
			int accountId = Integer.parseInt(requirement.getAccount1());
			//accountName = configDAO.getAccountMapping(accountId).getAccount().getAdminInfoValue().getValue();
			accountName = new TMPDAOUtil().getAccount(accountId).getAccountName();
			int projectId = Integer.parseInt(requirement.getProjectAdd());
			String incrementor=null;
			String dbDate=null;
			//projectName = configDAO.getProjectMapping(projectId).getProject().getAdminInfoValue().getValue();
			projectName = new TMPDAOUtil().getProject(projectId).getProjectName();
			
			requirementId = getLatestRequirementId();
			
			if(StringUtils.isNotBlank(requirementId)) {
				String[] requirementIdParts = requirementId.split("_");
				
				if(requirementIdParts.length==6){
					incrementor = requirementIdParts[5];
					dbDate = requirementIdParts[0]+"_"+requirementIdParts[1];
				}				
			}			
			requirementId = getRandomString(dbDate, incrementor, accountName, projectName, userName);
			System.out.println(" New Req ID : "+requirementId);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return requirementId;
	}
	*/

	/*public int createRequirement(Requirement requirement, String userId, String userName) {
		StringBuffer sql = new StringBuffer(
				"INSERT INTO REQUIREMENT (ID, CRITICALITY, SKILL_CATEGORY, PRIMARY_SKILL, JOB_DESCRIPTION, \r\n")
				.append("LOCATION, CITY, BILLING_RATE, INTIMATION_DATE, INTIMATED_BY, INTIMATOR_EMAIL, INTIMATION_MODE, \r\n")
				.append("REQUIREMENT_TYPE, EXPECTED_DOJ, SO, JO, \r\n")
				.append("IBG_CDG, IBU_CDU, ACCOUNT, PROJECT, CREATED_ON, CREATED_BY, BAND ,QUANTITY, PROJECT_DURATION, PID_CRMID_SO, YEAR_EXPERIENCE, \r\n")
				.append("SKILL_CATEGORY2, SKILL_CATEGORY3, SKILL_CATEGORY4, STATUS, OPPORTUNITY_STATUS) \r\n")
				.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		Connection conn = null;
		int value;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
		//	requirement.setId(getRequirementId(requirement, userName, userId));
			populateRequirementForInsert(ps, requirement, userId);
			value = ps.executeUpdate();
			ps.close();
			System.out.println("Successfully the requirement has been created!! "+requirement.getId());
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println("Failure!! the requirement has not been created!! "+requirement.getId());
			return 0;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlException) {
				}
			}
		}
		return value;
	}*/

	
	/*public static void insertUser(String name, String emailId, String password, Long contactNo, String displayName,
			String role, String accountId, ReqBulk us) {

		long userId = 0;

		if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(emailId)) {

			try {
				String insertUser = "insert into userexcel (NAME,EMAIL,CONTACT_NO,PASSWORD,DISPLAY_NAME,ROLE) values(?,?,?,?,?,?)";
				connect = Connectivity.getConnection();
				ps = connect.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
				
				 * connect = Connectivity.getConnection(); ps =
				 * Connectivity.getPreparedStatement(connect, insertUser);
				 * //ps=Statement.RETURN_GENERATED_KEYS;
				  ps.setString(1, name);
				ps.setString(2, emailId);
				ps.setString(3, contactNo + "");
				ps.setString(4, password);
				ps.setString(5, displayName);
				ps.setString(6, role);
				ps.executeUpdate();

				try {
					ResultSet generatedKeys = ps.getGeneratedKeys();
					if (generatedKeys.next()) {
						userId = generatedKeys.getLong(1);
						String insertAccount = "insert into user_account_excel (user_id,account_id)values(?,?)";
						connect = Connectivity.getConnection();
						ps = connect.prepareStatement(insertAccount);

						System.out.println(" id " + userId + " ** " + accountId);

						ps.setLong(1, userId);
						ps.setLong(2, new Long(accountId.substring(0, accountId.length() - 2)));
						ps.executeUpdate();
					} else {
						throw new SQLException("Creating user failed, no ID obtained.");
					}
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("error in insertion");
				} finally {
					Connectivity.closeInstance(connect, ps);
					// Connectivity.closeInstance(rt);
				}

			}*/

			// retrieve userObj value - set into prepare statement
			/*catch (SQLException e) {
				e.printStackTrace();
				System.out.println("error in insertion");
			} finally {
				Connectivity.closeInstance(connect, ps);
				// Connectivity.closeInstance(rt);
			}

		}*/
	//}

	public void processReqList(ArrayList<ReqBulk> reqList, String userId, String displayName) {
		ReqBulk bulkList = null;
		Requirement req= new Requirement();
		for (i = 0; i < reqList.size(); i++) {
			bulkList = (ReqBulk) reqList.get(i);
			
			
			
			req.setCriticality1(bulkList.getCriticality()+"");
			req.setSkillCategoryAdd1(bulkList.getSkillCategory()+"");
			req.setPrimarySkill1(bulkList.getpSkill()+"");
			req.setJobDescription(bulkList.getJobDescription());
			req.setLocation1(bulkList.getLocation()+"");
			req.setCity(bulkList.getCity());
			req.setBillingRate(bulkList.getBillingRate());
			req.setIntimationDate(bulkList.getIntiDate());
			req.setIntimatedBy(bulkList.getIntimatedBy());
			req.setIntimatorEmail(bulkList.getIntimatorEmail());
			req.setIntimationModeAdd(bulkList.getIntimationMode()+"");
			req.setRequirementTypeAdd(bulkList.getReqType()+"");
			req.setExpectedDOJ(bulkList.getExpectedDOJ());
			req.setActualClosureDate(bulkList.getActualClosureDate());
			req.setSo(bulkList.getSo());
			req.setJo(bulkList.getJo());
			req.setRemarks(bulkList.getRemarks());
			req.setIbg_cdg(bulkList.getIbg()+"");
			req.setIbu_cdu(bulkList.getIbu()+"");
			
			req.setAccount1(bulkList.getCustomerName()+"");
			
			System.out.println("Account Name :: "+req.getAccount1());
			
			req.setProjectAdd(bulkList.getProjectName()+"");
			req.setBand(bulkList.getBand());
			req.setQuantity(bulkList.getPosition()+"");
			req.setProjectDuration(bulkList.getProjectDuration()+"");
			req.setPid_crmid_so(bulkList.getPid());
			req.setYearExperience(bulkList.getExperience()+"");
			req.setSkillCategoryAdd2("");
			req.setSkillCategoryAdd3("");
			req.setSkillCategoryAdd4("");
			
			//req.setActivityOwner(bulkList.getActivityOwner());
			
			//req.setActualOwner(bulkList.getActualOwner());
			
			//req.setOppurtunitystatus(bulkList.getOpportunityStatus()+"");
		
			//req.setplbulkList.getPlannedClosureDate());
			
			//req.setposbulkList.getPositionStatus());
			
		
			
			
			
			
			System.out.println("processReqList");
			requirementDAO.createRequirement(req, userId, displayName);
		}
		
	}

	/*public static void main(String args[]) throws IOException {

		
		ArrayList<ReqBulk> reqList = getReqListFromExcel();
		processReqList(reqList);
	}*/

}
