package com.covideinfo.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.eclipse.jdt.internal.compiler.apt.model.ModuleElementImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
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

import com.covideinfo.enums.StaticData;
import com.covideinfo.model.Deepfreezer;
import com.covideinfo.model.Instrument;
import com.covideinfo.model.InstrumentModel;
import com.covideinfo.model.InstrumentType;
import com.covideinfo.service.InstrumentService;
import com.fasterxml.jackson.databind.Module;

@Controller
@RequestMapping("/instrument")
@PropertySource(value = { "classpath:application.properties" })
public class InstrumentController {

	@Autowired
	InstrumentService instrumentService;
	@Autowired
	MessageSource messageSource;

	// Instrument Creation Form
	@RequestMapping(value = "/instrumentForm", method = RequestMethod.GET)
	public String instrumentForm(ModelMap model) {
		List<InstrumentModel> modelList = instrumentService.getInstrumentModelList();
		List<InstrumentType> typeList = instrumentService.getInstrumentTypeList();
		Instrument instrument = new Instrument();
		model.addAttribute("instrument", instrument);
		model.addAttribute("modelList", modelList);
		model.addAttribute("typeList", typeList);
		return "instrumentFormPage";
	}

	@RequestMapping(value = "/saveInstrumentForm", method = RequestMethod.POST)
	public String saveInstrumentForm(@Valid Instrument instrume, @RequestParam("manDate") String manDate,
			@RequestParam("calDate") String calDate, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws ParseException {
		String userName = (String) request.getSession().getAttribute("longinUserName");
		Locale currentLocale = LocaleContextHolder.getLocale();
		boolean flag = false;
		if (instrume != null) {
			instrume.setCreatedBy(userName);
			instrume.setCreatedOn(new Date());
			flag = instrumentService.saveInstrumentForm(instrume, manDate, calDate);
		}

		if (flag) {
			String success_Msg = messageSource.getMessage("label.success", null, currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			String gvsuccessMsg = messageSource.getMessage("label.instrument.successmsg", null, currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
		} else {
			String error_Msg = messageSource.getMessage("label.faild", null, currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gvFailMsg = messageSource.getMessage("label.instrument.failedmsg", null, currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
		}
		return "redirect:/instrument/instrumentForm";
	}

	@RequestMapping(value = "/instrumentNoExitOrNot/{value}", method = RequestMethod.GET)
	public @ResponseBody String instrumentNoExitOrNot(@PathVariable("value") String value, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, RedirectAttributes redirectAttributes) {
		String flag = "No";
		if (value != "") {
			Instrument pojo = instrumentService.getInstrumentNoCheckExitOrNot(value);
			if (pojo != null) {
				flag = "Yes";
			}
		}
		return flag;

	}

	@RequestMapping(value = "/deepfreezerForm", method = RequestMethod.GET)
	public String deepfreezerForm(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		String userName = (String) request.getSession().getAttribute("longinUserName");
		Deepfreezer df = new Deepfreezer();
		String deepf = StaticData.DEEPFREEZER.toString();
		List<Long> ids = instrumentService.getInstrumentTypeForDeepfrezer(deepf);
		List<Instrument> instru = instrumentService.getInstrumentForDeepFrsszerList(ids);
		model.addAttribute("instru", instru);
		model.addAttribute("df", df);
		return "deepfreezerForm";

	}

	@RequestMapping(value = "/saveDeepfreser", method = RequestMethod.POST)
	public String saveDeepfreser(@RequestParam("instrument") Long instrument,
			@RequestParam("rackCount") String rackCount,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes)
			throws ParseException {
		String userName = (String) request.getSession().getAttribute("longinUserName");
		Locale currentLocale = LocaleContextHolder.getLocale();
		boolean flag = false;
		if (instrument > 0 && rackCount != "") {
			flag = instrumentService.saveDeepfreserForm(instrument, null, rackCount, userName);
		}

		if (flag) {
			String success_Msg = messageSource.getMessage("label.success", null, currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			String gvsuccessMsg = messageSource.getMessage("label.deepfreezer.successmsg", null, currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
		} else {
			String error_Msg = messageSource.getMessage("label.faild", null, currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gvFailMsg = messageSource.getMessage("label.deepfreezer.failedmsg", null, currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
		}
		return "redirect:/instrument/deepfreezerForm";
	}

	@RequestMapping(value = "/saveDeepfreserShelfs", method = RequestMethod.POST)
	public String saveDeepfreserShelfs(@RequestParam("instrument") Long instrument,
			@RequestParam("rackCount") String rackCount, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws ParseException {
		String userName = (String) request.getSession().getAttribute("longinUserName");
		Locale currentLocale = LocaleContextHolder.getLocale();
		boolean flag = false;
		if (instrument > 0 && rackCount != "") {
			flag = instrumentService.saveDeepfreserSeflfForm(instrument, rackCount, userName);
		}

		if (flag) {
			String success_Msg = messageSource.getMessage("label.success", null, currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			String gvsuccessMsg = messageSource.getMessage("label.deepfreezershelf.successmsg", null, currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", gvsuccessMsg);
		} else {
			String error_Msg = messageSource.getMessage("label.faild", null, currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String gvFailMsg = messageSource.getMessage("label.deepfreezershelf.failedmsg", null, currentLocale);
			redirectAttributes.addFlashAttribute("pageError", gvFailMsg);
		}
		return "redirect:/instrument/deepfreezerForm";
	}

	@RequestMapping(value = "/instrumentSelef/{insturmentId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Deepfreezer> instrumentSelef(@PathVariable("insturmentId") Long insturmentId,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		Locale currentLocale = LocaleContextHolder.getLocale();
		List<Deepfreezer> shelfs = instrumentService.instrumentShelfs(insturmentId);
		return shelfs;
	}
}
