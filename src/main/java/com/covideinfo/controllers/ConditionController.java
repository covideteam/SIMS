package com.covideinfo.controllers;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.similarity.CosineDistance;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.dto.ConditionDto;
import com.covideinfo.model.ConditionParameter;
import com.covideinfo.model.DefaultActivitys;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificCondition;
import com.covideinfo.service.ConditionService;
import com.covideinfo.service.GlobalValuesService;

@Controller
@RequestMapping("/condition")
public class ConditionController {
	
	@Autowired
	ConditionService conditionService;
	
	@Autowired  
	MessageSource messageSource;
	
	@Autowired
	GlobalValuesService globalValeusService;
	
	@RequestMapping(value="/conditionForm",method=RequestMethod.GET)
	public String conditionForm(ModelMap model,HttpServletRequest request,
			HttpServletResponse response ,RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		List<InternationalizaionLanguages> inLagList = globalValeusService.getInternationalizaionLanguages();
		Locale currentLocale = LocaleContextHolder.getLocale();
		Long langId = (Long) request.getSession().getAttribute("languageId");
		ConditionDto swaDto = conditionService.getLanguageDetails(messageSource, inLagList, currentLocale,langId);
		model.addAttribute("swaDto", swaDto);
		model.addAttribute("inLagList", inLagList);
		model.addAttribute("langId", langId);
		return "conditionParameterForm";
	}
	@RequestMapping(value="/saveConditionForm", method=RequestMethod.POST)
	public String saveConditionForm(ModelMap model, HttpServletRequest request, @RequestParam("valueName")String name,@RequestParam("dropDown")String dropDown,
			@RequestParam("pvalue")List<String> pvalList, @RequestParam("lanId")List<Long> lagList, RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("longinUserName");
		Locale currentLocale = LocaleContextHolder.getLocale();
		String result = conditionService.saveSubjectWithdrawActivity(name, pvalList, lagList, userName,dropDown);
		
		if(result.equals("success")) {
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			String cnsuccessMsg =messageSource.getMessage("label.cnSuccessMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", cnsuccessMsg);
		}else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String cnFailMsg =messageSource.getMessage("label.cnFailMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", cnFailMsg);
		}
		return "redirect:/condition/conditionForm";
	}
	
	@RequestMapping(value="/getconditions",method=RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody List<LanguageSpecificCondition> getConidtions() {
        Locale currentLocale = LocaleContextHolder.getLocale();
        List<LanguageSpecificCondition>  lscList = conditionService.getLanguageSpecificConditions(currentLocale);
        return lscList;
    }
	
	@RequestMapping(value="/changeStatus",method=RequestMethod.GET)
	public String changeStatus(@RequestParam ("dataid") long id,@RequestParam ("status") String status,ModelMap model,HttpServletRequest request,HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		boolean flag=false;
		if(id>0) {	
			if(status.equals("Inactive")) {
				ConditionParameter cp=conditionService.getConditionParameterWithId(id);
				cp.setActiveAndInactive("Active");
				flag=conditionService.updateStatus(cp);
				if(flag) {
					String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
					redirectAttributes.addFlashAttribute("success", success_Msg);

					String cnsuccessMsg =messageSource.getMessage("conditionparameter.label.cnactiveMsg", null,currentLocale);
					redirectAttributes.addFlashAttribute("pageMessage", cnsuccessMsg);
				}else {
					String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
					redirectAttributes.addFlashAttribute("error", error_Msg);

					String cnFailMsg =messageSource.getMessage("conditionparameter.label.cnactivefailMsg", null,currentLocale);
					redirectAttributes.addFlashAttribute("pageError", cnFailMsg);
				}
				
			}else {
				ConditionParameter cp=conditionService.getConditionParameterWithId(id);
				cp.setActiveAndInactive("Inactive");
				flag=conditionService.updateStatus(cp);
                if(flag) {
                	String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
        			redirectAttributes.addFlashAttribute("success", success_Msg);

        			String cnsuccessMsg =messageSource.getMessage("conditionparameter.label.cninactiveMsg", null,currentLocale);
        			redirectAttributes.addFlashAttribute("pageMessage", cnsuccessMsg);
				}else {
					String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
					redirectAttributes.addFlashAttribute("error", error_Msg);

					String cnFailMsg =messageSource.getMessage("conditionparameter.label.cninactivefailMsg", null,currentLocale);
					redirectAttributes.addFlashAttribute("pageError", cnFailMsg);
				}
				
				
			}
		
		}
		return "redirect:/condition/conditionForm";
		
	}
	
	@RequestMapping(value="/conditionNameCheckExitOrNot/{value}",method=RequestMethod.GET)
	public @ResponseBody String conditionNameCheckExitOrNot(ModelMap model,@PathVariable("value") String value,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) {
		String checkValue="No";
		ConditionParameter pojo=null;
		pojo=conditionService.conditionNameCheckExitOrNot(value);
		if(pojo==null) {
			checkValue="Yes";
		}
		return checkValue;
		
	}
}
