package com.tmp.util.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthenticationInterceptor implements HandlerInterceptor {
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		////system.out.println("Pre-Handle");
		// Avoid a redirect loop for some urls
		System.out.println(request.getRequestURI());
		if( !request.getRequestURI().equals("/ResourceManagementApp/") && 
			!request.getRequestURI().equals("/ResourceManagementApp/login") && 
			!request.getRequestURI().startsWith("/ResourceManagementApp/js/") &&
			!request.getRequestURI().startsWith("/ResourceManagementApp/css/") )
		{
			//system.out.println("preHandle inside");
			String userData = (String)request.getSession().getAttribute("userName");
			if(userData == null)
			{
				//system.out.println("preHandle inside session null");
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
