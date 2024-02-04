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

import com.covideinfo.dto.AllowMealsDataDto;
import com.covideinfo.dto.AllowMealsRecordsDto;
import com.covideinfo.dto.MessageDto;
import com.covideinfo.dto.StudyMasterDto;
import com.covideinfo.service.AllowMealsService;

@Controller
@RequestMapping("/allowMeal")
public class AllowMealsController {
	
	@Autowired
	AllowMealsService allowMealService;
	
	@RequestMapping(value="/allowMealsPage", method=RequestMethod.GET)
	public String allowMealsPage(ModelMap model) {
		List<StudyMasterDto> smList = allowMealService.getStudyMasterList();
		model.addAttribute("smList", smList);
		return "allowMeals";
	}
	@RequestMapping(value="/getAllowMealsRecords/{projectId}", method=RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody AllowMealsRecordsDto getAllowMealsRecords(@PathVariable("projectId")Long projectId) {
		return allowMealService.getAllowMealsRecords(projectId);
	}
	
	@RequestMapping(value="/saveAllowMealsRecord", method=RequestMethod.POST, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody MessageDto saveStudyMealConsumptionDietData(AllowMealsDataDto almDto, HttpServletRequest request) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		String result = allowMealService.saveAllowMealsRecord(userId, almDto);
		MessageDto msgDto = new MessageDto();
		String msg = "";
		if (result.equals("Success")) {
			msgDto.setMsgFlag(true);
			msg = "Allow Meals Data Saved Successfully....!";
		}else if(result.equals("Exists")) {
			msgDto.setMsgFlag(true);
			msg = "Data Already Exists.";
		}else {
			msgDto.setMsgFlag(false);
			msg = "Allow Meals Data Saving Failed. Please try again.";
		}
		msgDto.setMealsMsg(msg);
		return msgDto;
	}
	
	@RequestMapping(value="/inactiveAllowMealRecord/{recordId}", method=RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody MessageDto inactiveAllowMealRecord(@PathVariable("recordId")Long inactId, HttpServletRequest request) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		String result = allowMealService.inactiveAllowMealRecord(inactId, userId);
		MessageDto msgDto = new MessageDto();
		String msg = "";
		if (result.equals("Success")) {
			msgDto.setMsgFlag(true);
			msg = "Allow Meals Data Deleted Successfully....!";
		}else {
			msgDto.setMsgFlag(false);
			msg = "Allow Meals Data Deletion Failed. Please try again.";
		}
		msgDto.setMealsMsg(msg);
		return msgDto; 
	}
}
