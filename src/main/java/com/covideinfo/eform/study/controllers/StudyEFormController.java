package com.covideinfo.eform.study.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.eform.model.EForm;
import com.covideinfo.eform.service.EFormService;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.service.StudyService;

@Controller
@RequestMapping("/buildStdyeform")
@PropertySource(value = { "classpath:application.properties" })
public class StudyEFormController {
	
	@Autowired
	StudyService studyService;
	@Autowired
	EFormService eformService;

	@RequestMapping(value="/uploadStudyeform", method=RequestMethod.GET)
	public String uploadStudyeform(HttpServletRequest request, 
			ModelMap model,
			RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		Long activeStudyId = (Long) request.getSession().getAttribute("activeStudyId");
		StudyMaster sm = studyService.findByStudyId(activeStudyId);
//		List<EForm> eformList = eformService.findAllStudyEFormsUploaded(sm);
//		
//		model.addAttribute("PageHedding", "Upload Study E-Form");
//		model.addAttribute("activeUrl", "buildStdyeform/uploadStudyeform");
//		
//		model.addAttribute("eformList", eformList);
//		model.addAttribute("study", sm);
		return "uploadStudyeform.tiles";

	}
	
}
