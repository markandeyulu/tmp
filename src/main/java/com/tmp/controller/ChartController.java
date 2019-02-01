package com.tmp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tmp.dao.CandidateDAO;
import com.tmp.entity.Charts;
import com.tmp.entity.Departments;
import com.tmp.util.TMPUtil;

/**
 * This class used to populate the chart based on user inputs
 * @author DM00561492
 *
 */
@Controller
public class ChartController {
	
	@Autowired(required = true)
	@Qualifier("tmpUtil")
	TMPUtil tmpUtil;
	
	@Autowired(required = true)
	@Qualifier("candidateDAO")
	CandidateDAO candidateDAO;
	
	public CandidateDAO getCandidateDAO() {
		return candidateDAO;
	}

	public void setCandidateDAO(CandidateDAO candidateDAO) {
		this.candidateDAO = candidateDAO;
	}

	public TMPUtil getTmpUtil() {
		return tmpUtil;
	}

	public void setTmpUtil(TMPUtil tmpUtil) {
		this.tmpUtil = tmpUtil;
	}

	/**
	 * Chart page initial load
	 * @param chartModel Charts Object 
	 * @param req HttpServletRequest Object
	 * @return ModelAndView Object
	 */
	@RequestMapping(value = "/charts", method = RequestMethod.GET)
	public ModelAndView report(@ModelAttribute("charts") Charts chartModel, HttpServletRequest req) {
		ModelAndView model=new ModelAndView("charts");
		model.addObject("charts", new Charts());
		model.addObject("accountValuesJson", tmpUtil.getAccountList());
		return model;
	}
	
	
	

	/**
	 * This method used to pull the data from associate and reference tables and populate data for generate chart
	 * @param location String
	 * @param chartType String
	 * @param searchBy String
	 * @return list of department objects
	 * @throws IOException Exception if any
	 */
	@RequestMapping(value = "/getChartData", method = RequestMethod.GET, produces = {"application/json" })
	public @ResponseBody List<Departments> getPieChartDataJson(@RequestParam("location") String location,@RequestParam("chartType") String chartType, @RequestParam("searchBy") String searchBy )throws IOException {
		if (searchBy.equalsIgnoreCase("skill")) {
			return candidateDAO.getSkillChartData(location);
		} else {
			return candidateDAO.getAccountChartData(location);
		}
	}
	
	
}
