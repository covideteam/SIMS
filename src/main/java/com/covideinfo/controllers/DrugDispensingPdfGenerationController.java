package com.covideinfo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.covideinfo.dto.DrugDispancingPdfDto;
import com.covideinfo.service.DrugDispensingPdfService;

@Controller
@RequestMapping("/drugDispansePdf")
public class DrugDispensingPdfGenerationController {
	
	@Autowired
	DrugDispensingPdfService drugDispPdfService;
	/**
	 * @param model 
	 * @return This method returns DrugDispancingPdfDto DTO Object this contains to display for drug dispensing pdf generation purpose view the page with studies and periods drop down
	 */
	@RequestMapping(value="/generateDrugDispansePdf", method=RequestMethod.GET)
	public String generateDrugDispansePdfPage(ModelMap model) {
		DrugDispancingPdfDto ddpDto = drugDispPdfService.DrugDispancingPdfDtoDetails();
	    model.addAttribute("ddpDto", ddpDto);
	   return "drugDispensingPdfPage";
	}
	/**
	 * This method is used to generate drug dispensing pdf provided by the following parameters
	 * @param studyId
	 * @param periodId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/downloadDrugDispansePdf", method=RequestMethod.POST)
	public void downloadDrugDispansePdf(@RequestParam("studyVal")Long studyId, @RequestParam("periodVal") Long periodId, HttpServletRequest request, HttpServletResponse response) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		String dateStr = (String) request.getSession().getAttribute("clinicalDateTimeFormat");
		String dateStrWithTime = (String) request.getSession().getAttribute("dateTimeFormat");
		drugDispPdfService.downloadDrugDispansingPdf(request, response, studyId, periodId,  userId, dateStrWithTime, dateStr);
	}
		
	

}
