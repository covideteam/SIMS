package com.covideinfo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

public interface InternationalizationService {

	String changeLocale(String locationUrl, String langCode, HttpServletRequest request, HttpServletResponse response, MessageSource messageSource);

}
