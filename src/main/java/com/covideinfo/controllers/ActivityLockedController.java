package com.covideinfo.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.dao.ActivityLockedDao;
import com.covideinfo.dto.AjaxResponseDto;
import com.covideinfo.dto.DataPushingThread;
import com.covideinfo.dto.StudyDesignFromDto;
import com.covideinfo.model.ActivityLockedData;
import com.covideinfo.service.ActivityLockedService;
import com.covideinfo.util.DateFormatUtil;

@Controller
@RequestMapping("/activityLocked")
public class ActivityLockedController {
	
	@Autowired
	ActivityLockedService activityLockedService;
	
	@Autowired
	ActivityLockedDao activityLockedDao;
	
	@Autowired
	private Environment environment;
	
   	//id is support all type of locking 
	 @RequestMapping(value="/saveActivityLockingData",method=RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
		public @ResponseBody AjaxResponseDto saveActivityLockingData(StudyDesignFromDto sdfrom,
				ModelMap module,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
	    String username = request.getSession().getAttribute("userName").toString();
    	boolean flag=false;
    	DataPushingThread.dataPushing(activityLockedService,activityLockedDao);
    	AjaxResponseDto ajaxResponseDto = new AjaxResponseDto();
		ActivityLockedData aldpojo=activityLockedService.getActivityLockedDataExitOrNot(sdfrom);
    	if(aldpojo==null) {
    		if(sdfrom != null) {
        		flag=activityLockedService.saveActivityLockedData(sdfrom,username);
        	}
        	if(flag) {
        		ajaxResponseDto.setSuccess(true);
        		ajaxResponseDto.setMessage("");
        		ajaxResponseDto.setDate(DateFormatUtil.ConvertDate(new Date(), environment.getRequiredProperty("dateTimeFormatSeconds")));
        	}
    		else {
    			ajaxResponseDto.setSuccess(false);
        		ajaxResponseDto.setMessage("Saving Fail");
        		ajaxResponseDto.setDate(DateFormatUtil.ConvertDate(new Date(), environment.getRequiredProperty("dateTimeFormatSeconds")));
    		}
    	}else {
    		if(!aldpojo.getCreatedBy().equals(username)) {
    			ajaxResponseDto.setSuccess(false);
        		ajaxResponseDto.setMessage("Subject locked for activity " + aldpojo.getActivityName() );
        		ajaxResponseDto.setDate(DateFormatUtil.ConvertDate(new Date(), environment.getRequiredProperty("dateTimeFormatSeconds")));
        	}
    		else {
    			ajaxResponseDto.setSuccess(true);
        		ajaxResponseDto.setMessage("");
        		ajaxResponseDto.setDate(DateFormatUtil.ConvertDate(new Date(), environment.getRequiredProperty("dateTimeFormatSeconds")));
    		}
    	}
    	return ajaxResponseDto;
    }
    
    @RequestMapping(value="/deleteActivityLockingData",method=RequestMethod.POST)
	public @ResponseBody String deleteActivityLockingData(StudyDesignFromDto sdform,ModelMap module,HttpServletRequest request,HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
    	boolean flag=false;
    	ActivityLockedData aldpojo=activityLockedService.getActivityLockedDataExitOrNot(sdform);
    	  		flag=activityLockedService.deleteActivityLockingData(aldpojo);
  		if(flag) {
  			return "{\"Success\": true,\"Message\":\"" + "" + "\"}";
  		}else {
  			return "{\"Success\": false,\"Message\":\"Saving Fail\"}";
  		}
	    
		
	}
    
    
}
