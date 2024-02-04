package com.covideinfo.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.covideinfo.dto.MessageDto;
import com.covideinfo.dto.RecannulationDataDto;
import com.covideinfo.dto.RecannulationDto;
import com.covideinfo.service.RecannulaService;

@Controller
@RequestMapping("/reCannula")
public class RecannulationController {
	
	@Autowired
	RecannulaService reCannulaService;
	
	/**
	 * 
	 * @param model
	 * @param studyId
	 * @return This will returns recannulation page 
	 */
	@RequestMapping(value="/getRecannulationPage/{studyId}", method=RequestMethod.GET)
	public String  getRecannulationPage(ModelMap model, @PathVariable("studyId") Long studyId) {
		return "pages/clinical/reCannulationPage";
	}
	
	/**
	 * 
	 * @param model
	 * @param studyId
	 * @param subjNo
	 * @return this will returns the customized Re cannulation data given studyId and subject number
	 */
	@RequestMapping(value="/getRecannulationData/{studyId}/{subjectNo}", method=RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody RecannulationDto getRecannulationDataBasedOnStudySubject(ModelMap model, @PathVariable("studyId") Long studyId, @PathVariable("subjectNo")String subjNo) {
		RecannulationDto rcDto = reCannulaService.getRecannulationDtoDetails(studyId, subjNo);
		return rcDto;
	}
	/**
	 * 
	 * @param rcDto
	 * @param request
	 * @return This method saves the recannulation data and return success message or failure message or data already exists message
	 */
	@RequestMapping(value="/saveRecannulationData", method=RequestMethod.POST, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody MessageDto saveRecannulationData(RecannulationDataDto rcDto, HttpServletRequest request) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		String result = reCannulaService.saveRecannulationData(rcDto, userId);
		MessageDto msgDto = new MessageDto();
		if(result.equals("success")) {
			msgDto.setMsgFlag(true);
			msgDto.setMealsMsg("Subject Recannulation Data Saved Successfully....!");
		}else if(result.equals("exists")) {
			msgDto.setMsgFlag(false);
			msgDto.setMealsMsg("Subject Recannulation Data Already Exists.");
		}else {
			msgDto.setMsgFlag(false);
			msgDto.setMealsMsg("Subject Recannulation Data Saved Failed. Please Try Again.");
		}
		return msgDto;
	}
}
