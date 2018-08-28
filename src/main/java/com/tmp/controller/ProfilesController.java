package com.tmp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import com.tmp.dao.ConfigDAO;
import com.tmp.entity.Profile;
import com.tmp.util.TMPUtil;
@Controller
public class ProfilesController {

	@Autowired(required = true)
	@Qualifier("tmpUtil")
	TMPUtil tmpUtil;
	
	@Autowired(required = true)
	@Qualifier("configDAO")
	ConfigDAO configDAO;

	
	public TMPUtil getTmpUtil() {
		return tmpUtil;
	}

	public void setTmpUtil(TMPUtil tmpUtil) {
		this.tmpUtil = tmpUtil;
	}

	@RequestMapping(value = "/profiles", method = RequestMethod.GET)
	public ModelAndView getProfiles(HttpServletRequest request) throws IOException {
		HttpSession httpSession = request.getSession();
		String userId = httpSession.getAttribute("user").toString();
		ModelAndView model = new ModelAndView("profiles");
		if(httpSession != null && !httpSession.isNew()) {
			   //do something here
			} else {
			    model.setViewName("login");
			}
		model.addObject("profiles", new Profile());
		model.addObject("profilesJson", tmpUtil.getProfiles());
		model.addObject("profileSourceJson",tmpUtil.getConfigKeyValues(7));
		model.addObject("initialEvaluationResultJson",tmpUtil.getConfigKeyValues(8));
		model.addObject("customerInterviewStatusJson",tmpUtil.getConfigKeyValues(9));
		model.addObject("primarySkillJson",tmpUtil.getConfigKeyValues(11));
		model.addObject("accountValuesJson", tmpUtil.getAccountDetails(userId));
		model.addObject("projectValuesJson", tmpUtil.getProjectDetails(userId));
		model.addObject("criticalJson",tmpUtil.getConfigKeyValues(1));
		model.addObject("locationJson",tmpUtil.getConfigKeyValues(2));
		model.addObject("intimationModeJson",tmpUtil.getConfigKeyValues(3));
		model.addObject("requirementTypeJson",tmpUtil.getConfigKeyValues(4));
		model.addObject("positionStatusJson",tmpUtil.getConfigKeyValues(5));
		model.addObject("opportunityStatusJson",tmpUtil.getConfigKeyValues(6));
		model.addObject("skillCategoryJson",tmpUtil.getConfigKeyValues(10));
		model.addObject("requirementRefNum", tmpUtil.getRequirementRefNum(userId));
		model.addObject("addMessage", 3);
		model.addObject("deleteMessage",4);
		model.addObject("updateMessage", 4);
		return model;
	}

	@RequestMapping(value="/addprofile", method = RequestMethod.POST)
	public ModelAndView createProfile(ModelAndView model,HttpServletRequest request, @ModelAttribute("profiles") Profile profile, BindingResult bindingResult) {
		HttpSession session = request.getSession();
		String userId = session.getAttribute("user").toString();
		model = new ModelAndView("profiles");
		if(session != null && !session.isNew()) {
			   //do something here
			} else {
			    model.setViewName("login");
			}
		model.addObject("profiles", new Profile());
		model.addObject("profileSourceJson",tmpUtil.getConfigKeyValues(7));
		model.addObject("initialEvaluationResultJson",tmpUtil.getConfigKeyValues(8));
		model.addObject("customerInterviewStatusJson",tmpUtil.getConfigKeyValues(9));
		model.addObject("primarySkillJson",tmpUtil.getConfigKeyValues(11));
		model.addObject("addMessage", tmpUtil.createProfile(profile, userId));
		model.addObject("deleteMessage", 4);
		model.addObject("updateMessage", 4);
		model.addObject("profilesJson", tmpUtil.getProfiles());
		model.addObject("accountValuesJson", tmpUtil.getAccountDetails(userId));
		model.addObject("projectValuesJson", tmpUtil.getProjectDetails(userId));
		model.addObject("criticalJson",tmpUtil.getConfigKeyValues(1));
		model.addObject("locationJson",tmpUtil.getConfigKeyValues(2));
		model.addObject("intimationModeJson",tmpUtil.getConfigKeyValues(3));
		model.addObject("requirementTypeJson",tmpUtil.getConfigKeyValues(4));
		model.addObject("positionStatusJson",tmpUtil.getConfigKeyValues(5));
		model.addObject("opportunityStatusJson",tmpUtil.getConfigKeyValues(6));
		model.addObject("skillCategoryJson",tmpUtil.getConfigKeyValues(10));
		model.addObject("requirementRefNum", tmpUtil.getRequirementRefNum(userId));
		return model;
	}
	
	@RequestMapping(value="/profileById", method = RequestMethod.POST)
	public ModelAndView updateProfile(ModelAndView model,HttpServletRequest request, @ModelAttribute("profiles") Profile profile, BindingResult bindingResult) {
		HttpSession session = request.getSession();
		String userId = session.getAttribute("user").toString();
		model = new ModelAndView("profiles");
		if(session != null && !session.isNew()) {
			   //do something here
			} else {
			    model.setViewName("login");
			}
		model.addObject("updateMessage",tmpUtil.updateProfile(profile,userId));
		model.addObject("profiles", new Profile());
		model.addObject("profileSourceJson",tmpUtil.getConfigKeyValues(7));
		model.addObject("initialEvaluationResultJson",tmpUtil.getConfigKeyValues(8));
		model.addObject("customerInterviewStatusJson",tmpUtil.getConfigKeyValues(9));
		model.addObject("primarySkillJson",tmpUtil.getConfigKeyValues(11));
		model.addObject("profilesJson", tmpUtil.getProfiles());
		model.addObject("accountValuesJson", tmpUtil.getAccountDetails(userId));
		model.addObject("projectValuesJson", tmpUtil.getProjectDetails(userId));
		model.addObject("criticalJson",tmpUtil.getConfigKeyValues(1));
		model.addObject("locationJson",tmpUtil.getConfigKeyValues(2));
		model.addObject("intimationModeJson",tmpUtil.getConfigKeyValues(3));
		model.addObject("requirementTypeJson",tmpUtil.getConfigKeyValues(4));
		model.addObject("positionStatusJson",tmpUtil.getConfigKeyValues(5));
		model.addObject("opportunityStatusJson",tmpUtil.getConfigKeyValues(6));
		model.addObject("skillCategoryJson",tmpUtil.getConfigKeyValues(10));
		model.addObject("requirementRefNum", tmpUtil.getRequirementRefNum(userId));
		model.addObject("addMessage", 3);
		model.addObject("deleteMessage", 4);
		return model;
	}
	
	
	@RequestMapping(value = "/profileById", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody Profile profileById(@RequestParam("id") String profileId) {
		return tmpUtil.getProfile(profileId);
	}
	
	@RequestMapping(value = "/profileDelete", method = RequestMethod.POST, produces="text/plain")
	public @ResponseBody String deleteProfile(@RequestParam("id") String profileId) {
				
		tmpUtil.deleteProfile(profileId);
		 return "Successfully the profile has been deleted!!";
	}
	@RequestMapping(value="uploadFile",  method = RequestMethod.POST, produces="text/plain")
	public @ResponseBody String uploadFileHandlerProfile(@RequestParam("file") String file, HttpServletRequest request) {
		//system.out.println("upload file starting");
		String workingDirectory = System.getProperty("user.dir");
		File fileName = new File("D:\\", file);
		String file1 = fileName.getAbsolutePath();
		//system.out.println("Final filepath : " + fileName.getAbsolutePath());
		HttpSession session = request.getSession();
		if (!file1.isEmpty()) {
			try {
				FileInputStream fis = null;
				int result = 0;
				try {

					fis = new FileInputStream(file1);
					// Create an excel workbook from the file system.
					XSSFWorkbook workbook = new XSSFWorkbook(fis);
					// Get the first sheet on the workbook.
					XSSFSheet sheet = workbook.getSheetAt(0);
					Iterator<?> rows = sheet.rowIterator();
					String userId=session.getAttribute("user").toString();
					if(session != null && !session.isNew()) {
						   //do something here
						} else {
							return "redirect:/login.jsp";
						}
					while (rows.hasNext()) {
						
						XSSFRow row = (XSSFRow) rows.next();
						XSSFCell cell;
						for (int i = 0; i < row.getLastCellNum(); i++) {
							cell = row.getCell(i, MissingCellPolicy.CREATE_NULL_AS_BLANK);
							System.out.print("cell==> "+cell.toString() + " ");
						}
						//system.out.println ("Row No.: " + row.getRowNum ());
						if(row.getRowNum()==0 ){
							continue; //just skip the rows if row number is 0 or 1
						}
						else
						{
							Iterator<?> cells = row.cellIterator();
							Profile profile = new Profile();
							DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
							while (cells.hasNext()) {
								String refNo=((XSSFCell) cells.next()).toString().trim();
								profile.setReqRefNo(refNo);
								profile.setName(((XSSFCell) cells.next()).toString().trim());
								profile.setEmail(((XSSFCell) cells.next()).toString().trim());								
								profile.setContactNo(new BigDecimal(((XSSFCell) cells.next()).getNumericCellValue()).toString().trim());			
								
								String currentCompany = ((XSSFCell) cells.next()).toString().trim();
								if((currentCompany!=null) && (!currentCompany.isEmpty())){
									profile.setCurrentCompany(currentCompany);
									}else{
									profile.setCurrentCompany("");	
								}
								
								String location = ((XSSFCell) cells.next()).toString().trim();
								if((location!=null) && (!location.isEmpty())){
									profile.setLocation(location);
									}else{
									profile.setLocation("");	
								}
								
								String primarySkill = ((XSSFCell) cells.next()).toString().trim();
								if((primarySkill!=null) && (!primarySkill.isEmpty())){
									profile.setPrimarySkillAdd(primarySkill);
									}else{
									profile.setPrimarySkillAdd("");	
								}
								
								String profSharedDate = ((XSSFCell) cells.next()).toString().trim();
								if((profSharedDate!=null) && (!profSharedDate.equals(""))){
									Date profileSharedDate = formatter.parse(profSharedDate);
									profile.setProfileSharedDate(profileSharedDate);
									}else{
									Date profileSharedDate = null;
									profile.setProfileSharedDate(profileSharedDate);	
								}
								
								String profileSharedBy = (((XSSFCell) cells.next()).toString().trim());
								if((profileSharedBy!=null) && (!profileSharedBy.isEmpty())){
									profile.setProfileSharedBy(profileSharedBy);
									}else{
									profile.setProfileSharedBy("");	
								}
								
								String yearsofExp = ((XSSFCell) cells.next()).toString().trim();
								if((yearsofExp!=null) && (!yearsofExp.isEmpty())){
									int yearsOfExperience=(int)Double.parseDouble(yearsofExp);
									profile.setYearsOfExperience(yearsOfExperience);
									}else{
									profile.setYearsOfExperience(0);	
								}
								
								String relevantExp = ((XSSFCell) cells.next()).toString().trim();
								if((relevantExp!=null) && (!relevantExp.isEmpty())){
									int relevantExperience=(int)Double.parseDouble(relevantExp);
									profile.setRelevantExperience(relevantExperience);
									}else{
									profile.setRelevantExperience(0);	
								}
								
								String noticePerd = ((XSSFCell) cells.next()).toString().trim();
								if((noticePerd!=null) && (!noticePerd.isEmpty())){
									int noticePeriod=(int)Double.parseDouble(noticePerd);
									profile.setNoticePeriod(noticePeriod);
									}else{
									profile.setNoticePeriod(0);	
								}
								
								String currCTC = ((XSSFCell) cells.next()).toString().trim();
								if((currCTC!=null) && (!currCTC.isEmpty())){
									int currentCTC=(int)Double.parseDouble(currCTC);
									profile.setCurrentCTC(currentCTC);
									}else{
									profile.setCurrentCTC(0);	
								}
								
								String expectCTC = ((XSSFCell) cells.next()).toString().trim();
								if((expectCTC!=null) && (!expectCTC.isEmpty())){
									int expectedCTC=(int)Double.parseDouble(expectCTC);
									profile.setExpectedCTC(expectedCTC);
									}else{
									profile.setExpectedCTC(0);	
								}
								
								String allocation = ((XSSFCell) cells.next()).toString().trim();
								if((allocation!=null) && (!allocation.isEmpty())){
									//int isAllocated=(int)Integer.parseInt(allocation);
									profile.setIsAllocated1(allocation);
									}else{
									profile.setIsAllocated1("No");	
								}
								
								String account = ((XSSFCell) cells.next()).toString().trim();
								if((account!=null) && (!account.isEmpty())){
									profile.setAccount1(account);
									}else{
									profile.setAccount1("");	
								}
								String project = ((XSSFCell) cells.next()).toString().trim();
								if((project!=null) && (!project.isEmpty())){
									profile.setProject1(project);
									}else{
									profile.setProject1("");	
								}
								
								String allocStartDate = ((XSSFCell) cells.next()).toString().trim();
								if((allocStartDate!=null) && (!allocStartDate.equals(""))){
									Date allocationStartDate = formatter.parse(allocStartDate);
									profile.setAllocationStartDate(allocationStartDate);
								}else{
									Date allocationStartDate = null;
									profile.setAllocationStartDate(allocationStartDate);	
								}
								String allocEndDate = ((XSSFCell) cells.next()).toString().trim();
								if((allocEndDate!=null) && (!allocEndDate.equals(""))){
									Date allocationEndDate = formatter.parse(allocEndDate);
									profile.setAllocationEndDate(allocationEndDate);
								}else{
									Date allocationEndDate = null;
									profile.setAllocationEndDate(allocationEndDate);	
								}
								
								String profileSourceAdd = ((XSSFCell) cells.next()).toString().trim();
								if((profileSourceAdd!=null) && (!profileSourceAdd.isEmpty())){
									profile.setProfileSourceAdd(profileSourceAdd);
									}else{
									profile.setProfileSourceAdd("");	
								}
								
								String internalEvalResultDate = ((XSSFCell) cells.next()).toString().trim();
								if((internalEvalResultDate!=null) && (!internalEvalResultDate.equals(""))){
									Date internalEvaluationResultDate = formatter.parse(internalEvalResultDate);
									profile.setInternalEvaluationResultDate(internalEvaluationResultDate);
									}else{
									Date internalEvaluationResultDate = null;
									profile.setInternalEvaluationResultDate(internalEvaluationResultDate);	
								}
								
								String initialEvaluationResultAdd = ((XSSFCell) cells.next()).toString().trim();
								if((initialEvaluationResultAdd!=null) && (!initialEvaluationResultAdd.isEmpty())){
									profile.setInitialEvaluationResultAdd(initialEvaluationResultAdd);
									}else{
									profile.setInitialEvaluationResultAdd("");	
								}
								
								String profileSharedCustomer = ((XSSFCell) cells.next()).toString().trim();
								if((profileSharedCustomer!=null) && (!profileSharedCustomer.isEmpty())){
									profile.setProfileSharedCustomer(profileSharedCustomer);
									}else{
									profile.setProfileSharedCustomer("No");	
								}
								
								String profileSharedCustDate = ((XSSFCell) cells.next()).toString().trim();
								if((profileSharedCustDate!=null) && (!profileSharedCustDate.equals(""))){
									Date profileSharedCustomerDate = formatter.parse(profileSharedCustDate);
									profile.setProfileSharedCustomerDate(profileSharedCustomerDate);
									}else{
									Date profileSharedCustomerDate = null;
									profile.setProfileSharedCustomerDate(profileSharedCustomerDate);	
								}
								
								String customerInterviewStatusAdd = ((XSSFCell) cells.next()).toString().trim();
								if((customerInterviewStatusAdd!=null) && (!customerInterviewStatusAdd.isEmpty())){
									profile.setCustomerInterviewStatusAdd(customerInterviewStatusAdd);
									}else{
									profile.setCustomerInterviewStatusAdd("");	
								}
								
								String remarks = ((XSSFCell) cells.next()).toString();
								if((remarks!=null) && (!remarks.isEmpty())){
									profile.setRemarks(remarks);
									}else{
									profile.setRemarks("");	
								}
								
								int profileId = tmpUtil.isProfilesExist(profile,userId);
								if(profileId == 0){
									int id = tmpUtil.insertProfile(profile,userId);
									profile.setId(id);
									int profId = profile.getId();
									if(profId>0){
										int profileMapingId = tmpUtil.isProfileMapingExist(profId,refNo);
										if(profileMapingId == 0){
											profile.setId(profileId);
											if(profile.getId()>0){
												result = tmpUtil.createProfileRequirementMapping(profile,refNo, userId);
											}
										}
									}
								}else{
									int profileMapingId = tmpUtil.isProfileMapingExist(profileId,refNo);
									if(profileMapingId == 0){
										profile.setId(profileId);
										if(profile.getId()>0){
											result = tmpUtil.createProfileRequirementMapping(profile,refNo, userId);
										}
									}
								workbook.close();
									}
							}
					}
				}

				} catch (IOException e) {

					e.printStackTrace();

				} finally {

					if (fis != null) {

						fis.close();

					}

				}
				//if(result>0){
					return "Successfully uploaded a profile(s)";
				/*}else{
					return "Failed to upload profile(s)";	
				}*/
				
			} catch (Exception e) {
				return "Profile upload is failed";
			}
		} else {
			return "Failed to upload because the file was empty.";
		}
	}
	
}
