package com.covideinfo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.covideinfo.bo.StudyCreationBo;
import com.covideinfo.dto.BlankPdfDto;
import com.covideinfo.model.Projects;
import com.covideinfo.pdf.report.SinglePdfConversion;
import com.covideinfo.service.ProjectCrfGenerationService;

@Controller
@RequestMapping("/projectCrf")
public class ProjectCrfGenerationController {
	
	@Autowired
	private ProjectCrfGenerationService projectCrfGenService;
	
	@Autowired  
	MessageSource messageSource;
	
	@Autowired
	StudyCreationBo studyCreationBo;
	
	@RequestMapping(value="/projectCrfGenerationPage", method=RequestMethod.GET)
	public String projectCrfGenerationPage(ModelMap model) {
		List<Projects> proList = projectCrfGenService.getProjectsRecords();
		model.addAttribute("proList", proList);
		return "pdfGenerationPage";
	}
	
	@RequestMapping(value="/generateBlankCrf", method=RequestMethod.GET)
	public void generateBlankCrf(@RequestParam("projectNo")Long projectNo, HttpServletRequest request, HttpServletResponse response) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		Long langId = (Long) request.getSession().getAttribute("languageId");
		String userName = (String) request.getSession().getAttribute("longinUserName");
		String dateStr = (String) request.getSession().getAttribute("clinicalDateTimeFormat");
		String dateStrWithTime = (String) request.getSession().getAttribute("dateTimeFormat");
		List<String> finalFilesList = new ArrayList<>();
		List<String> filesList = null;
		try {
			BlankPdfDto bpDto = projectCrfGenService.getBlankPdfDtoDetails(projectNo, userName);
			Projects project = null;
			if(bpDto != null) {
				project = bpDto.getProject();
				List<String> fileList = projectCrfGenService.generateCoverPages(request, currentLocale, messageSource, projectNo, langId, bpDto, dateStr, dateStrWithTime);
				if(fileList != null && fileList.size() > 0) 
					finalFilesList.addAll(fileList);
				String noOfPeriods = bpDto.getNoOfPeriods();
				if(noOfPeriods != null && !noOfPeriods.equals("")) {
					for(int i=1; i<=Integer.parseInt(noOfPeriods); i++) {
						filesList = projectCrfGenService.generateBlankCrf(request, currentLocale, studyCreationBo, i, messageSource, Long.parseLong(noOfPeriods), bpDto, dateStr, langId, dateStrWithTime);
						if(filesList != null && filesList.size() > 0) 
							finalFilesList.addAll(filesList);
					}
				}
			}
			if(finalFilesList != null && finalFilesList.size() > 0) {
				SinglePdfConversion gsp = new SinglePdfConversion();
				gsp.convertSinglePdf(request, response, finalFilesList, project, messageSource, currentLocale);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
}
