package com.covideinfo.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.covideinfo.dto.MealMenuReportsDto;

public interface MealMenuReportService {

	MealMenuReportsDto getMealMenuReportDetails();

	MealMenuReportsDto getStduyMealReportDetails(Long studyId);

	String generateMealsMenuPdf(Long studyId, List<Long> dietIdsList, List<String> melsReportInfo,
			HttpServletRequest request, HttpServletResponse response, Locale currentLocale, Long langId, String dateStr,
			String dateStrWithTime, MessageSource messageSource, Long userId);

}
