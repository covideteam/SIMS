package com.covideinfo.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.service.StudyService;
@Component
@RequestMapping("/study")
@PropertySource(value = { "classpath:application.properties" })
public class BuildEFormStudy{
	@Autowired
	private Environment environment;
	@Autowired
	StudyService studyService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String adminHomePage(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		try {
			String currentStudy = request.getSession().getAttribute("projectNo").toString();
			if (currentStudy != null && !currentStudy.equals(""))
				return "studyDashBoard.tiles";
			else {
				redirectAttributes.addFlashAttribute("pageWarning", "Please select study");
				return "redirect:/dashboard/userWiseActiveStudies";
			}
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			redirectAttributes.addFlashAttribute("pageWarning", "Please select study");
			return "redirect:/dashboard/userWiseActiveStudies";
		}
	}
}
