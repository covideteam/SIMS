package com.covideinfo.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.covideinfo.dto.MissedTimePointsDto;

public interface MissedTpsService {

	MissedTimePointsDto getMissedTimePointsDtoDetails();

	MissedTimePointsDto generateMissedTimepointsReport(Long studyId, Locale currentLocale, String dateStr);

	String generateMealsMenuPdf(Long studyId, Long periodId, String activityCode, HttpServletRequest request,
			HttpServletResponse response, Locale currentLocale, Long langId, String dateStr, String dateStrWithTime,
			MessageSource messageSource, Long userId);

}
