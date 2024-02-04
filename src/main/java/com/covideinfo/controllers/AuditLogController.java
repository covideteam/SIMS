package com.covideinfo.controllers;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.ActivityLog;
import com.covideinfo.model.AuditLog;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.UserActivity;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.AuditLogService;
import com.covideinfo.service.UserService;

@Controller
@RequestMapping("/auditlog")
@PropertySource(value = { "classpath:application.properties" })
public class AuditLogController {
	
	@Autowired
	UserService userService;
	@Autowired
	AuditLogService auditLogService;
	@Autowired  
	MessageSource messageSource;
	
	// User Log Search Page
	@RequestMapping(value="/userLogInfoPage", method=RequestMethod.GET)
	public String userLogInfoPage(ModelMap model) {
		List<UserMaster>  userList=auditLogService.getUserMasterList();
		model.addAttribute("userList", userList);
		return "loginUserInfoPage";
	}
	// User Log Data View
	@RequestMapping(value="/userLoingAndLogoutData/{fromDate}/{toDate}/{userName}", method=RequestMethod.GET)
	public String userLoingAndLogoutData(ModelMap model,@PathVariable("fromDate")String fromDate,
			@PathVariable("toDate")String toDate, @PathVariable("userName")String  userName) {
		List<UserActivity> uaList = null;
		uaList = userService.getUserActivityList(fromDate, toDate, userName);
		model.addAttribute("userName", userName);
		model.addAttribute("uaList", uaList);
		return "pages/logindata/loginUserDetails";
	}
	//Audit Log Search
	@RequestMapping(value="/auditLogTrigerDataSearch", method=RequestMethod.GET)
	public String auditLogTrigerDataSearch(ModelMap model,HttpServletRequest request,HttpServletResponse response,
			RedirectAttributes redirectAttributes ) {
		List<UserMaster>  userList=auditLogService.getUserMasterList();
		List<StudyMaster>  studyList=auditLogService.getStudyMasterList();
		model.addAttribute("userList", userList);
		model.addAttribute("studyList", studyList);
		
		return "auditLogSearch";
		
	}
	
	//Audit Log Data View
	@RequestMapping(value="/auditLoginData/{fromDate}/{toDate}/{studyid}", method=RequestMethod.GET)
	public String auditLoginData(ModelMap model,@PathVariable("fromDate")String fromDate,
			@PathVariable("toDate")String toDate,
			 @PathVariable("studyid")Long  studyid) {
		List<AuditLog> auList = null;
		auList = userService.getAuditLogList(fromDate, toDate,studyid);
		for(AuditLog al:auList) {
			if(al.getFieldType().equalsIgnoreCase("static")) {
			 Locale locale = LocaleContextHolder.getLocale();
			 String data="";
			 try {
				 data=messageSource.getMessage(al.getFieldName(), null,locale);
			} catch (Exception e) {
				data="";
			}
			 al.setFieldName(data);
			}
		}
		
		model.addAttribute("auList", auList);
		return "pages/auditlog/auditLogDetails";
	}
	//Activity Log Search
	@RequestMapping(value="/activityLoginDataSearch", method=RequestMethod.GET)
	public String activityLoginDataSearch(ModelMap model,HttpServletRequest request,HttpServletResponse response,
			RedirectAttributes redirectAttributes ) {
		List<UserMaster>  userList=auditLogService.getUserMasterList();
		List<StudyMaster>  studyList=auditLogService.getStudyMasterList();
		model.addAttribute("userList", userList);
		model.addAttribute("studyList", studyList);
		
		return "activityLogSearch";
		
	}
	//Activity Log Data View
	@RequestMapping(value="/activityLoginData/{fromDate}/{toDate}/{volunteer}/{studyid}/{subjectid}", method=RequestMethod.GET)
	public String activityLoginData(ModelMap model,@PathVariable("fromDate")String fromDate,
			@PathVariable("toDate")String toDate, @PathVariable("volunteer")Long  volunteer,
			 @PathVariable("studyid")Long  studyid, @PathVariable("subjectid")Long  subjectid) {
		List<ActivityLog> auList = null;
		auList = userService.getActivityLogList(fromDate, toDate, volunteer,studyid,subjectid);
		model.addAttribute("auList", auList);
		return "pages/auditlog/activityLogDetails";
	}
	//Get Subject Data Based On Study Id
	@RequestMapping(value="/getSubjetDataWithStudyId/{studyid}", method=RequestMethod.GET)
	public String getSubjetDataWithStudyId(ModelMap model,
			 @PathVariable("studyid")Long  studyid) {
		List<StudySubjects> subjectList = null;
		subjectList = userService.getSubjetDataWithStudyId(studyid);
		model.addAttribute("subjectList", subjectList);
		return "pages/auditlog/activityLogForSubjectList";
	}
	//Get Volunteer Data Based On Study Id
	@RequestMapping(value="/getVolunteerDataWithStudyId/{studyid}", method=RequestMethod.GET)
	public String getVolunteerDataWithStudyId(ModelMap model,
			 @PathVariable("studyid")Long  studyid) {
		List<StudyVolunteerReporting> volList = null;
		volList = userService.getVolunteerDataWithStudyId(studyid);
		model.addAttribute("volList", volList);
		return "pages/auditlog/activityLogForVolunteerList";
	}
	
	
}
