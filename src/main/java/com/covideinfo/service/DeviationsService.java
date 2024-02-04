package com.covideinfo.service;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;

import com.covideinfo.dto.DeviationsDto;
import com.covideinfo.model.StudyMaster;

public interface DeviationsService {

	List<StudyMaster> getStudyMastersList();

	DeviationsDto getDeviationRecords(Long projectId, Long roleId, String type);

	String saveDeviationRecords(Long deviationId, Long roleId, String type, String comments, Long userId, MessageSource messageSource, Locale currentLocale, String submitType);

	

}
