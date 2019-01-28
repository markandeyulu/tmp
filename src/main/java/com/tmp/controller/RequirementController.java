package com.tmp.controller;

import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tmp.dao.CandidateDAO;
import com.tmp.email.EmailService;
import com.tmp.entity.Departments;
import com.tmp.entity.Hierarchy;
import com.tmp.entity.ReqBulk;
import com.tmp.entity.Requirement;
import com.tmp.entity.RequirementProfileMapping;
import com.tmp.entity.RequirementUserInfo;
import com.tmp.entity.Requirements;
import com.tmp.scheduler.SchedulerDAO;
import com.tmp.util.ReqBulkUploadUtil;
import com.tmp.util.TMPUtil;

@Controller
public class RequirementController {

	@Autowired(required = true)
	@Qualifier("tmpUtil")
	TMPUtil tmpUtil;
	
	@Autowired(required = true)
	@Qualifier("bulkUploadUtil")
	ReqBulkUploadUtil bulkUploadUtil;
	
	@Autowired(required = true)
	@Qualifier("scheduler")
	SchedulerDAO scheduler;
	
	@Autowired(required = true)
	@Qualifier("candidateDAO")
	CandidateDAO candidateDAO;
	
	public CandidateDAO getCandidateDAO() {
		return candidateDAO;
	}

	public void setCandidateDAO(CandidateDAO candidateDAO) {
		this.candidateDAO = candidateDAO;
	}

	@RequestMapping(value = "/requirements/{status}", method = RequestMethod.GET, produces = {"application/json" }, consumes = {"application/json" })
	@ResponseBody
	public Requirements getRequirements(@PathVariable("status") String status, @RequestParam("location") String location,
			@RequestParam("account") String account)throws IOException {
		return tmpUtil.getRequirements(location, account, status);
	}
	
	@RequestMapping(value = "/getPieChartDataJson", method = RequestMethod.GET, produces = {"application/json" })
	public @ResponseBody List<Departments> getPieChartDataJson(String id, String primarySkillId, String primarySkillName)throws IOException {
		System.out.println("getPieChartDataJson Called");
		
		
		
		Departments department = new Departments(id, primarySkillId, primarySkillName);
		
		/*Departments dept2=new Departments("2","10","PL/SQL");
		Departments dept3=new Departments("3","15",".Net");
		Departments dept4=new Departments("4","06","MainFrame");
		Departments dept5=new Departments("5","07","Python");
		Departments dept6=new Departments("6","08","Angular");
		Departments dept7=new Departments("7","10","Automation");
		Departments dept8=new Departments("8","05","Support");*/
		//List<Departments> depts = new ArrayList<Departments>();
		
		//candidateDAO.getPieChartData();
		
		/*depts.add(dept1);
		depts.add(dept2);
		depts.add(dept3);
		depts.add(dept4);
		depts.add(dept5);
		depts.add(dept6);
		depts.add(dept7);*/
		//depts.add(department);
		
		return candidateDAO.getPieChartData();
	}
	
	@RequestMapping(value = "/getBarChartDataJson", method = RequestMethod.GET, produces = {"application/json" })
	public @ResponseBody List<Departments> getBarChartDataJson()throws IOException {

		System.out.println("getBarChartDataJson Called");
		Departments dept1=new Departments("1","30","Java");
		Departments dept2=new Departments("2","10","PL/SQL");
		Departments dept3=new Departments("3","15",".Net");
		Departments dept4=new Departments("4","06","MainFrame");
		Departments dept5=new Departments("5","07","Python");
		Departments dept6=new Departments("6","08","Angular");
		Departments dept7=new Departments("7","10","Automation");
		Departments dept8=new Departments("8","05","Support");
		List<Departments> depts = new ArrayList<Departments>();
		
		depts.add(dept1);
		depts.add(dept2);
		depts.add(dept3);
		depts.add(dept4);
		depts.add(dept5);
		depts.add(dept6);
		depts.add(dept7);
		depts.add(dept8);
		
		return depts;
	}
	
	@RequestMapping(value = "/getTreeChartDataJson", method = RequestMethod.GET, produces = {"application/json" })
	public @ResponseBody List<Hierarchy> getTreeChartDataJson()throws IOException {
		Hierarchy h1= new Hierarchy("1","RamKrishna Rao",   "","false");
		Hierarchy h2= new Hierarchy("2","Arnold Santiago",       "1","false");
		Hierarchy h3= new Hierarchy("3","Kannan Kasturirajan",   "1","false");
		Hierarchy h4= new Hierarchy("4","Padmaganesh KN",        "1","false");
		Hierarchy h5= new Hierarchy("5","Raju Lakshman",         "3","false");
		Hierarchy h6= new Hierarchy("6","Rikky shah",            "3","false");
		Hierarchy h7= new Hierarchy("7","Umapathy Jangala",      "3","false");
		
		List<Hierarchy> hierchy = new ArrayList<Hierarchy>();
		hierchy.add(h1);
		hierchy.add(h2);
		hierchy.add(h3);
		hierchy.add(h4);
		hierchy.add(h5);
		hierchy.add(h6);
		hierchy.add(h7);
		
		return hierchy;
	}

	public TMPUtil getTmpUtil() {
		return tmpUtil;
	}

	public void setTmpUtil(TMPUtil tmpUtil) {
		this.tmpUtil = tmpUtil;
	}
	
	public ReqBulkUploadUtil getBulkUploadUtil() {
		return bulkUploadUtil;
	}

	public void setBulkUploadUtil(ReqBulkUploadUtil bulkUploadUtil) {
		this.bulkUploadUtil = bulkUploadUtil;
	}
	public SchedulerDAO getScheduler() {
		return scheduler;
	}

	public void setScheduler(SchedulerDAO scheduler) {
		this.scheduler = scheduler;
	}
	private static final ExecutorService emailExecutor = Executors.newFixedThreadPool(10);
	private static final EmailService emailService = new EmailService();
	
	@RequestMapping(value = "/requirements", method = RequestMethod.GET)
	public ModelAndView requirement(HttpServletRequest request,@RequestParam(value = "fromCreate", required=false) String fromCreate) {
		ModelAndView model=new ModelAndView("requirements");
		model.addObject("description", "user page !");
		model.addObject("requirements", new Requirement());
		HttpSession session = request.getSession();
		String userId = session.getAttribute("user").toString();
		//model.addObject("requirementNew", new Requirement());
		model.addObject("requirementtableJson",tmpUtil.getRequirementTableJson(userId));
		model.addObject("requirementblockJson",tmpUtil.getRequirementBlockJson(userId));
		model.addObject("locationJson",tmpUtil.getConfigKeyValues(2));
		model.addObject("criticalJson",tmpUtil.getConfigKeyValues(1));
		model.addObject("intimationModeJson",tmpUtil.getConfigKeyValues(3));
		model.addObject("requirementTypeJson",tmpUtil.getConfigKeyValues(4));
		model.addObject("positionStatusJson",tmpUtil.getConfigKeyValues(5));
		model.addObject("opportunityStatusJson",tmpUtil.getConfigKeyValues(6));
		model.addObject("skillCategoryJson",tmpUtil.getConfigKeyValues(10));
		model.addObject("primarySkillJson",tmpUtil.getConfigKeyValues(11));
		model.addObject("accountValuesJson", tmpUtil.getAccountList());
		model.addObject("projectValuesJson", tmpUtil.getProjectList());
		
		
		if(session != null && !session.isNew()) {
			   //do something here
			} else {
			    model.setViewName("login");
			}
		model.addObject("accountValuesJson", tmpUtil.getAccountDetails(userId));
		model.addObject("projectValuesJson", tmpUtil.getProjectDetails(userId));
		if(StringUtils.isNotBlank(fromCreate)) {
			model.addObject("addMessage", fromCreate);
		}else {
			model.addObject("addMessage", 3);	
		}
		model.addObject("updateMessage", 3);
		return model;
	}
	
	@RequestMapping(value = "/requirement", method = RequestMethod.GET)
	public ModelAndView requirement(@RequestParam("name") String name, HttpServletRequest req) {
		ModelAndView model=new ModelAndView("requirements");
		model.addObject("description", "user page !");
		model.addObject("locationJson",tmpUtil.getConfigKeyValues(2));
		model.addObject("criticalJson",tmpUtil.getConfigKeyValues(1));
		model.addObject("intimationModeJson",tmpUtil.getConfigKeyValues(3));
		model.addObject("requirementTypeJson",tmpUtil.getConfigKeyValues(4));
		model.addObject("positionStatusJson",tmpUtil.getConfigKeyValues(5));
		model.addObject("opportunityStatusJson",tmpUtil.getConfigKeyValues(6));
		model.addObject("skillCategoryJson",tmpUtil.getConfigKeyValues(10));
		model.addObject("primarySkillJson",tmpUtil.getConfigKeyValues(11));
		model.addObject("requirements", new Requirement());
		model.addObject("requirementtableJson",tmpUtil.getRequirementTableCustomernameJson(name));
		model.addObject("requirementblockJson",tmpUtil.getRequirementBlockJsonCustomername(name));
		HttpSession session = req.getSession();
		String userId = session.getAttribute("user").toString();
		if(session != null && !session.isNew()) {
			   //do something here
			} else {
			    model.setViewName("login");
			}
		model.addObject("accountValuesJson", tmpUtil.getAccountDetails(userId));
		model.addObject("projectValuesJson", tmpUtil.getProjectDetails(userId));
		model.addObject("addMessage", 3);
		model.addObject("updateMessage", 3);
		return model;
	}

	@RequestMapping(value = "/requirements", method = RequestMethod.POST)
	public ModelAndView processRequirement(@ModelAttribute("requirements") Requirement requirement, HttpServletRequest req) {
		ModelAndView model = new ModelAndView("requirements");
		model.addObject("description", "user page !");
		model.addObject("requirements", new Requirement());
		HttpSession session = req.getSession();
		String userId = session.getAttribute("user").toString();
		model.addObject("locationJson",tmpUtil.getConfigKeyValues(2));
		model.addObject("criticalJson",tmpUtil.getConfigKeyValues(1));
		model.addObject("intimationModeJson",tmpUtil.getConfigKeyValues(3));
		model.addObject("requirementTypeJson",tmpUtil.getConfigKeyValues(4));
		model.addObject("positionStatusJson",tmpUtil.getConfigKeyValues(5));
		model.addObject("opportunityStatusJson",tmpUtil.getConfigKeyValues(6));
		model.addObject("skillCategoryJson",tmpUtil.getConfigKeyValues(10));
		model.addObject("primarySkillJson",tmpUtil.getConfigKeyValues(11));
		model.addObject("requirementtableJson",tmpUtil.getRequirementTableSubmitJson(requirement, userId));
		model.addObject("requirementblockJson",tmpUtil.getRequirementSubmitJson(requirement, userId));
		
		if(session != null && !session.isNew()) {
			   //do something here
			} else {
			    model.setViewName("login");
			}
		model.addObject("accountValuesJson", tmpUtil.getAccountList());
		model.addObject("projectValuesJson", tmpUtil.getProjectList());
		model.addObject("addMessage", 3);
		model.addObject("updateMessage", 3);
		return model;
	}
	
	@RequestMapping(value = "/addrequirement", method = RequestMethod.POST)
	public ModelAndView createRequirement(ModelAndView model,HttpServletRequest request, @ModelAttribute("requirements") Requirement requirement,BindingResult bindingResult) {
		HttpSession session = request.getSession();
		String userId = session.getAttribute("user").toString();
		String displayName=session.getAttribute("displayName").toString();
		
		int addMessage = tmpUtil.createRequirement(requirement,userId,displayName);
		model.setViewName("redirect:requirements?fromCreate="+addMessage);
		model.addObject("addMessage",addMessage);
		return model;
	}
	
	@RequestMapping(value = "/requirementsById", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody Requirement requirement1(@RequestParam("id") String requirementId) {
		return tmpUtil.getRequirement(requirementId);
	}
	
	@RequestMapping(value = "/requirementDelete", method = RequestMethod.POST, produces="text/plain")
	public @ResponseBody String deleteRequirement(@RequestParam("dataArr[]") ArrayList<String> requirementId) {
		 tmpUtil.deleteRequirement(requirementId);
		 return "Successfully the requirement has been deleted!!";
	}
	
	@RequestMapping(value ="/requirementsById", method = RequestMethod.POST)
	public ModelAndView updateRequirement(ModelAndView model,HttpServletRequest request, @ModelAttribute("requirements") Requirement requirement, BindingResult bindingResult) {
		HttpSession session = request.getSession();
		String userId = session.getAttribute("user").toString();
		model = new ModelAndView("requirements");
		if(session != null && !session.isNew()) {
			   //do something here
			} else {
			    model.setViewName("login");
			}
		model.addObject("updateMessage",tmpUtil.updateRequirement(requirement,session.getAttribute("user").toString()));
		model.addObject("requirements", new Requirement());
		model.addObject("requirementtableJson",tmpUtil.getRequirementTableJson(userId));
		model.addObject("requirementblockJson",tmpUtil.getRequirementBlockJson(userId));
		model.addObject("locationJson",tmpUtil.getConfigKeyValues(2));
		model.addObject("criticalJson",tmpUtil.getConfigKeyValues(1));
		model.addObject("intimationModeJson",tmpUtil.getConfigKeyValues(3));
		model.addObject("requirementTypeJson",tmpUtil.getConfigKeyValues(4));
		model.addObject("positionStatusJson",tmpUtil.getConfigKeyValues(5));
		model.addObject("opportunityStatusJson",tmpUtil.getConfigKeyValues(6));
		model.addObject("skillCategoryJson",tmpUtil.getConfigKeyValues(10));
		model.addObject("primarySkillJson",tmpUtil.getConfigKeyValues(11));
		model.addObject("accountValuesJson", tmpUtil.getAccountDetails(userId));
		model.addObject("projectValuesJson", tmpUtil.getProjectDetails(userId));
		model.addObject("accountValuesJson", tmpUtil.getAccountList());
		model.addObject("projectValuesJson", tmpUtil.getProjectList());
		model.addObject("addMessage", 3);
		
		return model;
		
	}
	@RequestMapping(value = "/requirementProfileId", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ArrayList<RequirementProfileMapping> profiles(@RequestParam("id") String requirementId) {
		return tmpUtil.getRequirementProfile(requirementId);
	}
	
	@RequestMapping(value = "/requirementShortlistedProfileId", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ArrayList<RequirementProfileMapping> shortlistingProfiles(@RequestParam("id") String requirementId) {
		//tmpUtil.getShortlistedProfiles(requirementId);
		return tmpUtil.getRequirementProfileName(requirementId);
	}
	
	@RequestMapping(value ="/requirementProfile", method = RequestMethod.POST)
	public ModelAndView updateShortlistedProfile(ModelAndView model,HttpServletRequest request, @ModelAttribute("requirements") Requirement requirement,BindingResult bindingResult) {
		HttpSession session = request.getSession();
		String userId = session.getAttribute("user").toString();
		model = new ModelAndView("requirements");		
		model.addObject("updateMessage",tmpUtil.updateShortlistedProfile(requirement,session.getAttribute("user").toString()));
		model.addObject("requirementtableJson",tmpUtil.getRequirementTableJson(userId));
		model.addObject("requirementblockJson",tmpUtil.getRequirementBlockJson(userId));
		model.addObject("locationJson",tmpUtil.getConfigKeyValues(2));
		model.addObject("criticalJson",tmpUtil.getConfigKeyValues(1));
		model.addObject("intimationModeJson",tmpUtil.getConfigKeyValues(3));
		model.addObject("requirementTypeJson",tmpUtil.getConfigKeyValues(4));
		model.addObject("positionStatusJson",tmpUtil.getConfigKeyValues(5));
		model.addObject("opportunityStatusJson",tmpUtil.getConfigKeyValues(6));
		model.addObject("skillCategoryJson",tmpUtil.getConfigKeyValues(10));
		model.addObject("primarySkillJson",tmpUtil.getConfigKeyValues(11));
		
		if(session != null && !session.isNew()) {
			   //do something here
			} else {
			    model.setViewName("login");
			}
		model.addObject("accountValuesJson", tmpUtil.getAccountDetails(userId));
		model.addObject("projectValuesJson", tmpUtil.getProjectDetails(userId));
		model.addObject("addMessage", 3);
		return model;
		
	}
	
	@RequestMapping(value = "/requirementShortlistProfile", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ArrayList<Requirement> shortlistedProfiles(@RequestParam("id") String requirementId) {
		return tmpUtil.getShortlistedProfiles(requirementId);
	}
	
	@RequestMapping(value="uploadReqFile",  method = RequestMethod.POST, produces="text/plain")
	public @ResponseBody String uploadFileHandlerProfile(@RequestParam("file") String file, HttpServletRequest request) {
		
		try {
			ArrayList<ReqBulk> reqList = bulkUploadUtil.getReqListFromExcel(request);
			HttpSession session = request.getSession();
			String userId = session.getAttribute("user").toString();
			String displayName=session.getAttribute("displayName").toString();
			bulkUploadUtil.processReqList(reqList,userId,displayName);
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
			}
			return file;
			

	}
	
	@RequestMapping(value="scheduleService",  method = RequestMethod.POST, produces="text/plain")
	public @ResponseBody String scheduling(ModelAndView model,HttpServletRequest request, @ModelAttribute("requirements") Requirement requirement,BindingResult bindingResult) {
		Requirements requirements = new Requirements();
		requirements = scheduler.getRequirements();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    Date dateobj = new Date();
	    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    String currDate = null, reqEmailId = null, ibuheadEmailId = null;
	    String toString = null;
		try {
			currDate = df.format(dateobj);
	        System.out.println("curr date without parse: "+ df.format(dateobj));
	        System.out.println("curr date format parse"+currDate);
	        Map<String, ArrayList<String>> requestorRequirementsMap = new HashMap<String, ArrayList<String>>();
	        Map<String, ArrayList<RequirementUserInfo>> intimatorRequirementsMap = new HashMap<String, ArrayList<RequirementUserInfo>>();
	        Map<String, ArrayList<RequirementUserInfo>> prjtOwrRequirementsMap = new HashMap<String, ArrayList<RequirementUserInfo>>();
	        Map<String, ArrayList<RequirementUserInfo>> ibuHeadRequirementsMap = new HashMap<String, ArrayList<RequirementUserInfo>>();
	        boolean isResult2 = false, isResult3 = false, isResult4 = false, isResult7 = false;		
	        
	        for (Requirement req : requirements.getRequirements()) {
	        	String updatedDate = dateFormat.format(req.getUpdatedOn());
		        System.out.println("updated date : " + updatedDate);
		        long result = dateDifference(currDate, updatedDate);
		        ArrayList<RequirementUserInfo> requirementIdList = new ArrayList<RequirementUserInfo>();
		        ArrayList<String> reqRequestorIdList = new ArrayList<String>();
		        int userId= req.getUserId();
	        	 reqEmailId = scheduler.getEmailId(userId);
	        	 int accountID= req.getAccount().getAccountId();
	        	 ibuheadEmailId = scheduler.getIBUHeadEmailId(accountID);
	        	
		        if(result ==  2) {
		        	
		        	// if given reqEmailId is not in map already, add the reqId into reqList and set it as value in map
		        	// if given reqEmailId is exist in map, get the existing reqList and add the reqId with existing list and set into map again.
		        	
		        	 
		        	//requestorRequirementsMap = setRequestorRequirementId(reqEmailId,req,requestorRequirementsMap);
		        	ArrayList<String> reqIdList = new ArrayList<String>();
		        	
		        	if (requestorRequirementsMap.containsKey(reqEmailId)) {
		        		reqRequestorIdList =  requestorRequirementsMap.get(reqEmailId);
		        		reqRequestorIdList.add(req.getId());
		       		 requestorRequirementsMap.put(reqEmailId,reqRequestorIdList);
		        	}else {
		       		 reqIdList.add(req.getId());
		       		 requestorRequirementsMap.put(reqEmailId,reqIdList);
		       	 	}
		        	isResult2 = true;
		        	
		        	
				}else if(result == 3){
					
					 String intimatorEmail = req.getIntimatorEmail();
					 
					 ArrayList<RequirementUserInfo> reqIdList2 = new ArrayList<RequirementUserInfo>();
					 
					 if (intimatorRequirementsMap.containsKey(intimatorEmail)) {
						 requirementIdList = intimatorRequirementsMap.get(intimatorEmail);
						 
						 RequirementUserInfo reqUserInfo = new RequirementUserInfo();
						 reqUserInfo.setRequestorEmail(scheduler.getEmailId(userId));
						 reqUserInfo.setRequirementId(req.getId());
						 requirementIdList.add(reqUserInfo);
						 intimatorRequirementsMap.put(intimatorEmail,requirementIdList);
						 /*Iterator<RequirementUserInfo> iter = requirementIdList.iterator();
						 while(iter.hasNext())
						 {
							 RequirementUserInfo reqInfo = iter.next();
							 if(reqInfo.getRequestorEmail().equalsIgnoreCase(scheduler.getEmailId(userId))){
								 reqInfo.setRequestorEmail(scheduler.getEmailId(userId));
								 reqInfo.setRequirementId(req.getId());
								 requirementIdList.add(reqInfo);
								 break;
							 }
						 }
						 if(!isExist) {
							 userInfo.setRequestorEmail(scheduler.getEmailId(userId));
							 userInfo.setRequirementId(req.getId());
							 requirementIdList.add(userInfo);
							 intimatorRequirementsMap.put(intimatorEmail,requirementIdList);
						 }*/
		        	 }else {
		        		 RequirementUserInfo reqUserInfo = new RequirementUserInfo();
		        		 reqUserInfo.setRequestorEmail(scheduler.getEmailId(userId));
		        		 reqUserInfo.setRequirementId(req.getId());
						 reqIdList2.add(reqUserInfo);
						 intimatorRequirementsMap.put(intimatorEmail,reqIdList2);
						 
		        	 }
					isResult3 = true;
					
					//intimatorRequirementsMap = setInimatorId(intimatorEmail,req);
					//generateEmailPDF(3,intimatorRequirementsMap);
					 
					//ArrayList<String> reqIdList = new ArrayList<String>();
					//allEmail.concat(reqEmailId);
					//allEmail.concat(req.getIntimatorEmail());
					//intimatorRequirementsMap.put(req.getId(), allEmail);
						
				}else if(result ==  4){
					String actualEmail = req.getActualOwnerEmail();
					 
					 ArrayList<RequirementUserInfo> reqIdList2 = new ArrayList<RequirementUserInfo>();
					 if (prjtOwrRequirementsMap.containsKey(actualEmail)) {
					
						 requirementIdList = prjtOwrRequirementsMap.get(actualEmail);
						 
						 RequirementUserInfo userInfo = new RequirementUserInfo();
		        		 userInfo.setRequestorEmail(scheduler.getEmailId(userId));
						 userInfo.setRequirementId(req.getId());
						 userInfo.setIntimatorEmail(req.getIntimatorEmail());
						 requirementIdList.add(userInfo);
						 prjtOwrRequirementsMap.put(actualEmail,requirementIdList);
						 
						 /*Iterator<RequirementUserInfo> iter = requirementIdList.iterator();
						 while(iter.hasNext())
						 {
							 RequirementUserInfo reqInfo = iter.next();
							 if(reqInfo.getRequestorEmail().equalsIgnoreCase(scheduler.getEmailId(userId)) && reqInfo.getIntimatorEmail().equalsIgnoreCase(req.getIntimatorEmail())){
								 isExist = true;
								 break;
							 }
						 }
						 if(!isExist) {
							 userInfo.setRequestorEmail(scheduler.getEmailId(userId));
							 userInfo.setRequirementId(req.getId());
							 userInfo.setIntimatorEmail(req.getIntimatorEmail());
							 requirementIdList.add(userInfo);
							 prjtOwrRequirementsMap.put(actualEmail,requirementIdList);
						 }*/
		        	 }else {
		        		 RequirementUserInfo userInfo = new RequirementUserInfo();
		        		 userInfo.setRequestorEmail(scheduler.getEmailId(userId));
						 userInfo.setRequirementId(req.getId());
						 userInfo.setIntimatorEmail(req.getIntimatorEmail());
						 reqIdList2.add(userInfo);
						 prjtOwrRequirementsMap.put(actualEmail,reqIdList2);
						 
		        	 }
					 isResult4 = true;
					
					///prjtOwrRequirementsMap = setRequirementId(actualEmail,req); 
					//generateEmailPDF(4,prjtOwrRequirementsMap);
					/*allEmail.concat(reqEmailId);
					allEmail.concat(req.getIntimatorEmail());
					allEmail.concat(req.getActualOwnerEmail());
					
					prjtOwrRequirementsMap.put(req.getId(), allEmail);*/
						
				}else if(result >=  7){
					boolean isExist = false;
					 
					 ArrayList<RequirementUserInfo> reqIdList2 = new ArrayList<RequirementUserInfo>();
					 if (ibuHeadRequirementsMap.containsKey(ibuheadEmailId)) {
					
					
						 requirementIdList = ibuHeadRequirementsMap.get(ibuheadEmailId);
						 
						 RequirementUserInfo userInfo = new RequirementUserInfo();
		        		 userInfo.setRequestorEmail(scheduler.getEmailId(userId));
						 userInfo.setRequirementId(req.getId());
						 userInfo.setIntimatorEmail(req.getIntimatorEmail());
						 userInfo.setActualOwrEmail(req.getActualOwnerEmail());
						 requirementIdList.add(userInfo);
						 ibuHeadRequirementsMap.put(ibuheadEmailId,requirementIdList);
						 
						 /* Iterator<RequirementUserInfo> iter = requirementIdList.iterator();
						while(iter.hasNext())
						 {
							 RequirementUserInfo reqInfo = iter.next();
							 if(reqInfo.getRequestorEmail().equalsIgnoreCase(scheduler.getEmailId(userId)) && 
									 reqInfo.getIntimatorEmail().equalsIgnoreCase(req.getIntimatorEmail()) && 
									 reqInfo.getActualOwrEmail().equalsIgnoreCase(req.getActualOwnerEmail()) ){
								 isExist = true;
								 break;
							 }
						 }
						 if(!isExist) {
							 userInfo.setRequestorEmail(scheduler.getEmailId(userId));
							 userInfo.setRequirementId(req.getId());
							 userInfo.setIntimatorEmail(req.getIntimatorEmail());
							 userInfo.setActualOwrEmail(req.getActualOwnerEmail());
							 requirementIdList.add(userInfo);
							 prjtOwrRequirementsMap.put(ibuheadEmailId,requirementIdList);
						 }*/
		        	 }else {
		        		 RequirementUserInfo userInfo = new RequirementUserInfo();
		        		 userInfo.setRequestorEmail(scheduler.getEmailId(userId));
						 userInfo.setRequirementId(req.getId());
						 userInfo.setIntimatorEmail(req.getIntimatorEmail());
						 userInfo.setActualOwrEmail(req.getActualOwnerEmail());
						 reqIdList2.add(userInfo);
						 ibuHeadRequirementsMap.put(ibuheadEmailId,reqIdList2);
						 
		        	 }
					 isResult7 = true;
					//ibuHeadRequirementsMap.put(req.getId(), allEmail);
						//generateEmailPDF(7,prjtOwrRequirementsMap);
				}
	        }
	       if(isResult2) {
	         generateEmailPDF1(2,requestorRequirementsMap);
	       }if(isResult3) {
	    	 generateEmailPDF(3,intimatorRequirementsMap); 
	       }if(isResult4) {
	    	  generateEmailPDF(4,prjtOwrRequirementsMap);  
	       }if(isResult7) {
	    	   generateEmailPDF(7,ibuHeadRequirementsMap);
	       }
	      
	     //toString= stringList.get(0);
	     // reqIdString= stringList.get(1);
	      
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	/*	model.addObject("users", toString);
		 model.addObject("requirementId", reqIdString);
		model.setViewName("redirect:jsp/email.jsp");	*/
		toString = "Hi, The Email has sent successfully to the respective users!!!";
		return toString;
	}
    public static long dateDifference(final String sysDate,
            final String formDate) {
     long diff = 0;
     long diffDays = 0;
     final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
     Date d1 = null;
     Date d2 = null;
     try {
            d1 = format.parse(sysDate);
            d2 = format.parse(formDate);
            // in milliseconds
            diff = d1.getTime() - d2.getTime();
            diffDays = diff / (24 * 60 * 60 * 1000);
     } catch (final ParseException e) {
            System.out.println("Excepiton "+e.getMessage());
     }
     return diffDays;
}

	
	@RequestMapping(method = RequestMethod.POST, value="/generateEmailPDF1")
	public ModelAndView generateEmailPDF1(int count, Map<String,ArrayList<String>>requirementsMap) {
			ModelAndView model=null;
			model= new ModelAndView("EmailPDF");
			HashMap<String,String> paramMap = new HashMap<String,String>();
			String to = null;
			if(count >=2) {
				
				for (Map.Entry<String, ArrayList<String>> requirementValueMap : requirementsMap.entrySet()) {
				     to = requirementValueMap.getKey();
				    ArrayList<String> value = requirementValueMap.getValue();
				    for(String aString : value){
				        System.out.println(" value : " + aString);
				    }
				}
			}else if(count >= 3) {
				 to = "sp00372151@techmahindra.com";	
			}else if(count >= 4) {
				 to = "MK00473326@techmahindra.com";	
			}else if(count >= 7) {
				 to = "SJ00364832@TechMahindra.com";
			}
			//if(to != null && !to.isEmpty()) {
				String cc = paramMap.get("emailCC");
				
				String from = "TMPDoNotReply@TechMahindra.com";
				//NVO-367: Change to show brand name in from field of email
				String fromName = "Saranya";
				String subject = "Hai";
				Object pdfIn = null;
				//DataSource pdfSource = new ByteArrayDataSource(pdfIn, "application/pdf");
				
				String htmlContent = "hello";//emailService.templateToHtml(pdfGen.getXMLSource(list), getEmailTemplate(guide.getVehicleDetails().getMake().getMakeName()));
				
			//sendAsynchronousEmail(to, cc, from, fromName, subject, htmlContent);
			//}
		model.setViewName("redirect:email");	
			return model;
		
	}
	@RequestMapping(method = RequestMethod.POST, value="/generateEmailPDF")
	public ArrayList<String> generateEmailPDF(int count, Map<String, ArrayList<RequirementUserInfo>> requirementsMap) {
			ModelAndView model=null;
			model= new ModelAndView("EmailPDF");
			HashMap<String,String> paramMap = new HashMap<String,String>();
			Set<String> to= new HashSet<String>();
			Set<String> reqId= new HashSet<String>();	
			ArrayList<String> stringList = new ArrayList<String>();
				for (Entry<String, ArrayList<RequirementUserInfo>> requirementValueMap : requirementsMap.entrySet()) {
				   to.add(requirementValueMap.getKey());
				   System.out.println(" value finally : " + requirementValueMap.getKey());
				   ArrayList<RequirementUserInfo> valueList = requirementValueMap.getValue();
				   for(RequirementUserInfo userInfo : valueList){
				    	if(count == 2) {
				        System.out.println(" value of requestor email : " + userInfo.getRequestorEmail());
				        to.add(userInfo.getRequestorEmail());
				        System.out.println(" to value : " +to);
				        System.out.println(" value of req id : " + userInfo.getRequirementId());
				    	}else if(count == 3) {
						        to.add(userInfo.getRequestorEmail());
						        reqId.add(userInfo.getRequirementId());
						        System.out.println(" value of req id : " + userInfo.getRequirementId());
						        System.out.println(" to value : " +to);
				    	}else if(count == 4) {
				    		 System.out.println(" value of actual owner email : " + userInfo.getActualOwrEmail());
						        to.add(userInfo.getIntimatorEmail());	
						        to.add(userInfo.getRequestorEmail());
						        to.add(userInfo.getIntimatorEmail());
						        reqId.add(userInfo.getRequirementId());
						        System.out.println(" value of req id : " + userInfo.getRequirementId());
						        System.out.println(" to value : " +to);
				    	}else if(count >= 7) {
						        to.add(userInfo.getRequestorEmail());
						        to.add(userInfo.getActualOwrEmail());
						        to. add(userInfo.getIntimatorEmail());
						        reqId.add(userInfo.getRequirementId());
						        System.out.println(" value of req id : " + userInfo.getRequirementId());
						        System.out.println(" to value : " +to);
				    	}
				}
				}
			
			//if(to != null && !to.isEmpty()) {
				String cc = paramMap.get("emailCC");
				
				String from = "TMPDoNotReply@TechMahindra.com";
				//NVO-367: Change to show brand name in from field of email
				String fromName = "Saranya";
				String subject = "Hai";
				Object pdfIn = null;
				//DataSource pdfSource = new ByteArrayDataSource(pdfIn, "application/pdf");
				
				String htmlContent = "hello";//emailService.templateToHtml(pdfGen.getXMLSource(list), getEmailTemplate(guide.getVehicleDetails().getMake().getMakeName()));
				
				String toString = "", reqIdString = "";
				for(String s:to) {
					toString += (toString==""?"":',')+s;
				}
				for(String rId:reqId) {
					reqIdString += (reqIdString==""?"":',')+rId;
				}
				 System.out.println("toString......"+toString);
				 
				 System.out.println("ReqID String......."+reqIdString);
		//	sendAsynchronousEmail(toString, cc, from, fromName, subject, htmlContent);
			//}
				 stringList.add(toString);
				 stringList.add(reqIdString);
				// model.addObject("users", toString);
				 //model.addObject("requirementId", reqIdString);
				 //model.setViewName("redirect:jsp/email.jsp");
			return stringList;
		
	}

	private void sendAsynchronousEmail(final String to, final String cc, final String from, final String fromName, final String subject,
			   final String htmlContent){
		
				emailExecutor.submit(
				new Runnable(){
				public void run() {
				try {
					emailService.sendMail(to, cc, from, fromName, subject, htmlContent);
				} catch (Exception e) {
					System.out.println("Error sending email to "+ e);//LOGGER.log(Level.SEVERE, "Error sending email to "+ to, e);
				}
				}
				});
	}
}

