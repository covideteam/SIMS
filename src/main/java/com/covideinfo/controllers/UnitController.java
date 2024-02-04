package com.covideinfo.controllers;

import java.io.Reader;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.UnitsMaster;
import com.covideinfo.service.UnitService;

@Controller
@RequestMapping("/units")
public class UnitController {
	
	@Autowired
	UnitService unitService;
	
	@Autowired  
	MessageSource messageSource;
	
	
	@RequestMapping(value="/unitsForm", method=RequestMethod.GET)
	public String getUnitForm(ModelMap model) {
		List<InternationalizaionLanguages> inlagList = unitService.getLanguagesList();
		List<UnitsMaster> unitList=unitService.geUnitsMasterList();
		model.addAttribute("langsList", inlagList);
		model.addAttribute("unitEntity", new UnitsMaster());
		model.addAttribute("unitList", unitList);
		
		return "unitsFom";
	}
	
	@RequestMapping(value="/checkUnitsName/{name}", method=RequestMethod.GET)
	public String formulaMaster(@PathVariable("name") String name,
			ModelMap model, RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		if(name.contains("@@@"))
			name = name.replaceAll("@@@", "/");
		UnitsMaster units = unitService.checkUnitsName(name);
		if(units != null )	
			model.addAttribute("result", "yes");
		else
			model.addAttribute("result", "no");
		return "pages/units/result";
	}
	@RequestMapping(value="/saveUnits", method=RequestMethod.POST)
	public String saveUnits(ModelMap model, @ModelAttribute("unitEntity") UnitsMaster units, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("longinUserName").toString();
		Locale currentLocale = LocaleContextHolder.getLocale();
		String flag = unitService.saveUnitsDetails(units, username);
		if(flag.equals("success")) {
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			String gasuccessMsg =messageSource.getMessage("label.unit.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", gasuccessMsg);
		}else if(flag.equals("failed")) {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gaFailMsg =messageSource.getMessage("label.unit.failure", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gaFailMsg);
		}else if(flag.equals("exist")) {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gaFailMsg =messageSource.getMessage("label.unit.exists", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gaFailMsg);
		}
		return "redirect:/units/unitsForm";
	}
	

}
