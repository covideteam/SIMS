package com.covideinfo.controllers;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.dao.ActivityLockedDao;
import com.covideinfo.dto.RoleWiseListDto;
import com.covideinfo.model.ApplicationMenus;
import com.covideinfo.model.ApplictionSideMenus;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.RolesWiseModules;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.ActivityLockedService;
import com.covideinfo.service.StudyService;
import com.covideinfo.service.UserService;
import com.covideinfo.service.UserWiseStudiesAsignService;
import com.covideinfo.service.impl.LanguageService;

@Controller
@RequestMapping("/administration")
public class AdministrationController {
	
	@Autowired
	UserService userService;
	@Autowired
	StudyService studyService;
	@Autowired
    private Environment environment;
	
	@Autowired
	ActivityLockedService activityLockedService;
	
	@Autowired
	ActivityLockedDao activityLockedDao;
	
	@Autowired
	LanguageService languageService;
	
	@Autowired  
	MessageSource messageSource;
	
	@Autowired
	UserWiseStudiesAsignService userWiseStudiesAsignService;
	
	@SuppressWarnings("unused")
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String adminHomePage(HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
		String userName = request.getSession().getAttribute("userName").toString();
		HttpSession session = request.getSession(false);
		if (session != null) {
			if(userName != null && !userName.equals("")) {
				removeUserSocketServiece(userName);
				UserMaster user = userService.getUserdetails(userName);
				if(user != null) {
					Locale currentLocale = LocaleContextHolder.getLocale();
					//Locking Thread Project In Live then Code On Comments
					//Locking Thread
					//DataPushingThread.dataPushing(activityLockedService,activityLockedDao); 
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
				String validationText =messageSource.getMessage("label.valReqText", null,currentLocale);
				String validationForCount =messageSource.getMessage("label.study.validationForCount", null,currentLocale);
				String internalIssues =messageSource.getMessage("label.study.internalIssues", null,currentLocale);
				String validationBelowedCount =messageSource.getMessage("label.study.validationBelowedCount", null,currentLocale);
				String emptySubjec =messageSource.getMessage("label.study.emptySubjec", null,currentLocale);
				String emptyStandby =messageSource.getMessage("label.study.emptyStandby", null,currentLocale);
				String checkDecimal =messageSource.getMessage("label.mealDiet.checkDecimal", null,currentLocale);
				String mealtitleexists =messageSource.getMessage("label.mealDiet.exists", null,currentLocale);
				
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
				studyService.saveApplicationSideMenuds();
				Long count = userService.getSidemenusCount();
				if(count == null)
					count =0L;
				request.getSession().setAttribute("sideMenusCount", count);
				List<RolesWiseModules> rwmList = userService.getApplicationMenus(user);
				if(rwmList != null && rwmList.size() > 0) {
					for(RolesWiseModules rwm : rwmList) {
						request.getSession().setAttribute("Menu"+rwm.getUsermenu().getId(), true);
						request.getSession().setAttribute("SMenu"+rwm.getAppsideMenu().getId(), true);
					}
				}
				List<StudyMaster> studys = studyService.allStudys();
				model.addAttribute("studys", studys);
				if (user.isPwdChangeCundition() == false) {
					if(user.getAccountexprie().after(new Date())) {
						return  "adminDashBoard.tiles";
					
					}else {
					return "redirect:/uad/changePassword";
				}
			}else {
				return "redirect:/uad/changePassword";
			}
			}else return "redirect:/login";
		}else 
			return "redirect:/login";
		
		}else 
			return "redirect:/login";
	}
		
	/*
	 * If any Sockets aviable in appliation scope need to remove all user sockets
	 */
	private void removeUserSocketServiece(String userName) {
		TestWebSocket.vitalCollectionData.remove(userName);
		
	}


	@RequestMapping(value = "/changeUserStudy/{studyId}", method = RequestMethod.GET)
	public  @ResponseBody String changeUserStudy(@PathVariable("studyId") Long studyId, ModelMap model,
			HttpServletRequest request) {
		if(studyId != -1) {
			StudyMaster study = studyService.findByStudyId(studyId);
			request.getSession().setAttribute("studyId", study.getId());
			request.getSession().setAttribute("projectNo", study.getProjectNo());
			return studyService.studyPeriodMasterSelectBox(study);			
		}else
			return "0--0";
	}
	@RequestMapping(value = "/changeUserPeriod/{periodId}", method = RequestMethod.GET)
	public  @ResponseBody String changeUserPeriod(@PathVariable("periodId") Long periodId, ModelMap model,
			HttpServletRequest request) {
		if(periodId != -1) {
			StudyPeriodMaster period = studyService.periodById(periodId);
			request.getSession().setAttribute("periodId", period.getId());
			request.getSession().setAttribute("periodNo", period.getPeriodNo());
			return period.getPeriodNo() + "";			
		}else
			return "0";
	}
	
	
	@RequestMapping(value="/changePassword", method=RequestMethod.GET)
	public String changePassword(RedirectAttributes redirectAttributes, ModelMap model) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		return "changePassword";
	}
	@RequestMapping(value="/updatePassword", method=RequestMethod.POST)
	public String updatePassword( RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String userName = request.getSession().getAttribute("longinUserName").toString();
		
		UserMaster checkLoginUser = userService.findByUsername(userName);
		String password = request.getParameter("pws1").trim();
		Locale currentLocale = LocaleContextHolder.getLocale();

		if(checkLoginUser != null) {
			boolean flag = userService.updateLoginPassword(checkLoginUser, password);
			if(flag) {
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);

				redirectAttributes.addFlashAttribute("pageMessage", "Password Change Successuflly");
			}else {
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

				redirectAttributes.addFlashAttribute("pageError", "Failed To Change Password");
			}
		}else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			redirectAttributes.addFlashAttribute("pageError", " login details not exist.");
		}
		return "redirect:/administration/changePassword";
	}
	
	@RequestMapping(value="/asignStudy", method=RequestMethod.GET)
	public String asignCurrentStudy(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
		List<StudyMaster> userStudys = userWiseStudiesAsignService.allUserActiveStudys(userId);
		model.addAttribute("userStudys", userStudys);
		return "asignStudy.tiles";
	}
		
	@RequestMapping(value="/sidemenuList",method=RequestMethod.GET)
	public String UserMenuData(ModelMap model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		//Here getting Id from Session Who have been logged...
		Long roleid =(Long) request.getSession().getAttribute("roleId");
		
		//Here Getting Menulist with UserId
	    List<ApplicationMenus> appMenu = userService.getApplicationMenusWithRoleId(roleid); 
	    
	    //Here Getting SideMenu List with UserId
	    List<ApplictionSideMenus> sideMenu = userService.getApplictionSideMenusWithRoleId(roleid); 
	    
	    //Here returning Data To Jsp pages
	    RoleWiseListDto pojo=new RoleWiseListDto();
	    pojo.setAppMenu(appMenu);
	    pojo.setSideMenu(sideMenu);
	   // model.addAttribute("appMenu", appMenu);
	   // model.addAttribute("sideMenu", sideMenu);
	    model.addAttribute("pojo", pojo);
	    
	    //put appMenu and sideMenu in one dto and return that dto 
		return "changePassword.tiles";
	}
	
	
	/*@RequestMapping(value = "/userStudys/{studyId}", method = RequestMethod.GET)
	public String userStudys(@PathVariable("studyId") Long studyId, ModelMap model,
			HttpServletRequest request) {
		StudyMaster study = studyService.findByStudyId(studyId);
		List<UserWiseStudiesAsignMaster> activeUsers = userWiseStudiesAsignService.findUsersFindByStudy(studyId);
		List<String> activedUsers = new ArrayList<>();
		for(UserWiseStudiesAsignMaster user: activeUsers) {
			if(user.isStatus())
				activedUsers.add(user.getUserId().getUsername());
		}
		model.addAttribute("activedUsers", activedUsers);
		List<UserMaster> allLoginUsers = userService.findAllActiveUsers(); 
		model.addAttribute("allLoginUsers", allLoginUsers);
		model.addAttribute("study", study);
		return "pages/administration/study/userStudys";
	}*/
	
	/*@RequestMapping(value="/asignStudySave", method=RequestMethod.POST)
	public String asignStudySave(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Long studyid = Long.parseLong(request.getParameter("studyid"));
		Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
		UserMaster loginUser = userService.findById(userId);
		StudyMaster study = studyService.findByStudyId(studyid);
		
		String userIds = request.getParameter("cserIds");
		String[] ids = userIds.split(",");
		List<Long> cids = new ArrayList<>();
		for (String s : ids)
			if (s.trim() != "")
				cids.add(Long.parseLong(s.trim()));
		String userIdsRemove = request.getParameter("cserIdsRemove");
		String[] idsRemove = userIdsRemove.split(",");
		List<Long> cidsRemove = new ArrayList<>();
		for (String s : idsRemove)
			if (s.trim() != "")
				cidsRemove.add(Long.parseLong(s.trim()));
		
		
		List<UserWiseStudiesAsignMaster> userUpdate = new ArrayList<>();
		List<UserWiseStudiesAsignMaster> activeUsers = userWiseStudiesAsignService.findUsersFindByStudy(studyid);
		for(UserWiseStudiesAsignMaster user: activeUsers) {
			if(!cids.contains(user.getUserId().getId())) {
				if(cidsRemove.contains(user.getUserId().getId())){
					user.setStatus(false);
					user.setUpdatedBy(loginUser);
					user.setUpdatedOn(new Date());
					user.setUpdateReason(request.getParameter(user.getUserId().getId()+"_comment"));
					userUpdate.add(user);
				}
			}else {
				user.setStatus(true);
				user.setUpdatedBy(loginUser);
				user.setUpdatedOn(new Date());
				user.setUpdateReason(request.getParameter(user.getUserId().getId()+"_comment"));
				userUpdate.add(user);
			}
			cids.remove(user.getUserId().getId());
		}
		
		List<UserWiseStudiesAsignMaster> usersave = new ArrayList<>();
		for(Long l : cids) {
			UserWiseStudiesAsignMaster uwsam = new UserWiseStudiesAsignMaster();
			uwsam.setStudyMaster(study);
			uwsam.setUserId(userService.findById(l));
			uwsam.setCreatedBy(loginUser);
			uwsam.setCreatedOn(new Date());
			uwsam.setUpdateReason(request.getParameter(l+"_comment"));
			usersave.add(uwsam);
		}
		
		userWiseStudiesAsignService.saveStudyAsignStudyCreationTime(usersave);
		userWiseStudiesAsignService.updateUserWiseStudiesAsignMaster(userUpdate);
		redirectAttributes.addFlashAttribute("pageMessage", "Study Assign Done Successfully");
		return "redirect:/administration/asignStudy";
	}*/

	@RequestMapping(value="/getCsrfToken",method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody CsrfToken getCurrentCsrfToken() {
	    // quick-test
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpSession session= attr.getRequest().getSession(false);
	    if (session == null) {
	        return null;
	    }
	    return (CsrfToken) session.getAttribute("org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN");
	}
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		return "accessDenied.tiles";
	}
}
