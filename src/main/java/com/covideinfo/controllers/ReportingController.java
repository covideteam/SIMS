package com.covideinfo.controllers;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.dto.ReportingDto;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.model.StudyActivityParameters;
import com.covideinfo.model.StudyGroup;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.ReportingService;
import com.covideinfo.service.UserCreationService;

@Controller
@RequestMapping("/reporting")
public class ReportingController {
	
	@Autowired
	ReportingService reportingService;
	
	@Autowired  
	MessageSource messageSource;
	
	@Autowired
	UserCreationService userCreationService;
	
	@RequestMapping(value="/getReportingForm/{studyId}", method=RequestMethod.GET)
	public String reporting(ModelMap model, RedirectAttributes redirectAttributes, @PathVariable("studyId")Long studyId) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		Locale currentLocale = LocaleContextHolder.getLocale();
		ReportingDto rdto = reportingService.getReportingDtoDetails(currentLocale);
		model.addAttribute("rdto", rdto);
		model.addAttribute("svr", new StudyVolunteerReporting());
		return "pages/reporting/repotingPage";
	}
	@RequestMapping(value="/saveReportingVolunteerData", method=RequestMethod.POST)
	public @ResponseBody String saveReportingVolunteerData(@RequestParam("studyId")Long projectId, @RequestParam("gender")Long genderId,
			StudyVolunteerReporting svr,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String msg = "";
		String userName = (String) request.getSession().getAttribute("longinUserName");
		String result = reportingService.savesaveReportingVolunteerData(projectId, svr, userName, genderId);
		if(result.equals("success")) {
			msg = messageSource.getMessage("label.reporting.successMsg", null,currentLocale);
		}else {
			if(result.equals("NoPeriods")) 
				msg = messageSource.getMessage("label.reporring.noPeriods", null,currentLocale);
			else if(result.equals("ReachedLimit"))
				msg = messageSource.getMessage("label.reporting.exceedLimit", null,currentLocale);
			else if(result.equals("exists"))
				msg = messageSource.getMessage("label.exists", null,currentLocale);
			else 
				msg = messageSource.getMessage("label.reporting.failureMsg", null,currentLocale);
		}
		return "{\"Success\": \""+result+"\",\"Message\":\"" + msg + "\"}";
	}
	@RequestMapping(value="/getAllSubjectActivityForm/{groupId}", method=RequestMethod.GET)
	public String getAllSubjectActivityForm(ModelMap model,@PathVariable("groupId")Long groupId, RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		Locale currentLocale = LocaleContextHolder.getLocale();
		ReportingDto rdto = reportingService.getReportingDtoDetails(currentLocale);
		//rdto.setStudyId(studyId);
		rdto.setGroupId(groupId);
		GlobalActivity ga=reportingService.getGlobalActivityWithName("allSubjectActivity");
		StudyGroup sg=reportingService.getStudyGroupWithId(groupId);
		List<StudySubjects> ss=reportingService.getStudySubjectsWithGroupId(groupId);
		StudySubjects ssdata=ss.get(1);
		StudyActivityData sad=reportingService.getStudyActivityDataCheckAllSubjectActivity(ga,sg,ssdata);
		String start="";
		if(sad!=null) {
			start="Yes";
		}else {
			start="No";
		}
		//String start="No";
		model.addAttribute("rdto", rdto);
		//model.addAttribute("sad", sad);
		model.addAttribute("start", start);
		
		return "pages/reporting/allSubjectActivityPage";
	}
	
	
	@RequestMapping(value="/saveAllSubjectActivityStart", method=RequestMethod.POST)
	public String saveAllSubjectActivityStart(ModelMap model,HttpServletRequest request,@RequestParam("agroupid")Long agroupid, RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		Locale currentLocale = LocaleContextHolder.getLocale();
		long uid = (long) request.getSession().getAttribute("userId");
		UserMaster um=userCreationService.getUserMasterWithId(uid);
		GlobalActivity ga=reportingService.getGlobalActivityWithName("allSubjectActivity");
		StudyGroup sg=reportingService.getStudyGroupWithId(agroupid);
		List<StudySubjects> ss=reportingService.getStudySubjectsWithGroupId(agroupid);
		StudyActivities sa=reportingService.getStudyActivitiesWithStudyIdAndGlobalActivityId(ga,sg);
		StudyActivityParameters saplist=reportingService.getStudyActivityParametersWitgStudyActivitiesId(sa);
		boolean flag=false;
		flag=reportingService.saveAllSubjectActivityStart(ss,sa,um,saplist);
		
		if(flag) {
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);


			redirectAttributes.addFlashAttribute("pageMessage", " Study Execution Start");
		}
		else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			redirectAttributes.addFlashAttribute("pageError", " Study Execution Start Failed ");
		}
		return "redirect:/studyExe/studyExePage";
	}
	@RequestMapping(value="/saveAllSubjectActivityStop", method=RequestMethod.GET)
	public String saveAllSubjectActivityStop(ModelMap model,HttpServletRequest request,@RequestParam("groupId")Long groupId,@RequestParam("subjectNo")String subjectNo, RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		Locale currentLocale = LocaleContextHolder.getLocale();
		long uid = (long) request.getSession().getAttribute("userId");
		UserMaster um=userCreationService.getUserMasterWithId(uid);
		GlobalActivity ga=reportingService.getGlobalActivityWithName("allSubjectActivity");
		StudyGroup sg=reportingService.getStudyGroupWithId(groupId);
		List<StudySubjects> ss=reportingService.getStudySubjectsWithGroupId(groupId);
		StudyActivities sa=reportingService.getStudyActivitiesWithStudyIdAndGlobalActivityId(ga,sg);
		StudyActivityParameters saplist=reportingService.getStudyActivityParametersWitgStudyActivitiesIdForStop(sa);
		boolean flag=false;
		flag=reportingService.saveAllSubjectActivityStop(ss,sa,um,subjectNo,saplist);
		
		if(flag) {
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);


			redirectAttributes.addFlashAttribute("pageMessage", " Study Execution Stop");
		}
		else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			redirectAttributes.addFlashAttribute("pageError", " Study Execution Stop Failed ");
		}
		return "redirect:/studyExe/studyExePage";
	}
}
