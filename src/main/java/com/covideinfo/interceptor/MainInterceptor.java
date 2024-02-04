package com.covideinfo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.covideinfo.controllers.TestWebSocket;
import com.covideinfo.util.SidemeusXmlFile;

public class MainInterceptor implements HandlerInterceptor {
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		// TODO Auto-generated method stub
		String userName = (String) request.getSession().getAttribute("userName");
		String role = (String) request.getSession().getAttribute("userRole");
		TestWebSocket.emittersMap.remove(userName);
		TestWebSocket.sampleSaparation.remove(userName);
		
		System.out.println("=================================> preHandler(-,-,-) <========================================");
		request.getSession().setMaxInactiveInterval(30*60);
		
		/*Date date  = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
		 SimpleDateFormat sdfDateWithTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        
		String time = sdf.format(date);
		request.getSession().setAttribute("currentDate", sdfdate.format(new Date()));
		request.getSession().setAttribute("currentTime", time);*/
		Long sideMenuCount = (Long) request.getSession().getAttribute("sideMenusCount");
		String path = request.getServletPath();
//		String realPath = request.getSession().getServletContext().getRealPath("/");
		boolean flag = true;
		//To Exclude URL From authorization add action name here
		if(!path.equals("/userlogin/") && !path.equals("/logout") && !path.equals("/login") && !path.equals("/administration/") &&
				!path.equals("/userProjects/getUserProjects") && !path.equals("/accessDenied") && !path.equals("/timeCtrl/serverTime")) {
			if(role.equals("SUPERADMIN") && (path.equals("/modulesAccess/getModuleAccessForm") || path.equals("/modulesAccess/addLinkNames") 
					|| path.equals("/userRole/addApprovalLevals") || path.equals("/user/createUser") || path.equals("/userRole/createRole"))) {
			}else {
				flag  = SidemeusXmlFile.readSidemeusXmlFile(request, path, sideMenuCount);
				if(flag == false)
					response.sendRedirect(request.getContextPath() + "/administration/accessDenied");
			}
		}
		
		return true;
	}



	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(
				"=================================> postHandler(-,-,-) <========================================");
		/*Date date  = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(date);
		request.getSession().setAttribute("currentDate", sdfdate.format(new Date()));
		request.getSession().setAttribute("currentTime", time);*/
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println(
				"=================================> afterCompletion(-,-,-) <========================================");
		/*Date date  = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(date);
		request.getSession().setAttribute("currentDate", sdfdate.format(new Date()));
		request.getSession().setAttribute("currentTime", time);*/
	}

}
