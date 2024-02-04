package com.covideinfo.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
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

import com.covideinfo.dao.RandomizationDao;
import com.covideinfo.dto.ActvitityDetailsDto;
import com.covideinfo.dto.CommentsDto;
import com.covideinfo.dto.ParameterDto;
import com.covideinfo.dto.ProjectDetailsDto;
import com.covideinfo.dto.ResultDto;
import com.covideinfo.enums.ActionTypes;
import com.covideinfo.enums.StatusType;
import com.covideinfo.enums.StudyActivities;
import com.covideinfo.model.ActivityDraftReviewAudit;
import com.covideinfo.model.DiscrepancyModel;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyDiscrepancy;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.dummy.DataKeyVal;
import com.covideinfo.model.dummy.ProjectDetailsKeyVal;
import com.covideinfo.model.dummy.ProjectKeyVal;
import com.covideinfo.service.GlobalActivityService;
import com.covideinfo.service.ParameterService;
import com.covideinfo.service.ProjectDesignService;
import com.covideinfo.service.StudyDesignService;
import com.covideinfo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/studydesign")
@PropertySource(value = { "classpath:application.properties" })
public class StudyDesignController {
	@Autowired
	StudyDesignService studyDesignService;
	@Autowired
	UserService userService;
	@Autowired  
	MessageSource messageSource;
	@Autowired
	GlobalActivityService globalActivity;
	@Autowired
	RandomizationDao randomizationDao;
	@Autowired
	Environment environment;
	@Autowired
	ParameterService parameterService;
	
	@Autowired
	ProjectDesignService projectDesignService;
	
	private ReentrantLock mutex = new ReentrantLock();
	
	
	@RequestMapping(value="/studyDesignPage", method= { RequestMethod.GET , RequestMethod.POST})
	public  String getProjectList(HttpServletRequest request, 
			ModelMap model,RedirectAttributes redirectAttributes) throws IOException {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		long roleid=0;
		long userroleid = (long) request.getSession().getAttribute("roleId");
		List<Long> list=studyDesignService.getDraftReviewStageDraftStage();
		list.add(roleid);
		boolean flag=false;
		if(list.contains(userroleid)) {
			flag=true;
		}
		List<Projects> plist=new ArrayList<>();
		if(flag) {
		 plist=studyDesignService.getProjectsListForDraft(list);
		}
		ObjectMapper mapper = new ObjectMapper();
	    String jsonString = mapper.writeValueAsString(plist);
	    model.addAttribute("plist", plist);
		model.addAttribute("jsonString", jsonString);
		
		return "projectList";
	}
	@RequestMapping(value="/projectApprovalList", method = { RequestMethod.GET, RequestMethod.POST })
	public  String projectApprovalList(HttpServletRequest request, 
			ModelMap model,RedirectAttributes redirectAttributes) throws IOException {
		long roleid=0;
		roleid = (long) request.getSession().getAttribute("roleId");
		List<Long> list=studyDesignService.getDraftReviewStageDraftStage();
		List<Projects> plist=null;
		if(!list.contains(roleid)) {
			if(roleid>0) {
				plist=studyDesignService.getProjectsList(roleid);
			}
		}
		
		
		ObjectMapper mapper = new ObjectMapper();
	    String jsonString = mapper.writeValueAsString(plist);
	    model.addAttribute("plist", plist);
		model.addAttribute("jsonString", jsonString);
		return "projectApprovalList";
	}
	
	/*
	   * To automatically save field wise study design data 
	   * @Param - Project key value object
	   * @Returns - Returns the status of the transaction as response in json format
	*/
	@RequestMapping(value="/autoSave",  method =  RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String autoSave(HttpServletRequest request,ProjectKeyVal prod) {
		mutex.lock();
		try {
			String username = request.getSession().getAttribute("userName").toString();
		    String prod1=prod.getProjectNo();
			String prod7=prod.getProjectId();
			String prod2=prod.getFieldName();
			String prod3=prod.getFieldValue();
			int prod4=prod.getRowNumber();
			int prod5=prod.getSubRowNumber();
			String prod6=prod.getType();
			int prod8=prod.getActiveandinactive();
			
			boolean flag=false;
			boolean flagx=false;
			if(prod7 == null || prod7.equals("")) {
			Projects ppojo=null;
			 ppojo=studyDesignService.getProjectsWithProjectNo(prod1);
			StatusMaster sm=studyDesignService.getStatusMasterForSubmit("DRAFT");
			if(ppojo==null) {
				Projects ps=new Projects();
				ps.setProjectNo(prod1);
				ps.setStatus(sm);
				ps.setCreatedBy(username);
				ps.setCreatedOn(new Date());
				
				ProjectsDetails pdp=new ProjectsDetails();
				pdp.setProjectsId(ps);
				pdp.setFieldName(prod2);
				pdp.setFieldValue(prod3);
				pdp.setRowNo(prod4);
				pdp.setSubRowNo(prod5);
				pdp.setType(prod6);
				pdp.setStatusid(null);
				pdp.setCreatedBy(username);
				pdp.setDisplayValue(prod.getDisplayValue());
				pdp.setCreatedOn(new Date());
				pdp.setTreatmentNo(prod.getTreatmentNo());
				ppojo=ps;
				Long langId = (Long) request.getSession().getAttribute("languageId");
				List<ActvitityDetailsDto> activities =	globalActivity.getDefaultActivities(langId);
				Long[] aIds = new Long[activities.size()];
				Integer i=0;
				for(ActvitityDetailsDto activity : activities) {
					aIds[i] = activity.getActivityId();
					i++;
				}
				
				List<ProjectsDetails> projectDetailsList = new ArrayList<>();
				projectDetailsList.add(pdp);
				
				i=0;
				List<ParameterDto> defaultActivityParameters = parameterService.getActivityDefaultParameters(aIds, langId);
				for(ParameterDto defaultActivityParameter : defaultActivityParameters) {
					ProjectsDetails projectsDetails = new ProjectsDetails();
					projectsDetails.setProjectsId(ps);
					projectsDetails.setFieldName("DefaultParameter");
					projectsDetails.setFieldValue(defaultActivityParameter.getParameterId().toString());
					projectsDetails.setRowNo(i);
					projectsDetails.setSubRowNo(i);
					projectsDetails.setType("DefaultActivity");
					projectsDetails.setStatusid(null);
					projectsDetails.setCreatedBy(username);
					projectsDetails.setDisplayValue(defaultActivityParameter.getActivityId().toString());
					projectsDetails.setCreatedOn(new Date());
					projectsDetails.setTreatmentNo((long)1);
					projectDetailsList.add(projectsDetails);
					i++;
				}
				
				flag= studyDesignService.getAutoSaveData(ps,projectDetailsList);
	    	}else {
	    		ProjectsDetails pd=studyDesignService.getProjectsDetails(ppojo,prod2,prod4,prod5,prod6);
	    		if(pd!=null) {
						if (!prod2.equals("projectNumber")) {
							pd.setFieldValue(prod3);
							pd.setCreatedBy(username);
							pd.setCreatedOn(new Date());
							pd.setTreatmentNo(prod.getTreatmentNo());
							flag = studyDesignService.getUpdateAutoSaveData(pd);
						} else {
							flagx = true;
						}
	
					} else {
						ProjectsDetails pdp = new ProjectsDetails();
						pdp.setProjectsId(ppojo);
						pdp.setFieldName(prod2);
						pdp.setFieldValue(prod3);
						pdp.setRowNo(prod4);
						pdp.setSubRowNo(prod5);
						pdp.setType(prod6);
						pdp.setStatusid(null);
						pdp.setFieldOrder(prod.getFieldOrder());
						pdp.setCreatedBy(username);
						pdp.setCreatedOn(new Date());
						pdp.setDisplayValue(prod.getDisplayValue());
						pdp.setTreatmentNo(prod.getTreatmentNo());
						flag = studyDesignService.getNewAutoSaveData(pdp);
					}
	    	
	    	}
			if(flagx) {
				//String data = getLanguage Data
				 //String data="Already Exists";
				 Locale locale = LocaleContextHolder.getLocale();
				 String data2=messageSource.getMessage("label.projectExists", null,locale);
				//String data2="label.projectExists";
				 return "{\"Success\": false,\"ProjectId\":" + ppojo.getProjectId()+",\"Message\":\"" + data2 + "\"}";
			}else {
				String data3="Saving Failed Please try again.";
				if(flag)
		            //return "{ Success: true,ProjectId:" + projectId +" }";
		             return "{\"Success\": true,\"ProjectId\":" + ppojo.getProjectId() + "}";
		        else
		        	return "{\"Success\": false,\"ProjectId\":" + ppojo.getProjectId()+",\"Message\":\"" + data3 + "\"}";
			}
			
	    	
	    	
			//prod7 not equel empty
			}else {
				long id=Long.parseLong(prod7);
				Projects ppojo=null;
				ppojo=studyDesignService.getProjectsWithProjectId(id);
				ProjectsDetails pd=studyDesignService.getProjectsDetails(id,prod2,prod4,prod5,prod6);
		    	if(pd!=null) {
		    		if(prod2.equals("projectNumber")) {
		    			ppojo.setProjectNo(prod3);
		        		flag= studyDesignService.getUpdateProjectData(ppojo);
		    		}
		    		pd.setFieldValue(prod3);
		    		pd.setDisplayValue(prod.getDisplayValue());
		    		pd.setFieldOrder(prod.getFieldOrder());
		    		pd.setCreatedBy(username);
		    		pd.setCreatedOn(new Date());
		    		pd.setDisplayValue(prod.getDisplayValue());
		    		pd.setTreatmentNo(prod.getTreatmentNo());
		    		flag= studyDesignService.getUpdateAutoSaveData(pd);
		    	}else {
		    		ProjectsDetails pdp=new ProjectsDetails();
		    		pdp.setProjectsId(ppojo);
		    		pdp.setFieldName(prod2);
		    		pdp.setFieldValue(prod3);
		    		pdp.setRowNo(prod4);
		    		pdp.setSubRowNo(prod5);
		    		pdp.setType(prod6);
		    		pdp.setStatusid(null);
		    		pdp.setFieldOrder(prod.getFieldOrder());
		    		pdp.setCreatedBy(username);
		    		pdp.setCreatedOn(new Date());
		    		pdp.setDisplayValue(prod.getDisplayValue());
		    		pdp.setTreatmentNo(prod.getTreatmentNo());
		    		flag= studyDesignService.getNewAutoSaveData(pdp);
		    	 }
		    	String data4="Saving Failed Please try again.";
		    	if(flag)
		            //return "{ Success: true,ProjectId:" + projectId +" }";
		             return "{\"Success\": true,\"ProjectId\":" + ppojo.getProjectId() + "}";
		        else
		        	return "{\"Success\": false,\"ProjectId\":" + ppojo.getProjectId()+",\"Message\":\"" + data4 + "\"}";
		    	
		    	}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			mutex.unlock();
		}
		return "{\"Success\": false,\"ProjectId\":" + "" +",\"Message\":\"" + "Failed to save data." + "\"}";
	}
    
	@RequestMapping(value="/eligibleProjectList", method=RequestMethod.GET)
	public  String eligibleProjectList(HttpServletRequest request, 
			ModelMap model,RedirectAttributes redirectAttributes) throws IOException {
		List<Projects> plist=studyDesignService.getEligibleProjectList();
		ObjectMapper mapper = new ObjectMapper();
	    String jsonString = mapper.writeValueAsString(plist);
	    model.addAttribute("plist", plist);
		model.addAttribute("jsonString", jsonString);
		return "eligibleProjectList";
	}
	
	@RequestMapping(value="/projectView/{projectId}/{lanID}", method=RequestMethod.GET)
	public  String projectView(@PathVariable ("projectId") Long projectId,@PathVariable ("lanID") Long lanID, HttpServletRequest request, 
			ModelMap model,RedirectAttributes redirectAttributes) throws IOException {
		Projects ppojo=studyDesignService.getProjectWithId(projectId);
		String username = request.getSession().getAttribute("userName").toString();
	    ProjectDetailsDto pdlist=studyDesignService.getProjectsDetails(projectId,lanID, username);	
		ObjectMapper mapper = new ObjectMapper();
	    String jsonString = mapper.writeValueAsString(pdlist);
	    System.out.println(jsonString);
		model.addAttribute("pdlist", pdlist);
		model.addAttribute("ppojo", ppojo);	
		model.addAttribute("jsonString", jsonString);
		return "projectViewDetails";
	}
	
	
	@RequestMapping(value="/discrepancy",method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String discrepancy(HttpServletRequest request, CommentsDto keyValue) throws IOException 
	{
		String username = request.getSession().getAttribute("userName").toString();
		StudyDiscrepancy dis = studyDesignService.saveDiscrepancy(keyValue, username);
		return "{\"Success\": true,\"CommentId\":" + dis.getId() + "}";
	}
	
	@ResponseBody
	@RequestMapping(value="/discrepancyModification", method =  RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public void discrepancyModification(HttpServletRequest request, DataKeyVal prod) throws IOException {
		String username = request.getSession().getAttribute("userName").toString();
	    long id=prod.getStudyDiscrepancyId();
		String comm=prod.getComments();
		String val=prod.getNewVal();
		if(id>0) {
			StudyDiscrepancy sdpojo=studyDesignService.getStudyDiscrepancyWithId(id);
			sdpojo.setResponse(comm);
			sdpojo.setStatus("close");
			sdpojo.setUpdateBy(username);
			sdpojo.setUpdateOn(new Date());
			studyDesignService.updateDiscrepancy(sdpojo);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteComment", method =  RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public void deleteComment(HttpServletRequest request, @RequestParam("commentId")Long commentId) throws IOException {
		String username = request.getSession().getAttribute("userName").toString();
		studyDesignService.deleteComment(commentId, username);
	}
	
	@ResponseBody
	@RequestMapping(value="/discrepancySDClose", method =  RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public void discrepancyClose(HttpServletRequest request, DataKeyVal prod) throws IOException {
		String username = request.getSession().getAttribute("userName").toString();
	    long id=prod.getStudyDiscrepancyId();
		if(id>0) {
			StudyDiscrepancy sdpojo=studyDesignService.getStudyDiscrepancyWithId(id);
			sdpojo.setStatus("close");
			studyDesignService.updateDiscrepancy(sdpojo);
		}
	}

	@RequestMapping(value="/submitDraft",  method =  RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String submitDraft(HttpServletRequest request,ProjectDetailsKeyVal prod) throws IOException {
		String username = request.getSession().getAttribute("longinUserName").toString();
		UserMaster checkLoginUser = userService.findByUsername(username);
	    long id=prod.getProjectId();
		//long roleid=prod.getRoleid(); not need
		String actionname=prod.getActionName();
		boolean flag=false;
		if(id>0) {
		//ProjectsDetails pd=studyDesignService.getProjectsDetailsWithpdId(id);
		Projects poject=studyDesignService.getProjectsWithProjectId(id);
		
		DraftReviewStage drs=studyDesignService.getDraftReviewStageWithRoleidAndActionname(checkLoginUser.getRole().getId(),actionname);
	  
		//Projects poject=pd.getProjectsId();
		
		List<StudyDiscrepancy> exit=studyDesignService.getStudyDiscrepancyExitOrNot(poject.getProjectId());
		
		if(exit.size()==0) {
			StatusMaster sm=studyDesignService.getStatusMasterForSubmit("SUBMIT");
			poject.setStatus(sm);
			ActivityDraftReviewAudit adra=new ActivityDraftReviewAudit();
			
			adra.setDateOfActivity(new Date());
			//adra.setProjectId(poject.getProjectId());
			adra.setReviewState(drs.getId());
			adra.setRole(checkLoginUser.getRole());
			adra.setUser(checkLoginUser);
			adra.setProjectId(id);
			adra.setStatus("IN APPROVAL");
			poject.setRoleId(drs.getToRole().getId());
			
			Projects project =studyDesignService.saveActivityDraftReviewAudit(adra,poject);
			
			if(project != null)
				return "{\"Success\": true,\"ProjectId\":" + poject.getProjectId()+",\"Message\":\"" + "Data submitted successfully" + "\"}";
			else
				return "{\"Success\": false,\"ProjectId\":" + poject.getProjectId()+",\"Message\":\"" + "Project Submition Failed. Please Try Again." + "\"}";
		}else {
			return "{\"Success\": false,\"ProjectId\":" + poject.getProjectId()+",\"Message\":\"" + "Please close all descripencies" + "\"}";
		}
		
	   }else {
		   return "{\"Success\": false,\"ProjectId\":" + "" +",\"Message\":\"Project Id Does'nt Exists. Please Try Again." + "" + "\"}";
	  }
	}
	
	@RequestMapping(value="/sendComments", method =  RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResultDto sendCommentsDraft(HttpServletRequest request, ProjectDetailsKeyVal prod) throws IOException {
		ResultDto resultDto = new ResultDto();
		String username = request.getSession().getAttribute("userName").toString();
		long roleId = Long.parseLong(request.getSession().getAttribute("roleId").toString());
		long projectId=prod.getProjectId();
		UserMaster checkLoginUser = userService.findByUsername(username);
		Projects project =studyDesignService.saveActivityDraftReviewAudit(projectId,roleId, ActionTypes.SENDCOMMENTS,checkLoginUser);
		if(project != null) {
			resultDto.setMessage("Comments forwarded successfully");
			resultDto.setSuccess("true");
		}
		else {
			resultDto.setMessage("Issue while sending comments. Please contact administrator.");
			resultDto.setSuccess("false");
		}
		return resultDto;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="/approvalDraft",  method =  RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String approvalDraft(HttpServletRequest request, ProjectDetailsKeyVal prod) throws IOException {
		String username = request.getSession().getAttribute("userName").toString();
		UserMaster checkLoginUser = userService.findByUsername(username);
		Locale currentLocale = LocaleContextHolder.getLocale();
		prod.setActionName("APPROVAL");
		String result = studyDesignService.approveProject(prod, username, messageSource, currentLocale);
		String resultLabel =messageSource.getMessage("label.project.stucces", null,currentLocale);
		if(resultLabel.equals(result)) {
			String successLabel =messageSource.getMessage("label.projectReview.success", null,currentLocale);
			return "{\"Success\": true,\"Message\":\"" +successLabel+"\"}";
		}else {
			String failLabel =messageSource.getMessage("label.project.Fail", null,currentLocale);
			if(resultLabel.equals(failLabel)) {
				String failLabelMessage =messageSource.getMessage("label.projeftReview.failure", null,currentLocale);
				return "{\"Success\": false,\"Message\":\"" +result+"\"}";
			}else 
				return "{\"Success\": false,\"Message\":\"" +result+"\"}";
		}
	}
	
	@RequestMapping(value="/testPage", method=RequestMethod.GET)
	public String testPage(HttpServletRequest request, 
			ModelMap model,
			RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		
		return "testPage.tiles";

	}
	@RequestMapping(value="/studyDesignForm", method=RequestMethod.GET)
	public String studyDesignForm(HttpServletRequest request, 
			ModelMap model,
			RedirectAttributes redirectAttributes) {
		long roleid=0;
		long userroleid = (long) request.getSession().getAttribute("roleId");
		List<Long> list=studyDesignService.getDraftReviewStageDraftStage();
		list.add(roleid);
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		model.addAttribute("review", StatusType.createupdate.toString());
		
		ArrayList<String> activities =new ArrayList<String>();
		activities.add(StudyActivities.SkinSensivity.toString());
		activities.add(StudyActivities.SkinAdhesion.toString());
		activities.add(StudyActivities.InclusionCriteria.toString());
		activities.add(StudyActivities.ExclusionCriteria.toString());
		activities.add(StudyActivities.StudyExecutionVitals.toString());
		activities.add(StudyActivities.DosingCollection.toString());
		
		model.addAttribute("globalActivities", globalActivity.getGlobalActivities(activities));
		Locale currentLocale = LocaleContextHolder.getLocale();

		boolean flag=false;
		if(list.contains(userroleid)) {
			flag=true;
		}
		if(!flag) {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			redirectAttributes.addFlashAttribute("pageError","Access Denied");
			return "redirect:/studydesign/studyDesignPage";
		}else {
			return "studyDesignForm.tiles";
		}
	}
	
	@RequestMapping(value="/projectEdit", method=RequestMethod.POST)
	public String projectEdit(@RequestParam("id") long id,HttpServletRequest request, 
			ModelMap model,
			RedirectAttributes redirectAttributes) throws JsonProcessingException {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		model.addAttribute("psid", id);
		model.addAttribute("review", StatusType.createupdate.toString());
		
		ArrayList<String> activities =new ArrayList<String>();
		activities.add(StudyActivities.SkinSensivity.toString());
		activities.add(StudyActivities.SkinAdhesion.toString());
		activities.add(StudyActivities.InclusionCriteria.toString());
		activities.add(StudyActivities.ExclusionCriteria.toString());
		activities.add(StudyActivities.StudyExecutionVitals.toString());
		activities.add(StudyActivities.DosingCollection.toString());
		
		model.addAttribute("globalActivities", globalActivity.getGlobalActivities(activities));
		
		return "studyDesignForm.tiles";
	}
	
	@RequestMapping(value="/projectReview/{id}", method=RequestMethod.GET)
	public String projectReview(@PathVariable("id") long id,HttpServletRequest request, 
			ModelMap model,
			RedirectAttributes redirectAttributes) throws JsonProcessingException {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		model.addAttribute("psid", id);
		model.addAttribute("review", StatusType.review.toString());
		
		ArrayList<String> activities =new ArrayList<String>();
		activities.add(StudyActivities.SkinSensivity.toString());
		activities.add(StudyActivities.SkinAdhesion.toString());
		activities.add(StudyActivities.InclusionCriteria.toString());
		activities.add(StudyActivities.ExclusionCriteria.toString());
		activities.add(StudyActivities.DosingCollection.toString());
		activities.add(StudyActivities.StudyExecutionVitals.toString());
		
		model.addAttribute("globalActivities", globalActivity.getGlobalActivities(activities));
		
		return "studyDesignForm.tiles";
	}
	
	@RequestMapping(value="/projectInformation/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ProjectDetailsDto projectInformation(HttpServletRequest request, 
			ModelMap model,RedirectAttributes redirectAttributes,@PathVariable("id") long id) throws IOException {
		Long langId = (Long) request.getSession().getAttribute("languageId");
		return  studyDesignService.getProjectsDetails(id, langId, environment.getProperty("dateTimeFormat"));
	}
	
	@RequestMapping(value="/projectCloneForm",method=RequestMethod.GET)
	public String projectCloneForm(ModelMap model,HttpServletRequest request ,HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws JsonProcessingException {
		
		List<Projects> plist=studyDesignService.getProjectAllList();
		ObjectMapper mapper = new ObjectMapper();
	      //Converting the Object to JSONString
	      String jsonPList = mapper.writeValueAsString(plist);
		
		model.addAttribute("jsonPList", jsonPList);
		
		//need to add tiles 
		return "projectCloneForm";
		
	}
	
	@RequestMapping(value="/saveProjectClone",method=RequestMethod.GET)
	public String saveProjectClone(@RequestParam("exitingproject") long exitingproject,@RequestParam("newProjectNo") String newProjectNo, ModelMap model,HttpServletRequest request ,HttpServletResponse response,
			RedirectAttributes redirectAttributes)  {
	/*@RequestMapping(value="/saveProjectClone/{exitingproject}/{newProjectNo}",method=RequestMethod.GET)
	public String saveProjectClone(@PathVariable("exitingproject") long exitingproject,@PathVariable("newProjectNo") String newProjectNo, ModelMap model,HttpServletRequest request ,HttpServletResponse response,
			RedirectAttributes redirectAttributes)  {*/
		String username = request.getSession().getAttribute("userName").toString();
		Projects ppojo=studyDesignService.getProjectsWithProjectId(exitingproject);
		ProjectDetailsDto pdList=studyDesignService.getProjectsDetailsVal(ppojo.getProjectId(), environment.getProperty("dateTimeFormat"));
		Projects ps=new Projects();
		ps.setProjectNo(newProjectNo);
		ps.setBuildStudyStatus(null);
		ps.setStatus(null);
		ps.setCreatedBy(username);
		ps.setCreatedOn(new Date());
		boolean flag=studyDesignService.saveProjectClone(ps, pdList.getProjectDetails(), username);
		Locale currentLocale = LocaleContextHolder.getLocale();
		String successMsg =messageSource.getMessage("label.projectCloning.SuccessMsg", null,currentLocale);
		String failMsg =messageSource.getMessage("label.projectCloning.FailMsg", null,currentLocale);
		if(flag) {
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			redirectAttributes.addFlashAttribute("pageMessage",successMsg);
			//redirectAttributes.addAttribute("pageMessage","Successfully Save");
		}
		else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			redirectAttributes.addFlashAttribute("pageError",failMsg);
			//redirectAttributes.addAttribute("pageError"," Failed Please try again.");
		}
		return "redirect:/studydesign/studyDesignPage";
	}
	
	@RequestMapping(value="/deactivateProjectDetails",method=RequestMethod.POST)
	public void deactivateProjectDetails(HttpServletRequest request ,HttpServletResponse response,
			RedirectAttributes redirectAttributes, ProjectKeyVal keyVal)  {
		studyDesignService.deActivateProjectDetailsData(Long.parseLong(keyVal.getProjectId()), keyVal.getRowNumber(), keyVal.getSubRowNumber(), keyVal.getType());
	}
	
	@RequestMapping(value="/updateOtherActivityParameters",method=RequestMethod.POST)
	public void updateOtherActivityParameters(HttpServletRequest request ,HttpServletResponse response,
			RedirectAttributes redirectAttributes,@PathVariable("projectId") Long projectId, @PathVariable("parameterId") Long parameterId, 
			@PathVariable("activityId") Long activityId,@PathVariable("isDelete") boolean isDelete)  {
		String username = request.getSession().getAttribute("userName").toString();
		studyDesignService.updateOtherActivityParameters(projectId, parameterId, activityId, isDelete, username);
	}
	
	@RequestMapping(value="/discrepencies",method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<DiscrepancyModel> getDescripencies(ProjectKeyVal keyVal) throws Exception  {
		return studyDesignService.getDescrepencies(keyVal, environment);
	}
	
	@RequestMapping(value="/deactivateTreatment",method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public void deactivateTreatment(ProjectKeyVal prod, HttpServletRequest request) throws Exception  {
		String username = request.getSession().getAttribute("userName").toString();
		for(int i = prod.getRowNumber();i > prod.getSubRowNumber();i--) {
			projectDesignService.deActivateTreatment(Long.parseLong(prod.getProjectId()), i, username);	
		}
	}
	
	@RequestMapping(value="/deactivateTypeData",method=RequestMethod.POST)
	@ResponseBody
	public void deactivateTypeData(ProjectKeyVal prod, HttpServletRequest request )  {
		projectDesignService.deactiavateProjectDetailsByType(Long.parseLong(prod.getProjectId()), prod.getType(), request.getSession().getAttribute("userName").toString());
	}
	
	@RequestMapping(value="/addActivityParameter/{projectId}/{actvitiyId}/{parameterId}",method=RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Integer addActivityParameter(@PathVariable ("projectId") long projectId,@PathVariable ("actvitiyId") long actvitiyId,
					@PathVariable ("parameterId") long parameterId, HttpServletRequest request ) throws Exception  {
		String username = request.getSession().getAttribute("userName").toString();
		return studyDesignService.addActivityParameter(projectId, actvitiyId, parameterId, username);
	}
	
	//Project Cloning To New project
	@RequestMapping(value="/getProjectListPopup",method=RequestMethod.GET)
	public String getProjectListPopup(ModelMap model ,HttpServletRequest request ,HttpServletResponse response,
			RedirectAttributes redirectAttributes)  {
		List<Projects> projectList =studyDesignService.getProjectAllList();
		model.addAttribute("projectList", projectList);
		return "pages/studydesign/projectCloningPopup";
	}
	
	@RequestMapping(value="/checkExitOrNotProjectName/{prono}",method=RequestMethod.GET)
	public @ResponseBody String checkExitOrNotProjectName(@PathVariable ("prono") String prono,ModelMap model ,HttpServletRequest request ,HttpServletResponse response,
			RedirectAttributes redirectAttributes)  {
		
		String meg="No";
		List<Projects> exit =studyDesignService.getProjectWithProjectNo(prono);
		try {
			if(exit.size()>0) {
				meg="No";
			}else {
				meg="Yes";
			}
		} catch (Exception e) {
			e.printStackTrace();
			meg="No";
		}
		return meg;
	}
	
	@RequestMapping(value="/projectInformationByType/{projectId}/{type}",produces="application/json")
	public @ResponseBody ProjectDetailsDto projectInformationByType(HttpServletRequest request, 
			ModelMap model,RedirectAttributes redirectAttributes,@PathVariable("projectId") long projectId,@PathVariable("type") String type) throws IOException {
		ProjectDetailsDto projectDetailDto=new ProjectDetailsDto();
		projectDetailDto.setProjectDetails(projectDesignService.getProjectDetailsByType(projectId, type));
		return  projectDetailDto;
	}
}
