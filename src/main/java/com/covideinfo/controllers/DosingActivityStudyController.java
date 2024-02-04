package com.covideinfo.controllers;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.DosingActivityService;
import com.covideinfo.service.StudyService;
import com.covideinfo.service.UserService;

@Controller
@RequestMapping("/dosingActivity")
public class DosingActivityStudyController {
	
	@Autowired
	DosingActivityService dosingActivityService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	StudyService studyService;
	
	@Autowired  
	MessageSource messageSource;
	
	//Dosing Activity For the Study
	@RequestMapping(value="/dosingActivityForm",method=RequestMethod.GET)
	public String getDosingActvityForStudy(HttpServletRequest request,HttpServletResponse response,ModelMap module,
			RedirectAttributes redirectAttributes ) {
		List<StudyMaster> sml=dosingActivityService.getStudyMasterList();
		module.addAttribute("sml", sml);
		return "dosingActivityForStudyForm";
	}

	@RequestMapping(value="/getSujectListOfStudy/{val}",method=RequestMethod.GET)
	public String getSujectListOfStudy(@PathVariable ("val") Long id,HttpServletRequest request,HttpServletResponse response,ModelMap module,
			RedirectAttributes redirectAttributes) {
		List<StudySubjects> ssList=dosingActivityService.getStudySubjects(id);
		StudyMaster smpojo=dosingActivityService.getStudyMasterWithId(id);
		if(ssList.size()>0) {
			module.addAttribute("ssList", ssList);
			module.addAttribute("size", ssList.size());
			module.addAttribute("subjectSize", smpojo.getNoOfSubjects());
			
		}else {
			module.addAttribute("ssList", "");
			module.addAttribute("size", 0);
			module.addAttribute("subjectSize", smpojo.getNoOfSubjects());
		}
		
		
		return "pages/dosing/sujectListView";
		
	}
	@RequestMapping(value="/dosingActivityForStudySave",method=RequestMethod.POST)
	public String dosingActivityForStudySave(@RequestParam ("proid") long proid,HttpServletRequest request,HttpServletResponse response,ModelMap module,
			RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("userName").toString();
		UserMaster user = dosingActivityService.getUserdetails(username);
		StudyMaster sm = dosingActivityService.findByStudyId(proid);
		Locale currentLocale = LocaleContextHolder.getLocale();

		boolean flag=false;
		flag=dosingActivityService.saveStudyGroupForProject(sm,user);
		if(flag) {
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			redirectAttributes.addFlashAttribute("pageMessage", "Study Group Saving Successuflly");
		}else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);
			redirectAttributes.addFlashAttribute("pageError", "Failed To Study Group");
		}
		
		return "redirect:/dosingActivity/dosingActivityForm";
		
	}
	
	
}
