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

import com.covideinfo.dto.ActvitityDetailsDto;
import com.covideinfo.dto.GlobalActivityDto;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.service.GlobalActivityService;

@Controller
@RequestMapping("/globalAtivity")
public class GlobalActivityController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	GlobalActivityService globalActivityService;

	@RequestMapping(value = "/activityPage", method = RequestMethod.GET)
	public String createActivity(ModelMap model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		String userRole = (String) request.getSession().getAttribute("userRole");
		Long langId = (Long) request.getSession().getAttribute("languageId");
		Locale currentLocale = LocaleContextHolder.getLocale();
		GlobalActivityDto gaDto = globalActivityService.getGlobalActivityDetails(messageSource, currentLocale, langId);
		model.addAttribute("gaDto", gaDto);
		if(gaDto != null)
			model.addAttribute("inLagList", gaDto.getInlList());
		model.addAttribute("userRole", userRole);
		model.addAttribute("langId", langId);
		return "activityPage";
	}

	@RequestMapping(value = "/getGlobalActivity/{code}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<GlobalActivity> getGlobalActivity(@PathVariable("code") String code) {
		return globalActivityService.getGlobalActivity(code);
	}

	@RequestMapping(value = "/getStudyDesignActivities", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<GlobalActivity> getStudyDesignActivities() {
		return globalActivityService.getStudyDesignActivities();
	}

	@RequestMapping(value = "/checkActivityDuplication/{actName}", method = RequestMethod.GET)
	public String checkActivityDuplication(@PathVariable("actName") String actName, ModelMap model) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String result = "";
		GlobalActivity ga = globalActivityService.getGlobalActivityRecord(actName);
		if (ga != null)
			result = messageSource.getMessage("label.gaAlredyExists", null, currentLocale);
		model.addAttribute("result", result);
		return "pages/administration/global/gaResultPage";
	}

	@RequestMapping(value = "/saveGlobalActivity", method = RequestMethod.POST)
	public String saveGlobalActivity(ModelMap model, HttpServletRequest request, @RequestParam("valueName") String name,
			@RequestParam("allowMultiple") String allowMultiple, @RequestParam("pvalue") List<String> pvalList,
			@RequestParam("lanId") List<Long> lagList, @RequestParam("roleName") List<Long> roleIdsList,
			@RequestParam("activitycode") String actCode, @RequestParam("actGroupval") Long groupId,
			@RequestParam("orderNo") int orderNo, @RequestParam("headdingVal") String headdingVal,
			@RequestParam("showInPDFVal") String showInPDFVal,
			@RequestParam("subjectsInputVal") String subjectsInputVal,
			@RequestParam("eligibleForNextProcessVal") String eligibleForNextProcessVal, RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("longinUserName");
		Locale currentLocale = LocaleContextHolder.getLocale();
		String userRole = (String) request.getSession().getAttribute("userRole");
		String result = globalActivityService.saveGlobalActivityDetails(name, pvalList, lagList, userName,
				allowMultiple, roleIdsList, userRole, groupId, actCode, orderNo, headdingVal, showInPDFVal,
				subjectsInputVal,eligibleForNextProcessVal);
		if (result.equals("success")) {
			String success_Msg = messageSource.getMessage("label.success", null, currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			String gasuccessMsg = messageSource.getMessage("label.gaSuccessMsg", null, currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", gasuccessMsg);
		} else {
			String error_Msg = messageSource.getMessage("label.faild", null, currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gaFailMsg = messageSource.getMessage("label.gaFailMsg", null, currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gaFailMsg);
		}
		return "redirect:/globalAtivity/activityPage";
	}

	@RequestMapping(value = "/otherActivities", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<ActvitityDetailsDto> otherActivities(HttpServletRequest request) {
		Long langId = (Long) request.getSession().getAttribute("languageId");
		return globalActivityService.getOtherActivities(langId);
	}

	@RequestMapping(value = "/defalutActivities", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<ActvitityDetailsDto> defalutActivities(HttpServletRequest request) {
		Long langId = (Long) request.getSession().getAttribute("languageId");
		return globalActivityService.getDefaultActivities(langId);
	}
}
