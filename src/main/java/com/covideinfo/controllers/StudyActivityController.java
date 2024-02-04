package com.covideinfo.controllers;

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

import com.covideinfo.dto.GlobalparameterFromDto;
import com.covideinfo.dto.StudyDesingActivityDetailsDto;
import com.covideinfo.model.SubjectActivityData;
import com.covideinfo.service.RulesService;
import com.covideinfo.service.StudyActivityService;

@Controller
@RequestMapping("/studyActivity")
public class StudyActivityController {

	@Autowired
	StudyActivityService studyActivityService;

	@Autowired
	RulesService rulesService;

	@Autowired
	MessageSource messageSource;

	// Study Design From parameters showing code
	@RequestMapping(value = "/studyDesignActivityDetails/{activityId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody StudyDesingActivityDetailsDto sutyDesignActivity(ModelMap model,
			@PathVariable("activityId") Long activityId, HttpServletRequest request) {
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		return studyActivityService.getStudyDesingActivityDetailsDtoDetails(activityId, languageId);
	}
	
	@RequestMapping(value = "/getActivityParameters/{activityId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody StudyDesingActivityDetailsDto getActivityParameters(ModelMap model,
			@PathVariable("activityId") Long activityId, HttpServletRequest request) {
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		return studyActivityService.getActivityParamters(activityId, languageId);
	}

	// Study Execution From Data papulation code
	@RequestMapping(value = "/studyExecutionActivityDetails/{studyId}/{activityId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody GlobalparameterFromDto sutyExecutionActivity(ModelMap model,
			@PathVariable("studyId") Long studyId, @PathVariable("activityId") Long activityId,
			HttpServletRequest request) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		return studyActivityService.globalparameterFromDtoerFromDto(activityId, languageId, studyId, currentLocale);
	}

	@RequestMapping(value = "/saveStudyActivityFromsData", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody MessageDto saveStudyActivityFromsData(HttpServletRequest request,
			SubjectActivityData subjectActivityData) {
		String userName = (String) request.getSession().getAttribute("longinUserName");
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		Locale currentLocale = LocaleContextHolder.getLocale();
		String dateFormat = (String) request.getSession().getAttribute("clinicalDateTimeFormat");
		String dateStrWithTime = (String) request.getSession().getAttribute("dateTimeFormat");
		MessageDto dto = new MessageDto();
		com.covideinfo.dto.MessageDto mdto = studyActivityService.saveStudyActivityFromsData(subjectActivityData, messageSource, currentLocale, userName, languageId, dateFormat, dateStrWithTime);
		if(mdto != null) {
			if (mdto.getMealsMsg().equals("success")) {
				String successMsg = messageSource.getMessage("label.stdActSuccessMsg", null, currentLocale);
				dto.setMessage(successMsg);
				dto.setType("Success");
			} else if (mdto.getMealsMsg().equals("Failed")) {
				String falilMsg = messageSource.getMessage("label.stdActFailMsg", null, currentLocale);
				dto.setMessage(falilMsg);
				dto.setType("Failed");
			} else {
				dto.setMessage(mdto.getMealsMsg());
				if(mdto.isMsgFlag())
					dto.setType("Success");
				else
					dto.setType("Failed");
			}
		}else {
			dto.setMessage("");
			dto.setType("Failed");
		}
		return dto;
	}
}
