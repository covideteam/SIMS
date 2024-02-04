package com.covideinfo.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.DocumentryRequirements;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.DrugReception;
import com.covideinfo.model.DrugReceptionReviewAudit;
import com.covideinfo.model.ProjectDetailsRandomization;
import com.covideinfo.model.Projects;
import com.covideinfo.model.RandomizationReviewAudit;
import com.covideinfo.model.Regulatories;
import com.covideinfo.model.UnitsMaster;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.DrugReceptionService;

@Controller
@RequestMapping("/drugReception")
public class DrugReceptionController {

	@Autowired
	DrugReceptionService drugReceptionService;
	@Autowired  
	MessageSource messageSource;
	//Drug Reception List
	@RequestMapping(value="/drugListForm", method=RequestMethod.GET)
	public String studyDesignForm(HttpServletRequest request, 
			ModelMap model,
			RedirectAttributes redirectAttributes) {
		List<DrugReception> druglist=drugReceptionService.getDrugReceptionList();
		model.addAttribute("druglist", druglist);
		return "drugListForm";
		
	}
	//Drug Reception Form
	@RequestMapping(value="/drugReceptionForm", method=RequestMethod.GET)
	public String drugReceptionForm(ModelMap model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		long roleid=0;
		long userroleid = (long) request.getSession().getAttribute("roleId");
		List<Long> list=drugReceptionService.getDrugReceptionSubmitList();
		list.add(roleid);
		
		List<Projects> plist=drugReceptionService.getEligibleProjectList();
		
		DrugReception drug=new DrugReception();
		DocumentryRequirements document=new DocumentryRequirements();
		
		List<Regulatories> regList=drugReceptionService.getRegulatoriesList();
		List<UnitsMaster> unitList=drugReceptionService.getUnitsMasterList();
		
		model.addAttribute("plist", plist);
		model.addAttribute("drug", drug);
		model.addAttribute("regList", regList);
		model.addAttribute("unitList", unitList);
		model.addAttribute("document", document);
		model.addAttribute("typeForm", "new");
		boolean flag=false;
		if(list.contains(userroleid)) {
			flag=true;
		}
		if(!flag) {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			redirectAttributes.addFlashAttribute("pageError","Access Denied");
			return "redirect:/drugReception/drugListForm";
		}else {
			return "drugReceptionForm";
		}
		
	}
	//Drug Reception Saving Method
	@RequestMapping(value="/saveDrugReception", method=RequestMethod.POST)
	public String saveDrugReception(@Valid DrugReception drung,@Valid DocumentryRequirements document, BindingResult result,HttpServletRequest request,ModelMap model, RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("userName").toString();
		Locale currentLocale = LocaleContextHolder.getLocale();
		if (result.hasErrors()) {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gvFailMsg =messageSource.getMessage("label.drugFailMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			return "redirect:/drugReception/drugReceptionForm";
	    } else {
		if(drung!=null) {
			drung.setCreatedBy(username);
			drung.setCratedOn(new Date());
			DrugReceptionReviewAudit drrpojo=null;
			if(drung.getProjectId().getProjectId()<0) {
				drung.setProjectId(null);
				drung.setRandamizationCode("");
			}else {
				UserMaster checkLoginUser = drugReceptionService.findByUsername(username);
				DraftReviewStage drsp=drugReceptionService.getDrugReceptionStage(checkLoginUser.getRole());
				
        		if(drsp!=null) {
        			drrpojo=new DrugReceptionReviewAudit();
        			drrpojo.setComment("nil");
        			drrpojo.setDate(new Date());
        			drrpojo.setInTheFlow(true);
        			drrpojo.setReviewState(drsp.getId());
        			drrpojo.setRole(checkLoginUser.getRole());
        			drrpojo.setStatus("IN APPROVAL");
        			drrpojo.setDrugid(drung.getId());
        			drrpojo.setUser(checkLoginUser);
        		}
				drung.setStatus("Completed");
			}
			document.setCreatedBy(username);
			document.setCratedOn(new Date());
			boolean flag=drugReceptionService.saveDrugReception(drung,document,drrpojo);
			if(flag) {
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);


				String gvsuccessMsg =messageSource.getMessage("label.drugSuccessMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
			}else {
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

				String gvFailMsg =messageSource.getMessage("label.drugFailMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			}
		}
	    }
		return "redirect:/drugReception/drugReceptionForm";
	}
	//Drug Reception Update Form
	@RequestMapping(value="/updateDrugReceptionForm/{id}", method=RequestMethod.GET)
	public String updateDrugReceptionForm(@PathVariable("id") Long id, ModelMap model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		long roleid=0;
		long userroleid = (long) request.getSession().getAttribute("roleId");
		List<Long> list=drugReceptionService.getDrugReceptionSubmitList();
		list.add(roleid);
		
		List<Projects> plist=drugReceptionService.getEligibleProjectList();
		DrugReception drug=drugReceptionService.getDrugReceptionWithId(id);
		DocumentryRequirements document=drugReceptionService.getDocumentryRequirementsWithDrugId(id);
		
		List<Regulatories> regList=drugReceptionService.getRegulatoriesList();
		List<UnitsMaster> unitList=drugReceptionService.getUnitsMasterList();
		
		model.addAttribute("plist", plist);
		model.addAttribute("drug", drug);
		model.addAttribute("regList", regList);
		model.addAttribute("unitList", unitList);
		model.addAttribute("document", document);
		model.addAttribute("typeForm", "edit");
		
		boolean flag=false;
		if(list.contains(userroleid)) {
			flag=true;
		}
		if(!flag) {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			redirectAttributes.addFlashAttribute("pageError","Access Denied");
			return "redirect:/drugReception/drugListForm";
		}else {
			return "drugReceptionUpdateForm";
		}
		
		
	}
	
	//Drug Reception Update Method
	@RequestMapping(value="/updateDrugReception", method=RequestMethod.POST)
	public String updateDrugReception(@Valid DrugReception drung,@Valid DocumentryRequirements document, BindingResult result,HttpServletRequest request,ModelMap model, RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("userName").toString();
		DrugReception drugData=drugReceptionService.getDrugReceptionWithId(drung.getId());
		drung.setCreatedBy(drugData.getCreatedBy());
		drung.setCratedOn(drugData.getCratedOn());
		
		DocumentryRequirements docreq=drugReceptionService.getDocumentryRequirementsWithDrugId(drung.getId());
		document.setId(docreq.getId());
		document.setCreatedBy(docreq.getCreatedBy());
		document.setCratedOn(docreq.getCratedOn());
		Locale currentLocale = LocaleContextHolder.getLocale();
		if (result.hasErrors()) {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gvFailMsg =messageSource.getMessage("label.drugFailMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			return "redirect:/drugReception/drugReceptionForm";
	    } else {
	    
		if(drung!=null) {
			drung.setUpdatedBy(username);
			drung.setUpdateOn(new Date());
			DrugReceptionReviewAudit drrpojo=null;
			if(drung.getProjectId().getProjectId()<0) {
				drung.setProjectId(null);
				drung.setRandamizationCode("");
				drung.setStatus("InCompleted");
			}else {
				UserMaster checkLoginUser = drugReceptionService.findByUsername(username);
				DraftReviewStage drsp=drugReceptionService.getDrugReceptionStage(checkLoginUser.getRole());
				
        		if(drsp!=null) {
        			drrpojo=new DrugReceptionReviewAudit();
        			drrpojo.setComment("nil");
        			drrpojo.setDate(new Date());
        			drrpojo.setInTheFlow(true);
        			drrpojo.setReviewState(drsp.getId());
        			drrpojo.setRole(checkLoginUser.getRole());
        			drrpojo.setStatus("IN APPROVAL");
        			drrpojo.setDrugid(drung.getId());
        			drrpojo.setUser(checkLoginUser);
        		}
				drung.setStatus("Completed");
			}
			document.setUpdatedBy(username);
			document.setUpdateOn(new Date());
			//Update Drug Reception
			boolean flag=drugReceptionService.updateDrugReception(drung,document,drrpojo);
			if(flag) {
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);


				String gvsuccessMsg =messageSource.getMessage("label.drugUpdateSuccessMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
			}else {
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

				String gvFailMsg =messageSource.getMessage("label.drugUpdateFailMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			}
		
	    }
	    }
		return "redirect:/drugReception/drugListForm";
	}
	@RequestMapping(value="/drugApprovalList", method=RequestMethod.GET)
	public String drugApprovalList(HttpServletRequest request, 
			ModelMap model,
			RedirectAttributes redirectAttributes) {
		
		List<DrugReception> druglist=drugReceptionService.getDrugReceptionOnlyCompletedList();
		model.addAttribute("druglist", druglist);

		return "drugApprovalList";
		
	}
	@RequestMapping(value="/approvalDrugReception/{id}", method=RequestMethod.GET)
	public String approvalDrugReception(@PathVariable("id") Long id, ModelMap model, HttpServletRequest request,RedirectAttributes redirectAttributes) {
		List<Projects> plist=drugReceptionService.getEligibleProjectList();
		DrugReception drug=drugReceptionService.getDrugReceptionWithId(id);
		DocumentryRequirements document=drugReceptionService.getDocumentryRequirementsWithDrugId(id);
		
		List<Regulatories> regList=drugReceptionService.getRegulatoriesList();
		List<UnitsMaster> unitList=drugReceptionService.getUnitsMasterList();
		
		String userRole = request.getSession().getAttribute("userRole").toString();
		String roleId = request.getSession().getAttribute("roleId").toString();
		Long rid =Long.parseLong(roleId);
		List<DrugReceptionReviewAudit> rraList=drugReceptionService.getDrugReceptionReviewAuditWithDrugReceptionId(id);
		int size=rraList.size();
		DraftReviewStage firstRow=null;
		firstRow=drugReceptionService.getDrugReceptionReviewStageFirsyRow(rid);
		DrugReceptionReviewAudit rrapojo=null;
		if(size>0) {
			 rrapojo=rraList.get(size-1);
		}
			
		DraftReviewStage rrspojo=null;
	    String toRole=null;
	    if(rrapojo!=null) {
	    rrspojo=drugReceptionService.getDrugReceptionReviewStageWithreviewState(rrapojo.getReviewState());
	    if(rrspojo.getToRole()!=null) {
	    	toRole=rrspojo.getToRole().getRole();
		 }
	    }
		
		model.addAttribute("plist", plist);
		model.addAttribute("drug", drug);
		model.addAttribute("regList", regList);
		model.addAttribute("unitList", unitList);
		model.addAttribute("document", document);
		model.addAttribute("typeForm", "edit");
		
		model.addAttribute("rraList", rraList);
		model.addAttribute("rrapojo", rrapojo);
		model.addAttribute("userRole", userRole);
		model.addAttribute("toRole", toRole);
		model.addAttribute("drugId", id);
		model.addAttribute("rrspojo", rrspojo);
		if(firstRow!=null) {
		model.addAttribute("firstRow", firstRow.getFromRole().getRole());
		}else {
		model.addAttribute("firstRow","");
		}
		
		return "drugReceptionApprovalView";
	}
	@RequestMapping(value="/drugReceptionApprovalSave", method=RequestMethod.POST)
	public String drugReceptionApprovalSave(@Valid DrugReception drung,@Valid DocumentryRequirements document, BindingResult result,HttpServletRequest request,ModelMap model, RedirectAttributes redirectAttributes
			,@RequestParam ("approvalType") String approvalType,@RequestParam ("commentsval") String commentsval,
			@RequestParam ("reviewState") Long reviewState) {
		String username = request.getSession().getAttribute("userName").toString();
		DrugReception drugData=drugReceptionService.getDrugReceptionWithId(drung.getId());
		
		String userRole = request.getSession().getAttribute("userRole").toString();
		UserMaster checkLoginUser = drugReceptionService.findByUsername(username);
		DraftReviewStage rrspojo=drugReceptionService.getDrugReceptionReviewStageWithreviewState(reviewState);
		DraftReviewStage stage=null;
		stage=drugReceptionService.getDrugReceptionReviewStageWithTypeAndFromRole(rrspojo.getToRole(),approvalType);
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		drugData.setReviewerStatus("Approval");
		drugData.setReviewerBy(username);
		drugData.setReviewerOn(new Date());
		/*if(stage!=null) {
		   	if(stage.getToRole()!=null) {*/
		    //Approval vela
		    boolean flag=drugReceptionService.drugReceptionApprovalSave(drugData);
			if(flag) {
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);


				String gvsuccessMsg =messageSource.getMessage("label.drugApprovalSuccessMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
			}else {
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);

				String gvFailMsg =messageSource.getMessage("label.drugApprovalFailMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
			}
		  /* }else {
			  // Finale Approval code
		   }*/
		/*}else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			redirectAttributes.addFlashAttribute("pageError","Drug Reception Approval Fail.Please Check");
		}*/
	    
		return "redirect:/drugReception/drugApprovalList";
	}
	
}
