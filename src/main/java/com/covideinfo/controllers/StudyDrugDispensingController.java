package com.covideinfo.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.DrugReception;
import com.covideinfo.model.Projects;
import com.covideinfo.model.Regulatories;
import com.covideinfo.model.StudyDrugDispensing;
import com.covideinfo.model.UnitsMaster;
import com.covideinfo.service.DrugReceptionService;
import com.covideinfo.service.PharmacyService;

@Controller
@RequestMapping("/studyDrugDispensing")
public class StudyDrugDispensingController {

	@Autowired
	DrugReceptionService drugReceptionService;
	@Autowired
	PharmacyService pharmacyService;
	
	@Autowired  
	MessageSource messageSource;
	//Study Drug Dispensing List 
	@RequestMapping(value="/studyDrugDispensingList", method=RequestMethod.GET)
	public String studyDrugDispensingList(HttpServletRequest request,ModelMap model,
			RedirectAttributes redirectAttributes) {
		
	    List<StudyDrugDispensing> dispennsinglist=drugReceptionService.getStudyDrugDispensingApprovalAndNotUpdatedList();
		model.addAttribute("dispennsinglist", dispennsinglist);
		return "drugDispensingList";
	}
	//Study Drug Dispensing Form 
	@RequestMapping(value="/drugDispensingForm/{id}", method=RequestMethod.GET)
	public String drugDispensingForm(@PathVariable ("id") Long id,ModelMap model, RedirectAttributes redirectAttributes) {
		List<Projects> plist=drugReceptionService.getEligibleProjectList();
		StudyDrugDispensing dispensing=drugReceptionService.getStudyDrugDispensingDataWithId(id);
		List<DrugReception> drpojoList=drugReceptionService.getDrugReceptionWithProjectIdAndRandomizationCode(dispensing.getProjectId(),dispensing.getRandamizationCode());
		if(drpojoList.size()>0) {
			System.out.println(drpojoList.size());
			DrugReception drpojo=drpojoList.get(drpojoList.size()-1);
			dispensing.setApplicableRegulationId(drpojo.getApplicableRegulationId());
			dispensing.setSponsorStudyCode(drpojo.getSponsorStudyCode());
			dispensing.setDinstinctiveTradeName(drpojo.getDinstinctiveTradeName());
			dispensing.setGenericName(drpojo.getGenericName());
			dispensing.setPharmaceuticalForm(drpojo.getPharmaceuticalForm());
			dispensing.setStrength(drpojo.getStrength());
			dispensing.setStrengthUnitId(drpojo.getStrengthUnit());
			dispensing.setSpecialCondition(drpojo.getShippingCoditions());
			dispensing.setBatchLotNumber(drpojo.getBatchLotNumber());
			dispensing.setManufacturingDate(drpojo.getManufacturingDate());
			dispensing.setStorageConditions(drpojo.getStorageConditions());
			dispensing.setStorageDegreesCelsius(drpojo.getStorageDegreesCelsius());
			dispensing.setStorageDegreesFahranneit(drpojo.getStorageDegreesFahranneit());
			dispensing.setExpirationRatestDate(drpojo.getExpirationRatestDate());
			
		}
		List<Regulatories> regList=drugReceptionService.getRegulatoriesList();
		List<UnitsMaster> unitList=drugReceptionService.getUnitsMasterList();
		
		model.addAttribute("plist", plist);
		model.addAttribute("dispensing", dispensing);
		model.addAttribute("regList", regList);
		model.addAttribute("unitList", unitList);
		return "drugDispensingForm";
	}
	//Study Drug Dispensing Form 
	@RequestMapping(value="/drugDispensingFormOne", method=RequestMethod.GET)
	public String drugDispensingFormOne(ModelMap model, RedirectAttributes redirectAttributes) {
		List<Long> ids=new ArrayList<>();
		List<DrugReception> drigp=drugReceptionService.getDrugReceptionListOnlyApproval();
		for(DrugReception add:drigp) {
			if(!ids.contains(add.getProjectId().getProjectId())) {
				ids.add(add.getProjectId().getProjectId());
			}
		}
		//List<Projects> plist=drugReceptionService.getEligibleProjectList();
		List<Projects> plist=drugReceptionService.getEligibleProjectListForDrugDispensing(ids);
		StudyDrugDispensing dispensing=new StudyDrugDispensing();
		List<Regulatories> regList=drugReceptionService.getRegulatoriesList();
		List<UnitsMaster> unitList=drugReceptionService.getUnitsMasterList();
		
		model.addAttribute("plist", plist);
		model.addAttribute("dispensing", dispensing);
		model.addAttribute("regList", regList);
		model.addAttribute("unitList", unitList);
		return "drugDispensingFormOne";
	}
	//Study Drug Dispensing Approval Saving Method 
	@RequestMapping(value="/saveStudyDrugDispensingApproval", method=RequestMethod.POST)
	public String saveStudyDrugDispensingApproval(@Valid StudyDrugDispensing dispensing, BindingResult result,HttpServletRequest request,ModelMap model, RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("userName").toString();
		Locale currentLocale = LocaleContextHolder.getLocale();
		if (result.hasErrors()) {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gvFailMsg =messageSource.getMessage("label.drugFailMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			return "redirect:/studyDrugDispensing/studyDrugDispensingList";
	    } else {
		if(dispensing!=null) {
			dispensing.setCreatedBy(username);
			dispensing.setCratedOn(new Date());
			boolean flag=drugReceptionService.saveStudyDrugDispensing(dispensing);
			if(flag) {
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);


				String gvsuccessMsg =messageSource.getMessage("label.studyDrugDispensing.SuccessMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
			}else {
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

				String gvFailMsg =messageSource.getMessage("label.studyDrugDispensing.FailMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			}
		}
	    }
		return "redirect:/studyDrugDispensing/studyDrugDispensingList";
	}
	
	//Study Drug Dispensing Saving Method 
	@RequestMapping(value="/saveStudyDrugDispensing", method=RequestMethod.POST)
	public String saveStudyDrugDispensing(@Valid StudyDrugDispensing dispensing, BindingResult result,HttpServletRequest request,ModelMap model, RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("userName").toString();
		Locale currentLocale = LocaleContextHolder.getLocale();
		StudyDrugDispensing pojoexi=drugReceptionService.getStudyDrugDispensingDataWithId(dispensing.getId());
		dispensing.setApprovalBy(pojoexi.getApprovalBy());
		dispensing.setApprovlOn(pojoexi.getApprovlOn());
		dispensing.setStatus(pojoexi.getStatus());
		dispensing.setUpdatedBy(username);
		dispensing.setUpdateOn(new Date());
		dispensing.setFinalStatus("Completed");
		if (result.hasErrors()) {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gvFailMsg =messageSource.getMessage("label.drugFailMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			return "redirect:/studyDrugDispensing/drugDispensingForm";
	    } else {
		if(dispensing!=null) {
			dispensing.setCreatedBy(username);
			dispensing.setCratedOn(new Date());
			boolean flag=drugReceptionService.updateStudyDrugDispensingData(dispensing);
			if(flag) {
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);


				String gvsuccessMsg =messageSource.getMessage("label.studyDrugDispensing.SuccessMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
			}else {
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

				String gvFailMsg =messageSource.getMessage("label.studyDrugDispensing.FailMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			}
		}
	    }
		return "redirect:/studyDrugDispensing/studyDrugDispensingList";
	}
	
}
