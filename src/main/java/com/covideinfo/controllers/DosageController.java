package com.covideinfo.controllers;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.DosageForm;
import com.covideinfo.service.DosageService;

@Controller
@RequestMapping("/dosageCon")
public class DosageController {

	@Autowired
	DosageService dosageService;
	
	@Autowired  
	MessageSource messageSource;
	
	@RequestMapping(value="/dosageNew", method=RequestMethod.GET)
	public String productNew(RedirectAttributes redirectAttributes, ModelMap model,HttpServletRequest request) {
		DosageForm df= new DosageForm();
		model.addAttribute("df", df);
		return "dosageForm";
	}
	
	@RequestMapping(value = "/saveDosage", method = RequestMethod.POST)
	public String saveDosage(ModelMap model,@Valid DosageForm df, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String username = request.getSession().getAttribute("longinUserName").toString();
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		Locale currentLocale = LocaleContextHolder.getLocale();
		if(df!=null) {
//			df.setCreatedBy(username);
			df.setCreatedOn(new Date());
			boolean flag = dosageService.saveDosage(df,username);
			if(flag) {
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);

				redirectAttributes.addFlashAttribute("pageMessage","Successfully Save");
			}
				//redirectAttributes.addAttribute("pageMessage","Successfully Save");
			else {
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

				redirectAttributes.addFlashAttribute("pageError"," Failed Please try again.");
				//redirectAttributes.addAttribute("pageError"," Failed Please try again.");
			}
		}
		
		return "redirect:/dosageCon/dosageNew";
	}
	@RequestMapping(value="/checkDosageId/{dosid}", method=RequestMethod.GET)
	public String checkDosageId(@PathVariable("dosid") String dosid,
			ModelMap model, RedirectAttributes redirectAttributes) {
		List<DosageForm> list = dosageService.getDosageIdCheck(dosid);
		if(list.size() > 0)
			model.addAttribute("message", "yes");
		else
			model.addAttribute("message", "no");
		return "pages/result";
	}
}
