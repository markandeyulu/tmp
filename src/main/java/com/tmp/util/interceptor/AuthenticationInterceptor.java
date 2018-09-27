package com.tmp.util.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthenticationInterceptor implements HandlerInterceptor {
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		// Avoid a redirect loop for some urls
		if(request.getRequestURI().equals("/ResourceManagementApp/") ||  
			request.getRequestURI().equals("/ResourceManagementApp/login") ) {
			String userData = (String)request.getSession().getAttribute("userName");
			if(StringUtils.isNotBlank(userData)){
				response.sendRedirect("/ResourceManagementApp/dashboard.html");
				return true;
			}
		} else if(!request.getRequestURI().startsWith("/ResourceManagementApp/js/") &&
				!request.getRequestURI().startsWith("/ResourceManagementApp/css/") && 
				!request.getRequestURI().startsWith("/ResourceManagementApp/img/")){
			System.out.println("Redirecting to --> "+request.getRequestURI());
			String userData = (String)request.getSession().getAttribute("userName");
			if(StringUtils.isBlank(userData)){
				response.sendRedirect("/ResourceManagementApp/");
				return false;
			}
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
					throws Exception {
		////system.out.println("After-Completion");
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		////system.out.println("Post-Handle");
	}
}
