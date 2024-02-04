package com.covideinfo.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.DispensedIPHandover;
import com.covideinfo.model.DrugReception;
import com.covideinfo.model.IPDiscard;
import com.covideinfo.model.IPRetention;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.StatusOfUnusedDispensed;
import com.covideinfo.model.StudyDrugDispensing;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.service.DrugReceptionService;
import com.covideinfo.service.PharmacyService;

@Controller
@RequestMapping("/pharmacyData")
public class PharmacyController {

	@Autowired
	PharmacyService pharmacyService;
	@Autowired
	DrugReceptionService drugReceptionService;
	
	@Autowired  
	MessageSource messageSource;
	//Line Clearance Activity List
	@RequestMapping(value="/lineClearanceActivityList", method=RequestMethod.GET)
	public String lineClearanceActivityList(HttpServletRequest request, 
			ModelMap model,
			RedirectAttributes redirectAttributes) {
		List<StudyDrugDispensing> dispennsinglist=drugReceptionService.getStudyDrugDispensingInApprovalList();
		model.addAttribute("dispennsinglist", dispennsinglist);

		return "lineClearanceActivityList";
		
	}
	//Line Clearance Activity Form
	@RequestMapping(value="/lineClearanceActivityForm/{id}", method=RequestMethod.GET)
	public String lineClearanceActivityForm(@PathVariable ("id") Long id,ModelMap model, RedirectAttributes redirectAttributes) {
		
		StudyDrugDispensing sdd=pharmacyService.getStudyDrugDispensingWithId(id);
		model.addAttribute("sdd", sdd);
		
		return "lineClearanceActivityForm";
	}
	//Line Clearance Activity Saving Method
	@RequestMapping(value="/saveLineClearanceActivity", method=RequestMethod.POST)
	public String saveLineClearanceActivity(@RequestParam ("dataid") Long id,HttpServletRequest request,ModelMap model, RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("userName").toString();
		Locale currentLocale = LocaleContextHolder.getLocale();
		
		if(id!=null&& id>0) {
			StudyDrugDispensing sdd=pharmacyService.getStudyDrugDispensingWithId(id);
			sdd.setStatus("Approval");
			sdd.setApprovalBy(username);
			sdd.setApprovlOn(new Date());
			boolean flag=pharmacyService.updateStudyDrugDispensingApproval(sdd);
			if(flag) {
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);


				String gvsuccessMsg =messageSource.getMessage("label.lineClearanceActivity.SuccessMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
			}else {
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

				String gvFailMsg =messageSource.getMessage("label.lineClearanceActivity.FailMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			}
		}else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gvFailMsg =messageSource.getMessage("label.lineClearanceActivity.FailMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
		}
	    
		return "redirect:/pharmacyData/lineClearanceActivityList";
	}
	//Dispensed IP Handover List
	@RequestMapping(value="/dispensedIPHandoverList", method=RequestMethod.GET)
	public String dispensedIPHandoverList(HttpServletRequest request, 
			ModelMap model,
			RedirectAttributes redirectAttributes) {
		List<DispensedIPHandover> dataList=pharmacyService.getDispensedIPHandoverList();
		model.addAttribute("dataList", dataList);

		return "dispensedIPHandoverList";
		
	}
	//Dispensed IP Handover Form
	@RequestMapping(value="/dispensedIPHandoverForm", method=RequestMethod.GET)
	public String dispensedIPHandoverForm(ModelMap model, RedirectAttributes redirectAttributes) {
		DispensedIPHandover ipHandform= new DispensedIPHandover();
		List<Projects> plist=drugReceptionService.getEligibleProjectList();
		
		model.addAttribute("plist", plist);
		model.addAttribute("ipHandform", ipHandform);
		
		return "dispensedIPHandoverForm";
	}
	//Dispensed IP Handover Saving Method
	@RequestMapping(value="/saveDispensedIPHandover", method=RequestMethod.POST)
	public String saveDispensedIPHandover(@Valid DispensedIPHandover ipHandform, BindingResult result,HttpServletRequest request,ModelMap model, RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("userName").toString();
		Locale currentLocale = LocaleContextHolder.getLocale();
		if (result.hasErrors()) {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gvFailMsg =messageSource.getMessage("label.dispensedIPHandover.FailMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			
			return "redirect:/pharmacyData/dispensedIPHandoverForm";
	    } else {
		if(ipHandform!=null) {
			ipHandform.setCreatedBy(username);
			ipHandform.setCreatedOn(new Date());
			boolean flag=pharmacyService.saveDispensedIPHandover(ipHandform);
			if(flag) {
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);


				String gvsuccessMsg =messageSource.getMessage("label.dispensedIPHandover.SuccessMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
			}else {
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

				String gvFailMsg =messageSource.getMessage("label.dispensedIPHandover.FailMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			}
		}
	    }
		return "redirect:/pharmacyData/dispensedIPHandoverForm";
	}
	//IP Retention List
	@RequestMapping(value="/ipRetentionList", method=RequestMethod.GET)
	public String ipRetentionList(HttpServletRequest request, 
			ModelMap model,
			RedirectAttributes redirectAttributes) {
		List<IPRetention> dataList=pharmacyService.getipRetentionList();
		model.addAttribute("dataList", dataList);

		return "ipRetentionList";
		
	}
	//IP Retention Form
	@RequestMapping(value="/ipRetentionForm", method=RequestMethod.GET)
	public String ipRetentionForm(ModelMap model, RedirectAttributes redirectAttributes) {
		IPRetention ippojo= new IPRetention();
		List<Projects> plist=drugReceptionService.getEligibleProjectList();
		
		model.addAttribute("plist", plist);
		model.addAttribute("ippojo", ippojo);
		
		return "ipRetentionForm";
	}
	//IP Retention Saving Method
	@RequestMapping(value="/saveIPRetention", method=RequestMethod.POST)
	public String saveIPRetention(@Valid IPRetention ippojo, BindingResult result,HttpServletRequest request,ModelMap model, RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("userName").toString();
		Locale currentLocale = LocaleContextHolder.getLocale();
		if (result.hasErrors()) {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gvFailMsg =messageSource.getMessage("label.iPRetention.FailMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			
			return "redirect:/pharmacyData/ipRetentionForm";
	    } else {
		if(ippojo!=null) {
			ippojo.setCreatedBy(username);
			ippojo.setCreatedOn(new Date());
			boolean flag=pharmacyService.saveIPRetention(ippojo);
			if(flag) {
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);


				String gvsuccessMsg =messageSource.getMessage("label.iPRetention.SuccessMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
			}else {
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

				String gvFailMsg =messageSource.getMessage("label.iPRetention.FailMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			}
		}
	    }
		return "redirect:/pharmacyData/ipRetentionForm";
	}
	//Status Of Unused Dispensed List
	@RequestMapping(value="/statusOfUnusedDispensedList", method=RequestMethod.GET)
	public String statusOfUnusedDispensedList(HttpServletRequest request, 
			ModelMap model,
			RedirectAttributes redirectAttributes) {
		List<StatusOfUnusedDispensed> dataList=pharmacyService.getStatusOfUnusedDispensedList();
		model.addAttribute("dataList", dataList);

		return "statusOfUnusedDispensedList";
		
	}
	//Status Of Unused Dispensed Form
	@RequestMapping(value="/statusOfUnusedDispensedForm", method=RequestMethod.GET)
	public String statusOfUnusedDispensedForm(ModelMap model, RedirectAttributes redirectAttributes) {
		StatusOfUnusedDispensed soupojo= new StatusOfUnusedDispensed();
		List<Projects> plist=drugReceptionService.getEligibleProjectList();
		
		model.addAttribute("plist", plist);
		model.addAttribute("soupojo", soupojo);
		
		return "statusOfUnusedDispensedForm";
	}
	//Status Of Unused Dispensed Saving Method
	@RequestMapping(value="/saveStatusOfUnusedDispensed", method=RequestMethod.POST)
	public String saveStatusOfUnusedDispensed(@Valid StatusOfUnusedDispensed soupojo, BindingResult result,HttpServletRequest request,ModelMap model, RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("userName").toString();
		Locale currentLocale = LocaleContextHolder.getLocale();
		if (result.hasErrors()) {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gvFailMsg =messageSource.getMessage("label.statusOfUnusedDispensed.FailMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			
			return "redirect:/pharmacyData/statusOfUnusedDispensedForm";
	    } else {
		if(soupojo!=null) {
			soupojo.setCreatedBy(username);
			soupojo.setCreatedOn(new Date());
			boolean flag=pharmacyService.saveStatusOfUnusedDispensed(soupojo);
			if(flag) {
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);


				String gvsuccessMsg =messageSource.getMessage("label.statusOfUnusedDispensed.SuccessMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
			}else {
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

				String gvFailMsg =messageSource.getMessage("label.statusOfUnusedDispensed.FailMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			}
		}
	    }
		return "redirect:/pharmacyData/statusOfUnusedDispensedForm";
	}
	//IP Discard List
	@RequestMapping(value="/ipDiscardList", method=RequestMethod.GET)
	public String ipDiscardList(HttpServletRequest request, 
			ModelMap model,
			RedirectAttributes redirectAttributes) {
		List<IPDiscard> dataList=pharmacyService.getIPDiscardList();
		model.addAttribute("dataList", dataList);

		return "ipDiscardList";
		
	}
	//IP Discard Form
	@RequestMapping(value="/ipDiscardForm", method=RequestMethod.GET)
	public String ipDiscardForm(ModelMap model, RedirectAttributes redirectAttributes) {
		IPDiscard ippojo= new IPDiscard();
		List<Projects> plist=drugReceptionService.getEligibleProjectList();
		
		model.addAttribute("plist", plist);
		model.addAttribute("ippojo", ippojo);
		
		return "ipDiscardForm";
	}
	//IP Discard Saving Method
	@RequestMapping(value="/saveIPDiscard", method=RequestMethod.POST)
	public String saveIPDiscard(@Valid IPDiscard ippojo, BindingResult result,HttpServletRequest request,ModelMap model, RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("userName").toString();
		Locale currentLocale = LocaleContextHolder.getLocale();
		if (result.hasErrors()) {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gvFailMsg =messageSource.getMessage("label.ipDiscard.FailMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			
			return "redirect:/pharmacyData/ipDiscardForm";
	    } else {
		if(ippojo!=null) {
			ippojo.setCreatedBy(username);
			ippojo.setCreatedOn(new Date());
			boolean flag=pharmacyService.saveIPDiscard(ippojo);
			if(flag) {
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);


				String gvsuccessMsg =messageSource.getMessage("label.ipDiscard.SuccessMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
			}else {
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

				String gvFailMsg =messageSource.getMessage("label.ipDiscard.FailMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			}
		}
	    }
		return "redirect:/pharmacyData/ipDiscardForm";
	}
	@RequestMapping(value="/getSponsorCodeWithProjectId/{proid}", method=RequestMethod.GET)
	public @ResponseBody String getSponsorCodeWithProjectId(@PathVariable ("proid") Long proid, HttpServletRequest request, 
			ModelMap model,RedirectAttributes redirectAttributes) {
     	String sponsercode="";
	    ProjectsDetails pojo=pharmacyService.getSponsorCodeWithProjectId(proid);
	    sponsercode=pojo.getFieldValue();
		return sponsercode;
		
	}
	
	@RequestMapping(value="getPeriodDataBasedonProjectId/{id}",method=RequestMethod.GET)
	public String getPeriodDataBasedonProjectId(@PathVariable("id") long id,ModelMap model ,HttpServletRequest request,HttpServletResponse response
			,RedirectAttributes redirectAttributes ) {
		List<StudyPeriodMaster> sgpList=pharmacyService.getPeriodDataBasedonProjectId(id);
		
		model.addAttribute("sgpList", sgpList);
		return "pages/pharmacy/periodDataList";
		
	}
	@RequestMapping(value="getRandomizationDataBasedonProjectId/{id}",method=RequestMethod.GET)
	public String getRandomizationDataBasedonProjectId(@PathVariable("id") long id,ModelMap model ,HttpServletRequest request,HttpServletResponse response
			,RedirectAttributes redirectAttributes ) {
		List<DrugReception> drugList=pharmacyService.getDrugReceptionBasedonProjectId(id);
		List<String> rand=new ArrayList<String>();
		for(DrugReception adds:drugList) {
			if(!rand.contains(adds.getRandamizationCode())){
				rand.add(adds.getRandamizationCode());
			}
		}
		model.addAttribute("rand", rand);
		return "pages/pharmacy/randomizationDataList";
		
	}
	@RequestMapping(value="getRandamizationCodeWithProjectIdForDrugReception/{id}",method=RequestMethod.GET)
	public String getRandamizationCodeWithProjectIdForDrugReception(@PathVariable("id") long id,ModelMap model ,HttpServletRequest request,HttpServletResponse response
			,RedirectAttributes redirectAttributes ) {
		List<ProjectsDetails> drugList=pharmacyService.getRandamizationCodeWithProjectId(id);
		List<String> rand=new ArrayList<String>();
		for(ProjectsDetails adds:drugList) {
			if(!rand.contains(adds.getFieldValue())){
				rand.add(adds.getFieldValue());
			}
		}
		model.addAttribute("rand", rand);
		return "pages/pharmacy/randomizationDataList";
		
	}
	
	
	
}
