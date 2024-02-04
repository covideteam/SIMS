package com.covideinfo.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.UserMaster;
import com.covideinfo.service.UserAdminService;

@Controller
@RequestMapping("/uad")
@PropertySource(value = { "classpath:application.properties" })
public class UserAdministrationController {
	
	
    @Autowired
    private Environment environment;
    
    @Autowired  
	MessageSource messageSource;
	
	@Autowired
	UserAdminService uaService;
	
	 @RequestMapping(value="/changePassword", method=RequestMethod.GET)
		public String changePassword(ModelMap model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
			model.addAllAttributes(redirectAttributes.getFlashAttributes());
			String username = request.getSession().getAttribute("longinUserName").toString();
			UserMaster user = uaService.getLoginUsersRecord(username);
			if(!user.getRole().getRole().equals("SUPERADMIN"))
				model.addAttribute("user", user);
			else
				model.addAttribute("user", null);
			return "changePassword.tiles";
		}
	 
	 @RequestMapping(value="/oldPwdCheck/{userId}/{pwd}", method=RequestMethod.GET)
		public String oldPwdCheck(ModelMap model, @PathVariable("userId")long userId, @PathVariable("pwd")String pwd) {
			boolean flag = uaService.checkOldPwdMatching(userId, pwd);
			if(flag)
				model.addAttribute("result", "true");
			else model.addAttribute("result", "false");
			return "pages/changeResetPasword/result";
		}
		
		@RequestMapping(value="/savechangepwd", method=RequestMethod.GET)
		public String savechangepwd(ModelMap model, RedirectAttributes redirectAttributes,HttpServletRequest request,
				@RequestParam("userId")long userId, @RequestParam("newPwd")String newPwd, @RequestParam("tranPwd")String tranPwd) {
			String username = request.getSession().getAttribute("userName").toString();
			String passwordExpDays = environment.getRequiredProperty("passwordExpireDays");
			Locale currentLocale = LocaleContextHolder.getLocale();

			boolean flag = uaService.saveChangedPassword(userId, newPwd, tranPwd, username, passwordExpDays);
			if(flag) {
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);

				redirectAttributes.addFlashAttribute("pageMessage", "Password Changed Sucessfully..!");
			}
			else { 
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

				redirectAttributes.addFlashAttribute("pageError", "Password Changing Failed. Please Try Again...!");
			}
			return "redirect:/userlogin/";
		}
		
		@RequestMapping(value="/resetPassword/{id}", method=RequestMethod.GET)
		public String resetPassword(ModelMap model, RedirectAttributes redirectAttributes,@PathVariable ("id") Long userId) {
			model.addAllAttributes(redirectAttributes.getFlashAttributes());
			UserMaster user = uaService.getLoginUsersListExceptSuperAdmin(userId);
			model.addAttribute("user", user);
			return "passwordRest.tiles";
		}
		
		@RequestMapping(value="/saveResetPassword", method=RequestMethod.POST)
		public String saveResetPassword(ModelMap model, RedirectAttributes redirectAttributes,HttpServletRequest request, @RequestParam("username")long userId,@RequestParam("resetPassword")String resetPassword,@RequestParam("reason")String reason) {
			String username = request.getSession().getAttribute("userName").toString();
			String passwordExpDays = environment.getRequiredProperty("passwordExpireDays");
			String result = uaService.saveResetPassword(userId, reason, username, passwordExpDays,resetPassword);
			Locale currentLocale = LocaleContextHolder.getLocale();

			if(result.equals("success")) {
				redirectAttributes.addFlashAttribute("pageMessage", "Password Reset Done Sucessfully..!");
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);

			}
			else {
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

				redirectAttributes.addFlashAttribute("pageError", "Password Reset Failed. Please Try Again...!");
//			return "redirect:/uad/resetPassword";
			}
			return "redirect:/user/createUser";
		}

}
