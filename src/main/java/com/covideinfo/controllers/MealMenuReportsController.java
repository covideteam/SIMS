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

import com.covideinfo.dto.MealMenuReportsDto;
import com.covideinfo.service.MealMenuReportService;

@RequestMapping("/mealMenuReports")
@Controller
public class MealMenuReportsController {
	
	@Autowired
	MealMenuReportService mealMenuReportService;
	
	@Autowired  
	MessageSource messageSource;
	
	@RequestMapping(value="/generateMealMenuReport", method=RequestMethod.GET)
	public String generateMealMenuReport(ModelMap model) {
		MealMenuReportsDto mmrDto = mealMenuReportService.getMealMenuReportDetails();
		model.addAttribute("smList", mmrDto.getSmList());
		return "mealsReoportPage";
	}
	
	@RequestMapping(value="/getStduyMealReportDetails/{studyId}", method=RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody MealMenuReportsDto getStduyMealReportDetails(@PathVariable("studyId")Long studyId) {
		return mealMenuReportService.getStduyMealReportDetails(studyId);
	}

	@RequestMapping(value="/generateMealsMenuPdf", method=RequestMethod.POST)
	public StreamingResponseBody generateMealsMenuPdf(@RequestParam("studyId")Long studyId, @RequestParam("dietIds")List<Long> dietIdsList,
			@RequestParam("melsReportInfo")List<String> melsReportInfo,HttpServletRequest request, HttpServletResponse response) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		Long langId = (Long) request.getSession().getAttribute("languageId");
		Long userId = (Long) request.getSession().getAttribute("userId");
		String dateStr = (String) request.getSession().getAttribute("clinicalDateTimeFormat");
		String dateStrWithTime = (String) request.getSession().getAttribute("dateTimeFormat");
		String fileName = mealMenuReportService.generateMealsMenuPdf(studyId, dietIdsList, melsReportInfo, request, response, currentLocale, langId, dateStr, dateStrWithTime, messageSource, userId);
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
