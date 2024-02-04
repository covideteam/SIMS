package com.covideinfo.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.covideinfo.dto.GlobalParameterDetailsDto;
import com.covideinfo.service.StudyActivityTimePointsRetrivingSercive;

@Controller
@RequestMapping("/studyActivityTimePoints")
public class StudyActivityTimePointsRetrivingController {

	
	@Autowired
	StudyActivityTimePointsRetrivingSercive satprService;
	
	@RequestMapping(value="/getStudyActivityTimePointsWithParameters/{studyId}/{subject}/{activityId}", method=RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody GlobalParameterDetailsDto  getStudyActivityTimePointsWithParameters(@PathVariable("studyId")Long studyId,
			@PathVariable("subjectNo")String subjectNo, @PathVariable("activityId")Long activityId, HttpServletRequest request) {
		long languageId = (long) request.getSession().getAttribute("languageId");
		return satprService.getStudyActivityTimePointsDtoDetails(studyId, subjectNo, activityId, languageId);
	}
}
