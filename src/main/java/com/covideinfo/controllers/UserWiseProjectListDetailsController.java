package com.covideinfo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.covideinfo.dao.impl.UserWiseProjectsImpl;
import com.covideinfo.dto.StudyDetailsDto;
import com.covideinfo.service.impl.UserWiseProjectsServiceImpl;

@Controller
@RequestMapping("/userProjects")
public class UserWiseProjectListDetailsController {
	
	@Autowired
	UserWiseProjectsImpl uwspldDaoImpl;
	
	@Autowired
	UserWiseProjectsServiceImpl userWisePSImpl;
	
	
	@RequestMapping(value="/getUserProjects", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<StudyDetailsDto> getUserProjects(HttpServletRequest request) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		Long role = (Long) request.getSession().getAttribute("roleId");
		List<StudyDetailsDto> pdDto = userWisePSImpl.getUserProjectsDetails(userId, languageId, role);
		return pdDto;
	}

}
