package com.covideinfo.controllers;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.covideinfo.dto.DeviationsDto;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.service.DeviationsService;

@Controller
@RequestMapping("/deviations")
public class DeviationsController {
	
	@Autowired
	private DeviationsService deviationService;
	
	@Autowired
	MessageSource messageSource;
	
	@RequestMapping(value="/deviationPage/{type}", method=RequestMethod.GET)
	public String deviationPage(ModelMap model, @PathVariable("type")String type, HttpServletRequest request) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String devSuccessMsg = messageSource.getMessage("label.deviationReviewSuccess", null, currentLocale);
		String dateFormat = (String) request.getSession().getAttribute("clinicalDateTimeFormat");
		List<StudyMaster> smList = deviationService.getStudyMastersList();
		model.addAttribute("smList", smList);
		model.addAttribute("type", type);
		model.addAttribute("devSuccessMsg", devSuccessMsg);
		model.addAttribute("dateFormat", dateFormat);
		return "deviationPage";
	}
	
	@RequestMapping(value="/getDeviationRecords/{projectId}/{type}", method=RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DeviationsDto getDeviationRecords(ModelMap model, @PathVariable("projectId")Long projectId,@PathVariable("type")String type, HttpServletRequest request) {
		Long roleId = (Long) request.getSession().getAttribute("roleId");
		DeviationsDto devDto = deviationService.getDeviationRecords(projectId, roleId, type);
		return devDto;
	}
	
	@RequestMapping(value="/saveDeviationRecord/{deviationId}/{type}/{comments}/{submitType}", method=RequestMethod.GET)
	public @ResponseBody String saveDeviationRecord(ModelMap model, @PathVariable("deviationId")Long deviationId,@PathVariable("type")String type,
			@PathVariable("comments")String comments,@PathVariable("submitType")String submitType,HttpServletRequest request) {
		Long roleId = (Long) request.getSession().getAttribute("roleId");
		Long userId = (Long) request.getSession().getAttribute("userId");
		Locale currentLocale = LocaleContextHolder.getLocale();
		return  deviationService.saveDeviationRecords(deviationId, roleId, type, comments, userId, messageSource, currentLocale, submitType);
	}
}
