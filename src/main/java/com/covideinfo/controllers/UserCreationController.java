package com.covideinfo.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.UserMaster;
import com.covideinfo.pdf.report.SinglePdfForExportTables;
import com.covideinfo.service.UserCreationService;

@Controller
@RequestMapping("/user")
@PropertySource(value = { "classpath:application.properties" })
public class UserCreationController {
	
	@Autowired
	UserCreationService userCreationService;
	
	@Autowired  
	MessageSource messageSource;
	
	 @Autowired
	 private Environment environment;
	
	@RequestMapping(value="/createUser", method=RequestMethod.GET)
	public String createUser(ModelMap model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		List<RoleMaster> rolesList = userCreationService.getRolesMasterRecordsList();
		
		List<UserMaster> userList=userCreationService.getUserMasterList();
		
		long uid = (long) request.getSession().getAttribute("userId");
		UserMaster um=userCreationService.getUserMasterWithId(uid);
		//List<UserMaster> userList=null;
		if(um!=null) {
			if(um.getRole().getRole().equals("SUPERADMIN")) {
				userList = userCreationService.findOnlyAdminRoles();
			}else if
			(um.getRole().getRole().equals("ADMIN")) {
				userList = userCreationService.findRolesWithOutAdminRoles();
			}
			model.addAttribute("userList", userList);
		}else {
			model.addAttribute("userList", userList);
		}
		
		model.addAttribute("rolesList", rolesList);
		//model.addAttribute("userList", userList);
		model.addAttribute("user", new UserMaster());
		return "createUser";
	}
	
	
	@RequestMapping(value="/checkUserIdDuplication/{userId}", method=RequestMethod.GET)
	public String checkUserIdDuplication(ModelMap model, @PathVariable("userId")String userId) {
		UserMaster user = userCreationService.checkUserDuplication(userId);
		if(user == null)
			model.addAttribute("result", "");
		else {
			if(user.isAccountNotDisable() == false && user.isAccountNotLock() == false)
				model.addAttribute("result", userId+" is deactivated status, try another User Name.");
			else
				model.addAttribute("result", userId +" Already Exists.");
		}
			
		return "pages/users/userDuplicateMsgPage";
	}
	
	@RequestMapping(value="/saveUser", method=RequestMethod.POST)
	public String saveUser(@ModelAttribute("user")UserMaster user,@RequestParam("email")String email, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("userName").toString();
		String passwordExpDays = environment.getRequiredProperty("passwordExpireDays");
		Locale currentLocale = LocaleContextHolder.getLocale();
		long no = userCreationService.saveUser(username, user, passwordExpDays,email);
		if(no > 0) {
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);
			
			String gvsuccessMsg =messageSource.getMessage("label.userCrSuccess", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
		}
		else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);
			
			String gvErrorMsg =messageSource.getMessage("label.userCrError", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvErrorMsg);
			
			
		}
		return "redirect:/user/createUser";
	}

	@RequestMapping(value="/userUpdate/{id}", method=RequestMethod.GET)
	public String userUpdate(ModelMap module,HttpServletRequest request,HttpServletResponse response,@PathVariable ("id") Long id,
			RedirectAttributes  redirectAttributes ) {
		UserMaster um=userCreationService.getUserMasterWithId(id);
		long uid = (long) request.getSession().getAttribute("userId");
		UserMaster umm=userCreationService.getUserMasterWithId(uid);
		List<RoleMaster> rolesList=null;
		if(umm.getRole().getRole().equals("SUPERADMIN")) {
			rolesList = userCreationService.findOnlyAdminRolesForRoles();
		}
		if(umm.getRole().getRole().equals("ADMIN")) {
			rolesList = userCreationService.findRolesWithOutAdminRolesForRoles();
		}
		
		
		module.addAttribute("um", um);
		module.addAttribute("rolesList", rolesList);
		return "updateUser";
		
	}
	
	@RequestMapping(value="/userUpdateData", method=RequestMethod.POST)
	public String userUpdateData(@ModelAttribute("um")UserMaster um, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("userName").toString();
		Locale currentLocale = LocaleContextHolder.getLocale();
		boolean flag=false;
		UserMaster user=userCreationService.getUserMasterWithId(um.getId());
		user.setUpdatedBy(null);
		user.setUpdatedOn(new Date());
		user.setRole(um.getRole());
		flag = userCreationService.userUpdate(username, user);
		if(flag) {
			String gvsuccessMsg =messageSource.getMessage("label.userUpSuccess", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
		}
		else {
			String gvErrorMsg =messageSource.getMessage("label.userUpError", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvErrorMsg);
		}
		return "redirect:/user/createUser";
	}
	
	
	@RequestMapping(value="/userAccountDisable", method=RequestMethod.POST)
	public String userAccountDisable(@RequestParam("useridval")Long useridval, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		UserMaster um=userCreationService.getUserMasterWithId(useridval);
		um.setAccountNotDisable(false);
		um.setAccountNotLock(false);
		boolean flag = userCreationService.updateUserStatus(um);
		if(flag) {
			String successMsg =messageSource.getMessage("label.userAccountDisableSuccess", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", successMsg);
		}
		else {
			String errorMsg =messageSource.getMessage("label.userDisableError", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", errorMsg);
		}
		return "redirect:/user/createUser";
	}
	@RequestMapping(value="/exportUserList", method=RequestMethod.GET)
	public void exportUserList(HttpServletRequest request, HttpServletResponse response) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		Long userId = (Long) request.getSession().getAttribute("userId");
		String dateFormat = environment.getRequiredProperty("dateTimeFormatSeconds");
		String fileName = userCreationService.generateUsersListPdf(request, response, userId, dateFormat, messageSource, currentLocale);
		if(fileName != null) {
		    List<String> filesList = new ArrayList<>();
		    filesList.add(fileName);
		    try {
		    	SinglePdfForExportTables spet = new SinglePdfForExportTables();
			    spet.convertSinglePdf(request, response, filesList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		    
		}
			
	}
}
