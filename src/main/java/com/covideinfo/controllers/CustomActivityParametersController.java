package com.covideinfo.controllers;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.ConditionParameter;
import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.service.CustomActivityParameterService;

@Controller
@RequestMapping("/customActParams")
public class CustomActivityParametersController{
	
	@Autowired
	CustomActivityParameterService cusActParmService;
	
	@Autowired  
	MessageSource messageSource;
	
	
	@RequestMapping(value="/createCustomActivityParameters", method=RequestMethod.GET)
	public String createCustomActivityParameters(ModelMap model, RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		List<GlobalActivity> actList = cusActParmService.getGlobalActivitiesList();
		List<CustomActivityParameters> ccList = cusActParmService.getCustomActivityParametersList();
		model.addAttribute("actList", actList);
		model.addAttribute("ccList", ccList);
		return "customActParamPage";
	}
	@RequestMapping(value="/saveCustomActivityParamData", method=RequestMethod.POST)
	public String saveCustomActivityParamData(@RequestParam("activity")Long activityId,@RequestParam("parameterValue")String parameterValue, @RequestParam("actparm")Long parameterId,
			HttpServletRequest request, RedirectAttributes redirectAttributes/*@RequestParam("parmVal")String paramVal*/) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String userName = (String) request.getSession().getAttribute("longinUserName");
		String result = cusActParmService.saveCustomActivityParameterRecord(activityId, parameterId, userName,parameterValue);
		if(result.equals("success")) {
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			String gvsuccessMsg =messageSource.getMessage("lable.cusActParmSuccess", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
		}else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gvFailMsg =messageSource.getMessage("lable.cusActParmFail", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
		}
		return "redirect:/customActParams/createCustomActivityParameters";
		
	}
	@RequestMapping(value="/checkActivityAndParameterExistingOrNot/{id}",method=RequestMethod.GET)
	public @ResponseBody String conditionNameCheckExitOrNot(ModelMap model,@PathVariable("id") Long id,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) {
		String checkValue="No";
		GlobalParameter paramPojo=cusActParmService.getGlobalParameterWithId(id);
		CustomActivityParameters pojo=null;
		pojo=cusActParmService.checkActivityAndParameterExistingOrNot(paramPojo.getActivity(),paramPojo);
		if(pojo==null) {
			checkValue="Yes";
		}
		return checkValue;
		
	}
}
