package com.tmp.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.tmp.entity.Requirement;
import com.tmp.entity.RequirementProfileMapping;
import com.tmp.entity.Requirements;
import com.tmp.util.TMPUtil;

@Controller
public class RequirementController {

	@Autowired(required = true)
	@Qualifier("tmpUtil")
	TMPUtil tmpUtil;

	@RequestMapping(value = "/requirements/{status}", method = RequestMethod.GET, produces = {"application/json" }, consumes = {"application/json" })
	@ResponseBody
	public Requirements getRequirements(@PathVariable("status") String status, @RequestParam("location") String location,
			@RequestParam("account") String account)throws IOException {
		return tmpUtil.getRequirements(location, account, status);
	}

	public TMPUtil getTmpUtil() {
		return tmpUtil;
	}

	public void setTmpUtil(TMPUtil tmpUtil) {
		this.tmpUtil = tmpUtil;
	}
	
	@RequestMapping(value = "/requirements", method = RequestMethod.GET)
	public ModelAndView requirement(HttpServletRequest request) {
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
		model.addObject("accountValuesJson", tmpUtil.getAccountDetails(userId));
		model.addObject("projectValuesJson", tmpUtil.getProjectDetails(userId));
		model.addObject("addMessage", 3);
		model.addObject("updateMessage", 3);
		return model;
	}
	
	@RequestMapping(value = "/addrequirement", method = RequestMethod.POST)
	public ModelAndView createRequirement(ModelAndView model,HttpServletRequest request, @ModelAttribute("requirements") Requirement requirement,BindingResult bindingResult) {
		model = new ModelAndView("requirements");
		HttpSession session = request.getSession();
		String userId = session.getAttribute("user").toString();
		String userName = session.getAttribute("userName").toString();
		
		model.addObject("addMessage", tmpUtil.createRequirement(requirement,userId,userName));
		model.addObject("description", "user page !");
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
		model.addObject("updateMessage", 3);
		model.addObject("accountValuesJson", tmpUtil.getAccountDetails(userId));
		model.addObject("projectValuesJson", tmpUtil.getProjectDetails(userId));
		return model;
	}
	
	@RequestMapping(value = "/requirementsById", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody Requirement requirement1(@RequestParam("id") String requirementId) {
		return tmpUtil.getRequirement(requirementId);
	}
	
	@RequestMapping(value = "/requirementDelete", method = RequestMethod.POST, produces="text/plain")
	public @ResponseBody String deleteRequirement(@RequestParam("id") String requirementId) {
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
}

