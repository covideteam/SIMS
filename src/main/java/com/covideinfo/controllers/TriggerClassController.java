package com.covideinfo.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.triggers.TriggerCreationFunction;
@Controller
@RequestMapping("/triggerController")
public class TriggerClassController {
	
	@Autowired  
	MessageSource messageSource;
	
	@RequestMapping(value="/triggerForm",method=RequestMethod.GET)
	public String triggerForm(ModelMap model,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes ) {
		return "triggerForm";
	}
	@RequestMapping(value="/saveTriggerCall",method=RequestMethod.POST)
	public String saveTriggerCall(ModelMap model,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes ) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		TriggerCreationFunction tcf= new TriggerCreationFunction();
		String result=tcf.triggersCretionFunction();
		
		if(result.equals("success")) {
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			String successMsg =messageSource.getMessage("label.Trigger.gpSuccessMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", successMsg);
		}
		else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String failMsg =messageSource.getMessage("label.Trigger.gpFailMsg", null,currentLocale);
			String msg=failMsg;
			redirectAttributes.addFlashAttribute("pageError", msg);
		}
		return "redirect:/triggerController/triggerForm";
	}
	
	

}
