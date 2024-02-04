package com.covideinfo.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.core.internal.resources.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.dao.RandomizationDao;
import com.covideinfo.dao.StudyDesignDao;
import com.covideinfo.dto.RandomizationViewDto;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.ProjectDetailsRandomization;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.RandomizationFileStatus;
import com.covideinfo.model.RandomizationReviewAudit;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.RandomizationService;
import com.covideinfo.service.UserService;

@Controller
@RequestMapping("/randomization")
public class RandomizationController {
	
	@Autowired
	RandomizationService randomizationService;
	@Autowired  
	MessageSource messageSource;
	@Autowired
	UserService userService;
	@Autowired
	StudyDesignDao studyDesignDao;
	@Autowired
	RandomizationDao randomizationDao;
	@Autowired
    private Environment environment;
	
	
	
	
	//Randomization Form
	@RequestMapping(value="/uploadRandomizationForm",method=RequestMethod.GET)
	public String uploadRandomizationForm(ModelMap model,HttpServletRequest request,HttpServletResponse response) {
		String userRole = request.getSession().getAttribute("userRole").toString();
		String roleId = request.getSession().getAttribute("roleId").toString();
		Long rid =Long.parseLong(roleId);
		
		String dateFormat =environment.getRequiredProperty("clinicalDateTimeFormat");
		List<Projects> proList=randomizationService.getProjectForRandomization();
		
		List<Projects> proListShow=null;
		proListShow=randomizationService.getProjectListOnlySubjetcsAndPeriodsAndRandomizationInputCompletedProjects();
		
		DraftReviewStage firstRow=null;
		firstRow=randomizationService.getRandomizationReviewStageFirsyRow(rid);
		
		model.addAttribute("proList", proList);
		model.addAttribute("userRole", userRole);
		if(firstRow!=null) {
		model.addAttribute("firstRow", firstRow.getFromRole().getRole());
		}else {
		model.addAttribute("firstRow", null);
		}
		model.addAttribute("proListShow", proListShow);
		return "uploadRandomizationForm"; 
		
	}
	//Randomization file data view
		@RequestMapping(value="/randomizationApprovalList",method=RequestMethod.GET)
		public String randomizationApprovalList(ModelMap model,HttpServletRequest request,HttpServletResponse response) {
			String userRole = request.getSession().getAttribute("userRole").toString();
			List<Projects> proList=randomizationService.getProjectForRandomization();
			
			model.addAttribute("proList", proList);
			
			return "randomizationApprovalList"; 
			
		}
	@RequestMapping(value="/saveUploadRandomization",method=RequestMethod.POST)
	public String saveUploadRandomization(@RequestParam CommonsMultipartFile file,@RequestParam ("commentsf") String commentsf, HttpSession session,@RequestParam ("projectNoVal") Long projectNoVal,ModelMap model,HttpServletRequest request,HttpServletResponse response,
			RedirectAttributes redirectAttributes ) throws IOException {
		//String projectNo="project1";
		Locale currentLocale = LocaleContextHolder.getLocale();
		String username = request.getSession().getAttribute("userName").toString();
		String msg=randomizationService.saveUploadRandomization(file,session,projectNoVal,username,commentsf);
		
		if(msg=="success") {
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			String smsg = messageSource.getMessage("label.randomizationSuccess", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", smsg);
		}else if(msg=="noofSubjectNotMatch") {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String smsg = messageSource.getMessage("label.randomizationNoofSubjectNotMatch", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError",smsg);
		}
		else if(msg=="noofPeriodNotMatch") {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String smsg = messageSource.getMessage("label.randomizationNoofPeriodNotMatch", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", smsg);
		}
		else if(msg=="subjectOrdeOrCodeType") {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String smsg = messageSource.getMessage("label.subjectOrdeOrCodeType", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", smsg);
		}
		else if(msg=="reviewStageCheck") {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String smsg = messageSource.getMessage("label.reviewStageCheck", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", smsg);
		}
		else if(msg=="randomizationCodeCheck") {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String smsg = messageSource.getMessage("label.randomizationCodeCheck", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", smsg);
		}
		else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String smsg = messageSource.getMessage("label.randomizationError", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", smsg);
		}
		return "redirect:/randomization/uploadRandomizationForm"; 
		
	}
	
	//Randomization Approval View
		@RequestMapping(value="/randomizationApprovalView/{id}",method=RequestMethod.GET)
		public String randomizationApprovalView(@PathVariable ("id") Long id,ModelMap model,HttpServletRequest request,HttpServletResponse response,
				RedirectAttributes redirectAttributes ) {
			String userRole = request.getSession().getAttribute("userRole").toString();
			String roleId = request.getSession().getAttribute("roleId").toString();
			Long rid =Long.parseLong(roleId);
			List<ProjectDetailsRandomization> randomList=null;
			 randomList=randomizationService.getProjectDetailsRandomizationWithProjectId(id);
			List<RandomizationReviewAudit> rraList=randomizationService.getRandomizationReviewAuditWithProjectId(id);
			int size=rraList.size();
			DraftReviewStage firstRow=null;
			firstRow=randomizationService.getRandomizationReviewStageFirsyRow(rid);
			RandomizationReviewAudit rrapojo=null;
			if(size>0) {
				 rrapojo=rraList.get(size-1);
			}
				
			DraftReviewStage rrspojo=null;
		    String toRole=null;
		    if(rrapojo!=null) {
		    rrspojo=randomizationService.getRandomizationReviewStageWithreviewState(rrapojo.getReviewState());
		    if(rrspojo.getToRole()!=null) {
		    	toRole=rrspojo.getToRole().getRole();
			 }
		    }
		    List<String> periodname=new ArrayList<>();
		    for(ProjectDetailsRandomization add:randomList) {
				 String subj="01";
				 if(add.getSubjectNo().equals(subj)) {
					 periodname.add(add.getPeriod());
				 }
			 }
			 List<RandomizationViewDto> ramList=new ArrayList<>();
			 List<String> subno=new ArrayList<>();
			 for(ProjectDetailsRandomization add:randomList) {
				 RandomizationViewDto pojo=new RandomizationViewDto();
				 pojo.setSubjectNo(add.getSubjectNo());
				 pojo.setProjectNo(add.getProjectNo());
				 if(!subno.contains(add.getSubjectNo())) {
					 ramList.add(pojo);
					 subno.add(pojo.getSubjectNo());
				 }
			 }
			for(RandomizationViewDto data:ramList) {
				List<ProjectDetailsRandomization> plist=randomizationService.getProjectDetailsRandomizationForSplit(data);
				 List<String> list=new ArrayList<String>();
				 List<String> list2=new ArrayList<String>();
				for(ProjectDetailsRandomization pddata:plist) {
					list.add(pddata.getPeriod());
					list2.add(pddata.getRandomizationCode());
				}
				data.setPeriod(list);
				data.setRandomizationCode(list2);
			}
			ProjectsDetails pojo=randomizationService.getProjectsDetailsWithNoofPeriods(id);
		    
			model.addAttribute("randomList", randomList);
			model.addAttribute("rraList", rraList);
			model.addAttribute("rrapojo", rrapojo);
			model.addAttribute("userRole", userRole);
			model.addAttribute("toRole", toRole);
			model.addAttribute("projectId", id);
			model.addAttribute("rrspojo", rrspojo);
			if(firstRow!=null) {
			model.addAttribute("firstRow", firstRow.getFromRole().getRole());
			}else {
			model.addAttribute("firstRow","");
			}
			model.addAttribute("ramList", ramList);
			model.addAttribute("psize", pojo.getFieldValue());
			model.addAttribute("periodname", periodname);
			return "randomizationApprovalView";
			
		}
	@RequestMapping(value="/randomizationApproval",method=RequestMethod.POST)
	public String randomizationApproval(@RequestParam ("projectId") Long id,@RequestParam ("approvalType") String approvalType,@RequestParam ("commentsval") String commentsval,
			@RequestParam ("reviewState") Long reviewState,ModelMap model,HttpServletRequest request,HttpServletResponse response,
			RedirectAttributes redirectAttributes ) {
		boolean flag=false;
		String username = request.getSession().getAttribute("userName").toString();
		String userRole = request.getSession().getAttribute("userRole").toString();
		UserMaster checkLoginUser = userService.findByUsername(username);
		DraftReviewStage rrspojo=randomizationService.getRandomizationReviewStageWithreviewState(reviewState);
		/*List<RandomizationReviewAudit> rraList=randomizationService.getRandomizationReviewAuditWithProjectId(id);
		RandomizationReviewAudit pojo=null;
		int size=rraList.size();
		DraftReviewStage stage=null;
		pojo=rraList.get(size-1);
		if(size>0) {
			if(!pojo.getStatus().equals("IN APPROVAL")) {
			if(size>=2) {
				stage=randomizationService.getRandomizationReviewStageWithTypeFromRole(rrspojo.getToRole(),approvalType,reviewState+2);
			}else {
				stage=randomizationService.getRandomizationReviewStageWithTypeFromRole(rrspojo.getToRole(),approvalType,reviewState+1);
			}
			}else {
				if(approvalType.equals("SEND COMMENT")) {
				stage=randomizationService.getRandomizationReviewStageWithTypeFromRole(rrspojo.getToRole(),approvalType,reviewState+2);
				}else {
					stage=randomizationService.getRandomizationReviewStageWithTypeFromRole(rrspojo.getToRole(),approvalType,reviewState+1);
				}
			}
		}*/
		DraftReviewStage stage=null;
		stage=randomizationService.getRandomizationReviewStageWithTypeAndFromRole(rrspojo.getToRole(),approvalType);
		Projects poject=randomizationService.getProjectsWithProjectId(id);
		Locale currentLocale = LocaleContextHolder.getLocale();

		if(stage!=null) {
	   	if(stage.getToRole()!=null) {
	   		//StudyMaster sm=randomizationDao.getStudyMasterWithId(id);
	   		
	   		StatusMaster statusm=studyDesignDao.getStatusMasterForSubmit(StudyStatus.INREVIEW.toString());
			poject.setRandamizationStatus(statusm);
			poject.setRandamizationRole(stage.getToRole().getId());
			
			RandomizationFileStatus rfsupdate=null;
			rfsupdate=studyDesignDao.getRandomizationFileStatusProWithId(poject.getProjectId());
			rfsupdate.setStatus(statusm);
			
	   	 flag=randomizationService.saveRandomizationApproval(stage,id,checkLoginUser,commentsval,approvalType,poject,rfsupdate);
	   	}else {
	   		StudyMaster sm=randomizationDao.getStudyMasterWithProjectNo(poject.getProjectNo());
	   		if(sm!=null) {
	   			StatusMaster statusm=studyDesignDao.getStatusMasterForSubmit(StudyStatus.APPROVED.toString());
				poject.setRandamizationStatus(statusm);
				long status=0;
				poject.setRandamizationRole(status);
				
				RandomizationFileStatus rfsupdate=null;
    			rfsupdate=studyDesignDao.getRandomizationFileStatusProWithId(poject.getProjectId());
    			rfsupdate.setStatus(statusm);
		   		List<ProjectDetailsRandomization> randomList=randomizationService.getProjectDetailsRandomizationWithStudyId(id);
		   		
		   		flag=randomizationService.saveRandomizationApprovalFinal(stage,id,checkLoginUser,commentsval,approvalType,randomList,poject,sm.getId());
	   		}else {
	   			StatusMaster statusm=studyDesignDao.getStatusMasterForSubmit(StudyStatus.APPROVED.toString());
				poject.setRandamizationStatus(statusm);
				long status=0;
				poject.setRandamizationRole(status);
				
				RandomizationFileStatus rfsupdate=null;
    			rfsupdate=studyDesignDao.getRandomizationFileStatusProWithId(poject.getProjectId());
    			rfsupdate.setStatus(statusm);
    			
		   	    flag=randomizationService.saveRandomizationApproval(stage,id,checkLoginUser,commentsval,approvalType,poject,rfsupdate);
	   		}
			
	   	}
		}else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			redirectAttributes.addFlashAttribute("pageError","Randomization Approval Fail.Please Check");
		}
	   	if(flag) {		
	   		String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			redirectAttributes.addFlashAttribute("pageMessage", "Randomization Approval Successful");
		}else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			redirectAttributes.addFlashAttribute("pageError","Randomization Approval Fail.Please Check");
		}
	   	return "redirect:/randomization/randomizationApprovalList"; 
		
	}
	
	@RequestMapping(value="/randomizationFileView/{id}",method=RequestMethod.GET)
	public String randomizationFileView(@PathVariable ("id") Long id,ModelMap model,HttpServletRequest request,HttpServletResponse response,
			RedirectAttributes redirectAttributes ) {
		List<ProjectDetailsRandomization> randomList=null;
		 randomList=randomizationService.getProjectDetailsRandomizationWithProjectId(id);
		 List<RandomizationViewDto> ramList=new ArrayList<>();
		 List<String> subno=new ArrayList<>();
		 List<String> periodname=new ArrayList<>();
		 for(ProjectDetailsRandomization add:randomList) {
			 String subj="01";
			 if(add.getSubjectNo().equals(subj)) {
				 periodname.add(add.getPeriod());
			 }
		 }
		 String projectNo="";
		 for(ProjectDetailsRandomization add:randomList) {
			 RandomizationViewDto pojo=new RandomizationViewDto();
			 pojo.setSubjectNo(add.getSubjectNo());
			 pojo.setProjectNo(add.getProjectNo());
			 if(!subno.contains(add.getSubjectNo())) {
				 ramList.add(pojo);
				 subno.add(pojo.getSubjectNo());
			 }
		 }
		for(RandomizationViewDto data:ramList) {
			List<ProjectDetailsRandomization> plist=randomizationService.getProjectDetailsRandomizationForSplit(data);
			 List<String> list=new ArrayList<String>();
			 List<String> list2=new ArrayList<String>();
			for(ProjectDetailsRandomization pddata:plist) {
				list.add(pddata.getPeriod());
				list2.add(pddata.getRandomizationCode());
			}
			data.setPeriod(list);
			data.setRandomizationCode(list2);
		}
		List<RandomizationReviewAudit> rraList=randomizationService.getRandomizationReviewAuditWithProjectId(id);
		//Projects project=randomizationService.getProjectsWithProjectId(id);
		//StudyMaster sm=randomizationService.getStudyMasterWithProjectNo(project.getProjectNo());
		ProjectsDetails pojo=randomizationService.getProjectsDetailsWithNoofPeriods(id);
		model.addAttribute("randomList", randomList);
		model.addAttribute("rraList", rraList);
		model.addAttribute("ramList", ramList);
		model.addAttribute("psize", pojo.getFieldValue());
		model.addAttribute("periodname", periodname);
		
		
		
		
		return "randomizationFileView";
	}
}
