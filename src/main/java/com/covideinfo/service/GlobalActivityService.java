package com.covideinfo.service;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;

import com.covideinfo.dto.ActvitityDetailsDto;
import com.covideinfo.dto.GlobalActivityDto;
import com.covideinfo.model.GlobalActivity;

public interface GlobalActivityService {

	GlobalActivityDto getGlobalActivityDetails(MessageSource messageSource, Locale currentLocale, Long langId);
	
	List<GlobalActivity> getGlobalActivity(String code);
	 
	List<GlobalActivity> getGlobalActivities(List<String> codes);
	
	List<GlobalActivity> getStudyDesignActivities();

	String saveGlobalActivityDetails(String name, List<String> pvalList, List<Long> lagList, String userName, String allowMultiple, 
			List<Long> roleIdsList, String userRole, Long groupId, String actCode, int orderNo, String headdingVal, String showInPDFVal, String subjectsInput, String eligibleForNextProcessVal);

	GlobalActivity getGlobalActivityRecord(String actName);

	List<ActvitityDetailsDto> getOtherActivities(Long langId);

	List<ActvitityDetailsDto> getDefaultActivities(Long langId);

}
