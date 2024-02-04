package com.covideinfo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.covideinfo.service.StudyActivityService;
import com.covideinfo.service.SubjectAllotmentService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Controller
@RequestMapping("/subjectAllotment")
public class SubjectAllotmentController {
	
	@Autowired
	SubjectAllotmentService subjectAllotmentService;
	
	@Autowired
	StudyActivityService studyActivityService;
	
	@Autowired  
	MessageSource messageSource;
	
	@RequestMapping(value="/getsubjectNo/{studyId}", method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String getSubjectNo(@PathVariable("studyId")Long studyId) throws JsonProcessingException {
		List<String> subjectList = subjectAllotmentService.generateSubjectNo(studyId);
		ObjectMapper Obj = new ObjectMapper();
		Obj.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		Obj.setSerializationInclusion(Include.NON_NULL);
		return Obj.writeValueAsString(subjectList);
	}
	
	/*@RequestMapping(value="/saveFitForStudyFormData", method=RequestMethod.POST, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String saveFitForStudyFormData(SubjectActivityData sad, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("longinUserName");
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		Locale currentLocale = LocaleContextHolder.getLocale();
		String dateFormat = (String) request.getSession().getAttribute("clinicalDateTimeFormat");
		sad.setSaveTable("SCI");
		String result = studyActivityService.saveStudyActivityFromsData(sad, messageSource,currentLocale, userName,languageId, dateFormat);
		if(result.equals("success")) {
//			String finalResult = subjectAllotmentService.studySubjectAllotment(sad, userName);
			String successMsg =messageSource.getMessage("label.stdActSuccessMsg", null,currentLocale);
			return "{\"Success\": \""+true+"\",\"Message\":\"" + successMsg + "\"}";
			if(finalResult.equals("success")) {
				String successMsg =messageSource.getMessage("label.stdActSuccessMsg", null,currentLocale);
				return "{\"Success\": \""+true+"\",\"Message\":\"" + successMsg + "\"}";
			}else {
				String falilMsg =messageSource.getMessage("label.stdActFailMsg", null,currentLocale);
				return "{\"Success\": \""+false+"\",\"Message\":\"" + falilMsg + "\"}";
			}
		}else {
			String falilMsg =messageSource.getMessage("label.stdActFailMsg", null,currentLocale);
			return "{\"Success\": \""+false+"\",\"Message\":\"" + falilMsg + "\"}";
		}
	}*/

}
