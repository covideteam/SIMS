package com.covideinfo.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.dto.DrugDispansingEntryDetailsDto;
import com.covideinfo.service.DrugDispansingEntryService;

@Controller
@RequestMapping("/drugDispanse")
public class DrugDispansingController {
	
	@Autowired
	DrugDispansingEntryService ddeservice;
	
	/**
	 * 
	 * This method will display the approved studies list for Drug Dispense Entry  
	 * @param redirectAttributes is used to display redirected parameter values in Drug Dispense Entry Page
	 * @return drugDispenseEntryPage
	 */
	@RequestMapping(value="/drugDispanseEntry", method=RequestMethod.GET)
	public String drugDispanceEntryPage(ModelMap model, RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		DrugDispansingEntryDetailsDto ddedDto = ddeservice.getDrugDispansingDetails();
		model.addAttribute("ddedDto", ddedDto);
		return "drugDispenseEntryPage";
	}
    /**
     * 
     * @param model is used to store data in request scope
     * @param studyId  by using this parameter we can get this study related total required drug dispensing data
     * @return JSON Object.
     */
	@RequestMapping(value="/drugDispanseDetails/{studyId}", method=RequestMethod.GET, produces= {org.springframework.http.MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DrugDispansingEntryDetailsDto drugDispanseDetails(ModelMap model, @PathVariable("studyId")Long studyId) {
		DrugDispansingEntryDetailsDto ddedDto = ddeservice.getDrugDispansingInfoDetails(studyId);
		return ddedDto;
	}
	/**
	 * This method is used to saving or updating the Drug Dispensing data details as given treatment wise by using the following parameters.
	 * @param model
	 * @param noOfUnits
	 * @param nameOfIp
	 * @param batchNoVal
	 * @param studyId
	 * @param expDate
	 * @param treatment
	 * @param request
	 * @param redirectAttributes
	 * @return success or failure message
	 */
	@RequestMapping(value="/saveDrugDispanseInfoDetails", method=RequestMethod.POST)
	public String saveDrugDispanseInfoDetails(ModelMap model, @RequestParam("noOfUnitsVal")String noOfUnits,
			@RequestParam("nameOfIpVal")String nameOfIp,@RequestParam("batchNoVal")String batchNoVal,@RequestParam("studyVal")Long studyId,
			@RequestParam("expDateVal")String expDate, @RequestParam("treatmentVal")Long treatment, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		boolean flag = ddeservice.saveDrugDispanseInfoDetails(noOfUnits,nameOfIp,batchNoVal,studyId,expDate,userId, treatment);
		if(flag)
			redirectAttributes.addFlashAttribute("pageMessage", "Drug Dispensing Entry Details Saving Done Successfully.....!");
		else 
			redirectAttributes.addFlashAttribute("pageError", "Drug Dispensing Entry Details Saving Failed. Please Try Again.");
		return "redirect:/drugDispanse/drugDispanseEntry";
	}
}
