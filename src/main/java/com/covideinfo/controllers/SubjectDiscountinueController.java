package com.covideinfo.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.covideinfo.dto.StudyWitdrawalSavingDto;
import com.covideinfo.dto.StudyWithDrawDto;
import com.covideinfo.model.SubjectActivityData;
import com.covideinfo.service.StudyActivityService;
import com.covideinfo.service.SubjectDiscontinueService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/disocontinue")
public class SubjectDiscountinueController {
	
	@Autowired
	SubjectDiscontinueService subdiscService;
	
	@Autowired
	StudyActivityService studyActivityService;
	
	@Autowired  
	MessageSource messageSource;
	
	@RequestMapping(value="/studyWithDrawPage/{studyId}", method=RequestMethod.GET)
	public String getstudyWithDrawPage(ModelMap model, @PathVariable("studyId")Long studyId) {
		String jsonStr = "";
		ObjectMapper mapper = new ObjectMapper();
		StudyWithDrawDto swdDto = subdiscService.getStudyWithDrawDtoDetails(studyId);
		if(swdDto != null)
			try {
				jsonStr = mapper.writeValueAsString(swdDto);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		model.addAttribute("jsonStr", jsonStr);
		return "pages/withDraw/studyWithDrawActivityPage";
		
	}
	
	
	/*@RequestMapping(value="/saveSubjectDiscoutinueFormData", method=RequestMethod.POST, produces = {org.springframework.http.MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String saveSubjectDiscountinueFormData(SubjectActivityData sad, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("longinUserName");
		Locale currentLocale = LocaleContextHolder.getLocale();
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		String dateFormat = (String) request.getSession().getAttribute("clinicalDateTimeFormat");
		sad.setSaveTable("SCO");
		String result = studyActivityService.saveStudyActivityFromsData(sad, messageSource,currentLocale, userName,languageId, dateFormat);
		if(result.equals("success")) {
			String finalResult = subdiscService.updateStudySubjectRecords(sad, userName);
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
	
	@RequestMapping(value = "/saveWithdrawOrDropOutData", method = RequestMethod.POST, produces = {org.springframework.http.MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody String  saveWithdrawOrDropOutData(StudyWitdrawalSavingDto swsDto, HttpServletRequest request) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		Long userId = (Long) request.getSession().getAttribute("userId");
		String dateFormat = (String) request.getSession().getAttribute("clinicalDateTimeFormat");
       String result = subdiscService.saveStaticActivityDataDetails(swsDto, userId, dateFormat);
		 if(result.equals("success")) {
    	   String successMsg =messageSource.getMessage("label.stdActSuccessMsg", null,currentLocale);
   		return "{\"Success\": \""+true+"\",\"Message\":\"" + successMsg + "\"}";
       }else if(result.equals("Duplicate")){
    	   String successMsg =messageSource.getMessage("label.stdDataAlready", null,currentLocale);
   		   return "{\"Success\": \""+false+"\",\"Message\":\"" + successMsg + "\"}";
       }else {
    	   String successMsg =messageSource.getMessage("label.stdActFailMsg", null,currentLocale);
   		   return "{\"Success\": \""+false+"\",\"Message\":\"" + successMsg + "\"}";
       }
       	
    }


}
