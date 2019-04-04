package com.tmp.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
//import com.forddirect.nvo.schema.Candidate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tmp.dao.CandidateDAO;
import com.tmp.dao.impl.CandidateDAOImpl;
import com.tmp.entity.Associate;

/**
 * This class used to read the data from excelsheet and insert into table
 * 
 * @author AJ00561494
 *
 */

public class CandidateExcelRead {
	@Autowired(required = true)
	@Qualifier("candidateDAO")
	CandidateDAO candidateDAO;

	public CandidateDAO getCandidateDAO() {
		return candidateDAO;
	}

	public void setCandidateDAO(CandidateDAO candidateDAO) {
		this.candidateDAO = candidateDAO;
	}

	/**
	 * This method used to read the excel file and populate the Candidate list to
	 * insert into table
	 * 
	 * @return List of Candidate Objects
	 */

	public static Date formatDate(String dateStr) {
		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		DateFormat toFormatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		Date toDate = null;
		String formatedDate = "";
		try {
			date = (Date) formatter.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			formatedDate = cal.get(Calendar.DATE) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
			System.out.println("Formatted Date " + formatedDate);
			toDate = toFormatter.parse(formatedDate);
			System.out.println("toDate object" + toDate);
		} catch (ParseException pe) {

		}
		return toDate;
	}

	static Long convertLongObject(Object value) {
		if (StringUtils.isNotBlank(value + "")) {
			if (value instanceof Double)
				return ((Double) value).longValue();
			else {
				String strValue = ((String) value).substring(1, ((String) value).length());
				try {
					return new Double(strValue).longValue();
				} catch (Exception e) {
					return new Long(0);
				}

			}
		} else
			// return result;
			return new Long(0);
	}

	public ArrayList<Associate> getCandidateListFromExcel(String files) {
		System.out.println("Read excel!!"+files);
		ArrayList<Associate> candidateList = new ArrayList<Associate>();
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(files);
			// Using XSSF for xlsx format, for xls use HSSF
			Workbook workbook = new XSSFWorkbook(fis);

			Sheet sheet = workbook.getSheetAt(0);
			Iterator rowIterator = sheet.iterator();

			// skipping the first row as it is column header
			if (rowIterator.hasNext())
				rowIterator.next();

			Associate candidate = null;

			// iterating over from second row
			while (rowIterator.hasNext()) {
				candidate = new Associate();
				Row row = (Row) rowIterator.next();
				Iterator cellIterator = row.cellIterator();
				// Iterating over each cell (column wise) in a particular row.
				while (cellIterator.hasNext()) {
					Cell cell = (Cell) cellIterator.next();
					// The Cell Containing String will is name.
					int columnIndex = 0;
					String columnValue = "";
					columnIndex = cell.getColumnIndex();
					if (Cell.CELL_TYPE_STRING == cell.getCellType()) {

						if (null != cell.getStringCellValue())
							columnValue = cell.getStringCellValue();

						if (columnIndex == 0) {
							candidate.setEmpId(columnValue);
						} else if (columnIndex == 1) {
							candidate.setEmpName(columnValue);
						} else if (columnIndex == 2) {
							candidate.setGender(columnValue);
						} else if (columnIndex == 3) {
							candidate.setHtrFlag(columnValue);
						} else if (columnIndex == 4) {
							candidate.setEmpIBU(columnValue);
						} else if (columnIndex == 5) {
							candidate.setBand(columnValue);
						}
						if (cell.getColumnIndex() == 6) {
							String totalExperience = "";

							try {
								totalExperience = cell.getStringCellValue() + "";
							} catch (Exception e) {
								totalExperience = cell.getNumericCellValue() + "";
							}

							candidate.setTotalExperience(convertLongObject(totalExperience));
						} else if (columnIndex == 7) {
							String techmExperience = "";
							try {
								techmExperience = cell.getStringCellValue() + "";
							} catch (Exception e) {
								techmExperience = cell.getNumericCellValue() + "";
							}
							candidate.setTechmExperience(convertLongObject(techmExperience));
						} else if (columnIndex == 8) {
							candidate.setCurrentCoutnry(columnValue);
						} else if (columnIndex == 9) {
							candidate.setCurrentLocationCity(columnValue);
						} else if (columnIndex == 10) {
							candidate.setOnsiteOffshore(columnValue);
						} else if (columnIndex == 14) {
							candidate.setProjectDescription(columnValue);
						} else if (columnIndex == 16) {
							candidate.setProjectContractType(columnValue);
						} else if (columnIndex == 17) {
							candidate.setProjectIBU(columnValue);
						} else if (columnIndex == 18) {
							candidate.setBillablityStatus(columnValue);
						} else if (columnIndex == 20) {
							candidate.setCustomerName(columnValue);
						} else if (columnIndex == 21) {
							candidate.setSupervisorName(columnValue);
						} else if (columnIndex == 22) {
							candidate.setProjectManagerName(columnValue);
						} else if (columnIndex == 23) {
							candidate.setProgramManagerName(columnValue);
						} else if (columnIndex == 24) {
							candidate.setCustomerGroupName(columnValue);
						} else if (columnIndex == 25) {
							candidate.setPrimarySkill(columnValue);
						} else if (columnIndex == 26) {
							candidate.setSecondarySkill(columnValue);
						} else if (columnIndex == 27) {
							candidate.setCurrentProjectRole(columnValue);
						} else if (columnIndex == 28) {
							candidate.setCertifications(columnValue);
						}
						// The Cell Containing numeric value will contain
					} else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {

						DecimalFormat df = new DecimalFormat();
						df.setGroupingUsed(false);

						// Cell with index 6 contains total experience
						if (columnIndex == 0)
							try {
								candidate.setEmpId(df.format(cell.getNumericCellValue()));
							} catch (Exception e) {
							}
						else if (columnIndex == 6) {
							candidate.setTotalExperience(cell.getNumericCellValue());
						} else if (columnIndex == 7) {
							candidate.setTechmExperience(cell.getNumericCellValue());
						} else if (columnIndex == 13)
							try {
								candidate.setProjectId(df.format(cell.getNumericCellValue()) + "");
							} catch (Exception e) {
							}
						else if (columnIndex == 19)
							try {
								candidate.setCustomerId(df.format(cell.getNumericCellValue()) + "");
							} catch (Exception e) {
							}
					}
					if (columnIndex == 11) {
						candidate.setStartDate(convertDateToString(cell.getDateCellValue()));
					}
					if (columnIndex == 12) {
						candidate.setEndDate(convertDateToString(cell.getDateCellValue()));
					}
					if (columnIndex == 15) {
						candidate.setProjectEndDate(convertDateToString(cell.getDateCellValue()));
					}
				}
				candidateList.add(candidate);
			}
			fis.close();

		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Size of candidate list "+candidateList.size());

		return candidateList;

	}

	public int insertResourceList(ArrayList<Associate> candidateList, String userId, String userName) {

		int result = candidateDAO.insertCandidateList(candidateList, userId, userName);
		return result;
	}

	/**
	 * This method used to convert java.util.Date object to String
	 * 
	 * @param date
	 * @return String formatted date (dd-MM-yyyy)
	 */
	private String convertDateToString(Date date) {
		String strDate = "";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		if (null != date)
			strDate = dateFormat.format(date);

		return strDate;
	}

}
