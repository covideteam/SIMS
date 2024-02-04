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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.dto.MessageDto;
import com.covideinfo.dto.StudyMealConsumptionDietDto;
import com.covideinfo.dto.StudyMealConsumptionItemsDataDto;
import com.covideinfo.service.StudySubjectMealConsumptionService;

@Controller
@RequestMapping("/subMealCon")
public class StudySubjectMealConsumptionController {
	
	@Autowired
	StudySubjectMealConsumptionService stdSubMealconsumptionService;
	
	/*This method will show the list Approved Projects list when click on Left Over Meals side link */
	@RequestMapping(value="/studyMealConsumptionPage", method=RequestMethod.GET)
	public String studyMealConsumptionPage(ModelMap model, RedirectAttributes redirectAttributes) {
		StudyMealConsumptionDietDto mdpiDto = stdSubMealconsumptionService.getStudyMealConsumptionDietDtoDetails();
		model.addAttribute("smList", mdpiDto.getSmList());
		return "studyMealConsumptionDetailsPage";
	}
	/*This method will gets the information of meals consumption records subject wise and timepoint wise meal items custom data. by taking input as projectId*/
	@RequestMapping(value="/getStudyRelatedMealsConsumptionDietDetails/{studyId}", method=RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody StudyMealConsumptionDietDto getStudyRelatedMealsConsumptionDietData(@PathVariable("studyId")Long studyId) {
		return stdSubMealconsumptionService.getStudyRelatedMealsConsumptionDietData(studyId);
	}
	
	/*This method will save the data what we are provided left quantity and leftCalories as subject and time point wise*/
	@RequestMapping(value="/saveStudyMealConsumptionDietData", method=RequestMethod.POST, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody MessageDto saveStudyMealConsumptionDietData(StudyMealConsumptionItemsDataDto smcitemData, HttpServletRequest request) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		String result = stdSubMealconsumptionService.saveStudyMealConsumptionDietData(userId, smcitemData);
		MessageDto msgDto = new MessageDto();
		String msg = "";
		if (result.equals("Success")) {
			msgDto.setMsgFlag(true);
			msg = "Meals Consumption Diet Data Saved Successfully....!";
		} else {
			msgDto.setMsgFlag(false);
			msg = "Meals Consumption Diet Data Saving Failed. Please try again.";
		}
		msgDto.setMealsMsg(msg);
		return msgDto;
	}

}
