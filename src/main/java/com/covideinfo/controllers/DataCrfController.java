package com.covideinfo.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.covideinfo.bo.StudyCreationBo;
import com.covideinfo.datCrf.MergeDataCrfsIntoSinglePage;
import com.covideinfo.dto.DataCrfDetailsPageDto;
import com.covideinfo.dto.DataCrfDtoDetails;
import com.covideinfo.service.DataCrfService;

@RequestMapping("/crfData")
@Controller
public class DataCrfController {
	
	
	@Autowired
	DataCrfService dataCrfService;
	
	
	@Autowired  
	MessageSource messageSource;
	
	@Autowired
	StudyCreationBo studyCreationBo;
	
	
	@RequestMapping(value="/studyCrfData", method=RequestMethod.GET)
	public String studyCrfDataPage(ModelMap model) {
		DataCrfDetailsPageDto dcpDto = dataCrfService.getDataCrfDetailsPageDtoDetails();
		model.addAttribute("dcpDto", dcpDto);
		return "studyCrfDataPage";
	}
	
	@RequestMapping(value="/getStudyCrfDataDetails/{studyId}", method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody DataCrfDetailsPageDto getStudyCrfDataDetails(@PathVariable("studyId")Long studyId, HttpServletRequest request) {
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		return dataCrfService.getStudyCrfDataDetails(studyId, languageId);
		
	}
	@RequestMapping(value="/generateDataCrf", method=RequestMethod.GET)
	public void generateDataCrf(@RequestParam("studyId")Long studyId,@RequestParam("periodId")Long periodId, @RequestParam("subjectId")Long subjectId, 
			@RequestParam("activityId")Long activityId, @RequestParam("reportType")String reportType,@RequestParam("volIds")List<Long> volIds, HttpServletRequest request, HttpServletResponse response) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		Long langId = (Long) request.getSession().getAttribute("languageId");
		Long userId = (Long) request.getSession().getAttribute("userId");
		String dateStr = (String) request.getSession().getAttribute("clinicalDateTimeFormat");
		String dateStrWithTime = (String) request.getSession().getAttribute("dateTimeFormat");
		List<String> finalFilesList = new ArrayList<>();
		List<String> filesList = null;
		DataCrfDtoDetails dcdDto = null; 
		try {
			dcdDto = dataCrfService.getStudyCrfDataDtoDetails(studyId, langId, userId);
			if(dcdDto != null) {
				/*List<String> fileList = dataCrfService.generateCoverPagesForDataCrf(request, currentLocale, messageSource, langId, dcdDto, dateStr);
				if(fileList != null && fileList.size() > 0) 
					finalFilesList.addAll(fileList);*/
				filesList = dataCrfService.generateDataCrf(periodId, subjectId, activityId, dcdDto, messageSource, langId, dateStr, currentLocale, request, response, dateStrWithTime, reportType, volIds); 
				if(filesList != null && filesList.size() > 0) 
					finalFilesList.addAll(filesList);
			}
			if(finalFilesList != null && finalFilesList.size() > 0) {
				MergeDataCrfsIntoSinglePage gsp = new MergeDataCrfsIntoSinglePage();
				gsp.convertSinglePdf(request, response, finalFilesList, messageSource, currentLocale);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	

}
