package com.covideinfo.controllers;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.covideinfo.dto.GlobalParameterDto;
import com.covideinfo.dto.ParameterDto;
import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.service.ParameterService;

@Controller
@RequestMapping("/parameters")
public class GlobalParametersController {
	
	@Autowired  
	MessageSource messageSource;
	
	@Autowired
	ParameterService pareameterService;
	
	@RequestMapping(value="/parmetersPage", method=RequestMethod.GET)
	public String createGlobalParameters(ModelMap model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		Locale currentLocale = LocaleContextHolder.getLocale();
		Long langId = (Long) request.getSession().getAttribute("languageId");
		GlobalParameterDto gpDto = pareameterService.getPrameterDetails(messageSource, currentLocale,langId);
		model.addAttribute("langId", langId);
		model.addAttribute("gpDto", gpDto);
		model.addAttribute("phase", gpDto.getPhaseList());
		model.addAttribute("inLagList", gpDto.getInlList());
		return "parametersPage";
	}
	
	@RequestMapping(value="/saveGlobalParameter", method=RequestMethod.POST)
	public String saveGlobalParameter(ModelMap model, HttpServletRequest request, @RequestParam("parameterName")String name,
			@RequestParam("pvalue")List<String> pvalList, @RequestParam("lanId")List<Long> lagList, @RequestParam("groupName")Long groupId, 
			@RequestParam("controlType")String controlCode,@RequestParam("controlValues")List<Long> valIds, @RequestParam("order")int order, 
			@RequestParam("bindActVal")String bindVal, @RequestParam("activityName")Long activityId,
			@RequestParam("controlName") String controlName, @RequestParam("unitsId")Long unitsId,
			@RequestParam("phase")List<String> phaseList,@RequestParam("paramMandatoryVal")String  paramMandatoryVal,RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("longinUserName");
		Locale currentLocale = LocaleContextHolder.getLocale();
		String result = pareameterService.saveGlobalParameterDetails(name, pvalList, lagList, groupId, controlCode, valIds, 
				bindVal, activityId, userName, order,controlName, unitsId, phaseList, paramMandatoryVal);
		if(result.equals("success")) {
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);


			String gpsuccessMsg =messageSource.getMessage("label.gpSuccessMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", gpsuccessMsg);
		}else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);


			String gpFailMsg =messageSource.getMessage("label.gpFailMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gpFailMsg);
		}
		return "redirect:/parameters/parmetersPage";
	}

	@RequestMapping(value="/getParameters", method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<ParameterDto> getActivitiesParameters(@RequestParam Long[] aids,HttpServletRequest request){
		Long langId = (Long) request.getSession().getAttribute("languageId");
		return pareameterService.getActivityDefaultParameters(aids, langId);
	}
	
	@RequestMapping(value="/getParameters/{activityId}", method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<ParameterDto> getParameters(@PathVariable("activityId") Long activityId,HttpServletRequest request){
		Long langId = (Long) request.getSession().getAttribute("languageId");
		return pareameterService.getActivityDefaultParameters(activityId, langId);
	}
	
	
	@RequestMapping(value="/parameterNameCheckExitOrNot/{value}",method=RequestMethod.GET)
	public @ResponseBody String parameterNameCheckExitOrNot(ModelMap model,@PathVariable("value") String value,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) {
		String checkValue="No";
		GlobalParameter paramPojo=null;
		paramPojo=pareameterService.parameterNameCheckExitOrNot(value);
		if(paramPojo==null) {
			checkValue="Yes";
		}
		return checkValue;
		
	}
}

