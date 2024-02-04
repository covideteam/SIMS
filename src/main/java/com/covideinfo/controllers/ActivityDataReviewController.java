package com.covideinfo.controllers;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.covideinfo.dto.ActivityDataReviewDataDto;
import com.covideinfo.dto.ActivityDataReviewDto;
import com.covideinfo.dto.RequestStatusDto;
import com.covideinfo.dto.ResultDto;
import com.covideinfo.dto.StudyActivityDataReviewDto;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.ActivityDataReviewService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Controller
@RequestMapping("/actvityReview")
public class ActivityDataReviewController {
	@Autowired
	private Environment environment;
	
	@Autowired
	ActivityDataReviewService activityReviewService;
	
	@Autowired  
	MessageSource messageSource;
	
	@RequestMapping(value="/getStudyActivityDataForReview/{studyId}/{activityId}", method=RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<ActivityDataReviewDto> getStudyActivityDataForReview(@PathVariable("studyId")Long studyId, 
			@PathVariable("activityId")Long activityId, HttpServletRequest request) throws JsonProcessingException {
		Long roleId =(Long) request.getSession().getAttribute("roleId");
		return activityReviewService.getStudyActivityDataForReview(studyId, activityId, roleId, environment.getRequiredProperty("dateFormat"));
	}
		
	@RequestMapping(value="/getActivityDataDetails/{activityDataId}", method=RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<ActivityDataReviewDataDto> getActivityDataDetails(@PathVariable("activityDataId")Long activityDataId) throws JsonProcessingException {
		Locale currentLocale = LocaleContextHolder.getLocale();
		return activityReviewService.getActivityDataDetails(activityDataId, currentLocale, environment.getRequiredProperty("dateFormat"));
	}

	@RequestMapping(value="/accept", method=RequestMethod.POST, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody RequestStatusDto acceptStudyActivityData(StudyActivityDataReviewDto studyActivityData, HttpServletRequest request) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String userName=request.getSession().getAttribute("userName").toString();
		return activityReviewService.acceptStudyActivityData(studyActivityData, userName,currentLocale);
	}
	
	@RequestMapping(value="/sendComments", method=RequestMethod.POST, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody RequestStatusDto sendCommentsStudyActivityData(StudyActivityDataReviewDto studyActivityData, HttpServletRequest request) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		if(studyActivityData.getComments().size() == 0) {
			RequestStatusDto resultStatusDto= new RequestStatusDto();
			resultStatusDto.setResult(false);
			resultStatusDto.setMessage(messageSource.getMessage("label.sendCommentsAddComments", null, currentLocale));
		}
		String userName=request.getSession().getAttribute("userName").toString();
		return activityReviewService.sendCommentsStudyActivityData(studyActivityData, userName,currentLocale);
	}
}
