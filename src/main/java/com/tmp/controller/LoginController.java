package com.tmp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tmp.entity.DashboardRequirement;
import com.tmp.entity.User;
import com.tmp.util.TMPUtil;

@Controller
public class LoginController {
	@Autowired(required = true)
	@Qualifier("tmpUtil")
	TMPUtil tmpUtil;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login(ModelMap model) {
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("login", new User());
		mav.addObject("errorMsg", false);
		mav.addObject("message", false);
		return mav;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboardRequirement(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("dashboard");
		model.addObject("description", "user page !");
		HttpSession session = request.getSession();
		String userId = session.getAttribute("user").toString();
		model.addObject("dashboardRequirementsJson", tmpUtil.getRequirementJson(userId));
		model.addObject("dashboardRequirement", new DashboardRequirement());
		model.addObject("dashboardblockJson", tmpUtil.getDashboardJson(userId));		
		if(session != null && !session.isNew()) {
		   //do something here
		} else {
		    model.setViewName("login");
		}

		model.addObject("accountValuesJson", tmpUtil.getAccountDetails(userId));
		model.addObject("projectValuesJson", tmpUtil.getProjectDetails(userId));
		model.addObject("locationJson", tmpUtil.getConfigKeyValues(2));
		model.addObject("primarySkillJson", tmpUtil.getConfigKeyValues(11));
		model.addObject("profileSourceJson",tmpUtil.getConfigKeyValues(7));
		model.addObject("initialEvaluationResultJson",tmpUtil.getConfigKeyValues(8));
		model.addObject("customerInterviewStatusJson",tmpUtil.getConfigKeyValues(9));
		model.addObject("profilesJson", tmpUtil.getProfiles());
		model.addObject("criticalJson",tmpUtil.getConfigKeyValues(1));
		model.addObject("intimationModeJson",tmpUtil.getConfigKeyValues(3));
		model.addObject("requirementTypeJson",tmpUtil.getConfigKeyValues(4));
		model.addObject("positionStatusJson",tmpUtil.getConfigKeyValues(5));
		model.addObject("opportunityStatusJson",tmpUtil.getConfigKeyValues(6));
		model.addObject("skillCategoryJson",tmpUtil.getConfigKeyValues(10));

		return model;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.POST)
	public ModelAndView processDashboardRequirement(HttpServletRequest request,
			@ModelAttribute("dashboardRequirement") DashboardRequirement dashboardRequirement) {
		ModelAndView model = new ModelAndView("dashboard");
		HttpSession session = request.getSession();
		String userId = session.getAttribute("user").toString();
		model.addObject("description", "user page !");
		model.addObject("dashboardRequirementsJson", tmpUtil.getRequirementJson(dashboardRequirement, userId));
		model.addObject("dashboardblockJson", tmpUtil.getDashboardJson(dashboardRequirement, userId));
		if(session != null && !session.isNew()) {
			   //do something here
			} else {
			    model.setViewName("login");
			}
		model.addObject("accountValuesJson", tmpUtil.getAccountDetails(userId));
		model.addObject("projectValuesJson", tmpUtil.getProjectDetails(userId));
		model.addObject("locationJson", tmpUtil.getConfigKeyValues(2));
		model.addObject("primarySkillJson", tmpUtil.getConfigKeyValues(11));
		model.addObject("profileSourceJson",tmpUtil.getConfigKeyValues(7));
		model.addObject("initialEvaluationResultJson",tmpUtil.getConfigKeyValues(8));
		model.addObject("customerInterviewStatusJson",tmpUtil.getConfigKeyValues(9));
		model.addObject("profilesJson", tmpUtil.getProfiles());
		model.addObject("criticalJson",tmpUtil.getConfigKeyValues(1));
		model.addObject("intimationModeJson",tmpUtil.getConfigKeyValues(3));
		model.addObject("requirementTypeJson",tmpUtil.getConfigKeyValues(4));
		model.addObject("positionStatusJson",tmpUtil.getConfigKeyValues(5));
		model.addObject("opportunityStatusJson",tmpUtil.getConfigKeyValues(6));
		model.addObject("skillCategoryJson",tmpUtil.getConfigKeyValues(10));
		return model;
	}
	
	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.POST)
	public ModelAndView performLogin(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute("login") @Valid User userlogin) {
		ModelAndView mav = null;
		String username = userlogin.getName();
		String password = userlogin.getPassword();

		User dbUser = tmpUtil.getLoginDetails(username, password);
		
		if (dbUser.getId()>0) {//Valid user
			
			System.out.println("User Login Successful");
			
			HttpSession session = request.getSession(false);
			if(null == session || session.isNew()) {
				mav = new ModelAndView("login");
				return mav;
			}
			session.setAttribute("user", dbUser.getId());
			session.setAttribute("userName", dbUser.getName()); 
			session.setAttribute("displayName", dbUser.getDisplayName());
			session.setAttribute("userRole", dbUser.getRole().getDisplay());
			mav = new ModelAndView("dashboard");
			
			String userId = session.getAttribute("user").toString();
			mav.addObject("dashboardRequirementsJson", tmpUtil.getRequirementJson(userId));
			mav.addObject("dashboardRequirement", new DashboardRequirement());
			mav.addObject("dashboardblockJson", tmpUtil.getDashboardJson(userId));
			
			mav.addObject("accountValuesJson", tmpUtil.getAccountDetails(userId));
			mav.addObject("projectValuesJson", tmpUtil.getProjectDetails(userId));
			mav.addObject("locationJson", tmpUtil.getConfigKeyValues(2));
			mav.addObject("primarySkillJson", tmpUtil.getConfigKeyValues(11));
			mav.addObject("profileSourceJson",tmpUtil.getConfigKeyValues(7));
			mav.addObject("initialEvaluationResultJson",tmpUtil.getConfigKeyValues(8));
			mav.addObject("customerInterviewStatusJson",tmpUtil.getConfigKeyValues(9));
			mav.addObject("profilesJson", tmpUtil.getProfiles());
			mav.addObject("criticalJson",tmpUtil.getConfigKeyValues(1));
			mav.addObject("intimationModeJson",tmpUtil.getConfigKeyValues(3));
			mav.addObject("requirementTypeJson",tmpUtil.getConfigKeyValues(4));
			mav.addObject("positionStatusJson",tmpUtil.getConfigKeyValues(5));
			mav.addObject("opportunityStatusJson",tmpUtil.getConfigKeyValues(6));
			mav.addObject("skillCategoryJson",tmpUtil.getConfigKeyValues(10));
			
		} else {
			mav = new ModelAndView("login");
			mav.addObject("errorMsg", true);
			mav.addObject("message", false);
		}

		return mav;

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(ModelMap map, HttpSession httpSession) {
		ModelAndView model = null;
		if(httpSession.getAttribute("user")!=null){
			httpSession.invalidate();
		    model = new ModelAndView("login");
		    model.addObject("login", new User());
			model.addObject("message", true);
		}
		return model;

	}
	
	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public ModelAndView help(ModelMap model) {
		ModelAndView mav = new ModelAndView("help");
		mav.addObject("help", new User());
		return mav;
	}
	
	@RequestMapping(value = "/contactus", method = RequestMethod.GET)
	public ModelAndView contactUs(ModelMap model) {
		ModelAndView mav = new ModelAndView("contactus");
		mav.addObject("contactus", new User());
		return mav;
	}

	public TMPUtil getTmpUtil() {
		return tmpUtil;
	}

	public void setTmpUtil(TMPUtil tmpUtil) {
		this.tmpUtil = tmpUtil;
	}

}
