package com.covideinfo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.covideinfo.service.InternationalizationService;

@Controller
@RequestMapping("/inlag")
public class InternationalizationController {
	
	
	@Autowired
	InternationalizationService inalgService;
	
	@Autowired  
	MessageSource messageSource;
	
	@RequestMapping(value="/changeLocale/{langCode}/{locationUrl}", method=RequestMethod.GET, produces= {MediaType.ALL_VALUE})
	public @ResponseBody String changeLocale(ModelMap model, @PathVariable("langCode")String langCode, 
			@PathVariable("locationUrl")String locationUrl, HttpServletRequest request, HttpServletResponse response) {
		return inalgService.changeLocale(locationUrl, langCode, request, response, messageSource);
	}

}
