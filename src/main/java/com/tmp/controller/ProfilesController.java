package com.tmp.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Blob;
import com.tmp.dao.ConfigDAO;
import com.tmp.dao.ProfilesDAO;
import com.tmp.dao.ResumeUploadDAO;
import com.tmp.entity.Associate;
import com.tmp.entity.Charts;
import com.tmp.entity.Profile;
import com.tmp.entity.ProfileResume;
import com.tmp.entity.Profiles;
import com.tmp.service.ProfileService;
import com.tmp.util.CandidateExcelRead;
import com.tmp.util.TMPUtil;

@Controller
public class ProfilesController {

	@Autowired(required = true)
	@Qualifier("tmpUtil")
	TMPUtil tmpUtil;

	@Autowired(required = true)
	@Qualifier("configDAO")
	ConfigDAO configDAO;

	@Autowired(required = true)
	@Qualifier("profileService")
	ProfileService profileService;

	@Autowired(required = true)
	@Qualifier("candidateExcelRead")
	CandidateExcelRead candidateExcelRead;
	
	@Autowired(required = true)
	@Qualifier("resumeUploadDAO")
	ResumeUploadDAO resumeUploadDAO;
	
	@Autowired(required = true)
	@Qualifier("profilesDAO")
	ProfilesDAO profilesDAO;
	
	public ResumeUploadDAO getResumeUploadDAO() {
		return resumeUploadDAO;
	}

	public void setResumeUploadDAO(ResumeUploadDAO resumeUploadDAO) {
		this.resumeUploadDAO = resumeUploadDAO;
	}

	public CandidateExcelRead getCandidateExcelRead() {
		return candidateExcelRead;
	}

	public void setCandidateExcelRead(CandidateExcelRead candidateExcelRead) {
		this.candidateExcelRead = candidateExcelRead;
	}

	/**
	 * @return the profileService
	 */
	public ProfileService getProfileService() {
		return profileService;
	}

	/**
	 * @param profileService the profileService to set
	 */
	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

	public TMPUtil getTmpUtil() {
		return tmpUtil;
	}

	public void setTmpUtil(TMPUtil tmpUtil) {
		this.tmpUtil = tmpUtil;
	}
	 
	
		@RequestMapping(value = "/profiles", method = RequestMethod.GET)
	public ModelAndView getProfiles(HttpServletRequest request,
			@RequestParam(value = "fromCreateProfile", required = false) String fromCreateProfile) throws IOException {
		HttpSession httpSession = request.getSession();
		String userId = httpSession.getAttribute("user").toString();
		//ModelAndView model = new ModelAndView("profiles");
		ModelAndView model = new ModelAndView("profiles");
		if (httpSession != null && !httpSession.isNew()) {
			// do something here
		} else {
			model.setViewName("login");
		}
		model.addObject("profiles", new Profile());
		model.addObject("profilesJson", tmpUtil.getProfiles());
		model.addObject("profileSourceJson", tmpUtil.getConfigKeyValues(7));
		model.addObject("initialEvaluationResultJson", tmpUtil.getConfigKeyValues(8));
		model.addObject("customerInterviewStatusJson", tmpUtil.getConfigKeyValues(9));
		model.addObject("primarySkillJson", tmpUtil.getConfigKeyValues(11));
		model.addObject("accountValuesJson", tmpUtil.getAccountDetails(userId));
		model.addObject("projectValuesJson", tmpUtil.getProjectDetails(userId));
		model.addObject("criticalJson", tmpUtil.getConfigKeyValues(1));
		model.addObject("locationJson", tmpUtil.getConfigKeyValues(2));
		model.addObject("intimationModeJson", tmpUtil.getConfigKeyValues(3));
		model.addObject("requirementTypeJson", tmpUtil.getConfigKeyValues(4));
		model.addObject("positionStatusJson", tmpUtil.getConfigKeyValues(5));
		model.addObject("opportunityStatusJson", tmpUtil.getConfigKeyValues(6));
		model.addObject("skillCategoryJson", tmpUtil.getConfigKeyValues(10));
		model.addObject("requirementRefNum", tmpUtil.getRequirementRefNum(userId));
		model.addObject("accountValuesJson", tmpUtil.getAccountList());
		model.addObject("projectValuesJson", tmpUtil.getProjectList());
		model.addObject("offerProcessingstatusJson", tmpUtil.getConfigKeyValues(14));
		if (StringUtils.isNotBlank(fromCreateProfile)) {
			model.addObject("addMessage", fromCreateProfile);
		} else {
			model.addObject("addMessage", 3);
		}
		model.addObject("deleteMessage", 4);
		model.addObject("updateMessage", 4);
		return model;
	}

	@RequestMapping(value = "/addprofile", method = RequestMethod.POST)
	public ModelAndView createProfile(ModelAndView model, HttpServletRequest request,
			@ModelAttribute("profiles") Profile profile, BindingResult bindingResult) {
		HttpSession session = request.getSession();
		String userId = session.getAttribute("user").toString();
		// model = new ModelAndView("profiles");

		int addMessage = tmpUtil.createProfile(profile, userId);
		model.setViewName("redirect:profiles?fromCreateProfile=" + addMessage);
		return model;
	}

	@RequestMapping(value = "/profileById", method = RequestMethod.POST)
	public ModelAndView updateProfile(ModelAndView model, HttpServletRequest request,
			@ModelAttribute("profiles") Profile profile, BindingResult bindingResult) {
		HttpSession session = request.getSession();
		String userId = session.getAttribute("user").toString();
		model = new ModelAndView("profiles");
		if (session != null && !session.isNew()) {
			// do something here
		} else {
			model.setViewName("login");
		}
		model.addObject("updateMessage", tmpUtil.updateProfile(profile, userId));
		model.addObject("profiles", new Profile());
		model.addObject("profileSourceJson", tmpUtil.getConfigKeyValues(7));
		model.addObject("initialEvaluationResultJson", tmpUtil.getConfigKeyValues(8));
		model.addObject("customerInterviewStatusJson", tmpUtil.getConfigKeyValues(9));
		model.addObject("offerProcessingstatusJson", tmpUtil.getConfigKeyValues(14));
		model.addObject("primarySkillJson", tmpUtil.getConfigKeyValues(11));
		model.addObject("profilesJson", tmpUtil.getProfiles());
		model.addObject("accountValuesJson", tmpUtil.getAccountDetails(userId));
		model.addObject("projectValuesJson", tmpUtil.getProjectDetails(userId));
		model.addObject("criticalJson", tmpUtil.getConfigKeyValues(1));
		model.addObject("locationJson", tmpUtil.getConfigKeyValues(2));
		model.addObject("intimationModeJson", tmpUtil.getConfigKeyValues(3));
		model.addObject("requirementTypeJson", tmpUtil.getConfigKeyValues(4));
		model.addObject("positionStatusJson", tmpUtil.getConfigKeyValues(5));
		model.addObject("opportunityStatusJson", tmpUtil.getConfigKeyValues(6));
		model.addObject("skillCategoryJson", tmpUtil.getConfigKeyValues(10));
		model.addObject("requirementRefNum", tmpUtil.getRequirementRefNum(userId));
		model.addObject("accountValuesJson", tmpUtil.getAccountList());
		model.addObject("projectValuesJson", tmpUtil.getProjectList());
		model.addObject("addMessage", 3);
		model.addObject("deleteMessage", 4);
		return model;
	}
	
	
	@RequestMapping(value = "/offerStatus", method = RequestMethod.GET)
	public ModelAndView getOfferStatus(HttpServletRequest request ) throws IOException {
		HttpSession httpSession = request.getSession();
		String userId = httpSession.getAttribute("user").toString();
		ModelAndView model = new ModelAndView("offerStatus");
		if (httpSession != null && !httpSession.isNew()) {
			// do something here
		} else {
			model.setViewName("login");
		}
		model.addObject("profiles", new Profile());
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("profilesJson", profilesDAO.getOfferProcessingProfiles());
		String jsonStr = jsonObj.toString();
		model.addObject("profilesJson", jsonStr);
		model.addObject("initialEvaluationResultJson", tmpUtil.getConfigKeyValues(8));
		model.addObject("customerInterviewStatusJson", tmpUtil.getConfigKeyValues(9));
		model.addObject("positionStatusJson", tmpUtil.getConfigKeyValues(5));
		model.addObject("opportunityStatusJson", tmpUtil.getConfigKeyValues(6));
		model.addObject("requirementRefNum", tmpUtil.getRequirementRefNum(userId));
		model.addObject("offerProcessingstatusJson", tmpUtil.getConfigKeyValues(14));
		return model;
	}

	@RequestMapping(value = "/validateRefById", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody int validateRefById(@RequestParam("id") String refId) {
		return tmpUtil.getRefId(refId);
	}

	@RequestMapping(value = "/profileById", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Profile profileById(@RequestParam("id") String profileId) {
		return tmpUtil.getProfile(profileId);
	}

	@RequestMapping(value = "/profileDelete", method = RequestMethod.POST, produces = "text/plain")
	public @ResponseBody String deleteProfile(@RequestParam("dataArr[]") ArrayList<String> profileId) {

		tmpUtil.deleteProfile(profileId);
		return "Successfully the profile has been deleted!!";
	}

	@RequestMapping(value = "/candidateUpload", method = RequestMethod.POST, produces = "text/plain")
	public @ResponseBody String candidateBulkUpload(@RequestParam("file") String file, HttpServletRequest request) {
		String candidateMsg = "";
		String userId = null;
		String files = null;
		int resMessage = -800;
		HttpSession session = request.getSession();
		userId = session.getAttribute("user").toString();
		files = new File("D:\\", file).getAbsolutePath();
		ArrayList<Associate> candidateList = null;
		if (!files.isEmpty()) {
			candidateList = candidateExcelRead.getCandidateListFromExcel(files);

			if (candidateList != null && candidateList.size() > 0)
				resMessage = candidateExcelRead.insertResourceList(candidateList, userId, "Siva");

			System.out.println("resMessage " + resMessage);
			if (candidateList == null || candidateList.size() == 0)
				candidateMsg = "File is Empty!!!";
			if (resMessage == 0)
				candidateMsg = "Upload Failed: File has not been uploaded!!!";
			else if (resMessage > 0)
				candidateMsg = "Upload Sucess: " + resMessage + " File has uploaded sucessfully!!!";
		} else {

			candidateMsg = "File is Empty!!!";
		}
		return candidateMsg;
	}
	
	@RequestMapping(value = "canSampleDwnld", method = RequestMethod.GET)
	public void doDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
	
	String FILE_PATH = "D:\\excel\\sampleresource.xlsx";
	
	File file = new File(FILE_PATH);

	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
    BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(file));
    BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());
    
    byte[] buffer = new byte[1024];
    int bytesRead = 0;
    while ((bytesRead = inStrem.read(buffer)) != -1) {
      outStream.write(buffer, 0, bytesRead);
    }
    outStream.flush();
    inStrem.close();

	
	
		
	}

	@RequestMapping(value = "/resumeUpload", method = RequestMethod.POST, produces = "text/plain")
	public @ResponseBody String resumeBulkUpload(@RequestParam("resumeFile") String file, @RequestParam("profileId") String profileId, HttpServletRequest request) {
		String userId = null;
		String files = null;
		String resumeMessage = null;
		System.out.println("Inside Controller");
		HttpSession session = request.getSession();
		userId = session.getAttribute("user").toString();
		files = new File("D://", file).getAbsolutePath();
			ProfileResume resume = null;
			resume = new ProfileResume();
			System.out.println("ProfileId : "+profileId);
			resume.setProfileId(Integer.parseInt(profileId));
			resume.setFileName(files);
			resume.setUpdatedBy(Integer.parseInt(userId));
			resume.setUpdatedOn(new Date());
			resume.setCreatedBy(Integer.parseInt(userId));
			resume.setCreatedOn(new Date());
			//System.out.println("calling insert method");
			int result =  resumeUploadDAO.uploadResume(resume);
			//System.out.println("executed insert method");
			if(result == 0) {
				resumeMessage = ("Upload Failed: File has not been uploaded!!!");
			}else {
				resumeMessage = ("Upload Success: File has been uploaded!!!");
			}
			return resumeMessage;
	}

	@RequestMapping(value = "uploadFile", method = RequestMethod.POST, produces = "text/plain")
	public @ResponseBody String uploadFileHandlerProfile(@RequestParam("profileFile") String profileFile,
			HttpServletRequest request) {
		String file1 = null;
		String userId = null;
		String bulkMessage = "Upload file is empty!!";
		HttpSession session = request.getSession();
		userId = session.getAttribute("user").toString();
		System.out.println("Upload file " + profileFile);
		try {
			file1 = new File("D:\\", profileFile).getAbsolutePath();
			if (!file1.isEmpty()) {
				ArrayList<Profile> profileList = readDataFromExcel(file1);
				
				if(null != profileList && profileList.size() > 0) {
				int result = profileService.writeDataIntoDB(profileList, userId);

				if (result == profileList.size())
					bulkMessage =  "Successfully uploaded a profile(s)!!";
				else if (result == 0 )
					bulkMessage = "All profiles are already exists!!";
				else if (result == -900)
					bulkMessage = "Requirement Id not found!!";
				else if (result == -999 )
					bulkMessage = "Profile upload failed!!";
				else
					bulkMessage = (result)
							+ "profile(s) uploaded sucessfully ";
				}

			}

		} catch (Exception e) {
			System.out.println("Exception occured" + e.getMessage());
			e.printStackTrace();
			bulkMessage = "Profile upload is failed";
		}
		return bulkMessage ;
	}
	
	@RequestMapping(value = "proSampleDwnld", method = RequestMethod.GET)
	public void performDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
	
	String FILE_PATH = "D:\\excel\\samplebulkprofile.xlsx";
	
	File file = new File(FILE_PATH);

	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
    BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(file));
    BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());
    
    byte[] buffer = new byte[1024];
    int bytesRead = 0;
    while ((bytesRead = inStrem.read(buffer)) != -1) {
      outStream.write(buffer, 0, bytesRead);
    }
    outStream.flush();
    inStrem.close();

	
	
		
	}

	public ArrayList<Profile> readDataFromExcel(String file1) {

		ArrayList<Profile> profileList = new ArrayList<Profile>();
		if (!file1.isEmpty()) {
			FileInputStream fis = null;
			try {
				System.out.println("File " + file1);
				fis = new FileInputStream(file1);
				// Create an excel workbook from the file system.
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				// Get the first sheet on the workbook.
				XSSFSheet sheet = workbook.getSheetAt(0);
				Iterator<?> rows = sheet.rowIterator();

				while (rows.hasNext()) {
					Profile profile = new Profile();
					XSSFRow row = (XSSFRow) rows.next();
					XSSFCell cell;
					for (int i = 0; i < row.getLastCellNum(); i++) {
						cell = row.getCell(i, MissingCellPolicy.CREATE_NULL_AS_BLANK);
						System.out.print("cell==> " + cell.toString() + " ");
					}
					System.out.println("Row No.: " + row.getRowNum());
					if (row.getRowNum() == 0) {
						continue; // just skip the rows if row number is 0 or 1
					} else {
						Iterator<?> cells = row.cellIterator();

						while (cells.hasNext()) {
							String refNo = ((XSSFCell) cells.next()).toString().trim();
							profile.setReqRefNo(refNo);
							profile.setName(((XSSFCell) cells.next()).toString().trim());
							profile.setEmail(((XSSFCell) cells.next()).toString().trim());
							profile.setContactNo(
									new BigDecimal(((XSSFCell) cells.next()).getNumericCellValue()).toString().trim());

							String currentCompany = ((XSSFCell) cells.next()).toString().trim();
							if ((currentCompany != null) && (!currentCompany.isEmpty())) {
								profile.setCurrentCompany(currentCompany);
							} else {
								profile.setCurrentCompany("");
							}

							String location = ((XSSFCell) cells.next()).toString().trim();
							if ((location != null) && (!location.isEmpty())) {
								profile.setLocation(location);
							} else {
								profile.setLocation("");
							}

							String primarySkill = ((XSSFCell) cells.next()).toString().trim();
							if ((primarySkill != null) && (!primarySkill.isEmpty())) {
								profile.setPrimarySkillAdd(primarySkill);
							} else {
								profile.setPrimarySkillAdd("");
							}

							String profSharedDate = ((XSSFCell) cells.next()).toString().trim();

							profile.setProfileSharedDate(parseDate(profSharedDate));

							String profileSharedBy = (((XSSFCell) cells.next()).toString().trim());
							if ((profileSharedBy != null) && (!profileSharedBy.isEmpty())) {
								profile.setProfileSharedBy(profileSharedBy);
							} else {
								profile.setProfileSharedBy("");
							}

							String yearsofExp = ((XSSFCell) cells.next()).toString().trim();
							if ((yearsofExp != null) && (!yearsofExp.isEmpty())) {
								double yearsOfExperience = Double.parseDouble(yearsofExp);
								profile.setYearsOfExperience(yearsOfExperience);
							} else {
								profile.setYearsOfExperience(0);
							}

							String relevantExp = ((XSSFCell) cells.next()).toString().trim();
							if ((relevantExp != null) && (!relevantExp.isEmpty())) {
								double relevantExperience = Double.parseDouble(relevantExp);
								profile.setRelevantExperience(relevantExperience);
							} else {
								profile.setRelevantExperience(0);
							}

							String noticePerd = ((XSSFCell) cells.next()).toString().trim();
							if ((noticePerd != null) && (!noticePerd.isEmpty())) {
								int noticePeriod = (int) Double.parseDouble(noticePerd);
								profile.setNoticePeriod(noticePeriod);
							} else {
								profile.setNoticePeriod(0);
							}

							String currCTC = ((XSSFCell) cells.next()).toString().trim();
							if ((currCTC != null) && (!currCTC.isEmpty())) {
								int currentCTC = (int) Double.parseDouble(currCTC);
								profile.setCurrentCTC(currentCTC);
							} else {
								profile.setCurrentCTC(0);
							}

							String expectCTC = ((XSSFCell) cells.next()).toString().trim();
							if ((expectCTC != null) && (!expectCTC.isEmpty())) {
								int expectedCTC = (int) Double.parseDouble(expectCTC);
								profile.setExpectedCTC(expectedCTC);
							} else {
								profile.setExpectedCTC(0);
							}

							String allocation = ((XSSFCell) cells.next()).toString().trim();
							if ((allocation != null) && (!allocation.isEmpty())) {
								// int isAllocated=(int)Integer.parseInt(allocation);
								profile.setIsAllocated1(allocation);
							} else {
								profile.setIsAllocated1("No");
							}

							String allocStartDate = ((XSSFCell) cells.next()).toString().trim();

							profile.setAllocationStartDate(parseDate(allocStartDate));

							String allocEndDate = ((XSSFCell) cells.next()).toString().trim();

							profile.setAllocationEndDate(parseDate(allocEndDate));

							String profileSourceAdd = ((XSSFCell) cells.next()).toString().trim();
							if ((profileSourceAdd != null) && (!profileSourceAdd.isEmpty())) {
								profile.setProfileSourceAdd(profileSourceAdd);
							} else {
								profile.setProfileSourceAdd("");
							}

							String remarks = ((XSSFCell) cells.next()).toString();
							if ((remarks != null) && (!remarks.isEmpty())) {
								profile.setRemarks(remarks);
							} else {
								profile.setRemarks("");
							}

						}
					}
					profileList.add(profile);

				}

				workbook.close();
			} catch (IOException e) {

				e.printStackTrace();

			} catch (Exception e) {

				e.printStackTrace();

			} 
			finally {

				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		}
		return profileList;
	}

	private Date parseDate(String strDate) {
		Date convertedDate = null;
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		if (null != strDate)
			try {
				convertedDate = formatter.parse(strDate);
			} catch (ParseException e) {
				System.out.println("Exception during data parse " + e.getMessage());
			}
		return convertedDate;
	}
}
