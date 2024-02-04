package com.covideinfo.controllers;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

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

import com.covideinfo.dto.ParameterDetailDto;
import com.covideinfo.dto.RulesDetails;
import com.covideinfo.dto.RulesDetailsDto;
import com.covideinfo.dto.RulesGlobalParameterDto;
import com.covideinfo.dto.ValuesDto;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.service.RulesService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Controller
@RequestMapping("/rules")
public class ActivityRulesController {
	
	
	@Autowired  
	MessageSource messageSource;
	
	@Autowired
	RulesService rulesService;
	
	
	@RequestMapping(value="/rulesPage", method=RequestMethod.GET)
	public String rulesPage(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		Long langId = (Long) request.getSession().getAttribute("languageId");
		RulesDetailsDto rdDto = rulesService.getRulesDetailsDtoDetails(langId);
		model.addAttribute("actList", rdDto.getActList());
		model.addAttribute("inLagList", rdDto.getInLagList());
		return "activityRules";
	}

	
	@RequestMapping(value="/getActivityParameters/{activityId}", method=RequestMethod.GET)
	public @ResponseBody String getActivityParameters(ModelMap model, @PathVariable("activityId")Long activityId,HttpServletRequest request) {
		Long langId = (Long) request.getSession().getAttribute("languageId");
		List<RulesGlobalParameterDto> gpList = rulesService.getActivityParametersList(activityId, langId);
		String jsonStr = "";
		try {
			ObjectMapper Obj = new ObjectMapper();
			Obj.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			Obj.setSerializationInclusion(Include.NON_NULL);
			jsonStr =  Obj.writeValueAsString(gpList);
			model.addAttribute("jsonStr", jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return jsonStr;
	}
	
	@RequestMapping(value="/getParametersValues/{parameterId}/{controlId}", method=RequestMethod.GET)
	public @ResponseBody String getParametersValues(ModelMap model, @PathVariable("parameterId")Long parameterId, 
			@PathVariable("controlId")Long controlId) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		List<ValuesDto> valDto = rulesService.getValuesDetails(parameterId, currentLocale, controlId);
		String jsonStr = "";
		try {
			ObjectMapper Obj = new ObjectMapper();
			Obj.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			Obj.setSerializationInclusion(Include.NON_NULL);
			jsonStr =  Obj.writeValueAsString(valDto);
			model.addAttribute("jsonStr", jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return jsonStr;
	}
	
	@RequestMapping(value="/getdestActivitiesList/{acivityId}", method=RequestMethod.GET)
	public @ResponseBody String getdestActivitiesList(ModelMap model, @PathVariable("acivityId")Long acivityId) {
//		Locale currentLocale = LocaleContextHolder.getLocale();
		List<GlobalActivity> gactList = rulesService.getGlobalActivityList(acivityId);
		String jsonStr = "";
		try {
			ObjectMapper Obj = new ObjectMapper();
			Obj.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			Obj.setSerializationInclusion(Include.NON_NULL);
			jsonStr =  Obj.writeValueAsString(gactList);
			model.addAttribute("jsonStr", jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return jsonStr;
	}
	//ActivityRules
	@RequestMapping(value="/saveActivityRule", method=RequestMethod.POST)
	public String saveActivityRule(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam("sourceActivity")Long sourceActivity, @RequestParam("ruleType")String ruleType, 
			@RequestParam("validationCon")String validationCon, @RequestParam("sourceRuleParam")Long sourcePramId,
			@RequestParam("conditionVal")String conditionVal, @RequestParam("destActivity")Long destActivity, 
			@RequestParam("applyRuleFor")String applyRuleFor,@RequestParam("depconditionVal")String depconditionVal, 
			@RequestParam("checkedLspId")Long checkedLspId,@RequestParam("destRuleParam")Long destRuleParam, 
			@RequestParam("paramAction")String paramAction,@RequestParam("multiParam")List<Long> multiParam, 
			@RequestParam("valueOne")String  valueOne,@RequestParam("valueTwo")String  valueTwo,
			@RequestParam("ruleMsg")List<String> ruleMsgList, @RequestParam("lanId")List<Long> lagList, 
			@RequestParam("depParameter")Long depParameter,@RequestParam("checkedLspId2")Long checkedLspId2,
			@RequestParam("depcheckedLspId")Long depcheckedLspId,@RequestParam("depvalueOne")String depvalueOne,
			@RequestParam("deparamconditionVal")String deparamconditionVal,@RequestParam("tbCondition")String tbCondition,
			@RequestParam("sbglobalId")Long selectboxVal,@RequestParam ("depselectboxVal") Long depselectboxVal,
			@RequestParam("depActivities")List<Long> destMultList, @RequestParam("depcheckedLspId2")Long depcheckedLspId2,
			@RequestParam("selectedActivity")Long selectedActivity) {
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		String userName = (String) request.getSession().getAttribute("longinUserName");
		String result = rulesService.saveActivityRules(sourceActivity, ruleType, validationCon, sourcePramId,conditionVal, 
				destActivity, applyRuleFor, depconditionVal, destRuleParam, paramAction, multiParam, valueOne, valueTwo, ruleMsgList, lagList, 
				checkedLspId, userName,depParameter, checkedLspId2,selectboxVal, depcheckedLspId, depvalueOne, deparamconditionVal,
				tbCondition, depselectboxVal, destMultList, depcheckedLspId2);
		if(result.equals("success")) {
			String rlsuccessMsg = messageSource.getMessage("label.rlSuccessMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", rlsuccessMsg);
			redirectAttributes.addFlashAttribute("selectedActivity", selectedActivity);
			
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);
		}else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);
			
			String rlFailMsg = messageSource.getMessage("label.rlFailMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", rlFailMsg);
			redirectAttributes.addFlashAttribute("selectedActivity", selectedActivity);
		}
		return "redirect:/rules/rulesPage";
	}
	@RequestMapping(value="/getActivityRuleList/{activityId}", method=RequestMethod.GET)
	public String getActivityRuleList(ModelMap model, @PathVariable("activityId")Long activityId, HttpServletRequest request) {
		Long langId = (Long) request.getSession().getAttribute("languageId");
		RulesDetails rules = rulesService.getRulesDetails(activityId, langId);
		model.addAttribute("rules", rules);
		return "pages/rules/rulesViewPage";	
	}
	
	
	@RequestMapping(value="/getActivityParameterType/{parameterId}", method=RequestMethod.GET, produces= {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ParameterDetailDto getActivityParameterType(ModelMap model, @PathVariable("parameterId")Long parameterId, HttpServletRequest request) {
		Long langId = (Long) request.getSession().getAttribute("languageId");
		return rulesService.getParameterType(parameterId, langId);
	}
}
