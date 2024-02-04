package com.covideinfo.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.covideinfo.dto.MissedTimePointsDto;
import com.covideinfo.service.MissedTpsService;

@RequestMapping("/missedtpsReports")
@Controller
public class MissedTimepointsReportsController {
	
	
	@Autowired
	MissedTpsService missedTpsService;
	
	@Autowired  
	MessageSource messageSource;

	@RequestMapping(value="/generateMissedTimepointsReport", method=RequestMethod.GET)
	public String generateMissedTimepointsReport(ModelMap model) {
		MissedTimePointsDto mtpDto = missedTpsService.getMissedTimePointsDtoDetails();
		model.addAttribute("smList", mtpDto.getSmList());
		return "missedTimePointsPage";
	}
	
	@RequestMapping(value="/getMissedTpsReportDetails/{studyId}", method=RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody MissedTimePointsDto generateMissedTimepointsReport(@PathVariable("studyId")Long studyId, HttpServletRequest request) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String dateStr = (String) request.getSession().getAttribute("clinicalDateTimeFormat");
		return missedTpsService.generateMissedTimepointsReport(studyId, currentLocale, dateStr);
	}
	
	@RequestMapping(value="/generateMealsMenuPdf", method=RequestMethod.POST)
	public StreamingResponseBody generateMealsMenuPdf(@RequestParam("studyId")Long studyId, @RequestParam("periodId")Long periodId,
			@RequestParam("activityCode")String activityCode,HttpServletRequest request, HttpServletResponse response) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		Long langId = (Long) request.getSession().getAttribute("languageId");
		Long userId = (Long) request.getSession().getAttribute("userId");
		String dateStr = (String) request.getSession().getAttribute("clinicalDateTimeFormat");
		String dateStrWithTime = (String) request.getSession().getAttribute("dateTimeFormat");
		String fileName = missedTpsService.generateMealsMenuPdf(studyId, periodId, activityCode, request, response, currentLocale, langId, dateStr, dateStrWithTime, messageSource, userId);
//		if(!fileName.equals("")) {
			File file = new File(fileName);
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=MealMenuReport.pdf");
			response.setContentLength((int) file.length());
			return outputStream -> {
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			};
//		}
		
	}
}
