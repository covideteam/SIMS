package com.covideinfo.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.FromStaticData;
import com.covideinfo.service.StudyDesignService;
import com.covideinfo.service.UserService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/studydesignStaticData")
public class StaticDataForFromFieldsContoller {
	@Autowired
	StudyDesignService studyDesignService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/fieldValues/{fieldName}")
	public List<FromStaticData> fieldValues(HttpServletRequest request, ModelMap model,
			RedirectAttributes redirectAttributes, @PathVariable("fieldName") String fieldName) throws IOException {
		List<FromStaticData> keyValues = userService.fromStaticDataWithFieldName(fieldName);
		return keyValues;
	}
	
	@RequestMapping(value = "/studyStaticFieldValues")
	public @ResponseBody String studyStaticFieldValues(HttpServletRequest request, ModelMap model,
			RedirectAttributes redirectAttributes) throws IOException {
		List<FromStaticData> keyValues = userService.studyStaticFieldValues();
		return new Gson().toJson(keyValues);
	}
}
