package com.covideinfo.controllers;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.UserActivity;
import com.covideinfo.service.RoleMasterService;
import com.covideinfo.service.UserService;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {

	@Autowired
	UserService userService;

	@Autowired
	RoleMasterService roleMasterService;
	
	@Autowired
	MessageSource messageSource;

	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;

	@ModelAttribute("roles")
	public List<RoleMaster> initializeProfiles() {
		return roleMasterService.findAll();
	}

	
//	@RequestMapping(value = "/{locale:en|fr|vi}/login2")
	/*@RequestMapping(value = { "/{locale:en|es}/login", "/" }, method = RequestMethod.GET)
	public String loginPage(HttpServletRequest request, HttpServletResponse response, Locale locale, ModelMap model) {
		//userService.runPeriodStatusModification();
		System.out.println("============================> login action <================================");
		if (isCurrentAuthenticationAnonymous()) {
			return "login";
		} else {
			return "redirect:/logout";
		}
	}*/
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String loginMainPage(HttpServletRequest request, HttpServletResponse response, Locale locale, ModelMap model) {
		List<InternationalizaionLanguages> inlagList = userService.getInternationalizaionLanguagesRecrdsList();
		model.addAttribute("inlagList", inlagList);
		request.getSession().setAttribute("translationLanguage", "0");
		return "login";
	}

	@RequestMapping(value = { "/verifySession", "/" }, method = RequestMethod.GET)
	public String verifySession(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("============================> login action <================================");
		if (isAnonymous()) {
			return "redirect:/logout";
		} else {
			return "redirect:/administration/";
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		
		String user = (String) request.getSession().getAttribute("userName");
		Long id = (Long) request.getSession().getAttribute("uaId");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
			persistentTokenBasedRememberMeServices.logout(request, response, auth);
			SecurityContextHolder.getContext().setAuthentication(null);
			request.getSession().removeAttribute("userName");
			request.getSession().removeAttribute("userRole");
			
			if(id != null && id !=0) {
				UserActivity uaPojo = userService.getUserActivityRecord(id);
				if(uaPojo != null) {
					uaPojo.setLogoutDate(new Date());
					boolean flag = userService.updateUserActivityRecord(uaPojo);
					request.getSession().removeAttribute("uaId");
				}
		   	}
		}
		return "redirect:/login";
	}

	/*private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}*/

	private boolean isAnonymous() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authenticationTrustResolver.isAnonymous(authentication);
	}

}
