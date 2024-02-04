package com.covideinfo.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.covideinfo.util.SidemeusXmlFile;

public class SessionTimeoutCookieFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
		//Application Side menues Authentication Purpose getting all Side Links in Xml File
		String patha=null;
		SidemeusXmlFile.createApplicationMenu(config,patha);
	}

	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("In it function Do filter");
/*		HttpServletResponse httpResp = (HttpServletResponse) response;
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpSession session = httpReq.getSession(false);// don't create if it doesn't exist
		if(session != null && !session.isNew()) {
		    chain.doFilter(request, response);
		} else {
		    httpResp.sendRedirect(httpReq.getContextPath()+"/login");
		}*/
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
