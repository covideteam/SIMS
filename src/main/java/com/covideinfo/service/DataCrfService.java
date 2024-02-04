package com.covideinfo.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.covideinfo.dto.DataCrfDetailsPageDto;
import com.covideinfo.dto.DataCrfDtoDetails;

public interface DataCrfService {

	DataCrfDetailsPageDto getDataCrfDetailsPageDtoDetails();

	DataCrfDetailsPageDto getStudyCrfDataDetails(Long studyId, Long languageId);

	DataCrfDtoDetails getStudyCrfDataDtoDetails(Long studyId, Long langId, Long userId);

	/*List<String> generateCoverPagesForDataCrf(HttpServletRequest request, Locale currentLocale,
			MessageSource messageSource, Long langId, DataCrfDtoDetails dcdDto, String dateStr);*/

	List<String> generateDataCrf(Long periodId, Long subjectId, Long activityId, DataCrfDtoDetails dcdDto,
			MessageSource messageSource, Long langId, String dateStr, Locale currentLocale, HttpServletRequest request, HttpServletResponse response, String dateStrWithTime, String reportType, List<Long> volIds);

}
