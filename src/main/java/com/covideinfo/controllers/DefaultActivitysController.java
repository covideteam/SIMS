package com.covideinfo.controllers;

import java.io.IOException;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.dto.DefaultActivityDto;
import com.covideinfo.model.DefaultActivitys;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.model.StudyActivityDataDetailsDiscrepancy;
import com.covideinfo.model.StudyActivityDataReviewAudit;
import com.covideinfo.model.StudyCheckInActivityDataDetails;
import com.covideinfo.model.StudyCheckOutActivityDataDetails;
import com.covideinfo.model.StudyExecutionActivityDataDetails;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.dummy.DataCheckKeyVal;
import com.covideinfo.service.DefaultActivitysService;
import com.covideinfo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/defaultActivity")
@PropertySource(value = { "classpath:application.properties" })
public class DefaultActivitysController {
	
	
	@Autowired
	DefaultActivitysService defaultActivitysService;
	
	@Autowired
	UserService userService;
	
	@Autowired  
	MessageSource messageSource;
	
	@RequestMapping(value="/defaultActivityForm",method=RequestMethod.GET)
	public String getDefaultActivityForm(RedirectAttributes redirectAttributes ,ModelMap model,HttpServletRequest request,HttpServletResponse response  ) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		DefaultActivityDto daDto = defaultActivitysService.getDefaultActivityDtoDetails();
		model.addAttribute("activity", daDto.getGaList());
		model.addAttribute("parameter", daDto.getGpList());
		model.addAttribute("phase", daDto.getPhaseList());
		model.addAttribute("daList", daDto.getDaList());
		model.addAttribute("dapMap", daDto.getDapMap());
		
		return "defaultActivityForm";
		
	}
	@RequestMapping(value="/defaultActivitySave", method=RequestMethod.POST)
	public String defaultActivitySave(@RequestParam ("actid") long actid,@RequestParam ("paramid") List<Long> paramid,
			@RequestParam ("phaseid") long phaseid, @RequestParam("tableNameVal") String tableName,
			@RequestParam("subjectAllotmentVal")String subjectAllotment,@RequestParam("submitControls")String submitControls,
			@RequestParam("saveUrl")String saveUrl,@RequestParam("rejectUrl")String rejectUrl,
			@RequestParam("getUrl")String getUrl,
			RedirectAttributes redirectAttributes ,ModelMap model,HttpServletRequest request,HttpServletResponse response  ) {
		String username=request.getSession().getAttribute("longinUserName").toString();
		Locale currentLocale = LocaleContextHolder.getLocale();
		DefaultActivitys exitCheck = defaultActivitysService.getDefaultActivitysExit(actid, paramid, phaseid);
		if (exitCheck == null) {
			boolean flag = defaultActivitysService.saveDefaultActivity(actid, paramid, phaseid,/*tableName,*/ subjectAllotment,submitControls, username,getUrl,saveUrl,rejectUrl);
			if (flag) {
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);

				String dasuccessMsg =messageSource.getMessage("label.daSuccessMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageMessage", dasuccessMsg);
			}else {
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

				String daFailMsg =messageSource.getMessage("label.daFailMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageError", daFailMsg);
			}
		}else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String daExistMsg =messageSource.getMessage("label.daExistMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", daExistMsg);
		}
		return "redirect:/defaultActivity/defaultActivityForm";
	}
	
	@RequestMapping(value="/studyActivityDataList",method=RequestMethod.GET)
	public String studyActivityDataList(RedirectAttributes redirectAttributes ,ModelMap model,HttpServletRequest request,HttpServletResponse response  ) throws JsonProcessingException {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		
		List<StudyActivityData> studyActList=defaultActivitysService.getStudyActivityDataList();
		ObjectMapper mapper = new ObjectMapper();
	      //Converting the Object to JSONString
	  //  String jsonactdata = mapper.writeValueAsString(studyActList);
	  //  System.out.println(jsonactdata);
		model.addAttribute("studyActList", studyActList);
		//model.addAttribute("jsonactdata", jsonactdata);
		
		
		return "studyActivityDataList";
		
	}
	@RequestMapping(value="/studyActivityDataDetails/{id}",method=RequestMethod.GET)
	public String studyActivityDataDetails(@PathVariable ("id") long id, RedirectAttributes redirectAttributes ,ModelMap model,HttpServletRequest request,HttpServletResponse response  ) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		List<StudyCheckInActivityDataDetails> studyCheck=null;
		List<StudyCheckOutActivityDataDetails> studyCheckout=null;
		List<StudyExecutionActivityDataDetails> studyExecution=null;
		StudyActivityData saData=defaultActivitysService.getStudyActivityDataWithId(id);
		 studyCheck=defaultActivitysService.getStudyCheckInActivityDataDetailsWithIdList(id);
		 model.addAttribute("sadDetails", studyCheck);
		 if(studyCheck==null) {
			 studyCheckout=defaultActivitysService.getStudyCheckOutActivityDataDetailsWithIdList(id);
			 model.addAttribute("sadDetails", studyCheckout);
			 if(studyCheckout==null) {
				 studyExecution=defaultActivitysService.getStudyExecutionActivityDataDetailsWithIdList(id); 
				 model.addAttribute("sadDetails", studyCheckout);
			 }
		 }
		 model.addAttribute("saData", saData);
		 
		return "studyActivityDataDetails";
		
	}
	@RequestMapping(value="/studyActivityDataApproval/{id}",method=RequestMethod.GET)
	public String studyActivityDataApproval(@PathVariable ("id") long id, RedirectAttributes redirectAttributes ,ModelMap model,HttpServletRequest request,HttpServletResponse response  ) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		String username=request.getSession().getAttribute("userName").toString();
		UserMaster uspojo = userService.findByUsername(username);
		StudyActivityData saData=defaultActivitysService.getStudyActivityDataWithId(id);
		boolean exit=defaultActivitysService.getStudyActivityDataDetailsDiscrepancyCheck(saData);
		if(exit) {
			String accept="ACCEPT";
			DraftReviewStage drs=defaultActivitysService.getDraftReviewStageWithFromRoleAndActionName(uspojo.getRole().getId(),accept);
			boolean flag=false;
			boolean flag2=false;
			if(drs!=null) {
					if(drs.getToRole()==null) {
					saData.setStatus("approval");
					flag=defaultActivitysService.updateStudyActivityData(saData);
				}else {
					flag=true;
				}
				StudyActivityDataReviewAudit sadra=new StudyActivityDataReviewAudit();
				sadra.setReviewState(drs.getId());
				sadra.setRole(uspojo.getRole());
				sadra.setDateOfActivity(new Date());
				sadra.setComment("");
				sadra.setStudyActivityDataId(saData);
				sadra.setUser(uspojo);
				flag2=defaultActivitysService.saveStudyActivityDataReviewAudit(sadra);
			}
			 Locale locale = LocaleContextHolder.getLocale();
			 String data2=messageSource.getMessage("label.studyActivityDataApprovalPass", null,locale);
			 String data3=messageSource.getMessage("label.studyActivityDataApprovalFail", null,locale);
			 if(flag && flag2) {
				 String success_Msg =messageSource.getMessage("label.success", null,locale);
					redirectAttributes.addFlashAttribute("success", success_Msg);

				 redirectAttributes.addFlashAttribute("pageMessage", data2);
			 }else {
				 String success_Msg =messageSource.getMessage("label.success", null,locale);
					redirectAttributes.addFlashAttribute("success", success_Msg);

				 redirectAttributes.addFlashAttribute("pageMessage", data3);
			 }
			 return "redirect:/defaultActivity/studyActivityDataList";
		}else {
			 Locale locale = LocaleContextHolder.getLocale();
			 String data2=messageSource.getMessage("label.sadDiscrepancyExit", null,locale);
			redirectAttributes.addFlashAttribute("pageError", data2);
			String error_Msg =messageSource.getMessage("label.faild", null,locale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			return "redirect:/defaultActivity/studyActivityDataList";
		}
		
		 
		 
		
	}
	@RequestMapping(value="/studyActivityDataComments/{id}",method=RequestMethod.GET)
	public String studyActivityDataComments(@PathVariable ("id") long id, RedirectAttributes redirectAttributes ,ModelMap model,HttpServletRequest request,HttpServletResponse response  ) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		String username=request.getSession().getAttribute("userName").toString();
		UserMaster uspojo = userService.findByUsername(username);
		StudyActivityData saData=defaultActivitysService.getStudyActivityDataWithId(id);
		boolean exit=defaultActivitysService.getStudyActivityDataDetailsDiscrepancyCheck(saData);
		if(!exit) {
			String accept="SENDCOMMENT";
			DraftReviewStage drs=defaultActivitysService.getDraftReviewStageWithFromRoleAndActionName(uspojo.getRole().getId(),accept);
			
			boolean flag2=false;
			if(drs!=null) {
				saData.setStatus("sendcomment");
				
				StudyActivityDataReviewAudit sadra=new StudyActivityDataReviewAudit();
				sadra.setReviewState(drs.getId());
				sadra.setRole(uspojo.getRole());
				sadra.setDateOfActivity(new Date());
				sadra.setComment("");
				sadra.setStudyActivityDataId(saData);
				sadra.setUser(uspojo);
				flag2=defaultActivitysService.saveStudyActivityDataReviewAudit(sadra);
			 }
			 Locale locale = LocaleContextHolder.getLocale();
			 String data2=messageSource.getMessage("label.studyActivityDataSendCommentPass", null,locale);
			 String data3=messageSource.getMessage("label.studyActivityDataSendCommentFail", null,locale);
			 if(flag2) {
				 redirectAttributes.addFlashAttribute("pageMessage", data2);
				 String success_Msg =messageSource.getMessage("label.success", null,locale);
					redirectAttributes.addFlashAttribute("success", success_Msg);
	
			 }else {
				 redirectAttributes.addFlashAttribute("pageError", data3);
				 String error_Msg =messageSource.getMessage("label.faild", null,locale);
					redirectAttributes.addFlashAttribute("error", error_Msg);
			 }
		}else {
			 Locale locale = LocaleContextHolder.getLocale();
			 String data2=messageSource.getMessage("label.studyActivityDataDiscrepancyNeed", null,locale);
			 redirectAttributes.addFlashAttribute("pageMessage", data2);
			 String success_Msg =messageSource.getMessage("label.success", null,locale);
				redirectAttributes.addFlashAttribute("success", success_Msg);
		}
		 return "redirect:/defaultActivity/studyActivityDataList";
		
	}
	@RequestMapping(value="/studyActivityDataDetailsDiscrepancy/{jsonData}")
	public @ResponseBody String studyActivityDataDetailsDiscrepancy(HttpServletRequest request, 
			ModelMap model,RedirectAttributes redirectAttributes,@PathVariable("jsonData") String jsonData) throws IOException {
		String username = request.getSession().getAttribute("userName").toString();
	    ObjectMapper mapper = new ObjectMapper();	
	   // String jsonString2 = "{\"projectsDetailsId\":\"1\", \"comments\":\"comm\"}";
	    DataCheckKeyVal prod = mapper.readValue(jsonData, DataCheckKeyVal.class);
		long id=prod.getStudyActivityDataId();
		long did=prod.getStudyActivityDataDetailsId();
		String comm=prod.getComments();
		boolean flag=false;
		StudyActivityData saData=defaultActivitysService.getStudyActivityDataWithId(id);
		DefaultActivitys dapojo=defaultActivitysService.getPahsesWithSAD_Id(saData);
		String value="";
		GlobalValues gvalue=null;
		if(dapojo.getStudyPhases().getCode().equals("SCI")) {
			StudyCheckInActivityDataDetails studyCheckin=defaultActivitysService.getStudyCheckInActivityDataDetailsWithSadIdAndSaddId(id,did);
			if(studyCheckin.getGlobalValues()!=null) {
				gvalue=studyCheckin.getGlobalValues();
			}
			value=studyCheckin.getValue();
		}
		if(dapojo.getStudyPhases().getCode().equals("SCO")) {
			StudyCheckOutActivityDataDetails studyCheckin=defaultActivitysService.getStudyCheckOutActivityDataDetailsWithSadIdAndSaddId(id,did);
			if(studyCheckin.getGlobalValues()!=null) {
				gvalue=studyCheckin.getGlobalValues();
			}
			value=studyCheckin.getValue();
		}
        if(dapojo.getStudyPhases().getCode().equals("SCE")) {
        	StudyExecutionActivityDataDetails studyCheckin=defaultActivitysService.getStudyExecutionActivityDataDetailsWithSadIdAndSaddId(id,did);
			if(studyCheckin.getGlobalValues()!=null) {
				gvalue=studyCheckin.getGlobalValues();
			}
			value=studyCheckin.getValue();
		}
		
		StudyActivityDataDetailsDiscrepancy sdpojo=new StudyActivityDataDetailsDiscrepancy();
		sdpojo.setComments(comm);
		sdpojo.setStudyActionData(saData);
		sdpojo.setResponse("");
		sdpojo.setStatus("open");
		
		sdpojo.setCreatedBy(username);
		sdpojo.setCreatedOn(new Date());
		
		flag=defaultActivitysService.saveDiscrepancy(sdpojo);
		
		 Locale locale = LocaleContextHolder.getLocale();
		 String data2=messageSource.getMessage("label.sadDiscrepancySavePass", null,locale);
		 String data3=messageSource.getMessage("label.sadDiscrepancySaveFail", null,locale);
		 if(flag)
	             return "{\"Success\": true,\"Message\":" +data2 + "}";
	        else
	        	return "{\"Success\": false,\"Message\":\"" + data3 + "\"}";
	}
	@RequestMapping(value="/studyActivityDataDetailsDiscrepancyClose/{jsonData}")
	public @ResponseBody String studyActivityDataDetailsDiscrepancyClose(HttpServletRequest request, 
			ModelMap model,RedirectAttributes redirectAttributes,@PathVariable("jsonData") String jsonData) throws IOException {
		String username = request.getSession().getAttribute("userName").toString();
	    ObjectMapper mapper = new ObjectMapper();	
	   // String jsonString2 = "{\"projectsDetailsId\":\"1\", \"comments\":\"comm\"}";
	    boolean flag=false;
	    DataCheckKeyVal prod = mapper.readValue(jsonData, DataCheckKeyVal.class);
		long id=prod.getDiscrepancyId();
		String reponce=prod.getResponse();
		String newValue=prod.getValue();
		StudyActivityDataDetailsDiscrepancy saddd=defaultActivitysService.getStudyActivityDataDetailsDiscrepancyWithId(id);
		saddd.setResponse(reponce);
		saddd.setUpdateBy(username);
		saddd.setUpdateOn(new Date());
		saddd.setStatus("close");
		
		
		flag=defaultActivitysService.updateDiscrepancy(saddd);
		
		 Locale locale = LocaleContextHolder.getLocale();
		 String data2=messageSource.getMessage("label.sadDiscrepancyClosePass", null,locale);
		 String data3=messageSource.getMessage("label.sadDiscrepancyCloseFail", null,locale);
		 if(flag)
	             return "{\"Success\": true,\"Message\":" +data2 + "}";
	        else
	        	return "{\"Success\": false,\"Message\":\"" + data3 + "\"}";
	}
	//sadid =StudyActivityData
	//saddid =StudyActivityDataDetails
	@RequestMapping(value="/studyActivityDataDetailsDiscrepancyOpenView/{sadid}/{saddid}",method=RequestMethod.GET)
	public String studyActivityDataDetailsViewForDiscrepancyOpen(HttpServletRequest request, 
			ModelMap model,RedirectAttributes redirectAttributes,@PathVariable("id") long id,@PathVariable("saddid") long did) throws IOException {
		
		if(id>0 &&did>0) {
			String username = request.getSession().getAttribute("userName").toString();
			StudyActivityData saData=defaultActivitysService.getStudyActivityDataWithId(id);
			DefaultActivitys dapojo=defaultActivitysService.getPahsesWithSAD_Id(saData);
			String value="";
			GlobalValues gvalue=null;
			if(dapojo.getStudyPhases().getCode().equals("SCI")) {
				StudyCheckInActivityDataDetails studyCheckin=defaultActivitysService.getStudyCheckInActivityDataDetailsWithSadIdAndSaddId(id,did);
				if(studyCheckin.getGlobalValues()!=null) {
					gvalue=studyCheckin.getGlobalValues();
				}
				value=studyCheckin.getValue();
			}
			if(dapojo.getStudyPhases().getCode().equals("SCO")) {
				StudyCheckOutActivityDataDetails studyCheckin=defaultActivitysService.getStudyCheckOutActivityDataDetailsWithSadIdAndSaddId(id,did);
				if(studyCheckin.getGlobalValues()!=null) {
					gvalue=studyCheckin.getGlobalValues();
				}
				value=studyCheckin.getValue();
			}
	        if(dapojo.getStudyPhases().getCode().equals("SCE")) {
	        	StudyExecutionActivityDataDetails studyCheckin=defaultActivitysService.getStudyExecutionActivityDataDetailsWithSadIdAndSaddId(id,did);
				if(studyCheckin.getGlobalValues()!=null) {
					gvalue=studyCheckin.getGlobalValues();
				}
				value=studyCheckin.getValue();
			}
	        LanguageSpecificValueDetails langSpecific=null;
	        if(gvalue!=null) {
	        	 Locale locale = LocaleContextHolder.getLocale();
	   	         String lang=locale.getCountry();
	   	         InternationalizaionLanguages lanuage=defaultActivitysService.getInternationalizaionLanguagesWithCountryCode(lang);
	   	         langSpecific=defaultActivitysService.getLanguageSpecificValueDetail(gvalue,lanuage);
	        }
	        
	        ObjectMapper mapper = new ObjectMapper();
		    String jsonSAD = mapper.writeValueAsString(saData);
		    String jsonLangSecific = mapper.writeValueAsString(langSpecific);
		    
		//    String jsonSAD = mapper.writeValueAsString(saData);
			model.addAttribute("jsonSAD", jsonSAD);
			model.addAttribute("jsonLangSecific", jsonLangSecific);
			model.addAttribute("value", value);
		}
		
		
		
		
		//This data add tiles page after complet
		return "discrepancyOpenView";
	    
	}
	//sadid =StudyActivityData
	//saddid =StudyActivityDataDetails
	//discrepancyid=StudyActivityDataDetailsDiscrepancy
	@RequestMapping(value="/studyActivityDataDetailsDiscrepancyCloseView/{sadid}/{saddid}/{discrepancyid}",method=RequestMethod.GET)
	public String studyActivityDataDetailsDiscrepancyCloseView(HttpServletRequest request, 
			ModelMap model,RedirectAttributes redirectAttributes,@PathVariable("id") long id,@PathVariable("saddid") long did) throws IOException {
		
		if(id>0 &&did>0) {
			String username = request.getSession().getAttribute("userName").toString();
			StudyActivityData saData=defaultActivitysService.getStudyActivityDataWithId(id);
			DefaultActivitys dapojo=defaultActivitysService.getPahsesWithSAD_Id(saData);
			String value="";
			GlobalValues gvalue=null;
			if(dapojo.getStudyPhases().getCode().equals("SCI")) {
				StudyCheckInActivityDataDetails studyCheckin=defaultActivitysService.getStudyCheckInActivityDataDetailsWithSadIdAndSaddId(id,did);
				if(studyCheckin.getGlobalValues()!=null) {
					gvalue=studyCheckin.getGlobalValues();
				}
				value=studyCheckin.getValue();
			}
			if(dapojo.getStudyPhases().getCode().equals("SCO")) {
				StudyCheckOutActivityDataDetails studyCheckin=defaultActivitysService.getStudyCheckOutActivityDataDetailsWithSadIdAndSaddId(id,did);
				if(studyCheckin.getGlobalValues()!=null) {
					gvalue=studyCheckin.getGlobalValues();
				}
				value=studyCheckin.getValue();
			}
	        if(dapojo.getStudyPhases().getCode().equals("SCE")) {
	        	StudyExecutionActivityDataDetails studyCheckin=defaultActivitysService.getStudyExecutionActivityDataDetailsWithSadIdAndSaddId(id,did);
				if(studyCheckin.getGlobalValues()!=null) {
					gvalue=studyCheckin.getGlobalValues();
				}
				value=studyCheckin.getValue();
			}
	        LanguageSpecificValueDetails langSpecific=null;
	        if(gvalue!=null) {
	        	 Locale locale = LocaleContextHolder.getLocale();
	   	         String lang=locale.getCountry();
	   	         InternationalizaionLanguages lanuage=defaultActivitysService.getInternationalizaionLanguagesWithCountryCode(lang);
	   	         langSpecific=defaultActivitysService.getLanguageSpecificValueDetail(gvalue,lanuage);
	        }
	        StudyActivityDataDetailsDiscrepancy saddd=defaultActivitysService.getStudyActivityDataDetailsDiscrepancyWithId(id);
	        ObjectMapper mapper = new ObjectMapper();
		    String jsonSAD = mapper.writeValueAsString(saData);
		    String jsonLangSpecific = mapper.writeValueAsString(langSpecific);
		    String jsonDiscrepancy = mapper.writeValueAsString(saddd);
		    
		//    String jsonSAD = mapper.writeValueAsString(saData);
			model.addAttribute("jsonSAD", jsonSAD);
			model.addAttribute("jsonLangSpecific", jsonLangSpecific);
			model.addAttribute("value", value);
			model.addAttribute("jsonDiscrepancy", jsonDiscrepancy);
			model.addAttribute("value", value);
		}
		
		
		
		
		//This data add tiles page after complet
		return "discrepancyCloseView";
	    
	}
	@RequestMapping(value="/activityCheckExitOrNot/{id}",method=RequestMethod.GET)
	public @ResponseBody String activityCheckExitOrNot(ModelMap model,@PathVariable("id") long id,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) {
		String checkValue="No";
		DefaultActivitys daPojo=null;
		         daPojo=defaultActivitysService.getDefaultActivitysWithGlobalActivity(id);
		if(daPojo==null) {
			checkValue="Yes";
		}
		return checkValue;
		
	}
	
}
