package com.tmp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tmp.entity.Report;
import com.tmp.util.TMPUtil;
@Controller
public class ReportController  {

	@Autowired(required = true)
	@Qualifier("tmpUtil")
	TMPUtil tmpUtil;

	public TMPUtil getTmpUtil() {
		return tmpUtil;
	}

	public void setTmpUtil(TMPUtil tmpUtil) {
		this.tmpUtil = tmpUtil;
	}

	@RequestMapping(value = "/reports", method = RequestMethod.GET)
	public ModelAndView report() {
		Report reportmodel=new Report();
		return new ModelAndView("reports", "reportModel",reportmodel);
	}

	@RequestMapping(value="/report", method = RequestMethod.POST, produces = {"text/html" })
	public void reportGeneration(ModelMap model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("reportModel") Report reportbean)  
			throws ServletException, IOException {
		response.setContentType("text/html");  

		String startdate = reportbean.getStartdate();
		String enddate = reportbean.getEnddate();

		reportbean.setStartdate(startdate);
		reportbean.setEnddate(enddate);


		try {
			tmpUtil.addReport(reportbean, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
