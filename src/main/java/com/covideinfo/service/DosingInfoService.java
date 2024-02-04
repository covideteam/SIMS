package com.covideinfo.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.covideinfo.dto.DosingInfoDetialsDto;
import com.covideinfo.dto.DosingInfoSavingDetailsDto;

public interface DosingInfoService {

	DosingInfoDetialsDto getDosingInfoDetialsDto(long projectId);

	String saveDosingInfDetails(DosingInfoSavingDetailsDto dinfDto, String userName);

	void generatePdfforDosingData(MessageSource messageSource, Long langId, String dateStr,
			Locale currentLocale, HttpServletRequest request, HttpServletResponse response, Long projectId, Long userId);

}
