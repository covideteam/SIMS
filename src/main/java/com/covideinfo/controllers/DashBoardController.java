package com.covideinfo.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.service.UserService;

@Controller
@RequestMapping("/dashboard")
@PropertySource(value = { "classpath:application.properties" })
public class DashBoardController {
	
	@Autowired
	UserService userService;

	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String dashboardHomePage(HttpServletRequest request, RedirectAttributes redirectAttributes, ModelMap model) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		return "redirect:/administration/";
		
	}
	
	
	
	
	
}
