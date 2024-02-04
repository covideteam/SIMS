package com.covideinfo.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.DepartmentMaster;
import com.covideinfo.service.DeptService;

@Controller
@RequestMapping("/department")
public class DepartmentMasterController {
	
	@Autowired  
	MessageSource messageSource;
	
	@Autowired
	DeptService deptService;
	
	@RequestMapping(value="/createDept", method=RequestMethod.GET)
	public String createDept(ModelMap model, RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		model.addAttribute("dept", new DepartmentMaster());
		return "department";
	}
	
	@RequestMapping(value="/checkDeptDuplication/{deptName}", method=RequestMethod.GET)
	public String createDept(ModelMap model, @PathVariable("deptName")String deptName) {
		DepartmentMaster dept = deptService.getDepartmentMasterRecord(deptName);
		if(dept != null)
			model.addAttribute("result", deptName +" Already Exists.");
		else 
			model.addAttribute("result", "");
		return "pages/dept/deptMsgPage";
	}
	@RequestMapping(value="/saveDepartment", method=RequestMethod.POST)
	public String saveDepartment(@ModelAttribute("dept")DepartmentMaster dept, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("longinUserName");
		Locale currentLocale = LocaleContextHolder.getLocale();

		long no = deptService.saveDepartMasterMasterRecord(dept,userName);
		if(no > 0) {
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);
			redirectAttributes.addFlashAttribute("pageMessage", "Department Saved Successfully...!");
		}
		else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);
			redirectAttributes.addFlashAttribute("pageError", "Department Saving Failed. Please Try Again.");
		}
		return "redirect:/department/createDept";
	}

}
