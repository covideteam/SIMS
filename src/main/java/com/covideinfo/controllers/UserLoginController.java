package com.covideinfo.controllers;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.RolesWiseModules;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserActivity;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.StudyService;
import com.covideinfo.service.UserService;
import com.covideinfo.service.impl.LanguageService;

@Controller
@RequestMapping("/userlogin")
@PropertySource(value = { "classpath:application.properties" })
public class UserLoginController {
	
	@Autowired
	UserService userService;
	@Autowired
	StudyService studyService;
	@Autowired
    private Environment environment;
	@Autowired
    private LanguageService languageService;
	
	@Autowired  
	MessageSource messageSource;
	
	
	//User login main link
	@RequestMapping(value="/", method=RequestMethod.GET)

	public String dashboardHomePage(HttpServletRequest request, RedirectAttributes redirectAttributes, ModelMap model) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		HttpSession session = request.getSession();
		if(session != null) {
			Locale currentLocale = LocaleContextHolder.getLocale();
			String userName = request.getSession().getAttribute("userName").toString();
			if(userName != null && !userName.equals("")) {
				UserMaster user = userService.getUserdetails(userName);
				if(user != null) {
					
					UserActivity ua = new UserActivity();
					ua.setUserName(user.getUsername());
					ua.setLoginDate(new Date());
					ua.setUserid(user.getId());
					ua.setUserRole(user.getRole().getRole());
					long uaId = userService.saveUserActivityRecord(ua);
		            request.getSession().setAttribute("uaId", uaId);
		            
					InternationalizaionLanguages language =  languageService.getLanguage(currentLocale);
					request.getSession().setAttribute("longinUserName", user.getUsername());
					request.getSession().setAttribute("userFullName", user.getFullName());
					request.getSession().setAttribute("userId", user.getId());
					request.getSession().setAttribute("languageId", language.getId());
					request.getSession().setAttribute("languageCode", language.getLangCode());
					request.getSession().setAttribute("userRole", user.getRole().getRole());
					request.getSession().setAttribute("roleId", user.getRole().getId());
					request.getSession().setAttribute("mainUrl", environment.getRequiredProperty("mainUrl"));
					request.getSession().setAttribute("ajaxUrl", environment.getRequiredProperty("ajaxUrl"));
					request.getSession().setAttribute("clinicalDateTimeFormat", environment.getRequiredProperty("dateFormat"));
					request.getSession().setAttribute("dateFormat", environment.getRequiredProperty("dateFormat"));
					request.getSession().setAttribute("dateTimeFormatSeconds", environment.getRequiredProperty("dateTimeFormatSeconds"));
					request.getSession().setAttribute("dateTimeFormat", environment.getRequiredProperty("dateTimeFormat"));
					String validationForCount =messageSource.getMessage("label.study.validationForCount", null,currentLocale);
					String internalIssues =messageSource.getMessage("label.study.internalIssues", null,currentLocale);
					String validationBelowedCount =messageSource.getMessage("label.study.validationBelowedCount", null,currentLocale);
					String emptySubjec =messageSource.getMessage("label.study.emptySubjec", null,currentLocale);
					String emptyStandby =messageSource.getMessage("label.study.emptyStandby", null,currentLocale);
					String checkDecimal =messageSource.getMessage("label.mealDiet.checkDecimal", null,currentLocale);
					String mealtitleexists =messageSource.getMessage("label.mealDiet.exists", null,currentLocale);
					
					String validationText = messageSource.getMessage("label.valReqText", null,currentLocale);
					request.getSession().setAttribute("validationText", validationText);
					request.getSession().setAttribute("validationForCount", validationForCount);
					request.getSession().setAttribute("internalIssues", internalIssues);
					request.getSession().setAttribute("validationBelowedCount", validationBelowedCount);
					request.getSession().setAttribute("emptySubjec", emptySubjec);
					request.getSession().setAttribute("emptyStandby", emptyStandby);
					request.getSession().setAttribute("checkDecimal", checkDecimal);
					request.getSession().setAttribute("mealtitleexists", mealtitleexists);
					
					String searchLabel =messageSource.getMessage("label.search", null,currentLocale);
					request.getSession().setAttribute("searchLabel", searchLabel);
					String showLabel =messageSource.getMessage("label.show", null,currentLocale);
					request.getSession().setAttribute("showLabel", showLabel);
					
					String entriesLabel =messageSource.getMessage("label.entries", null,currentLocale);
					request.getSession().setAttribute("entriesLabel", entriesLabel);
					String noDtaAvlLabel =messageSource.getMessage("label.nodataAvl", null,currentLocale);
					request.getSession().setAttribute("noDtaAvlLabel", noDtaAvlLabel);
					String showingPgsLabel =messageSource.getMessage("label.showingPgs", null,currentLocale);
					request.getSession().setAttribute("showingPgsLabel", showingPgsLabel);
					String noRcdsLabel =messageSource.getMessage("label.noRecords", null,currentLocale);
					request.getSession().setAttribute("noRcdsLabel", noRcdsLabel);
					String filterLabel =messageSource.getMessage("label.filteredFrom", null,currentLocale);
					request.getSession().setAttribute("filterLabel", filterLabel);
					String totRcdsLabel =messageSource.getMessage("label.totRecords", null,currentLocale);
					request.getSession().setAttribute("totRcdsLabel", totRcdsLabel);
					String ofLabel =messageSource.getMessage("label.of", null,currentLocale);
					request.getSession().setAttribute("ofLabel", ofLabel);
					String prevPgLabel =messageSource.getMessage("label.previousPage", null,currentLocale);
					request.getSession().setAttribute("prevPgLabel", prevPgLabel);
					String nextLabel =messageSource.getMessage("label.next", null,currentLocale);
					request.getSession().setAttribute("nextLabel", nextLabel);
					request.getSession().setAttribute("pwscondition", user.isPwdChangeCundition());
					request.getSession().setAttribute("pwsExp", user.getAccountexprie().after(new Date()));
					
					List<RolesWiseModules> rwmList = userService.getApplicationMenus(user);
					for(RolesWiseModules rwm : rwmList) {
						request.getSession().setAttribute("Menu"+rwm.getUsermenu().getId(), true);
						request.getSession().setAttribute("SMenu"+rwm.getAppsideMenu().getId(), true);
					}
				}
				List<StudyMaster> studys = studyService.allStudys();
				model.addAttribute("studys", studys);
				studyService.saveApplicationSideMenuds();
				Long count = userService.getSidemenusCount();
				if(count == null)
					count =0L;
				request.getSession().setAttribute("sideMenusCount", count);
				if (user.isPwdChangeCundition() == false) {
					if(user.getAccountexprie().after(new Date())) {
						return  "adminDashBoard.tiles";
					
					}else {
					return "redirect:/uad/changePassword";
				}
			}else {
				return "redirect:/uad/changePassword";
			}
				
			}else {
				return "redirect:/login";
			}
		}else 
			return "redirect:/login";
		
		
	}
	
	

	
}
