package com.tmp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.tmp.entity.Departments;
import com.tmp.entity.Hierarchy;
import com.tmp.entity.ReqBulk;
import com.tmp.entity.Requirement;
import com.tmp.entity.RequirementProfileMapping;
import com.tmp.entity.Requirements;
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
	
	@RequestMapping(value = "/requirements/{status}", method = RequestMethod.GET, produces = {"application/json" }, consumes = {"application/json" })
	@ResponseBody
	public Requirements getRequirements(@PathVariable("status") String status, @RequestParam("location") String location,
			@RequestParam("account") String account)throws IOException {
		return tmpUtil.getRequirements(location, account, status);
	}
	
	@RequestMapping(value = "/getPieChartDataJson", method = RequestMethod.GET, produces = {"application/json" })
	public @ResponseBody List<Departments> getPieChartDataJson()throws IOException {
		System.out.println("getPieChartDataJson Called");
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
}

