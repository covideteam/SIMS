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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.dto.StudyMealsDietConfiguraionDto;
import com.covideinfo.service.StudyMealDietConfigService;

@Controller
@RequestMapping("stdMealDietConfig")
public class StudyMealDietConfigurationController {
	
	@Autowired
	StudyMealDietConfigService stdMealDietConfigService;
	
	/** 
     * When we click on Side menu link this method will retrieve the data and view that data in jsp page
     */ 
	@RequestMapping(value="/studyMealDietConfiguration", method=RequestMethod.GET)
	public String getStudyMealDietConfigurationPage(ModelMap model, RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		StudyMealsDietConfiguraionDto smdcDto = stdMealDietConfigService.getStudyMealsDietConfiguraionDtoDetails();
		model.addAttribute("smList", smdcDto.getSmList());
		return "studyMealDietConfigurationPage";
	}
	/** 
     * This method will getting required information by provided studyId related meals, meal diet, periods details and passing this data into json object
     */
	@RequestMapping(value="/getStudyRelatedMealsDietDetails/{studyId}", method=RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody StudyMealsDietConfiguraionDto getStudyMealDietConfigurationPage(@PathVariable("studyId")Long studyId) {
		StudyMealsDietConfiguraionDto smdcDto = stdMealDietConfigService.getStudyRelatedMealsConfigurationDetails(studyId);
		return smdcDto;
	}
	/** 
     * This method will save the data of Study wise configured Diet information for particular meal time points
     */
	@RequestMapping(value="/saveStudyMealDietCofigurationData", method=RequestMethod.POST)
	public String saveStudyMealDietCofigurationData(@RequestParam("projectId")Long projectId, @RequestParam("mealDietConfigData")List<String> mealdietList, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("longinUserName");
		boolean flag = stdMealDietConfigService.saveStudyMealDietCofigurationData(projectId, mealdietList, userName);
		if(flag)
			redirectAttributes.addFlashAttribute("pageMessage", "Study Meal Diet Configuration Data Saving Done Successfully....!");
		else redirectAttributes.addFlashAttribute("error", "Study Meal Diet Configuration Data Saving Failed. Please Try Again.");
	  return "redirect:/stdMealDietConfig/studyMealDietConfiguration";
	}

}
