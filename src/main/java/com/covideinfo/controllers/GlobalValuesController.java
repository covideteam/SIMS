package com.covideinfo.controllers;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.dto.GlobalValuesDto;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.service.GlobalValuesService;
import com.fasterxml.jackson.databind.ObjectWriter.GeneratorSettings;

@Controller
@RequestMapping("/values")
public class GlobalValuesController {
	
	
	@Autowired
	GlobalValuesService globalValeusService;
	
	@Autowired  
	MessageSource messageSource;
	
	@RequestMapping(value="/valuesPage", method=RequestMethod.GET)
	public String globalValues(ModelMap model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		Long langId = (Long) request.getSession().getAttribute("languageId");
		List<InternationalizaionLanguages> inLagList = globalValeusService.getInternationalizaionLanguages();
		Locale currentLocale = LocaleContextHolder.getLocale();
		GlobalValuesDto gvDto = globalValeusService.getLanguageDetails(messageSource, inLagList, currentLocale, langId);
		model.addAttribute("langId", langId);
		model.addAttribute("gvDto", gvDto);
		model.addAttribute("inLagList", inLagList);
		return "globalValues";
	}
	
	@RequestMapping(value="/saveGlobalValues", method=RequestMethod.POST)
	public String saveGlobalValues(ModelMap model, HttpServletRequest request, @RequestParam("valueName")String name,
			@RequestParam("pvalue")List<String> pvalList, @RequestParam("lanId")List<Long> lagList, @RequestParam("orderNo")int orderNo, RedirectAttributes redirectAttributes) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		Locale currentLocale = LocaleContextHolder.getLocale();
		String result = globalValeusService.saveGlobalValues(name, pvalList, lagList, userId, orderNo);
		if(result.equals("success")) {
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			String gvsuccessMsg =messageSource.getMessage("label.gvSuccessMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
		}else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gvFailMsg =messageSource.getMessage("label.gvFailMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
		}
		return "redirect:/values/valuesPage";
	}
	@RequestMapping(value="/valueCheckExitOrNot/{value}",method=RequestMethod.GET)
	public @ResponseBody String valueCheckExitOrNot(@PathVariable("value") String value ,ModelMap model, HttpServletRequest request,HttpServletResponse response) {
		String chek="Yse";
		GlobalValues gv=null;
		gv=globalValeusService.getGlobalValuesWithName(value);
		if(gv!=null) {
			chek="Yes";
		}else {
			chek="No";
		}
		return chek;
	}
	

}
