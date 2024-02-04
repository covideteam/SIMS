/**
 * 
 */
package com.covideinfo.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.dto.DosingInfoDetialsDto;
import com.covideinfo.dto.DosingInfoSavingDetailsDto;
import com.covideinfo.service.DosingInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author santosh.palakollu
 *
 */

@Controller
@RequestMapping("/dosingInfo")
@PropertySource(value = { "classpath:application.properties" })
public class DosingInfoController {
	@Autowired
	Environment environment;
	
	@Autowired
	DosingInfoService dosingInfoService;
	

	@Autowired
	MessageSource messageSource;

	// DosingInfoForm
	@RequestMapping(value = "/dosinginfoForm/{projectId}", method = RequestMethod.GET)
	public String dosinginfoForm(@PathVariable("projectId") long projectId, HttpServletRequest request, ModelMap model,
			RedirectAttributes redirectAttributes) throws JsonProcessingException {
		DosingInfoDetialsDto dinfDto = dosingInfoService.getDosingInfoDetialsDto(projectId);
		model.addAttribute("dinfDto", dinfDto);
		return "dosingInfoForm";
		
	}
	@RequestMapping(value = "/saveDosinginfo", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String saveDosingInfDetails(HttpServletRequest request,
			DosingInfoSavingDetailsDto dinfDto) {
		String userName = (String) request.getSession().getAttribute("longinUserName");
		Locale currentLocale = LocaleContextHolder.getLocale();
//		String dateFormat = (String) request.getSession().getAttribute("clinicalDateTimeFormat");
		String result = dosingInfoService.saveDosingInfDetails(dinfDto,userName);
		if (result.equals("success")) {
			String successMsg = messageSource.getMessage("lable.dosingInfoSuccess", null, currentLocale);
			return "{\"Success\": \"" + true + "\",\"Message\":\"" + successMsg + "\"}";
		} else if (result.equals("Failed")) {
			String falilMsg = messageSource.getMessage("label.dosingInfoFailure", null, currentLocale);
			return "{\"Success\": \"" + false + "\",\"Message\":\"" + falilMsg + "\"}";
		} else if(result.equals("updteNotRequired")){
			String eixstsMsg = messageSource.getMessage("label.dosingInfoExists", null, currentLocale);
			return "{\"Success\": \"" + false + "\",\"Message\":\"" + eixstsMsg + "\"}";
		}else {
			return "{\"Success\": \"" + false + "\",\"Message\":\"" + result + "\"}";
		}
	}
	
	@RequestMapping(value="/generateTimePointsPdf", method=RequestMethod.GET)
	public void generateTimePointsPdf(@RequestParam("projectId")Long ProjectId, HttpServletRequest request, HttpServletResponse response) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		Long langId = (Long) request.getSession().getAttribute("languageId");
		Long userId = (Long) request.getSession().getAttribute("userId");
		String dateStr = (String) request.getSession().getAttribute("dateFormat");
		dosingInfoService.generatePdfforDosingData(messageSource, langId, dateStr, currentLocale, request, response, ProjectId, userId);
	}
	
}
