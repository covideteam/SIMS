package com.covideinfo.controllers;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.DocumentryRequirements;
import com.covideinfo.service.DocumentryRequirementsService;

@Controller
@RequestMapping("/documentryRequ")
public class DocumentryRequirementsController {

	@Autowired  
	MessageSource messageSource;
	@Autowired  
	DocumentryRequirementsService documentryRequirementsService;
	
	@RequestMapping(value="/documentryRequirements", method=RequestMethod.GET)
	public String documentryRequirements(ModelMap model, RedirectAttributes redirectAttributes) {
		
		DocumentryRequirements document=new DocumentryRequirements();
		model.addAttribute("document", document);
		return "documentryRequirements";
	}
	
	
	@RequestMapping(value="/saveDocumentryRequ", method=RequestMethod.POST)
	public String saveDocumentryRequ(@Valid DocumentryRequirements document,BindingResult result,HttpServletRequest request,ModelMap model, RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("userName").toString();
		Locale currentLocale = LocaleContextHolder.getLocale();
		if (result.hasErrors()) {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gvFailMsg =messageSource.getMessage("label.documentryRequirements.FailMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			return "redirect:/documentryRequ/documentryRequirements";
		}else {
		if(document!=null) {
			document.setCreatedBy(username);
			document.setCratedOn(new Date());
			//document.setDocumentAvailable("Yes");
			boolean flag=documentryRequirementsService.saveDocumentryRequ(document);
			if(flag) {
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);


				String gvsuccessMsg =messageSource.getMessage("label.documentryRequirements.SuccessMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
			}else {
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

				String gvFailMsg =messageSource.getMessage("label.documentryRequirements.FailMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			}
		}
		}
		return "redirect:/documentryRequ/documentryRequirements";
	}
}
