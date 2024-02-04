package com.covideinfo.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.covideinfo.dto.ActivityDetailsDto;
import com.covideinfo.dto.ActivityUpdateDataDto;
import com.covideinfo.dto.MessageDto;
import com.covideinfo.service.ActivityUpdateService;

@Controller
@RequestMapping("activityUpdate")
public class ActivityUpdateController {
	
	@Autowired
	ActivityUpdateService actUpdateService;
	
	@RequestMapping(value="/getActivityDetails/{activityId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ActivityDetailsDto getActivityDetails(@PathVariable("activityId")Long activityId) {
		return actUpdateService.getActivityDetails(activityId);
	}
	@RequestMapping(value="/saveUpdatedActivityData", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MessageDto saveUpdatedActivityData(ActivityUpdateDataDto actUpDto, HttpServletRequest request) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		String result = actUpdateService.saveUpdatedActivityData(actUpDto, userId);
		MessageDto msgDto = new MessageDto();
		String msg = "";
		if (result.equals("success")) {
			msgDto.setMsgFlag(true);
			msg = "Activity Updated Successfully...!";
		} else if(result.equals("Failed")){
			msgDto.setMsgFlag(false);
			msg = "Activity Updation Failed. Please try again.";
		}else {
			msgDto.setMsgFlag(false);
			msg = result;
		}
		msgDto.setMealsMsg(msg);
		return msgDto;
	}
	
	
}
