package com.covideinfo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.covideinfo.dto.CpuActivitiesReviewDataDto;
import com.covideinfo.dto.MessageDto;
import com.covideinfo.service.CpurActivityReviewService;
import com.covideinfo.service.SampleScheduleCalculationService;

@Controller
@RequestMapping("/reviewData")
public class CpuAtivitiesDataReviewController {
	
	@Autowired
	CpurActivityReviewService cpureviewService;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	SampleScheduleCalculationService sscService;
	
	@RequestMapping(value="/getActivityDataForReview/{projectId}/{activityId}/{type}", method=RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody CpuActivitiesReviewDataDto getActivityDataForReview(@PathVariable("projectId")Long projectId, @PathVariable("activityId")Long activityId, 
			@PathVariable("type")String type, HttpServletRequest request) {
		Long roleId = (Long) request.getSession().getAttribute("roleId");
		Long userId = (Long) request.getSession().getAttribute("userId");
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		CpuActivitiesReviewDataDto cpuReviewData = cpureviewService.getCpuReviewActivityData(projectId, activityId, userId, roleId, languageId, type);
		return cpuReviewData;
	}
	
	@RequestMapping(value="/saveStaticDiscrepancy/{studyId}/{activityId}/{activityType}/{activityDataId}/{fieldName}/{descComments}/{parameterId}", method=RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody MessageDto saveStaticDiscrepancy(@PathVariable("studyId")Long studyId, @PathVariable("activityId")Long activityId,@PathVariable("activityType")String activityType,
			@PathVariable("activityDataId")Long activityDataId,@PathVariable("fieldName")String fieldName, @PathVariable("descComments")List<String> descComments, 
			@PathVariable("parameterId")String parameterId, HttpServletRequest request){
		Long userId = (Long) request.getSession().getAttribute("userId");
		MessageDto msgDto = cpureviewService.saveStaticDiscrepancyDetails(studyId, activityId, activityDataId, fieldName, descComments, activityType, userId, parameterId);
		return msgDto;
	}
	@RequestMapping(value="/saveDiscepencyResponseDetails/{desId}/{currentVal}/{newVal}/{response}/{activityType}", method=RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody MessageDto saveDiscepencyResponseDetails(@PathVariable("desId")Long desId, @PathVariable("currentVal")String currentVal,@PathVariable("newVal")String newVal,
			@PathVariable("response")String response,@PathVariable("activityType")String activityType, HttpServletRequest request){
		Long userId = (Long) request.getSession().getAttribute("userId");
		MessageDto msgDto = cpureviewService.saveStaticDiscrepancyResponseDetails(desId, currentVal, newVal, response, userId, activityType, sscService);
		return msgDto;
	}
	
}
