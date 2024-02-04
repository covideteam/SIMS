package com.covideinfo.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.dao.RandomizationDao;
import com.covideinfo.model.StudyGroups;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.service.GlobalActivityService;
import com.covideinfo.service.ParameterService;
import com.covideinfo.service.ProjectDesignService;
import com.covideinfo.service.StudyDesignService;
import com.covideinfo.service.StudyGroupsService;
import com.covideinfo.service.UserService;

@Controller
@RequestMapping("/studyGroups")
@PropertySource(value = { "classpath:application.properties" })
public class StudyGroupsController {
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
	
	@Autowired
	StudyGroupsService studyGroupsService;
	/** 
     * This method studyGroupsForm. 
     * @return studyGroupsForm 
     */ 
	
	@RequestMapping(value="/studyGroupsForm", method=RequestMethod.GET)
	public  String studyGroupsForm(HttpServletRequest request, 
			ModelMap model,RedirectAttributes redirectAttributes) throws IOException {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		String dateFormat = (String) request.getSession().getAttribute("dateFormat");
		List<StudyGroups> sgList=studyGroupsService.getStudyGroupsAll();
		List<StudyMaster> stuList=studyGroupsService.getStudyMasterWithActiveOnly(sgList);
		StudyGroups stugs=new StudyGroups();
	    model.addAttribute("stugs", stugs);
	    model.addAttribute("stuList", stuList);
	    model.addAttribute("sgList", sgList);
	    model.addAttribute("dateFormat", dateFormat);
	    
		
		return "studyGroupsPage";
	}
	/** 
     * This method saveStudyGroups. 
     * stugs--> StudyGroups object
     * typev--> Type (creation 0r Update)
     * @return studyGroupsForm 
     */ 
	@RequestMapping(value="/saveOrUpdateStudyGroups", method=RequestMethod.POST)
	public  String saveOrUpdateStudyGroups(@Valid StudyGroups stugs,@RequestParam ("typev") String typev,HttpServletRequest request, 
			ModelMap model,RedirectAttributes redirectAttributes) throws IOException {
		
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		String userName = (String) request.getSession().getAttribute("longinUserName");
		Locale currentLocale = LocaleContextHolder.getLocale();
		if(typev.equals("creation")) {
			if(stugs!=null) {
				boolean flag = studyGroupsService.saveStudyGroups(stugs, userName); 
				if(flag) {
					String gvsuccessMsg =messageSource.getMessage("label.study.success", null,currentLocale);
					redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
					String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
					redirectAttributes.addFlashAttribute("success", success_Msg);
				}else {
					String gvFailMsg =messageSource.getMessage("label.study.failed", null,currentLocale);
					redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
					String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
					redirectAttributes.addFlashAttribute("error", error_Msg);

				}
				}else {
					String gvFailMsg =messageSource.getMessage("label.study.failed", null,currentLocale);
					redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
					String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
					redirectAttributes.addFlashAttribute("error", error_Msg);
				}
		}else if(typev.equals("update")) {
			if(stugs!=null) {
				StudyGroups updatedata=studyGroupsService.getStudyGroupsWithId(stugs.getId());
				updatedata.setNoofSubjects(stugs.getNoofSubjects());
				boolean flag = studyGroupsService.updateStudyGroups(updatedata, userName); 
				if(flag) {
					String gvsuccessMsg =messageSource.getMessage("label.study.successUpdate", null,currentLocale);
					redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
					String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
					redirectAttributes.addFlashAttribute("success", success_Msg);
				}else {
					String gvFailMsg =messageSource.getMessage("label.study.failedUpdate", null,currentLocale);
					redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
					String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
					redirectAttributes.addFlashAttribute("error", error_Msg);

				}
				}else {
					String gvFailMsg =messageSource.getMessage("label.study.failedUpdate", null,currentLocale);
					redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
					String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
					redirectAttributes.addFlashAttribute("error", error_Msg);
				}
		}
		
		return "redirect:/studyGroups/studyGroupsForm";
	}
	/** 
     * This method Available Subject Count Update Time. 
     * proid--> Project Id
     * sgid--> Study Group Id
     * @return count 
     */ 
	@RequestMapping(value="/getAvailableSubjectCount/{proid}/{sgid}",method=RequestMethod.GET)
	public @ResponseBody String  getAvailableSubjectCount(ModelMap model,RedirectAttributes redirectAttributes,
			@PathVariable ("proid") long proid,@PathVariable ("sgid") long sgid) {
		String countva="";
		  if(proid>0) {
			  countva =studyGroupsService.getAvailableSubjectCount(proid,sgid);
		  }
		return countva;
	}
	/** 
     * This method Available Subject Count Creation Time. 
     * proid--> Project Id
     * @return count 
     */ 
	@RequestMapping(value="/getAvailableSubjectCountTwo/{proid}",method=RequestMethod.GET)
	public @ResponseBody String  getAvailableSubjectCountTwo(ModelMap model,RedirectAttributes redirectAttributes,
			@PathVariable ("proid") long proid) {
		String countva="";
		  if(proid>0) {
			  countva =studyGroupsService.getAvailableSubjectCountTwo(proid);
		  }
		return countva;
	}
	/** 
     * This method Study Groups Form. 
     * proid--> Project Id
     * @return Study Groups Form
     */ 
	@RequestMapping(value="/getstudyGroupsListWithStuddyId/{proid}",method=RequestMethod.GET)
	public  String  getstudyGroupsListWithStuddyId(ModelMap model,HttpServletRequest request,RedirectAttributes redirectAttributes,
			@PathVariable ("proid") long proid) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String validationForCount =messageSource.getMessage("label.study.validationForCount", null,currentLocale);
		String internalIssues =messageSource.getMessage("label.study.internalIssues", null,currentLocale);
		
		List<StudyGroups> list= new ArrayList<>();
		  if(proid>0) {
			  list =studyGroupsService.getstudyGroupsListWithStuddyId(proid);
		  }
		  model.addAttribute("sglist", list);
		  model.addAttribute("proidfin", proid);
		  model.addAttribute("validationForCount", validationForCount);
		  model.addAttribute("internalIssues", internalIssues);
		return "pages/studyGroup/studyGroupsForm";
	}
	/** 
     * This method Study Groups Creation Form
     * proid--> Project Id
     * @return Study Groups Creation Form
     */ 
	@RequestMapping(value="/studyGroupsCreation/{proidfin}",method=RequestMethod.GET)
	public  String  studyGroupsCreation(ModelMap model,RedirectAttributes redirectAttributes,
			@PathVariable ("proidfin") long proidfin) {
		StudyGroups stugs=new StudyGroups();
	    model.addAttribute("stugs", stugs);
	    model.addAttribute("proidcr", proidfin);
		return "pages/studyGroup/studyGroupsCreation";
	}
	/** 
     * This method Study Groups Update Form
     * groupid--> Study Group Id
     * @return Study Groups Update Form
     */ 
	@RequestMapping(value="/studyGroupsUpdate/{groupid}",method=RequestMethod.GET)
	public  String  studyGroupsUpdate(ModelMap model,RedirectAttributes redirectAttributes,
			@PathVariable ("groupid") long groupid) {
		StudyGroups stugs=studyGroupsService.getStudyGroupsWithId(groupid);
		
	    model.addAttribute("stugs", stugs);
	    model.addAttribute("proidcr", stugs.getStudy().getId());
		return "pages/studyGroup/studyGroupsCreation";
	}
	/** 
     * This method Study Group Status Change
     * id--> Study Group Id
     * @return Study Group Form
     */
	@RequestMapping(value="/studyGroupsStatusChnage/{id}",method=RequestMethod.GET)
	public  String  studyGroupsStatusChnage(ModelMap model,RedirectAttributes redirectAttributes,
			@PathVariable ("id") long id) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		StudyGroups pojo=null;
		pojo=studyGroupsService.getStudyGroupsWithId(id);
		boolean flag=false;
		if(pojo!=null) {
			flag=studyGroupsService.studyGroupsStatusChnage(pojo);
			if(flag) {
				String gvsuccessMsg =messageSource.getMessage("label.study.successStatusUpdate", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);
			}else {
				String gvFailMsg =messageSource.getMessage("label.study.failedStatusUpdate", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

			}
			}else {
				String gvFailMsg =messageSource.getMessage("label.study.failedStatusUpdate", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);
			}
		
		
		return "redirect:/studyGroups/studyGroupsForm";
	}
	/** 
     * This method Check Condition
     * id--> Study Group Id
     * @return condition value
     */
	@RequestMapping(value="/studyGroupsUpdateCheckValidation/{id}",method=RequestMethod.GET)
	public @ResponseBody String  studyGroupsUpdateCheckValidation(ModelMap model,RedirectAttributes redirectAttributes,
			@PathVariable ("id") long id) {
		String validate="No";
		  if(id>0) {
			  StudyGroups stugs=studyGroupsService.getStudyGroupsWithId(id);
			  List<StudyGroups> chek=studyGroupsService.getStudyGroupsCheckVal(stugs);
			  try {
				  if(chek.size()==0) {
					  validate="Yes";
				  }
			} catch (Exception e) {
				return validate;
			}
		  }
		return validate;
	}
	
	
	
	
}
