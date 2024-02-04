package com.covideinfo.dao;

import java.util.List;
import java.util.Locale;

import com.covideinfo.dto.ActvitityDetailsDto;
import com.covideinfo.dto.GlobalActivityDto;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;

public interface GlobalActivityDao {
	List<GlobalActivity> getGlobalActivity(String code);
	
	List<GlobalActivity> getGlobalActivities(List<String> codes);
	
	List<GlobalActivity> getStudyDesignActivities();
	
	GlobalActivityDto getGlobalActivityDetails(Long langId);

	List<InternationalizaionLanguages> getInternationalizaionLanguages();

	String saveGlobalValesRecords(GlobalActivity ga, List<LanguageSpecificGlobalActivityDetails> lsadList, Long groupId);

	GlobalActivity getGlobalActivityRecord(String actName);

	GlobalActivity getGlobalActivityByCode(String activityCode);

	List<LanguageSpecificGlobalActivityDetails> getOtherActivities(Long langId);
	
	List<LanguageSpecificGlobalActivityDetails> getDefaultActivities(Long langId);
}
