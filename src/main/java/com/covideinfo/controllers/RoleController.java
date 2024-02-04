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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.bo.StudyCreationBo;
import com.covideinfo.enums.StudyDesign;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.GlobalGroups;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.RoleMasterService;
import com.covideinfo.service.UserCreationService;

@Controller
@RequestMapping("/userRole")
public class RoleController {

	@Autowired
	RoleMasterService roleMasterService;
	
	@Autowired  
	MessageSource messageSource;
	
	@Autowired
	UserCreationService userCreationService;
	
	@Autowired
	StudyCreationBo studyCreationBo;
	
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String getAllActiveRoles() {
		List<RoleMaster> allRoles = roleMasterService.findAll();
		return null;
	}
		
	@RequestMapping(value="/createRole", method=RequestMethod.GET)
	public String createRole(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		//long uid = (long) request.getSession().getAttribute("userId");
		//UserMaster um=userCreationService.getUserMasterWithId(uid);
		List<RoleMaster> allRoles=null;
		List<RoleMaster> roleList=null;
		/*if(um!=null) {
			if(um.getRole().getRole().equals("SUPERADMIN")) {
				allRoles = roleMasterService.findOnlyAdminRoles();
			}
			if(um.getRole().getRole().equals("ADMIN")) {
				allRoles = roleMasterService.findRolesWithOutAdminRoles();
			}
			model.addAttribute("roleMaster", new RoleMaster());
			model.addAttribute("allRoles", allRoles);
		}else {
			model.addAttribute("roleMaster", new RoleMaster());
			model.addAttribute("allRoles", allRoles);
		}*/
		String userRole = (String) request.getSession().getAttribute("userRole");
		model.addAttribute("roleMaster", new RoleMaster());
		StatusMaster stm = studyCreationBo.statusMaster(StudyStatus.ACTIVE.toString());
		allRoles=userCreationService.getRolesMasterRecordsList();
		roleList=userCreationService.getRolesMasterRecordsListOnlyActive(stm);
		model.addAttribute("allRoles", roleList);
		model.addAttribute("userRole", userRole);
		return "createRole";
	}
	@RequestMapping(value="/checkRoleDuplication/{role}", method=RequestMethod.GET)
	public String checkRoleDuplication(ModelMap model, @PathVariable("role")String role) {
		RoleMaster rolem = roleMasterService.checkRoleDuplication(role);
		if(rolem == null)
			model.addAttribute("result", "");
		else
			model.addAttribute("result", role +" Already Exists.");
		return "pages/role/roleDuplicateMsgPage";
	}
	
	@RequestMapping(value="/saveRole", method=RequestMethod.POST)
	public String saveRole(@ModelAttribute("user")RoleMaster roleMaster, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("userName").toString();
		long uid = (long) request.getSession().getAttribute("userId");
		Locale currentLocale = LocaleContextHolder.getLocale();
		StatusMaster stm = studyCreationBo.statusMaster(StudyStatus.ACTIVE.toString());
		UserMaster um=userCreationService.getUserMasterWithId(uid);
		roleMaster.setCreatedBy(um);
		roleMaster.setCreatedOn(new Date());
		roleMaster.setRoleStatus(stm);
		
		long no = roleMasterService.saveRole(username, roleMaster);
		if(no > 0) {
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			String gvsuccessMsg =messageSource.getMessage("label.roleCrSuccess", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
		}
		else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gvErrorMsg =messageSource.getMessage("label.roleCrError", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvErrorMsg);
		}
		return "redirect:/userRole/createRole";
	}
	
	@RequestMapping(value = "/checkWrokFlowRecordStatus/{workName}/{actionName}/{fromRole}/{toRole}", method=RequestMethod.GET)
	public @ResponseBody String checkWrokFlowRecordStatus(@PathVariable("workName")String workName, @PathVariable("actionName")String actionName, @PathVariable("fromRole")Long fromRole, @PathVariable("toRole")Long toRole) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String gvsuccessMsg =messageSource.getMessage("label.workFlowExistsMessage", null,currentLocale);
		String result = roleMasterService.checkWrokFlowRecordStatus(workName, actionName, fromRole, toRole);
		if(result.equals("Exists"))
			return gvsuccessMsg;
		else return "";
	}
	
	// Add Approval Levels 
	@RequestMapping(value="/addApprovalLevals",method=RequestMethod.GET)
	public String addApprovalLevals(HttpServletRequest request,HttpServletResponse response,
			RedirectAttributes redirectAttributes,ModelMap model) {
		//String user=request.getSession().getAttribute("userName").toString();
		List<RoleMaster> rolesList = roleMasterService.findAll();
		DraftReviewStage drs=new DraftReviewStage();
		List<DraftReviewStage> drsList=roleMasterService.getDraftReviewStageList();
		Locale currentLocale = LocaleContextHolder.getLocale();
		String deviationSubmitMsg = messageSource.getMessage("label.fromRoleNotRequiredMsg", null, currentLocale);
		String workFlowReocrdExistMsg = messageSource.getMessage("label.workFlowExistsMessage", null, currentLocale);
		model.addAttribute("workFlowReocrdExistMsg", workFlowReocrdExistMsg);
		model.addAttribute("deviationSubmitMsg", deviationSubmitMsg);
		model.addAttribute("rolesList", rolesList);
		model.addAttribute("drs", drs);
		model.addAttribute("drsList", drsList);
		
		return "addApprovalLevals";
		
	}
	// Save Approval Levels 
	@RequestMapping(value="/saveApprovalLevels",method=RequestMethod.POST)
	public String saveApprovalLevels(@Valid DraftReviewStage drs, HttpServletRequest request,HttpServletResponse response,
			RedirectAttributes redirectAttributes,ModelMap model) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String user=request.getSession().getAttribute("userName").toString();
		if(drs!=null) {
			drs.setCreatedBy(user);
			drs.setCreatedOn(new Date());
			boolean flag=roleMasterService.saveApprovalLevelsData(drs);
			if(flag) {
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);
				redirectAttributes.addFlashAttribute("pageMessage", "Approval Levels Saving Successfully...!");
			}else {
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);
				redirectAttributes.addFlashAttribute("pageError", "Approval Levels Saving Failed. Please Try Again.");
			}
		}else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);
			redirectAttributes.addFlashAttribute("pageError", "Approval Levels Saving Failed. Please Try Again.");
		}
		return "redirect:/userRole/addApprovalLevals";
		
	}
	//Add Approval Levels Validation Check
	@RequestMapping(value="/checkApprovalSubmitValidation/{id}/{workName}/{action}",method=RequestMethod.GET)
	public @ResponseBody String checkApprovalSubmitValidation(@PathVariable ("id") Long id,@PathVariable ("workName") String workName,
			@PathVariable ("action") String action,HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes redirectAttributes,ModelMap model) {
		String meg="No";
		List<DraftReviewStage> drs=new ArrayList<>();
		if(action.equals("SUBMIT")) {
		 drs=roleMasterService.getDraftReviewStageWithFromRoleIdWithWorkName(id,workName);
		}
		List<DraftReviewStage> drsAppComm=roleMasterService.getDraftReviewStageWithFromRoleIdWithWorkNameTwo(id,workName,action);
		if(drs.size()==0 && drsAppComm.size()==0){
			meg="Yes";
		}else {
			meg="No";
		}
		return meg;
		
	}
	@RequestMapping(value="/roleDisable", method=RequestMethod.POST)
	public String roleDisable(@RequestParam("rolevalDis")Long roleval, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String username = request.getSession().getAttribute("userName").toString();
		StatusMaster stm = studyCreationBo.statusMaster(StudyStatus.INACTIVE.toString());
		Locale currentLocale = LocaleContextHolder.getLocale();
		RoleMaster rm=roleMasterService.getRoleMasterWithId(roleval);
		rm.setRoleStatus(stm);
		boolean flag = userCreationService.updateRoleStatus(rm);
		if(flag) {
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);


			String successMsg =messageSource.getMessage("label.roleInactiveSuccess", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", successMsg);
		}
		else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);


			String errorMsg =messageSource.getMessage("label.roleInactiveError", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", errorMsg);
		}
		return "redirect:/userRole/createRole";
	}
	
	@RequestMapping(value="/checkUsersIsAvelableInThisRole/{value}",method=RequestMethod.GET)
	public @ResponseBody String checkUsersIsAvelableInThisRole(@PathVariable("value") Long value ,ModelMap model, HttpServletRequest request,HttpServletResponse response) {
		String chek="Yse";
		List<UserMaster> um=null;
		um=userCreationService.checkUsersIsAvelableInThisRole(value);
		if(um.size()==0) {
			chek="Yes";
		}else {
			chek="No";
		}
		return chek;
	}
}
