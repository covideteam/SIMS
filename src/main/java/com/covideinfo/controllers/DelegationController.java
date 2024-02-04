package com.covideinfo.controllers;


import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.dao.impl.DelegationDaoImpl;
import com.covideinfo.dto.DelegationDto;
import com.covideinfo.model.UserWiseStudiesAsignMaster;




@Controller
@RequestMapping("/delegation")
public class DelegationController {
	
	@Autowired
	DelegationDaoImpl delegationDaoImpl;
	
	@Autowired  
	MessageSource messageSource;
	
	


		@RequestMapping(value="/delegationPage", method=RequestMethod.GET)
		public String globalValues(ModelMap model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
			model.addAllAttributes(redirectAttributes.getFlashAttributes());
			DelegationDto delDto = delegationDaoImpl.getDelegationDtoDetails();
			model.addAttribute("delDto", delDto);
			return "delegation";
		}
		
		
		@RequestMapping(value="/saveDelegation", method=RequestMethod.POST)
		public String saveGlobalValues(ModelMap model, HttpServletRequest request, @RequestParam("study")long study,
			 @RequestParam("studyRoles")String studyRoles,@RequestParam("user")long userId, RedirectAttributes redirectAttributes) {
			String userName = (String) request.getSession().getAttribute("longinUserName");
			Locale currentLocale = LocaleContextHolder.getLocale();
		    String result = delegationDaoImpl.saveDelegationDetails(study, userId, studyRoles, userName);
			if(result.equals("Success")) {
				String gvsuccessMsg =messageSource.getMessage("label.delsuccessmsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);
			}else {
				String gvFailMsg =messageSource.getMessage("label.delfailedmsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

			}
			return "redirect:/delegation/delegationPage";
		}
		@RequestMapping(value="/getDelegationDetailsForUpdate/{uwsId}", method=RequestMethod.GET)
		public String getDeligationDetailsForUpdate(@PathVariable("uwsId")Long uwsId, ModelMap model) {
			UserWiseStudiesAsignMaster uwsam = delegationDaoImpl.getUserWiseStudiesAsignMasterRecord(uwsId);
			DelegationDto delDto = delegationDaoImpl.getDelegationDtoDetails();
			model.addAttribute("delDto", delDto);
			model.addAttribute("uwsam", uwsam);
			return "pages/delegation/delegationUpdatePage";
		}
		
		@RequestMapping(value="/updateDelegationDetails", method=RequestMethod.POST)
		public String saveDelegationUpdateDetails(ModelMap model, HttpServletRequest request, @RequestParam("uwsStudy")Long study,
			 @RequestParam("uwsuserId")Long uwsuserId,@RequestParam("uswRoles")String uswRoles, @RequestParam("updateuwsId")Long uwsId,@RequestParam("status")boolean status, RedirectAttributes redirectAttributes) {
			String userName = (String) request.getSession().getAttribute("longinUserName");
			Locale currentLocale = LocaleContextHolder.getLocale();
			String result = delegationDaoImpl.updateDelegationDetails(study, uwsuserId, uswRoles, userName, uwsId,status);
			if(result.equals("success")) {
				String gvsuccessMsg =messageSource.getMessage("label.delsuccessmsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);
			}else {
				String gvFailMsg =messageSource.getMessage("label.delfailedmsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);
			}
			return "redirect:/delegation/delegationPage";
		}
		
		
		
		
		@RequestMapping(value="/deligationStatusChange", method=RequestMethod.POST)
		public String deleteDelegationDetails(ModelMap model, HttpServletRequest request,@RequestParam("statusuwsId")Long deluwsId, 
				@RequestParam("statusuwsval")boolean statusuwsval, RedirectAttributes redirectAttributes) {
			String userName = (String) request.getSession().getAttribute("longinUserName");
			String result = delegationDaoImpl.deleteDelegationDetails(deluwsId,userName, statusuwsval);
			
			return "redirect:/delegation/delegationPage";
		}
		
	}
