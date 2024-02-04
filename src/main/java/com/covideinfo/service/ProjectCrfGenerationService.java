package com.covideinfo.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;

import com.covideinfo.bo.StudyCreationBo;
import com.covideinfo.dto.BlankPdfDto;
import com.covideinfo.model.ApplicationConfiguration;
import com.covideinfo.model.Projects;

public interface ProjectCrfGenerationService {

	List<Projects> getProjectsRecords();

	List<String> generateBlankCrf(HttpServletRequest request, Locale currentLocale,
			StudyCreationBo studyCreationBo, int periodNo, MessageSource messageSource, Long noOfPeriods, BlankPdfDto bpDto, String dateStr, Long langId, String dateStrWithTime)throws Exception;

	BlankPdfDto getBlankPdfDtoDetails(Long projectNo, String userName);

	Map<String, String> getProjectTitleFromPojectDetails(Long projectNo);

	ApplicationConfiguration getApplicationConfigurationRecord(String string);

	List<String> generateCoverPages(HttpServletRequest request, Locale currentLocale, MessageSource messageSource,
			Long projectNo, Long langId, BlankPdfDto bpDto, String dateStr, String dateStrWithTime);

}
