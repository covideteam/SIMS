package com.covideinfo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.covideinfo.dto.ActivityDto;
import com.covideinfo.dto.CpuOnlineData;
import com.covideinfo.dto.VolunteerDto;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.service.StudyExecutionServiece;
import com.covideinfo.util.CorsConfigurationProcess;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Controller
@RequestMapping("/studyExe")
public class StudyExecutionController {
	
	@Autowired
	StudyExecutionServiece sexeService;
	
	
	@RequestMapping(value="/studyExePage", method=RequestMethod.GET)
	public String studyExecution(ModelMap model, HttpServletRequest request) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		List<StudyMaster> smList = sexeService.getStudyMasterList(userId);
		model.addAttribute("smList", smList);
		model.addAttribute("screenType", "activity");
		return "stuydExectutionPage";
	}
	
	@RequestMapping(value="/studyReview", method=RequestMethod.GET)
	public String studyReview(ModelMap model, HttpServletRequest request) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		List<StudyMaster> smList = sexeService.getStudyMasterList(userId);
		model.addAttribute("smList", smList);
		model.addAttribute("screenType", "review");
		return "studyReview";
	}
	
	@RequestMapping(value="/responseToStudyReview", method=RequestMethod.GET)
	public String responseToStudyReview(ModelMap model, HttpServletRequest request) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		List<StudyMaster> smList = sexeService.getStudyMasterList(userId);
		model.addAttribute("smList", smList);
		model.addAttribute("screenType", "responsetoreview");
		return "studyReview";
	}
	
	@RequestMapping(value="/getStudyActivities/{studyId}", method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ActivityDto getStudyActivities(ModelMap model, @PathVariable("studyId")Long studyId, HttpServletRequest request) throws JsonProcessingException {
		Long role = (Long) request.getSession().getAttribute("roleId");
		ActivityDto actDto = sexeService.getActivityDto(studyId, role);
		return actDto;
	}
	
	@RequestMapping(value="/getStudyVolunteers/{studyId}/{activityId}/{param}/{subjectScanned}", method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String getStudyVolunteers(ModelMap model, @PathVariable("studyId")Long studyId, 
			@PathVariable("activityId")Long activityId, @PathVariable("param")String param,@PathVariable("subjectScanned")Boolean isSubjectScanned, HttpServletRequest request) throws JsonProcessingException {
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		List<VolunteerDto> sadto = sexeService.getVolunteerList(studyId,activityId, param, languageId,isSubjectScanned);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.setSerializationInclusion(Include.NON_NULL);
		return mapper.writeValueAsString(sadto);
	}
}
