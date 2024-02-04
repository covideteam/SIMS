package com.covideinfo.controllers;
/*package com.covideinfo.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.covideinfo.model.SubjectActivityData;
import com.covideinfo.service.FitForStudyService;
import com.covideinfo.service.StudyActivityService;

@Controller
@RequestMapping("/incExc")
public class InclusionExclusionActivityController {
	
	
	@Autowired
	FitForStudyService firForStudyService;
	
	@Autowired
	StudyActivityService studyActivityService;
	
	@Autowired  
	MessageSource messageSource;
	
	
	@RequestMapping(value="/saveinclusionExclusionFormData", method=RequestMethod.POST, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String saveinclusionExclusionFormData(SubjectActivityData sad, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("longinUserName");
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		Locale currentLocale = LocaleContextHolder.getLocale();
		String dateFormat = (String) request.getSession().getAttribute("clinicalDateTimeFormat");
		sad.setSaveTable("SCI");
		String result = studyActivityService.saveStudyActivityFromsData(sad, messageSource,currentLocale, userName,languageId, dateFormat);
		if(result.equals("success")) {
			String finalResult = firForStudyService.inactiveVolunteerRecord(sad, userName);
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
	}

}
*/