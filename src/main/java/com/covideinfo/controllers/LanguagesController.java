package com.covideinfo.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.service.impl.LanguageService;

@Controller
@RequestMapping("/languages")
public class LanguagesController {
	@Autowired
	LanguageService languageService;

	@RequestMapping(value="/getAll", method=RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<InternationalizaionLanguages> getAll() throws IOException {
		return languageService.getLanguages();
	}
}
