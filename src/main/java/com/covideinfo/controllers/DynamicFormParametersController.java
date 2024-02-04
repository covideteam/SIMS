package com.covideinfo.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.covideinfo.dao.impl.DynamicFormParametersDaoImpl;
import com.covideinfo.dto.StudyDynamicFormParametersDto;

@Controller
@RequestMapping("/dynamicParms")
public class DynamicFormParametersController {

	@Autowired
	DynamicFormParametersDaoImpl dynamicFormParametersDto;

	@RequestMapping(value = "/getParameters/{studyId}/{activityId}/{volId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody StudyDynamicFormParametersDto getParameters(@PathVariable("studyId") Long studyId,
			@PathVariable("activityId") Long activityId, @PathVariable("subjectNo") String subjectNo,
			HttpServletRequest request) {
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		return dynamicFormParametersDto.getStudyDesingActivityDetailsDtoDetails(studyId, activityId, languageId,
				subjectNo);
	}

}
